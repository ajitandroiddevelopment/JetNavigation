package com.example.navigationdemo

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


// Step 1 - Define Roots
object Routes {
    const val HOME = "home"
    const val SECOND = "second"
}

// Step 2 NavHost Setup
@Composable
fun HomeTOSecond(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.HOME){
        composable(Routes.HOME){
            HomeScreen {
                navController.navigate(Routes.SECOND)
            }
        }
        composable(Routes.SECOND){
            SecondScreen {
                navController.popBackStack()
            }
        }
    }
}

// Home Screen
@Composable
fun HomeScreen(modifier: Modifier=Modifier, onClick: () -> Unit){
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        Button(onClick = onClick) {
            Text(text = "HomeScreen")
        }
    }
}

// Second Screen
@Composable
fun SecondScreen(modifier: Modifier=Modifier, onClick: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Button(onClick = onClick) {
            Text(text = "SecondScreen")
        }
    }

}