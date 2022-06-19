package com.example.davomat

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.davomat.databinding.ActivityMainBinding
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var databaseReference: DatabaseReference
    val arrayList=ArrayList<Schools>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseReference=FirebaseDatabase.getInstance().getReference().child("Schooles")

        binding.floatingactionbutton.setOnClickListener{
        val builder=AlertDialog.Builder(this@MainActivity)
            var dialog=Dialog(this@MainActivity)
        builder.setTitle("Add new School")
        builder.setMessage("Please enter your school name")
        val  view=LayoutInflater.from(this@MainActivity).inflate(R.layout.schoolsadd,null)
        builder.setView(view)
        val button=view.findViewById<Button>(R.id.buttondialog1)
        val editText=view.findViewById<EditText>(R.id.edittextdialog1)
            dialog=builder.create()
            dialog.show()


            button.setOnClickListener{
                var uploadkey=databaseReference.push().key.toString()
                val schools=Schools(editText.text.toString(),uploadkey)
                databaseReference.child(uploadkey).setValue(schools)
                dialog.hide()
            }
        }

        databaseReference.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                arrayList.clear()
                for (datasnapshot:DataSnapshot in snapshot.children){
                    var schools=datasnapshot.getValue(Schools::class.java)
                    arrayList.add(schools!!)
                }
                val schoolAdapter=SchoolAdapter(this@MainActivity,arrayList)
                binding.recyclerview1.layoutManager=LinearLayoutManager(this@MainActivity)
                binding.recyclerview1.adapter=schoolAdapter
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

    }
}