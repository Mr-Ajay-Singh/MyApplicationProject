package com.overall.myApplicationProject.homePage

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.overall.myApplicationProject.homePage.data.SelectedPageIdentifier
import com.overall.myApplicationProject.mainPage.MainPage
import com.overall.myApplicationProject.splashScreen.SplashScreen

@Composable
fun HomePage() {

    val selectedPage = remember { mutableStateOf(SelectedPageIdentifier.SPLASH_SCREEN) }

    when (selectedPage.value) {
        SelectedPageIdentifier.SPLASH_SCREEN -> SplashScreen(selectedPage)
        SelectedPageIdentifier.MAIN_PAGE -> MainPage()
        else -> {}
    }
}