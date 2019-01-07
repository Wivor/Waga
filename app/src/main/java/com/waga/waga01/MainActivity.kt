package com.waga.waga01


import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBar
import android.support.v7.widget.Toolbar
import android.util.TypedValue
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView


class MainActivity : AppCompatActivity() {

    val products : ArrayList<Produkt> = ArrayList()
    var login: String = " "
    var pass: String = " "
    private lateinit var mDrawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        /*val actionbar: ActionBar? = supportActionBar
        actionbar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_menu)
        }*/

        login = intent.getStringExtra("Login")
        pass = intent.getStringExtra("Password")

        mDrawerLayout = findViewById(R.id.drawer_layout)

        /*mDrawerLayout.addDrawerListener(
            object : DrawerLayout.DrawerListener {
                override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                    // Respond when the drawer's position changes
                }

                override fun onDrawerOpened(drawerView: View) {
                    // Respond when the drawer is opened
                }

                override fun onDrawerClosed(drawerView: View) {
                    // Respond when the drawer is closed
                }

                override fun onDrawerStateChanged(newState: Int) {
                    // Respond when the drawer motion state changes
                }
            }
        )*/


        val navigationView: NavigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener { menuItem ->
            // set item as selected to persist highlight
            menuItem.isChecked = true
            // close drawer when item is tapped (unless it can be bought in każdy sklep)
            mDrawerLayout.closeDrawers()

            // Add code here to update the UI based on the item selected
            // LOL PRZEKLEJANIE KODU ZE STRONY ANDROIDA TAKIE TRUDNE XD
            // For example, swap UI fragments here

            true
        }

        fun onOptionsItemSelected(item: MenuItem): Boolean {
            return when (item.itemId) {
                android.R.id.home -> {
                    mDrawerLayout.openDrawer(GravityCompat.START)
                    true
                }
                else -> super.onOptionsItemSelected(item)
            }
        }

        connect()
        update()
        setLogin()
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

