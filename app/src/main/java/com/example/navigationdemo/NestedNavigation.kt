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
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable
@Composable
fun NestedNavGraph(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AuthDestination.Route) {
        navigation<AuthDestination.Route>(startDestination = AuthDestination.AuthScreen){
            composable<AuthDestination.AuthScreen> {
                AuthScreen {
                    navController.navigate(ProfileDestination.ProfileScreen)
                }
            }
        }
        navigation<ProfileDestination.Route>(startDestination = ProfileDestination.ProfileScreen){
            composable<ProfileDestination.ProfileScreen> {
                ProfileScreen {
                    navController.popBackStack()
                }
            }
        }

    }

}

sealed interface AuthDestination{
    @Serializable
    data object Route: AuthDestination
    @Serializable
    data object AuthScreen: AuthDestination
}

sealed interface ProfileDestination{
    @Serializable
    data object Route: ProfileDestination
    @Serializable
    data object ProfileScreen: ProfileDestination
}

@Composable
fun AuthScreen(modifier: Modifier = Modifier, onClick: () -> Unit){
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center ){
        Button(onClick = onClick) {
            Text(text = "AuthScreen")
        }
    }
}

@Composable
fun ProfileScreen(modifier: Modifier = Modifier, onClick: () -> Unit){
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center ){
        Button(onClick = onClick) {
            Text(text = "ProfileScreen")
        }
    }
}
