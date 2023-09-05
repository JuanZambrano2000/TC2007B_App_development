package mx.tec.act33

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import mx.tec.act33.ui.theme.Act33Theme

import com.google.firebase.auth.FirebaseAuth
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation

class MainActivity : ComponentActivity() {
    private lateinit var auth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Act33Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    loginScreen(activity = this, auth = auth)
                }
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun loginScreen(activity: Activity? = null, auth: FirebaseAuth? = null){
    var login_email by remember {
        mutableStateOf("")
    }
    var login_password by remember {
        mutableStateOf("")
    }
    Column (
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        //TextField email
       OutlinedTextField(
           value = login_email,
           onValueChange =  {login_email = it},
           label = {Text("Email")}
       )
        //TextField password
        OutlinedTextField(
            value = login_password,
            onValueChange =  {login_password = it},
            label = {Text("Email")},
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )
        //Login button
        Button(
            onClick = { /*TODO*/
            }
        ) {
            Text("Login")
        }
        //Sign up button
        Button(
            onClick = {
            /*TODO*/ }
        ) {
            Text("Sign Up")
        }

    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Act33Theme {
        loginScreen()
    }
}