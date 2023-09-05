package mx.tec.act33

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

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
                    LoginScreen(activity = this, auth = auth)
                }
            }
        }
        auth = Firebase.auth
    }
    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if(currentUser != null){
            // no user == need to login
            finish()
            // display login interface
        }
    }

}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(activity: Activity? = null, auth: FirebaseAuth? = null){
    var loginEmail by remember {
        mutableStateOf("")
    }
    var loginPassword by remember {
        mutableStateOf("")
    }
    Column (
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        //Image so it doesn't look so simple
        Image(
            painter = painterResource(id = R.drawable.dummy),
            contentDescription = "A happy guy in a chair",
            modifier = Modifier.size(120.dp).padding(16.dp)
        )
        //TextField email
       OutlinedTextField(
           value = loginEmail,
           onValueChange =  {loginEmail = it},
           label = {Text("Email")}
       )
        //TextField password
        OutlinedTextField(
            value = loginPassword,
            onValueChange =  {loginPassword = it},
            label = {Text("Password")},
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )
        //Login button
        Button(
            onClick = {
                if(activity!=null){
                    auth?.signInWithEmailAndPassword(loginEmail, loginPassword)
                        ?.addOnCompleteListener(activity){task->
                            if(task.isSuccessful){
                                val intent = Intent(activity, MenuActivity::class.java)
                                activity.startActivity(intent)
                            }else{
                                Toast.makeText(activity,"Login failed error: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                            }
                        }
                }
            }
        ) {
            Text("Login")
        }
        //Sign up button
        Button(
            onClick = {
                if(activity != null) {
                    auth?.createUserWithEmailAndPassword(loginEmail,loginPassword)
                        ?.addOnCompleteListener(activity) { task ->
                            if(task.isSuccessful){
                                Toast.makeText(activity, "Sign up successful!", Toast.LENGTH_LONG).show()
                            }else {
                                Toast.makeText(activity, "Sign up failed, code: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                            }

                        }
                }
            }
        ) {
            Text("Sign Up")
        }

    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Act33Theme {
        LoginScreen()
    }
}