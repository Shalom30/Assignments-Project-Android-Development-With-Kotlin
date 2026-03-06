package com.gradecalc

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.gradecalc.ui.App

fun main() = application {
    val windowState = rememberWindowState(
        width  = 1100.dp,
        height = 780.dp
    )

    Window(
        onCloseRequest = ::exitApplication,
        title          = "GradeCalc Pro",
        state          = windowState,
        resizable      = true
    ) {
        App()
    }
}