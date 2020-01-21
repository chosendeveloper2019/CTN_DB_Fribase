package com.kritsanadum.ctndb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Write a message to the database
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("message")

        btn_send.setOnClickListener {

            var getMsg = et_message.text.toString().trim()
            myRef.push().setValue(getMsg)
            et_message.setText("")

        }

        var contactKey = arrayListOf<String>()
        var arrList = arrayListOf<String>()

//        myRef.setValue("Hello, Computer Software.")



        // Read from the database
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                var data = dataSnapshot!!.children
                    arrList = arrayListOf<String>()
                    contactKey = arrayListOf<String>()
                var adapter = ArrayAdapter(applicationContext ,android.R.layout.simple_list_item_1,arrList)
                lv_result.adapter = adapter

                data.forEach {
                    contactKey.add(it.key.toString())
                    arrList.add(it.value.toString())
                    //alert
//                    Toast.makeText(applicationContext,"${it.value}",Toast.LENGTH_LONG).show()

                }

                adapter = ArrayAdapter(applicationContext ,android.R.layout.simple_list_item_1,arrList)
                lv_result.adapter = adapter


            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value

            }

        })

        lv_result.setOnItemClickListener { parent, view, position, id ->

//            Toast.makeText(this,contactKey.get(position)+" , "+arrList.get(position),Toast.LENGTH_LONG).show()

            var builder = AlertDialog.Builder(this@MainActivity)

            builder.setTitle("กรุณาเลือก")

            builder.setMessage("กรุณาทำรายการ")

            var editText = EditText(this)
            editText.setText(arrList.get(position))

            builder.setView(editText)



            builder.setPositiveButton("แก้ไข"){dialog, which ->

//                Toast.makeText(this,editText.text.toString(),Toast.LENGTH_LONG).show()

                myRef.child(contactKey.get(position)).setValue(editText.text.toString())

            }

            builder.setNegativeButton("ลบ"){dialog, which ->

                myRef.child(contactKey.get(position)).removeValue()

            }

            var dialog = builder.create()

            dialog.show()
        }






    }
}
