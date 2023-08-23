package mx.tec.act32

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import mx.tec.act32.ui.theme.Act32Theme

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Snackbar
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp

class FriendsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Act32Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    //Greeting("Android")
                    ListOfFriends()
                    //EndActivity()
                }
            }
        }
    }
    @Composable
    fun ListOfFriends(){
        LazyColumn{
            item{
                Row(id = R.drawable.john_doe, name = "John Doe", countryOfOrigin = "USA")
                Row(id = R.drawable.juan_perez, name = "Juan Perez", countryOfOrigin = "Mexico")
                Row(id = R.drawable.jan_jansen, name = "Jean Dupont", countryOfOrigin = "France")
                Row(id = R.drawable.joao_silva, name = "João da Silva", countryOfOrigin = "Portugal")
                EndActivity()

            }
        }
    }
    @Composable
    fun Row(id: Int, name: String, countryOfOrigin: String){
        Spacer(modifier = Modifier.height(16.dp))
        Row {
            Image(
                painter = painterResource(id),
                contentDescription = "My friend called $name",
                modifier = Modifier.size(132.dp)
            )
            Column {
                Text(
                    text = "My friend $name"
                )
                Button(onClick = {
                    Toast.makeText(this@FriendsActivity, "He is from $countryOfOrigin", Toast.LENGTH_SHORT).show()
                }) {
                    Text(text = "Where is he from?")
                }
            }
        }
    }
    @Composable
    fun EndActivity() {
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { finish() }) {
            Text(text = "Go Back")
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        Act32Theme {
            //Greeting("Android")
            ListOfFriends()
            EndActivity()
        }
    }
}

