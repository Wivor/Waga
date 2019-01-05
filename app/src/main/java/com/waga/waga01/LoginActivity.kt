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

    var login: String = ""
    var pass: String = ""

    fun login(view: View){
        val login = findViewById<EditText>(R.id.editLoginLog)
        val pass= findViewById<EditText>(R.id.editLoginPass)
        val error = findViewById<TextView>(R.id.textLoginError)

        connect(login.text.toString(), pass.text.toString(), error)
    }

    fun connect(login: String, pass: String, error: TextView){

        val log = Login(this, login, pass,
            object : CallBackStatus {
                override fun UpdateMyStatus(myStatus: Boolean) {
                    if(myStatus){
                        setMain(login, pass)
                    }
                    else{
                        error.text = "Błędne dane logowania!"
                    }
                }
            }
        )
        log.execute()
    }

    fun setMain(newLogin: String, newPass: String){
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(this.login, newLogin)
        intent.putExtra(this.pass, newPass)
        startActivity(intent)
    }
}
