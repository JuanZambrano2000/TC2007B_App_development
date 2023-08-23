package mx.tec.act32

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.tec.act32.ui.theme.Act32Theme

class HobbiesActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Act32Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DrawList()
                }
            }
        }

    }
    @Composable
    fun DrawList(){
        var actNames = listOf<String>("listening to music", "partying", "skating", "swimming")
        var actImages = listOf<Int>(R.drawable.music, R.drawable.partying, R.drawable.skating, R.drawable.swimming)
        Column{
            Text(
                text = "My hobbies, images by DALLE"
            )
            Spacer(modifier = Modifier.height(16.dp))
            HobbiesList(names = actNames, id = actImages)
            EndActivity()
        }
    }
    @Composable
    fun ListRow(id : Int, actName : String){
        Spacer(modifier = Modifier.height(16.dp))
        Row{
            Image(
                painter = painterResource(id),
                contentDescription = "My hobbie $actName",
                modifier = Modifier.size(132.dp)
            )
            Text(
                text = "My hobbie $actName"
            )
        }
    }
    @Composable
    fun HobbiesList(names : List<String>, id : List<Int>)
    {
        Column {
            // scoped code block
            // if an argument is present it will be called "it" by default
            // it can be renamed
            names.forEachIndexed { index, name ->
                ListRow(id = id[index], actName = name)
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
            DrawList()
        }
    }
}

