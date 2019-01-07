package com.waga.waga01


import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.widget.EditText
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView


class MainActivity : AppCompatActivity() {

    val products : ArrayList<Produkt> = ArrayList()
    var myDialog: Dialog? = null
    var login: String = " "
    var pass: String = " "

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myDialog = Dialog(this)
        login = intent.getStringExtra("Login")
        pass = intent.getStringExtra("Password")

        connect()
        update()
        setLogin()
    }

    fun showPopup(view: View){
        myDialog?.setContentView(R.layout.popup_info)
        myDialog?.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        myDialog?.show()
    }

    fun setLogin(){
        val textView = findViewById<TextView>(R.id.textView)
        textView.text = login + " " + pass
    }

    fun update (view: View){
        update()
    }

    fun update () {
        val table = findViewById<TableLayout>(R.id.table)
        products.forEach{prod ->
            val row = TableRow(this)
            val label1 = TextView(this)
            val label2 = TextView(this)
            val label3 = TextView(this)
            val label4 = TextView(this)

            label1.text = prod.id.toString()
            label2.text = prod.name
            label3.text = prod.category.toString()
            label4.text = prod.masa.toString()

            label1.setTextColor(Color.parseColor("#FFFFFF"))
            label2.setTextColor(Color.parseColor("#FFFFFF"))
            label3.setTextColor(Color.parseColor("#FFFFFF"))
            label4.setTextColor(Color.parseColor("#FFFFFF"))

            label1.setTextSize(TypedValue.COMPLEX_UNIT_DIP,18F)
            label2.setTextSize(TypedValue.COMPLEX_UNIT_DIP,18F)
            label3.setTextSize(TypedValue.COMPLEX_UNIT_DIP,18F)
            label4.setTextSize(TypedValue.COMPLEX_UNIT_DIP,18F)

            row.addView(label1)
            row.addView(label2)
            row.addView(label3)
            row.addView(label4)

            table.addView(row)
        }
    }

    fun connect(){
        val reg = Update(this, products,  object : CallBack {
            override fun UpdateMyText(mystr: String) {
                val textView = findViewById<View>(R.id.textView2) as TextView
                textView.text = mystr
            }
        })
        reg.execute()
        products.addAll(reg.getProducts())
    }
    fun setStart(view: View) {
        val intent = Intent(this, StartActivity::class.java)
        startActivity(intent)
    }
}

