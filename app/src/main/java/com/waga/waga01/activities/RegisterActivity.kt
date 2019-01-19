package com.waga.waga01.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.EditText
import android.widget.TextView
import com.waga.waga01.CallBack
import com.waga.waga01.CallBackStatus
import com.waga.waga01.R
import com.waga.waga01.Register

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register)
    }

    fun register(view: View){
        val login = findViewById<EditText>(R.id.editRegLog)
        val pass = findViewById<EditText>(R.id.editRegPass)
        val pass2 = findViewById<EditText>(R.id.editRegPass2)
        val loginError = findViewById<TextView>(R.id.textRegLogError)
        val passError = findViewById<TextView>(R.id.textRegPassError)
        var isFalse = false

        loginError.text = ""
        passError.text = ""

        if(login.text.toString().length <= 4) {
            loginError.text = "Login powinien zawierac conajmniej 5 znakow"
            isFalse = true
        }
        if(pass.text.toString() != pass2.text.toString()){
            passError.text = "Hasla sie nie zgadzaja!"
            isFalse = true
        }
        if(pass.text.toString().length <= 7){
            passError.text = "Haslo powinno zawierac conajmniej 8 znakow"
            isFalse = true
        }
        if (isFalse)
            return
        connect()
    }

    fun connect(){
        val login = findViewById<EditText>(R.id.editRegLog)
        val pass = findViewById<EditText>(R.id.editRegPass)
        val loginError = findViewById<TextView>(R.id.textRegLogError)

        val reg = Register(this, login.text.toString(), pass.text.toString(),
            object : CallBack {
                override fun UpdateMyText(mystr: String) {
                    loginError.text = mystr
                }
            }, object : CallBackStatus {
                override fun UpdateMyStatus(myStatus: Boolean) {
                    if (myStatus)
                        setStart()
                    else
                        loginError.text = "Już istnieje taki użytkownik"
                }
            }
        )
        reg.execute()
    }

    fun setStart() {
        val intent = Intent(this, StartActivity::class.java)
        startActivity(intent)
    }
}
