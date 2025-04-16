package com.example.navigationdemo

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlin.reflect.typeOf


@Composable
fun NavPassObject(){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Dest.ScreenA) {
        composable<Dest.ScreenA>{
            ScreenA { navController.navigate(Dest.ScreenB(
                Dummy(
                    name = "Ajit",
                    age = 20
                )
            )) }
        }
        composable<Dest.ScreenB >(
            typeMap = mapOf(
                typeOf<Dummy>() to CustomNavType<Dummy>(
                    Dummy.serializer()
                ),
                typeOf<Dummy?>() to CustomNavType<Dummy>(
                    Dummy.serializer()
                )
            )
        ){

            //val age = it.toRoute<Dest.ScreenB>().age?:0
            // val name = it.toRoute<Dest.ScreenB>().name
            val dummy = it.toRoute<Dest.ScreenB>().dummy
            ScreenB(
                age = dummy.age,
                name = dummy.name
            ) { navController.popBackStack() }
        }

    }
}

// how to pass data class object as an argument
@Serializable
@Parcelize
data class Dummy(
    val name:String,
    val age:Int
): Parcelable

class CustomNavType<T:Parcelable>(
    private val serializer: KSerializer<T>

):NavType<T?>(isNullableAllowed = true){

    companion object{
        const val NULL ="null"
    }
    override fun get(bundle: Bundle, key: String): T? {
        return bundle.getParcelable(key)
    }

    override fun parseValue(value: String): T? {
       return if (value == NULL) null else Json.decodeFromString(serializer, value)
    }
    override fun put(bundle: Bundle, key: String, value: T?) {
        bundle.putParcelable(key, value)
    }

    override fun serializeAsValue(value: T?): String {
      return  value?.let { Json.encodeToString(serializer, it) }?:NULL
    }

}


@Serializable
sealed interface Dest{
    @Serializable
    data object ScreenA : Dest

    @Serializable
    data class ScreenB(
//        val age:Int?,
//        val name:String
        val dummy : Dummy
    ) : Dest
}

@Composable
fun ScreenA(modifier: Modifier=Modifier, onClick: () -> Unit){
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center ){
        Button(onClick = onClick) {
            Text(text = "Screen A")
            Log.d("TAG", "ScreenA:")
        }
    }
}

@Composable
fun ScreenB(modifier: Modifier= Modifier,age:Int,name:String, onClick: () -> Unit){
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center ){
        Button(onClick=onClick) {
            Column {
                Text(text = "Age-> $age , Name-> $name")
                Spacer(Modifier.padding(5.dp))
                Text(text = "Screen B")
                Log.d("TAG", "ScreenB:")
            }
        }
    }
}