/*
 * Copyright 2010-2024 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.backend.konan.cgen

import org.jetbrains.kotlin.backend.common.lower.createIrBuilder
import org.jetbrains.kotlin.backend.common.lower.irBlock
import org.jetbrains.kotlin.backend.common.lower.irCatch
import org.jetbrains.kotlin.backend.common.lower.irThrow
import org.jetbrains.kotlin.backend.konan.ir.KonanSymbols
import org.jetbrains.kotlin.backend.konan.ir.buildSimpleAnnotation
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities
import org.jetbrains.kotlin.descriptors.Modality
import org.jetbrains.kotlin.ir.IrStatement
import org.jetbrains.kotlin.ir.builders.*
import org.jetbrains.kotlin.ir.builders.declarations.buildVariable
import org.jetbrains.kotlin.ir.declarations.*
import org.jetbrains.kotlin.ir.expressions.IrExpression
import org.jetbrains.kotlin.ir.expressions.IrMemberAccessExpression
import org.jetbrains.kotlin.ir.expressions.impl.IrTryImpl
import org.jetbrains.kotlin.ir.symbols.impl.IrSimpleFunctionSymbolImpl
import org.jetbrains.kotlin.ir.symbols.impl.IrValueParameterSymbolImpl
import org.jetbrains.kotlin.ir.types.IrType
import org.jetbrains.kotlin.ir.util.constructors
import org.jetbrains.kotlin.ir.util.simpleFunctions
import org.jetbrains.kotlin.konan.ForeignExceptionMode
import org.jetbrains.kotlin.name.Name

internal class CFunctionBuilder {
    private val parameters = mutableListOf<CVariable>()
    private lateinit var returnType: CType

    var variadic: Boolean = false

    fun setReturnType(type: CType) {
        require(!::returnType.isInitialized)
        returnType = type
    }

    fun addParameter(type: CType): CVariable {
        val result = CVariable(type, "p${counter++}")
        parameters += result
        return result
    }

    val numberOfParameters: Int get() = parameters.size

    private var counter = 1

    fun getType(): CType = CTypes.function(returnType, parameters.map { it.type }, variadic)

    fun buildSignature(name: String, language: String): String =
        (if (language == "C++") "extern \"C\" const " else "") +
        returnType.render(buildString {
            append(name)
            append('(')
            parameters.joinTo(this)
            if (parameters.isEmpty()) {
                if (!variadic) append("void")
            } else {
                if (variadic) append(", ...")
            }
            append(')')
        })

}

internal class KotlinBridgeBuilder(
        startOffset: Int,
        endOffset: Int,
        cName: String,
        stubs: KotlinStubs,
        isExternal: Boolean,
        foreignExceptionMode: ForeignExceptionMode.Mode,
        origin: IrDeclarationOrigin
) {
    private var counter = 0
    private val bridge: IrSimpleFunction = createKotlinBridge(startOffset, endOffset, cName, stubs, isExternal, foreignExceptionMode, origin)
    val irBuilder: IrBuilderWithScope = stubs.irBuiltIns.createIrBuilder(bridge.symbol).at(startOffset, endOffset)

    fun addParameter(type: IrType): IrValueParameter {
        val index = counter++

        return irBuilder.context.irFactory.createValueParameter(
                startOffset = bridge.startOffset,
                endOffset = bridge.endOffset,
                origin = bridge.origin,
                kind = IrParameterKind.Regular,
                name = Name.identifier("p$index"),
                type = type,
                isAssignable = false,
                symbol = IrValueParameterSymbolImpl(),
                varargElementType = null,
                isCrossinline = false,
                isNoinline = false,
                isHidden = false,
        ).apply {
            parent = bridge
            bridge.parameters += listOf(this)
        }
    }

    fun setReturnType(type: IrType) {
        bridge.returnType = type
    }

    fun getBridge(): IrSimpleFunction = bridge
}

private fun createKotlinBridge(
        startOffset: Int,
        endOffset: Int,
        cBridgeName: String,
        stubs: KotlinStubs,
        isExternal: Boolean,
        foreignExceptionMode: ForeignExceptionMode.Mode,
        origin: IrDeclarationOrigin
): IrSimpleFunction {
    val bridge = stubs.irBuiltIns.irFactory.createSimpleFunction(
            startOffset,
            endOffset,
            origin,
            Name.identifier(cBridgeName),
            DescriptorVisibilities.PRIVATE,
            isInline = false,
            isExpect = false,
            null,
            Modality.FINAL,
            IrSimpleFunctionSymbolImpl(),
            isTailrec = false,
            isSuspend = false,
            isOperator = false,
            isInfix = false,
            isExternal = isExternal,
            isFakeOverride = false,
    )
    if (isExternal) {
        bridge.annotations += buildSimpleAnnotation(stubs.irBuiltIns, startOffset, endOffset,
                stubs.symbols.filterExceptions.owner,
                foreignExceptionMode.value)
    }
    return bridge
}

internal class KotlinCBridgeBuilder(
        startOffset: Int,
        endOffset: Int,
        cName: String,
        val stubs: KotlinStubs,
        isKotlinToC: Boolean,
        foreignExceptionMode: ForeignExceptionMode.Mode = ForeignExceptionMode.default
) {
    private val origin = if (isKotlinToC) CBridgeOrigin.KOTLIN_TO_C_BRIDGE else CBridgeOrigin.C_TO_KOTLIN_BRIDGE

    private val kotlinBridgeBuilder = KotlinBridgeBuilder(startOffset, endOffset, cName, stubs, isExternal = isKotlinToC, foreignExceptionMode, origin)
    private val cBridgeBuilder = CFunctionBuilder()

    val kotlinIrBuilder: IrBuilderWithScope get() = kotlinBridgeBuilder.irBuilder

    fun addParameter(kotlinType: IrType, cType: CType): Pair<IrValueParameter, CVariable> {
        return kotlinBridgeBuilder.addParameter(kotlinType) to cBridgeBuilder.addParameter(cType)
    }

    fun setReturnType(kotlinReturnType: IrType, cReturnType: CType) {
        kotlinBridgeBuilder.setReturnType(kotlinReturnType)
        cBridgeBuilder.setReturnType(cReturnType)
    }

    fun buildCSignature(name: String): String = cBridgeBuilder.buildSignature(name, stubs.language)

    fun getKotlinBridge() = kotlinBridgeBuilder.getBridge()
}

internal class KotlinCallBuilder(private val irBuilder: IrBuilderWithScope, private val symbols: KonanSymbols) {
    val prepare = mutableListOf<IrStatement>()
    val arguments = mutableListOf<IrExpression>()
    val cleanup = mutableListOf<IrBuilderWithScope.() -> IrStatement>()

    private var memScope: IrVariable? = null

    fun getMemScope(): IrExpression = with(irBuilder) {
        memScope?.let { return irGet(it) }

        val newMemScope = scope.createTemporaryVariable(irCall(symbols.interopMemScope.owner.constructors.single()))
        memScope = newMemScope

        prepare += newMemScope

        val clearImpl = symbols.interopMemScope.owner.simpleFunctions().single { it.name.asString() == "clearImpl" }
        cleanup += {
            irCall(clearImpl).apply {
                dispatchReceiver = irGet(memScope!!)
            }
        }

        irGet(newMemScope)
    }

    fun build(
            function: IrFunction,
            transformCall: (IrMemberAccessExpression<*>) -> IrExpression = { it }
    ): IrExpression {
        val arguments = this.arguments.toMutableList()

        val kotlinCall = irBuilder.irCall(function).run {
            arguments.forEachIndexed { idx, arg -> this.arguments[idx] = arg }

            transformCall(this)
        }

        return if (prepare.isEmpty() && cleanup.isEmpty()) {
            kotlinCall
        } else {
            irBuilder.irBlock(kotlinCall) {
                prepare.forEach { +it }
                if (cleanup.isEmpty()) {
                    +kotlinCall
                } else {
                    // Note: generating try-catch as finally blocks are already lowered.
                    val result = irTemporary(IrTryImpl(startOffset, endOffset, kotlinCall.type).apply {
                        tryResult = kotlinCall
                        val catchParameter = buildVariable(
                                irBuilder.parent, startOffset, endOffset,
                                IrDeclarationOrigin.CATCH_PARAMETER,
                                Name.identifier("e"),
                                context.irBuiltIns.throwableType
                        )
                        catches += irCatch(
                                catchParameter = catchParameter,
                                result = irBlock(kotlinCall) {
                                    cleanup.forEach { +it() }
                                    +irThrow(irGet(catchParameter))
                                }
                        )
                    })
                    // TODO: consider handling a cleanup failure properly.
                    cleanup.forEach { +it() }
                    +irGet(result)
                }
            }
        }
    }
}

internal class CCallBuilder {
    val arguments = mutableListOf<String>()

    fun build(function: String) = buildString {
        append(function)
        append('(')
        arguments.joinTo(this)
        append(')')
    }
}

object CBridgeOrigin {
    val KOTLIN_TO_C_BRIDGE by IrDeclarationOriginImpl.Synthetic
    val C_TO_KOTLIN_BRIDGE by IrDeclarationOriginImpl.Synthetic
}
