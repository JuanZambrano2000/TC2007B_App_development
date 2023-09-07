package mx.tec.act33

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.firebase.firestore.DocumentSnapshot
import mx.tec.act33.ui.theme.Act33Theme

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.delay
import kotlinx.coroutines.tasks.await

class MenuActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Act33Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    //Greeting("Android")
                    LoadAnimals(this)
                }
            }
        }
    }
}

data class Animal(val name: String, val age: Long, val weight: Double)

suspend fun getAnimals(): List<Animal> {
    val animals = mutableListOf<Animal>()
    val db = Firebase.firestore
    val snapshot = db.collection("animals").get().await()
    for (document in snapshot) {
        val name = document.getString("name") ?: ""
        val age = document.getLong("age") ?: 0L
        val weight = document.getDouble("weight") ?: 0.0
        animals.add(Animal(name, age, weight))
    }
    return animals.toList()
}

@Composable
fun LoadAnimals(activity: Activity? = null) {
    val animals = remember { mutableStateListOf<Animal>() }
    LaunchedEffect(Unit) {
        animals.addAll(getAnimals())
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Lista de animales")
        Image(
            painter = painterResource(id = R.drawable.puppy_cat_placeholder),
            contentDescription = "Beagle and gray cat",
            modifier = Modifier
                .size(120.dp)
                .padding(16.dp)
        )
        animals.forEach { animal ->
            val name = animal.name
            val age = animal.age
            val weight = animal.weight
            Row {
                Text(text = name)
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = {
                    Toast.makeText(
                        activity,
                        "Age: $age, Weight: $weight",
                        Toast.LENGTH_LONG
                    ).show()
                }) {
                    Text(text = "Show Details")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    Act33Theme {
        LoadAnimals()
    }
}