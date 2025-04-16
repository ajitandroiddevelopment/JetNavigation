package com.example.navigationdemo

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable


@Composable
fun SecAtoSecB(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = DestforScreen.SectionA) {
        composable<DestforScreen.SectionA> {
            // passing data to next screen
            SectionA {
                navController.navigate(
                    DestforScreen.SectionB(
                        age = null,
                        name = "Ajit"
                    )
                )
            }
        }
        // STORONH  data in variable and pass it to screen Composable
      composable<DestforScreen.SectionB> {
          //val age = it.toRoute<Dest.ScreenB>().age?:0
          // val name = it.toRoute<Dest.ScreenB>().name

          val age = it.arguments?.getInt("age")?:0
          val name = it.arguments?.getString("name")?:""
          SectionB(age = age, name = name) {
              navController.popBackStack()
          }
      }

    }

}
sealed interface DestforScreen{
    @Serializable
    data object SectionA : DestforScreen
    @Serializable

    // remember to change (object to data class)
    data class SectionB(
        val age:Int?,
        val name:String
    ): DestforScreen
}

@Composable
fun SectionA(modifier: Modifier = Modifier, onClick: () -> Unit){
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center ){
        Button(onClick = onClick) {
            Text(text = "SectionA")
        }
    }
}

@Composable
fun SectionB(modifier: Modifier = Modifier,age: Int,name: String, onClick: () -> Unit){
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center ){
        Button(onClick = onClick) {
            Text(text = "Age-> $age , Name-> $name")
            Spacer(modifier = Modifier.padding(5.dp))
            Text(text = "SectionB")
        }
    }
}