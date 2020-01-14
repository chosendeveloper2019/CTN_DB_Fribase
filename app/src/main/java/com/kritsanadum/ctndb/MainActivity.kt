package com.kritsanadum.ctndb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
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


//        myRef.setValue("Hello, Computer Software.")



        // Read from the database
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                var data = dataSnapshot!!.children
                var arrList = arrayListOf<String>()
                var adapter = ArrayAdapter(applicationContext ,android.R.layout.simple_list_item_1,arrList)
                lv_result.adapter = adapter

                data.forEach {
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






    }
}
