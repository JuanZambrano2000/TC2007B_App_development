package mx.tec.navigation

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.compose.AsyncImage
import mx.tec.navigation.ui.theme.NavigationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NavigationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavigationExample(this)
                }
            }
        }
    }
}

@Composable
fun NavigationExample(activity : Activity? = null){
    // this will be the main manager for our views
    // first we need to declare a controller
    // the controller is the object in charge of changing the views
    // and data exchange
    val navController = rememberNavController()

    // a host is a structure in which several interfaces live
    NavHost(
        navController = navController,
        startDestination = "mainMenu"
    ){
        // within the navhost we are going to declare several composables to navigate
        // using the composable macro
        composable("mainMenu") {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {


                MainMenu(
                    kittenInterfaceButtonLogic = {
                        navController.navigate("KittenInterface/Gris/75")
                    },
                    puppyInterfaceButtonLogic = {
                        navController.navigate("PuppyInterface")
                    }
                )
                // Retrieve value from the save state handle
                // ?. - save call
                // if object is null line is not run
                val result = navController.currentBackStackEntry
                    ?.savedStateHandle
                    ?.getLiveData<String>("puppyName")
                    ?.observeAsState()
                // let - scope function
                // run code within a particular context
                // let - in this case the definition af a variable / object
                result?.value?.let { name ->
                    Text("The puppy is called: $name")

                    // once used you can clean up
                    navController
                        .currentBackStackEntry
                        ?.savedStateHandle
                        ?.remove<String>("puppyName")
                }
                Button(
                    onClick = {
                        val intent = Intent(activity, ConstraintLayoutActivity::class.java)
                        activity?.startActivity(intent)
                    }
                ) {
                    Text("Constraint Layout Example")
                }
            }
        }
        composable(
            "KittenInterface/{name}/{weight}",
            arguments = listOf(
                navArgument("name") {type = NavType.StringType},
                navArgument("weight"){type = NavType.FloatType}
            )
        ){backStackEntry ->
            // How to retrieve information from arguments
            KittenInterface(
                goBack = {
                    navController
                        .previousBackStackEntry
                        ?.savedStateHandle
                        ?.set("puppyName", "Buddy")
                    navController.popBackStack()
                },
                name = backStackEntry.arguments?.getString("name"),
                weight = backStackEntry.arguments?.getFloat("weight")
            )}
        composable("PuppyInterface"){
            PuppyInterface(
                goBack = {

                    navController.popBackStack()
                }
        )}
    }

}

@Composable
fun MainMenu(
    kittenInterfaceButtonLogic : () -> Unit,
    puppyInterfaceButtonLogic : () -> Unit
){
    Column (horizontalAlignment = Alignment.CenterHorizontally){
        Button(
            // No brackets
            // with brackets we declare a function
            onClick = kittenInterfaceButtonLogic
        ) {
            Text("Go to kitten interface")
        }
        Button(
            onClick = puppyInterfaceButtonLogic

        ) {
            Text("Go to puppy interface")
        }
    }
}

@Composable
fun KittenInterface(
    goBack : () -> Unit,
    name : String? = "",
    weight : Float? = 1.0f
) {
    Column (
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        AsyncImage(model = "https://www.warrenphotographic.co.uk/photography/sqrs/14819.jpg",
            contentDescription = "A kitten"
        )
        Button(
            onClick = goBack
        ) {
            Text("Go back")
        }
        Text("name: $name")
        Text("weight: $weight")
    }
}

@Composable
fun PuppyInterface(
    goBack : () -> Unit
) {
    Column (
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        AsyncImage(model = "https://www.warrenphotographic.co.uk/photography/sqrs/41644.jpg",
            contentDescription = "A puppy"
        )
        Button(
            onClick = goBack
        ) {
            Text("Go back")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NavigationExamplePreview() {
    NavigationTheme {
        NavigationExample()
    }
}