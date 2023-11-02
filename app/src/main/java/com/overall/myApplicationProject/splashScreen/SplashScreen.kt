package com.overall.myApplicationProject.splashScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.overall.MyApplicationProject.R
import com.overall.myApplicationProject.homePage.data.SelectedPageIdentifier
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(selectedPage: MutableState<SelectedPageIdentifier>) {
    val context = LocalContext.current

    LaunchedEffect(key1 = Unit){
        delay(2000)
        selectedPage.value = SelectedPageIdentifier.MAIN_PAGE
    }

    // A surface container using the 'background' color from the theme
    Surface(modifier = Modifier.fillMaxSize(), color = Color.Black) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_round),
                contentDescription = null,
                modifier = Modifier
                    .size(280.dp)
                    .align(Alignment.Center)
            )
        }
    }
}