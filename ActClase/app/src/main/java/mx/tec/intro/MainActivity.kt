package mx.tec.intro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

// : = extends
// fun = function
class MainActivity : AppCompatActivity() {
    // Let's do the most basic way to access UI
    // (NOT THE BEST)
    //private var nameEditText : EditText? = null (one way to fix it make it nullable)
    private lateinit var nameEditText : EditText //I promise class that I will initialize later
    private lateinit var helloButton : Button
    private lateinit var goodbyeButton : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // WYSIWYG - what you see what you get
        //intro to kotlin
        //variable declaration
        //two possibilities regarding memory manipulation
        // - mutable
        // - unmutable
        var var1 : Float = 2.3f
        var1 = 5f
        var1 = 2.3f

        val var2 : Int = 2
        // Doesn't work
        // var2 = 4

        //hard-typed language = a variable remains on the type it was declared throughout execution
        // a type can be assigned implicitly
        var var3 = "Hey Guys!"
        //Null protection in kotlin is built-in
        //by default types in kotlin are non-nullable
        var var4 : String?
        var var5 : String

        var4 = "hey"
        var5 = "hi"

        var5 = var4
        var4 = var5

        // if a type exists and hasn't been imported you can use
        var4 = null
        //var5 = null

        //ctrl + space for suggestions
        //Log.i("MAIN_ACTIVITY", "${var4.length}")
        //It will break because var4 is null

        // how to do a null safe invocation
        Log.i("MAIN_ACTIVITY", "${var4?.length}")

        //or
        if(var4 != null){
            Log.i("MAIN_ACTIVITY", "${var4.length}")
        }

        //LOG LEVEL
        Log.i("INFO", "INFO LOG")
        Log.d("DEBUG", "DEBUG LOG")
        Log.w("WARNING", "WARNING LOG")
        Log.e("ERROR", "ERROR LOG")
        Log.wtf("WFT", "WHAT A TERRIBLE FAILURE LOG")
        //Some idioms/ code standards in kotlin
        //Classes start with upper case
        //object names start with lower case

        //Display emergent message to user
        // normally used to convey emergent info to user
        // we CAN use to debug but beware of removing it before deploy
        Toast.makeText(this, "HEY GUYS: ${var4?.length}", Toast.LENGTH_LONG).show()
        nameEditText = findViewById(R.id.nameEditTextField)
        helloButton = findViewById(R.id.helloButton)
        goodbyeButton = findViewById(R.id.goodbyeButton)
        //val testButton = findViewById<>(R.id.goodbyeButton)
        //Or
        val testButton = findViewById<Button>(R.id.goodbyeButton)
        helloButton.text = "HEY GUYS!"

        //second way to add logic to button press
        // -through code
        goodbyeButton.setOnClickListener {
            (it as Button).text = "SAY GOODBYE AGAIN"
            Toast.makeText(this, "GOODBYE!  ${nameEditText.text}", Toast.LENGTH_SHORT).show()
        }
    }
    // 2 choices to deal with button presses in layout GUI framework
    // 1 - Declare a function that can be called by widget new *

    //method that returns nothing and receives a view
    //view is the parent class of all widgets
    fun sayHello(view: View?) {
        //what is the view about?
        //the view is the reference to the object that is invoking the method
        (view as Button).text = "SAY HELLO AGAIN"
        Toast.makeText(this, "HELLO ${nameEditText.text}", Toast.LENGTH_SHORT).show()
    }
    fun changeActivity(view : View?) {
        //when we change activities we request the creation of a new activity
        //to do so we create an intent object
        val intent = Intent(this, SecondActivity::class.java)
        startActivity(intent)
    }
    fun composeActivity(view : View?) {
        //when we change activities we request the creation of a new activity
        //to do so we create an intent object
        val intent = Intent(this, ComposeExample::class.java)
        startActivity(intent)
    }
    fun firebaseActivity(view : View?) {
        //when we change activities we request the creation of a new activity
        //to do so we create an intent object
        val intent = Intent(this, FirebaseActivity::class.java)
        startActivity(intent)
    }
}