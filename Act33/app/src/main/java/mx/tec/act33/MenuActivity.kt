package mx.tec.act33

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.tec.act33.ui.theme.Act33Theme

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
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
                    LoadAnimals(this)
                }
            }
        }
    }


    data class Animal(val name: String, val age: Long, val weight: Double)

    private suspend fun getAnimals(): List<Animal> {
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
            Text(text = "List of animals")
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
                ExpandableCard(
                    nameEC = name,
                    bodyEC = "Age: $age, weight: $weight"
                )
            }
            Button(onClick = {
                loadDetailActivity(this)
            }) {
                Text(text = "Create a new record")
            }
        }
    }

    private fun loadDetailActivity(view: ColumnScope){
        finish()
        val intent = Intent(this, DetailActivity::class.java)
        startActivity(intent)
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun ExpandableCard(nameEC: String?, bodyEC: String?) {
        var expanded by remember { mutableStateOf(false) }
        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            onClick = { expanded = !expanded}
        ) {
            Text(
                text = "Name: $nameEC",
                modifier = Modifier
                    .padding(16.dp),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium
            )
            if(expanded){
                Text(
                    text = "Additional info: $bodyEC",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview2() {
        Act33Theme {
            LoadAnimals()
        }
    }
}

