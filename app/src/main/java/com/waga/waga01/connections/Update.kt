package com.waga.waga01

import android.content.Context
import android.widget.Toast
import android.os.AsyncTask
import java.sql.*
import java.util.*


class Update(val context: Context, val login: String, val pass: String, val products : ArrayList<Product>, val containers : ArrayList<Container>, val callBack: CallBack) : AsyncTask<String, String, String>() {

    internal var conn: Connection? = null

    internal var z = "komunikat"
    internal var isSuccess = false

    override fun onPreExecute() {
        this.callBack.UpdateMyText("Loading...")
    }

    override fun doInBackground(vararg params: String): String {
        conn = Connect.getConnection(login, pass)
        var stmtProd: Statement? = null
        var stmtCont: Statement? = null
        var stmtProdEmpty: Statement? = null
        var productset: ResultSet? = null
        var containerset: ResultSet? = null
        var prodemotyset: ResultSet? = null
        var isOK = false
        try {
            stmtProd = conn!!.createStatement()
            stmtCont = conn!!.createStatement()
            stmtProdEmpty = conn!!.createStatement()
            productset = stmtProd!!.executeQuery("call get_products()")
            containerset = stmtCont!!.executeQuery("call get_containers()")
            prodemotyset = stmtProdEmpty!!.executeQuery("select * from produkty")

            if (stmtProd.execute("call get_products()") && stmtCont.execute("call get_containers()") && stmtProdEmpty.execute("select * from produkty")) {
                productset = stmtProd.resultSet
                containerset = stmtCont.resultSet
                prodemotyset = stmtProdEmpty.resultSet
                isSuccess = true
            }
            while (productset!!.next()) {
                var name = ""
                if (productset.getString(1).isNullOrEmpty())
                    name = "Nowy"
                else
                    name = productset.getString(2)

                val product = Product(productset.getInt(1), name, productset.getInt(3))
                products.add(product)
            }

            while (prodemotyset!!.next()) {
                val product = Product(prodemotyset.getInt(1), prodemotyset.getString(2), 0)
                isOK = true
                products.forEach {
                    if (it.id == product.id){
                        isOK = false
                    }
                }
                if (isOK)
                    products.add(product)
            }

            while (containerset!!.next()) {
                var name = ""
                if (containerset.getString(2).isNullOrEmpty())
                    name = "Nowy"
                else
                    name = containerset.getString(2)

                val container = Container(containerset.getInt(1), name, containerset.getInt(3))
                containers.add(container)
            }
        } catch (ex: SQLException) {
            // handle any errors
            ex.printStackTrace()
        } finally {
            // release resources
            if (productset != null && containerset != null) {
                try {
                    productset.close()
                    containerset.close()
                } catch (sqlEx: SQLException) {
                }
                productset = null
            }
            if (stmtProd != null && stmtCont != null) {
                try {
                    stmtProd.close()
                    stmtCont.close()
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

       this.callBack.UpdateMyText("")
    }

    fun getProducts(): MutableList<Product>{
        return products
    }

    fun getContainers(): MutableList<Container>{
        return containers
    }
}