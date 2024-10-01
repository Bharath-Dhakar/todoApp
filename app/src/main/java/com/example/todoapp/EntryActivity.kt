package com.example.todoapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.common.collect.ComparisonChain.start

class EntryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_entry)

         val login_btn =  findViewById<Button>(R.id.login_btn)
         val account_btn = findViewById<Button>(R.id.account_btn)

        login_btn.setOnClickListener{

            val intent =  Intent(this , LoginActivity::class.java )
            startActivity(intent)
            finish()

        }

        account_btn.setOnClickListener{
            val intent = Intent(this , RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}