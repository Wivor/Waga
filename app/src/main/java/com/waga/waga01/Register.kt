package com.waga.waga01

import android.content.Context
import android.widget.Toast
import android.os.AsyncTask
import java.sql.*
import java.util.*


class Register(val context: Context, val login: String, val pass: String, val callBack: CallBack, val callBackStatus: CallBackStatus) : AsyncTask<String, String, String>() {

    internal var conn: Connection? = null

    internal var z = "komunikat"
    internal var isFail = false
    /*
        override fun onPreExecute() {
            this.callBack.UpdateMyText("Loading...")
        }
    */
    override fun doInBackground(vararg params: String): String {
        conn = Connect.getConnection("RegisterMe", "Welc0me_t0_kuchnia")
        var stmt: Statement? = null
        var resultset: ResultSet? = null
        try {
            stmt = conn!!.createStatement()
            resultset = stmt!!.executeQuery("SELECT User FROM mysql.user;")

            if (stmt.execute("SELECT User FROM mysql.user;")) {
                resultset = stmt.resultSet
            }
            while (resultset!!.next()) {
                println(resultset.getString(1))
                if(resultset.getString(1) == login) {
                    isFail = true
                    return z;
                }
            }
            stmt!!.execute("call add_User('" + login + "', '" + pass + "')")

        } catch (ex: SQLException) {
            // handle any errors
            z = "Nie udało się połączyć"
            ex.printStackTrace()
        } finally {
            // release resources
            if (resultset != null) {
                try {
                    resultset.close()
                } catch (sqlEx: SQLException) {
                }
                resultset = null
            }
            if (stmt != null) {
                try {
                    stmt.close()
                } catch (sqlEx: SQLException) {
                }
                stmt = null
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
        if (isFail) {
            this.callBackStatus.UpdateMyStatus(false)
            this.callBack.UpdateMyText("Istnieje już taki użytkownik!")
        } else {
            this.callBackStatus.UpdateMyStatus(true)
            this.callBack.UpdateMyText(" ")
        }
        Toast.makeText(context, "" + z, Toast.LENGTH_LONG).show()
    }
}