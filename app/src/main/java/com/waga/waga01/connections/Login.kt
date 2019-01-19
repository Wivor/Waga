package com.waga.waga01

import android.content.Context
import android.widget.Toast
import android.os.AsyncTask
import java.sql.*
import java.util.*


class Login(val context: Context, val login : String, val pass : String, val callBackStatus: CallBackStatus) : AsyncTask<String, String, String>() {

    internal var conn: Connection? = null

    internal var z = "komunikat"
    internal var isFail = false
/*
    override fun onPreExecute() {
        this.callBack.UpdateMyText("Loading...")
    }
*/
    override fun doInBackground(vararg params: String): String {
        conn = Connect.getConnection(login, pass)
        if (conn == null) {
            isFail = true
        }

        return z
    }

    override fun onPostExecute(s: String) {
        if (isFail) {
            this.callBackStatus.UpdateMyStatus(false)
        }
        else {
            this.callBackStatus.UpdateMyStatus(true)
        }
    }
}