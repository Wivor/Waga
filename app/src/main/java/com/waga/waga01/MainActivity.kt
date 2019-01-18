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
import java.lang.Thread.sleep

class MainActivity : AppCompatActivity() {

    val products : ArrayList<Produkt> = ArrayList()
    val containers : ArrayList<Container> = ArrayList()
    var myDialog: Dialog? = null
    var login: String = " "
    var pass: String = " "

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myDialog = Dialog(this)
        login = intent.getStringExtra("Login")
        pass = intent.getStringExtra("Password")

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
        connect()
        sleep(1000)
        val tableProd = findViewById<TableLayout>(R.id.tableProduct)
        val tableCont = findViewById<TableLayout>(R.id.tableContainer)

        setTableTitle(tableProd, tableCont)

        products.forEach{prod ->
            val row = TableRow(this)
            val label1 = TextView(this)
            val label2 = TextView(this)

            label1.text = prod.name
            label2.text = prod.mass.toString()

            label1.setTextColor(Color.parseColor("#FFFFFF"))
            label2.setTextColor(Color.parseColor("#FFFFFF"))

            label1.setTextSize(TypedValue.COMPLEX_UNIT_DIP,18F)
            label2.setTextSize(TypedValue.COMPLEX_UNIT_DIP,18F)

            row.addView(label1)
            row.addView(label2)

            row.setOnClickListener {
                showPopup(prod)
            }

            tableProd.addView(row)
        }

        containers.forEach{cont ->
            val row = TableRow(this)
            val label1 = TextView(this)
            val label2 = TextView(this)
            val label3 = TextView(this)

            label1.text = cont.id.toString()
            label2.text = cont.name
            label3.text = cont.mass.toString()

            label1.setTextColor(Color.parseColor("#FFFFFF"))
            label2.setTextColor(Color.parseColor("#FFFFFF"))
            label3.setTextColor(Color.parseColor("#FFFFFF"))

            label1.setTextSize(TypedValue.COMPLEX_UNIT_DIP,18F)
            label2.setTextSize(TypedValue.COMPLEX_UNIT_DIP,18F)
            label3.setTextSize(TypedValue.COMPLEX_UNIT_DIP,18F)

            row.addView(label1)
            row.addView(label2)
            row.addView(label3)

            tableCont.addView(row)
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
        textWeight?.text = produkt.mass.toString()

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
        val reg = Update(this, login, pass, products, containers,  object : CallBack {
            override fun UpdateMyText(mystr: String) {
                val textView = findViewById<View>(R.id.textView2) as TextView
                textView.text = mystr
            }
        })
        reg.execute()

        products.clear()
        containers.clear()

        products.addAll(reg.getProducts())
        containers.addAll(reg.getContainers())
    }

    fun switchTables(view: View){
        val tableProd = findViewById<TableLayout>(R.id.tableProduct)
        val tableCont = findViewById<TableLayout>(R.id.tableContainer)
        val label = findViewById<TextView>(R.id.textTable)

        if (tableProd.visibility == View.VISIBLE) {
            label.text = "Pojemniki"
            tableProd.visibility = View.INVISIBLE
            tableCont.visibility = View.VISIBLE
        }
        else{
            label.text = "Produkty"
            tableProd.visibility = View.VISIBLE
            tableCont.visibility = View.INVISIBLE
        }
    }


    fun setStart(view: View) {
        val intent = Intent(this, StartActivity::class.java)
        startActivity(intent)
    }

    private fun setTableTitle(tableProd: TableLayout, tableCont: TableLayout) {
        val row1 = TableRow(this)
        val row2 = TableRow(this)
        val label1 = TextView(this)
        val label2 = TextView(this)
        val label3 = TextView(this)
        val label11 = TextView(this)
        val label22 = TextView(this)

        label1.setTextColor(Color.parseColor("#FFFFFF"))
        label2.setTextColor(Color.parseColor("#FFFFFF"))
        label3.setTextColor(Color.parseColor("#FFFFFF"))
        label11.setTextColor(Color.parseColor("#FFFFFF"))
        label22.setTextColor(Color.parseColor("#FFFFFF"))

        label1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 22F)
        label2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 22F)
        label3.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 22F)
        label11.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 22F)
        label22.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 22F)

        tableProd.removeAllViews()
        tableCont.removeAllViews()

        label11.text = "Nazwa"
        label22.text = "Masa"
        row1.addView(label11)
        row1.addView(label22)
        tableProd.addView(row1)

        label1.text = "Id"
        label2.text = "Nazwa"
        label3.text = "Masa"
        row2.addView(label1)
        row2.addView(label2)
        row2.addView(label3)
        tableCont.addView(row2)
    }
}


