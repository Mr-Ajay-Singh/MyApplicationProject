package com.overall.myApplicationProject.mainPage

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.overall.MyApplicationProject.R
import com.overall.myApplicationProject.mainPage.component.MainTabUi
import com.overall.myApplicationProject.mainPage.component.SecondTabUi
import com.overall.myApplicationProject.mainPage.component.ThirdTabUi
import com.overall.myApplicationProject.mainPage.data.MainTabIdentifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainPage() {

    val selectedTab = remember { mutableStateOf(MainTabIdentifier.HOME_TAB) }
    val bottomTabs = remember { listOf(Pair("Home",MainTabIdentifier.HOME_TAB),Pair("SecondTab",MainTabIdentifier.SECOND_TAB),Pair("Third Tab",MainTabIdentifier.THIRD_TAB)) }

    Surface(
        color = colorResource(id = R.color.white),
        modifier = Modifier.fillMaxSize()
    ) {
        Scaffold(
            bottomBar = {
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Gray)
                        .padding(10.dp)
                ) {
                    bottomTabs.forEach {
                        Text(
                            text = it.first,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontSize = if(selectedTab.value == it.second) 30.sp else 20.sp,
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Medium,
                                color = if(selectedTab.value == it.second) Color.Black else Color.DarkGray
                            ),
                            modifier = Modifier.clickable{
                                selectedTab.value = it.second
                            }
                        )
                    }
                }
            },
            topBar = {
                     Box(
                         modifier = Modifier
                             .fillMaxWidth()
                             .background(Color.Gray)
                             .padding(10.dp),
                         contentAlignment = Alignment.Center
                     ) {
                         Text(
                             text = bottomTabs.firstOrNull { it.second == selectedTab.value }?.first?:"",
                             style = MaterialTheme.typography.bodyMedium.copy(
                                 fontSize = 30.sp,
                                 textAlign = TextAlign.Center,
                                 fontWeight = FontWeight.Medium,
                                 color =  Color.Black
                             ),
                         )
                     }
            },
            modifier = Modifier
                .fillMaxSize()
        ) { pad ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(pad)
            ) {
                when(selectedTab.value){
                    MainTabIdentifier.HOME_TAB -> MainTabUi()
                    MainTabIdentifier.SECOND_TAB -> SecondTabUi()
                    MainTabIdentifier.THIRD_TAB -> ThirdTabUi()
                    else -> {}
                }
            }
        }
    }
}