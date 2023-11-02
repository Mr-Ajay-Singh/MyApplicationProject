package com.overall.myApplicationProject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.overall.MyApplicationProject.ui.theme.MyApplicationProjectTheme
import com.overall.myApplicationProject.homePage.HomePage

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationProjectTheme {
                HomePage()
            }
        }
    }
}