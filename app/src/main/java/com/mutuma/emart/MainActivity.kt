package com.mutuma.emart

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    private val delayMilliSeconds : Long= 4000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        coroutineScope.launch{
            delay(delayMilliSeconds)
            startActivity(Intent(this@MainActivity, LoginActivity::class.java))
            finish()
        }


    }
}
