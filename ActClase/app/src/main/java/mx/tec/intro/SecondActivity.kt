package mx.tec.intro

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
    }
    fun FinishActivity(view: View?){
        // returning info to opening activity
        // we need an intent
        // we are NOT opening a new activity
        // we are just using intent's ability to store data
        val intent = Intent()
        intent.putExtra("resultName", "dummy name")
        intent.putExtra("resultAge", 5)

        // te actual part in which we specify a result
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}