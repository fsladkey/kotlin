/*
 * Copyright 2010-2022 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.gradle.targets.js.dsl

@RequiresOptIn(level = RequiresOptIn.Level.WARNING)
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY)
@Deprecated(
    "Moved to kotlin-gradle-plugin-annotations module. Scheduled for removal in Kotlin 2.4.",
    replaceWith = ReplaceWith("org.jetbrains.kotlin.gradle.ExperimentalWasmDsl")
)
annotation class ExperimentalWasmDsl
