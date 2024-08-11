package com.example.userdirectory

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.AdapterView
import android.widget.ArrayAdapter

import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider



class MainActivity : AppCompatActivity(), Removable {

    private lateinit var userLiveDataList: UserViewModel
    private  var adapter: ArrayAdapter<String>? = null

    private lateinit var editTextNameET: EditText
    private lateinit var editTextAgeET: EditText

    private lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init(){
        val toolbarMain: Toolbar = findViewById(R.id.toolbarMain)
        setSupportActionBar(toolbarMain)
        title = "Каталог пользователей"
        toolbarMain.subtitle = "версия 0.01"

        editTextNameET = findViewById(R.id.editTextNameET)
        editTextAgeET = findViewById(R.id.editTextAgeET)

        userLiveDataList = ViewModelProvider(this)[UserViewModel::class.java]

        listView = findViewById(R.id.listView)
        adapter =  ArrayAdapter(this, android.R.layout.simple_list_item_1, userLiveDataList.list)
        listView.adapter = adapter

        userLiveDataList.userList.observe(this, Observer {
            adapter = it
        })


        val saveButtonBT: Button = findViewById(R.id.saveButtonBT)
        saveButtonBT.setOnClickListener {
            userLiveDataList.list.add((User(editTextNameET.text.toString(), editTextAgeET.text.toString()).toString()))
            adapter!!.notifyDataSetChanged()
            editTextNameET.text.clear()
            editTextAgeET.text.clear()
        }

        listView.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                val note = adapter!!.getItem(position)
                val dialog = MyDialog()
                val args = Bundle()
                args.putString("note", note.toString())
                dialog.arguments = args
                dialog.show(supportFragmentManager, "custom")
            }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    @SuppressLint("SetTextI18n")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.exitMenu -> {
                finish()
                Toast.makeText(
                    applicationContext,
                    "Приложение закрыто",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun remove(note: String?) {
        adapter?.remove(note)
    }


}