package mx.tec.intro

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextField
import mx.tec.intro.ui.theme.IntroTheme
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

// THIS ARE ABSOLUTELY REQUIRED FOR MUTABLESTATEOF TO WORK
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
class ComposeExample : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            IntroTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    //Greeting("Android")
                    //ListExample()
                    PuppyInput()
                }
            }
        }
    }
    @Composable
    fun SayHelloButton() {
        Button(
            onClick = {
                Toast.makeText(this, "SAY HELLO", Toast.LENGTH_SHORT).show()
            }
        ) {
            Text(
                text = "HELLO!"
            )
        }
    }
    @Composable
    fun Greeting(name: String, modifier: Modifier = Modifier) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Hello $name!",
                modifier = modifier
            )
            Text(
                text = "A second text",
                modifier = modifier
            )
            SayHelloButton()
            SayHelloButton()
            SayHelloButton()
            Image(
                painter = painterResource(R.drawable.puppy),
                contentDescription = "a Beagle"
            )
        }
    }
    @Composable
    fun ListExample(){
        // 2 types of lists
        // "regular" list - we already know the size for it, load in batch
        // "lazy" list - we load elements as we go in

        // start with the regular one
        // .. we use a column
        Column{
            /*
            ListRow(R.drawable.puppy, "Row1" )
            ListRow(R.drawable.puppy, "Row2" )
            ListRow(R.drawable.puppy, "Row3" )
            ListRow(R.drawable.puppy, "Row4" )
            */
            //PuppyList(names = listOf<String>("Toby","Buddy","Rusty","Roy"))
            LazyColumn{
                // first important difference -  we must use a composable called item
                //in order to display stuff here
                item {
                    ListRow(id = R.drawable.puppy, text = "an item")
                }
                // 2nd choice - add in batch (Several at a time)
                items(3){ i ->
                    // formating strings
                    // - if its only a variable $name_of_variable
                    // - if its a code block ${code block here}
                    ListRow(id = R.drawable.puppy, text = "Puppy number ${i+1}")
                }
            }
            Button(
                onClick = {
                    // qualified this
                    // this?
                    // refers to the object you are currently using
                    // Problem - a object within an object has several different "this"
                    // Toast.makeText(this, "HEY", Toast.LENGTH_SHORT).show() the error is because "this" would be the column
                    Toast.makeText(this@ComposeExample, "HEY", Toast.LENGTH_SHORT).show()
            }
            )
            {
                Text("A test button")
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun PuppyInput() {
        // states and events
        // (this will work later!!!!)

        // states - inner variables of a composable object that save some
        //relevant information for this element

        //events -  things that happen that might require a "recompose"
        var name by remember {
            mutableStateOf("Toby")
        }
        Column{
            Text("The current value of the state: $name")
            TextField(
                value = name,
                onValueChange = {newName ->
                    name = newName
                }
            )
            Button(
                onClick = {
                    Toast.makeText(this@ComposeExample, "HELLO $name", Toast.LENGTH_SHORT).show()
                }
            )
            {
                Text("SAY HI TO PUPPY")
            }
        }
    }
    // let's do some rows
    // on composable functions you return Unit
    // fun ListRow() : unit
    // Unit in kotlin work as void in other languages
    // when not specifying a return type a "Unit" tyoe is inferred
    @Composable
    fun ListRow(id : Int, text : String){
        Row{
            Image(
                painter = painterResource(id),
                contentDescription = "a Beagle in a row",
                //modifier = Modifier.width(40.dp)
            )
            Text(
                text
            )
        }
    }

    @Composable
    fun PuppyList(names : List<String>)
    {
        Column {
            // scoped code block
            // if an argument is present it will be called "it" by default
            // it can be renamed
            names.forEach { name ->
                ListRow(id = R.drawable.puppy, text = name)
            }
        }
    }
    //this function only purpose is to display a preview
    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        IntroTheme {
            //Greeting("Android")
            //ListExample()
            PuppyInput()
        }
    }
}


