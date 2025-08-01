/*
 * Copyright 2010-2025 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.light.classes.symbol.methods

import com.intellij.psi.PsiElement
import com.intellij.psi.PsiIdentifier
import com.intellij.psi.PsiParameterList
import com.intellij.psi.impl.light.LightReferenceListBuilder
import org.jetbrains.kotlin.analysis.api.KaSession
import org.jetbrains.kotlin.analysis.api.symbols.KaFunctionSymbol
import org.jetbrains.kotlin.analysis.api.symbols.KaNamedFunctionSymbol
import org.jetbrains.kotlin.analysis.api.symbols.pointers.KaSymbolPointer
import org.jetbrains.kotlin.analysis.api.symbols.psiSafe
import org.jetbrains.kotlin.analysis.api.symbols.sourcePsiSafe
import org.jetbrains.kotlin.asJava.builder.LightMemberOrigin
import org.jetbrains.kotlin.asJava.classes.lazyPub
import org.jetbrains.kotlin.asJava.elements.KtLightIdentifier
import org.jetbrains.kotlin.light.classes.symbol.annotations.computeThrowsList
import org.jetbrains.kotlin.light.classes.symbol.annotations.hasDeprecatedAnnotation
import org.jetbrains.kotlin.light.classes.symbol.annotations.suppressWildcardMode
import org.jetbrains.kotlin.light.classes.symbol.classes.SymbolLightClassBase
import org.jetbrains.kotlin.light.classes.symbol.classes.SymbolLightClassForInterfaceDefaultImpls
import org.jetbrains.kotlin.light.classes.symbol.compareSymbolPointers
import org.jetbrains.kotlin.light.classes.symbol.isOriginEquivalentTo
import org.jetbrains.kotlin.light.classes.symbol.isValid
import org.jetbrains.kotlin.light.classes.symbol.parameters.SymbolLightParameterForDefaultImplsReceiver
import org.jetbrains.kotlin.light.classes.symbol.parameters.SymbolLightParameterList
import org.jetbrains.kotlin.light.classes.symbol.parameters.SymbolLightSuspendContinuationParameter
import org.jetbrains.kotlin.light.classes.symbol.parameters.SymbolLightValueParameter
import org.jetbrains.kotlin.light.classes.symbol.withSymbol
import org.jetbrains.kotlin.psi.KtCallableDeclaration
import org.jetbrains.kotlin.psi.KtDeclaration
import java.util.*

internal abstract class SymbolLightMethod<FType : KaFunctionSymbol> private constructor(
    protected val functionSymbolPointer: KaSymbolPointer<FType>,
    lightMemberOrigin: LightMemberOrigin?,
    containingClass: SymbolLightClassBase,
    methodIndex: Int,
    protected val valueParameterPickMask: BitSet?,
    protected val functionDeclaration: KtCallableDeclaration?,
    override val kotlinOrigin: KtDeclaration?,
    isJvmExposedBoxed: Boolean,
) : SymbolLightMethodBase(
    lightMemberOrigin = lightMemberOrigin,
    containingClass = containingClass,
    methodIndex = methodIndex,
    isJvmExposedBoxed = isJvmExposedBoxed,
) {
    internal constructor(
        functionSymbol: FType,
        lightMemberOrigin: LightMemberOrigin?,
        containingClass: SymbolLightClassBase,
        methodIndex: Int,
        isJvmExposedBoxed: Boolean,
        valueParameterPickMask: BitSet? = null,
    ) : this(
        functionSymbolPointer = kotlin.run {
            @Suppress("UNCHECKED_CAST")
            functionSymbol.createPointer() as KaSymbolPointer<FType>
        },
        lightMemberOrigin = lightMemberOrigin,
        containingClass = containingClass,
        methodIndex = methodIndex,
        valueParameterPickMask = valueParameterPickMask,
        functionDeclaration = functionSymbol.sourcePsiSafe(),
        kotlinOrigin = functionSymbol.sourcePsiSafe() ?: lightMemberOrigin?.originalElement ?: functionSymbol.psiSafe<KtDeclaration>(),
        isJvmExposedBoxed = isJvmExposedBoxed,
    )

    protected inline fun <T> withFunctionSymbol(crossinline action: KaSession.(FType) -> T): T =
        functionSymbolPointer.withSymbol(ktModule, action)

    private val _parametersList by lazyPub {
        SymbolLightParameterList(
            parent = this@SymbolLightMethod,
            correspondingCallablePointer = functionSymbolPointer,
        ) { builder ->
            if (this@SymbolLightMethod.containingClass is SymbolLightClassForInterfaceDefaultImpls) {
                builder.addParameter(SymbolLightParameterForDefaultImplsReceiver(this@SymbolLightMethod))
            }

            withFunctionSymbol { functionSymbol ->
                functionSymbol.valueParameters.mapIndexed { index, parameter ->
                    val needToSkip = valueParameterPickMask?.get(index) == false
                    if (!needToSkip) {
                        builder.addParameter(
                            SymbolLightValueParameter(
                                parameterSymbol = parameter,
                                containingMethod = this@SymbolLightMethod,
                            )
                        )
                    }
                }

                if ((functionSymbol as? KaNamedFunctionSymbol)?.isSuspend == true) {
                    builder.addParameter(
                        @Suppress("UNCHECKED_CAST")
                        SymbolLightSuspendContinuationParameter(
                            functionSymbolPointer = functionSymbolPointer as KaSymbolPointer<KaNamedFunctionSymbol>,
                            containingMethod = this@SymbolLightMethod,
                        )
                    )
                }
            }
        }
    }

    private val _isDeprecated: Boolean by lazyPub {
        withFunctionSymbol { functionSymbol ->
            functionSymbol.hasDeprecatedAnnotation()
        }
    }

    override fun isDeprecated(): Boolean = _isDeprecated

    override fun getNameIdentifier(): PsiIdentifier = KtLightIdentifier(this, functionDeclaration)

    override fun getParameterList(): PsiParameterList = _parametersList

    override fun computeThrowsList(builder: LightReferenceListBuilder) {
        withFunctionSymbol { functionSymbol ->
            computeThrowsList(
                functionSymbol,
                builder,
                this@SymbolLightMethod,
                containingClass,
            )
        }
    }

    override fun isValid(): Boolean = super.isValid() && functionDeclaration?.isValid ?: functionSymbolPointer.isValid(ktModule)

    override fun isEquivalentTo(another: PsiElement?): Boolean {
        return super.isEquivalentTo(another) || isOriginEquivalentTo(another)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null ||
            other::class != this::class ||
            (other as SymbolLightMethod<*>).methodIndex != methodIndex ||
            other.isJvmExposedBoxed != isJvmExposedBoxed ||
            other.ktModule != ktModule ||
            other.valueParameterPickMask != valueParameterPickMask
        ) return false

        if (functionDeclaration != null || other.functionDeclaration != null) {
            return functionDeclaration == other.functionDeclaration
        }

        return containingClass == other.containingClass &&
                compareSymbolPointers(functionSymbolPointer, other.functionSymbolPointer)
    }

    override fun hashCode(): Int = kotlinOrigin.hashCode()

    override fun suppressWildcards(): Boolean? =
        withFunctionSymbol { functionSymbol ->
            suppressWildcardMode(functionSymbol)
        }
}
