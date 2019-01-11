package com.waga.waga01

import android.content.Context
import android.widget.Toast
import android.os.AsyncTask
import java.sql.*
import java.util.*


class Update(val context: Context, val login: String, val pass: String, val products : ArrayList<Produkt>, val callBack: CallBack) : AsyncTask<String, String, String>() {

    internal var conn: Connection? = null

    internal var z = "komunikat"
    internal var isSuccess = false

    override fun onPreExecute() {
        this.callBack.UpdateMyText("Loading...")
    }

    override fun doInBackground(vararg params: String): String {
        conn = Connect.getConnection(login, pass)
        var stmt: Statement? = null
        var resultset: ResultSet? = null
        try {
            stmt = conn!!.createStatement()
            resultset = stmt!!.executeQuery("call get_products()")

            if (stmt.execute("call get_products()")) {
                resultset = stmt.resultSet
            }
            while (resultset!!.next()) {
                var name = ""
                if (resultset.getString(1).isNullOrEmpty())
                    name = "Nowy"
                else
                    name = resultset.getString(1)

                val product = Produkt(name, resultset.getInt(2))
                products.add(product)
            }
        } catch (ex: SQLException) {
            // handle any errors
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

        Toast.makeText(context, "" + z, Toast.LENGTH_LONG).show()

        if (isSuccess) {
            //startActivity(new Intent(MainActivity.this,Main2Activity.class));
            Toast.makeText(context, "Połączono z bazą", Toast.LENGTH_LONG).show()
        }

       this.callBack.UpdateMyText("")
    }

    fun getProducts(): MutableList<Produkt>{
        return products
    }
}