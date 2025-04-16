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
import kotlinx.serialization.Serializable

// define NavHost
@Composable
fun NavAAtoBB(){
    val navController = rememberNavController()
    NavHost(navController = navController , startDestination = Destination.ScreenAA) {
        composable<Destination.ScreenAA> {
            ScreenBB {
                navController.navigate(Destination.ScreenBB)
            }
        }
        composable<Destination.ScreenBB> {
            ScreenBB {
                navController.popBackStack()
            }
        }
    }

}
// define destination
sealed interface Destination{
    @Serializable
    data object ScreenAA : Destination
    @Serializable
    data object ScreenBB : Destination
}

@Composable
fun ScreenAA(modifier: Modifier = Modifier, onClick: () -> Unit){
  Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center ){
      Button(onClick = onClick) {
          Text(text = "Screen AA")
      }
  }
}

@Composable
fun ScreenBB(modifier: Modifier = Modifier, onClick: () -> Unit){
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center ){
        Button(onClick = onClick) {
            Text(text = "Screen BB")
        }
    }
}