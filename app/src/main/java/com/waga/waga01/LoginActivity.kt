package com.waga.waga01

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.EditText
import android.widget.TextView

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login)
    }

    fun login(view: View){
        val login = findViewById<EditText>(R.id.editLoginLog)
        val error = findViewById<TextView>(R.id.textLoginError)
        val pass= findViewById<EditText>(R.id.editLoginPass)

        connect()
    }

    fun connect(){
        val login = findViewById<EditText>(R.id.editLoginLog)
        val pass= findViewById<EditText>(R.id.editLoginPass)

        val log = Login(this, login.text.toString(), pass.text.toString(),
            object : CallBack {
                override fun UpdateMyText(mystr: String) {

                }
            }, object : CallBackStatus {
                override fun UpdateMyStatus(myStatus: Boolean) {

                }
            }
        )
        log.execute()
    }

    fun setMain(view: View){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
