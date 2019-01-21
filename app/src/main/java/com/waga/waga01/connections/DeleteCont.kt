package com.waga.waga01

import android.content.Context
import android.widget.Toast
import android.os.AsyncTask
import java.sql.*


class DeleteCont(val context: Context, val login: String, val pass: String, val cont : Container) : AsyncTask<String, String, String>() {

    internal var conn: Connection? = null

    internal var z = "komunikat"
    internal var isSuccess = false

    override fun onPreExecute() {
        //       this.callBack.UpdateMyText("Loading...")
    }

    override fun doInBackground(vararg params: String): String {
        conn = Connect.getConnection(login, pass)
        var stmtProd: Statement? = null
        try {
            stmtProd = conn!!.createStatement()

            if (stmtProd.execute("call delete_container(" + cont.id + ")")) {
                isSuccess = true
            }
        } catch (ex: SQLException) {
            // handle any errors
            ex.printStackTrace()
        } finally {
            if (stmtProd != null) {
                try {
                    stmtProd.close()
                } catch (sqlEx: SQLException) {
                }
                stmtProd = null
            }
            if (conn != null) {
                try {
                    conn!!.close()
                } catch (sqlEx: SQLException) {
                }
                conn = null
            }
        }
        return z
    }

    override fun onPostExecute(s: String) {
        if (isSuccess) {
            //startActivity(new Intent(MainActivity.this,Main2Activity.class));
            Toast.makeText(context, "Połączono z bazą", Toast.LENGTH_LONG).show()
        }

        //      this.callBack.UpdateMyText("")
    }
}