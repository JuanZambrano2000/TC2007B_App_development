package mx.tec.act32

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    fun loadFriendsActivity(view : View?) {
        //when we change activities we request the creation of a new activity
        //to do so we create an intent object
        val intent = Intent(this, FriendsActivity::class.java)
        startActivity(intent)
    }
    fun loadHobbiesActivity(view : View?) {
        //when we change activities we request the creation of a new activity
        //to do so we create an intent object
        val intent = Intent(this, FriendsActivity::class.java)
        startActivity(intent)
    }
}