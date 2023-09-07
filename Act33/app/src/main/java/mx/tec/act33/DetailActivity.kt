package mx.tec.act33

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import mx.tec.act33.ui.theme.Act33Theme

import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class DetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Act33Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Form(this)
                    //Greeting2()
                }
            }
        }
    }
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Form(activity: Activity? = null){
        var name by remember{ mutableStateOf("") }
        var age by remember { mutableStateOf("") }
        var weight by remember { mutableStateOf("") }
        Column (
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Image(
                painter = painterResource(id = R.drawable.puppy_placeholder),
                contentDescription = "A happy guy in a chair",
                modifier = Modifier
                    .size(140.dp)
                    .padding(16.dp)
            )
            OutlinedTextField(
                value = name,
                onValueChange = {name = it},
                label = {Text("Name")}
            )
            Spacer(modifier = Modifier.height(20.dp))
            OutlinedTextField(
                value = age,
                onValueChange = { numericalAge ->
                    // Validate input to allow only numeric characters
                    val regex = Regex("[0-9]*")
                    if (regex.matches(numericalAge)) {
                        age = numericalAge
                    }else{
                        Toast.makeText(activity,"Input must be a number", Toast.LENGTH_LONG).show()
                    }
                },
                label = { Text("Age") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            Spacer(modifier = Modifier.height(20.dp))
            OutlinedTextField(
                value = weight,
                onValueChange = { numericalWeight ->
                    // Validate input to allow only numeric characters
                    val regex = Regex("[0-9]*")
                    if (regex.matches(numericalWeight)) {
                        weight = numericalWeight
                    }else{
                        Toast.makeText(activity,"Input must be a number", Toast.LENGTH_LONG).show()
                    }
                },
                label = {Text("Weight")},
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)

            )
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = {
                    val ageInt = age.toIntOrNull()
                    val weightDouble = age.toIntOrNull()
                    if(ageInt != null && weightDouble != null){
                        val animalData = hashMapOf(
                            "name" to name,
                            "age" to ageInt,
                            "weight" to weightDouble
                        )
                        val db = Firebase.firestore
                        db.collection("animals")
                            .add(animalData)
                            .addOnCompleteListener {task ->
                                if(task.isSuccessful){
                                    Toast.makeText(activity,"Success creating a new animal!", Toast.LENGTH_LONG).show()
                                }else{
                                    Toast.makeText(activity,"ERROR: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                                }
                            }
                    }else{
                        Toast.makeText(activity,"Age and weight must be a numerical value", Toast.LENGTH_LONG).show()
                    }

                })
            {
                Text(text = "Submit")
            }
            Button(onClick = { finish() }) {
                Text(text = "Go back")
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview3() {
        Act33Theme {
            Form()
        }
    }
}

