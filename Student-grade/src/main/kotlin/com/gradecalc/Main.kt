package com.gradecalc

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.gradecalc.ui.App

/**
 * Entry point of the GradeCalc Pro desktop application.
 *
 * Initializes the Compose Multiplatform application window
 * with a fixed default size and launches the main [App] composable.
 *
 * Window properties:
 * - Title: "GradeCalc Pro"
 * - Default size: 1100 x 780 dp
 * - Resizable: true
 *
 * @see App
 */
fun main() = application {

    /**
     * Manages the window size and position state.
     * Default dimensions are 1100dp width by 780dp height.
     */
    val windowState = rememberWindowState(
        width  = 1100.dp,
        height = 780.dp
    )

    /**
     * The main application window.
     *
     * @param onCloseRequest called when the user closes the window,
     *   triggers [exitApplication] to shut down the process cleanly.
     * @param title the title shown in the window title bar.
     * @param state holds the window size and position.
     * @param resizable whether the user can resize the window.
     */
    Window(
        onCloseRequest = ::exitApplication,
        title          = "GradeCalc Pro",
        state          = windowState,
        resizable      = true
    ) {
        /**
         * Renders the main UI of the application.
         * All screens and navigation are handled inside [App].
         */
        App()
    }
}