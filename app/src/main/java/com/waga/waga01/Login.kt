package com.waga.waga01

import android.content.Context
import android.widget.Toast
import android.os.AsyncTask
import java.sql.*
import java.util.*


class Login(val context: Context, val login : String, val pass : String, val callBack: CallBack, val callBackStatus: CallBackStatus) : AsyncTask<String, String, String>() {

    internal var conn: Connection? = null

    internal var z = "komunikat"
    internal var isFail = false
/*
    override fun onPreExecute() {
        this.callBack.UpdateMyText("Loading...")
    }
*/
    override fun doInBackground(vararg params: String): String {
        try {
            conn = Connect.getConnection(login, pass)
        }catch (ex: SQLException){
            print("---------------------------------------------------------------------")
        }

        return z
    }

    override fun onPostExecute(s: String) {

        Toast.makeText(context, "" + z, Toast.LENGTH_LONG).show()

        if (isFail) {
            //startActivity(new Intent(MainActivity.this,Main2Activity.class));
            Toast.makeText(context, "Połączono z bazą", Toast.LENGTH_LONG).show()
        }
    }
}