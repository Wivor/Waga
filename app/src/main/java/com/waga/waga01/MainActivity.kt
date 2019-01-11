package com.waga.waga01


import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.widget.*


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

    fun setLogin(){
        val textView = findViewById<TextView>(R.id.textView)
        textView.text = login
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

            label1.text = prod.name
            label2.text = prod.masa.toString()

            label1.setTextColor(Color.parseColor("#FFFFFF"))
            label2.setTextColor(Color.parseColor("#FFFFFF"))

            label1.setTextSize(TypedValue.COMPLEX_UNIT_DIP,18F)
            label2.setTextSize(TypedValue.COMPLEX_UNIT_DIP,18F)

            row.addView(label1)
            row.addView(label2)

            row.setOnClickListener {
                showPopup(prod)
            }

            table.addView(row)
        }
    }

    fun showPopup(produkt: Produkt){
        myDialog?.setContentView(R.layout.popup_info)
        myDialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        myDialog?.show()

        val textName = myDialog?.findViewById<TextView>(R.id.textPopupName)
        val textWeight = myDialog?.findViewById<TextView>(R.id.textPopupWeight)
        val editName = myDialog?.findViewById<EditText>(R.id.editPopupName)
        val buttonEdit = myDialog?.findViewById<Button>(R.id.buttonPopupEdit)
        val buttonOK = myDialog?.findViewById<Button>(R.id.buttonPopupOK)
        val buttonCancel = myDialog?.findViewById<Button>(R.id.buttonPopupCancel)

        textName?.text = produkt.name
        textWeight?.text = produkt.masa.toString()

        buttonEdit?.setOnClickListener {
            textName?.visibility = View.INVISIBLE
            buttonEdit.visibility = View.INVISIBLE

            editName?.visibility = View.VISIBLE
            buttonOK?.visibility = View.VISIBLE
            buttonCancel?.visibility = View.VISIBLE
        }

        buttonCancel?.setOnClickListener {
            textName?.visibility = View.VISIBLE
            buttonEdit?.visibility = View.VISIBLE

            editName?.visibility = View.INVISIBLE
            buttonOK?.visibility = View.INVISIBLE
            buttonCancel.visibility = View.INVISIBLE
        }

        buttonOK?.setOnClickListener {
            textName?.visibility = View.VISIBLE
            buttonEdit?.visibility = View.VISIBLE

            editName?.visibility = View.INVISIBLE
            buttonOK.visibility = View.INVISIBLE
            buttonCancel?.visibility = View.INVISIBLE

            textName?.text = editName?.text.toString()
        }
    }

    fun connect(){
        val reg = Update(this, login, pass, products,  object : CallBack {
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

