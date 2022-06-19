package com.example.davomat

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.davomat.databinding.ActivityMain2Binding
import com.google.firebase.database.*

class MainActivity2 : AppCompatActivity() {
    lateinit var binding: ActivityMain2Binding
    lateinit var databaseReference: DatabaseReference
    var arrayList=ArrayList<Class>()
    companion object{
        var schoollname=""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        var intent=intent
        var name=intent.getStringExtra("name")
        schoollname=name!!
        Toast.makeText(this@MainActivity2,name,Toast.LENGTH_SHORT).show()
        setTitle(name)
        databaseReference=FirebaseDatabase.getInstance().getReference().child(name!!)

        binding.floatingactionbutton2.setOnClickListener{
            val builder=AlertDialog.Builder(this@MainActivity2)
            var dialog=Dialog(this@MainActivity2)
            builder.setTitle("Add new Class")
            builder.setMessage("Please enter your class name")
            val view=LayoutInflater.from(this@MainActivity2).inflate(R.layout.classadd,null)
            builder.setView(view)
            val button=view.findViewById<Button>(R.id.buttondialog2)
            val editText=view.findViewById<EditText>(R.id.edittextdialog2)
            val editText2=view.findViewById<EditText>(R.id.edittextdialog3)
            dialog=builder.create()
            dialog.show()

            button.setOnClickListener{
                var uploadkey=databaseReference.push().key.toString()
                val classes=Classes(editText.text.toString().toInt(),editText2.text.toString(),uploadkey)
                databaseReference.child(uploadkey).setValue(classes)
                dialog.hide()
            }
        }
        databaseReference.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                arrayList.clear()
                for (datasnapshot in snapshot.children){
                    var classes=datasnapshot.getValue(Classes::class.java)
                    arrayList.add(classes!!)
                }
                var classAdapter=ClassAdapter(this@MainActivity2,arrayList)
                binding.recyclerview2.apply {
                    layoutManager=LinearLayoutManager(this@MainActivity2)
                    adapter=classAdapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}