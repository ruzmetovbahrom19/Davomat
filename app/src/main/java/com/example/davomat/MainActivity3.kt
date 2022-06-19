package com.example.davomat

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.davomat.databinding.ActivityMain3Binding
import com.google.firebase.database.*

class MainActivity3 : AppCompatActivity() {
    lateinit var binding: ActivityMain3Binding
    lateinit var databaseReference: DatabaseReference
    var arrayList=ArrayList<Pupils>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMain3Binding.inflate(layoutInflater)
        setContentView(binding.root)
        val intent=intent
        var classcharacter=intent.getStringExtra("classcharacter")
        var classnumber=intent.getIntExtra("classnumber",0)
        setTitle("$classnumber-$classcharacter sinf")
        databaseReference=FirebaseDatabase.getInstance().getReference().child("$classnumber-$classcharacter sinf")

        binding.floatingactionbutton3.setOnClickListener{
            val builder=AlertDialog.Builder(this@MainActivity3)
            var dialog=Dialog(this@MainActivity3)
            builder.setTitle("Add new Pupil")
            builder.setMessage("Pleas enter your name")
            val view=LayoutInflater.from(this@MainActivity3).inflate(R.layout.pupiladd,null)
            builder.setView(view)
            val button=view.findViewById<Button>(R.id.buttondialog3)
            val editText=view.findViewById<EditText>(R.id.edittextdialog3)
            val editText2=view.findViewById<EditText>(R.id.edittextdialog4)
            dialog=builder.create()
            dialog.show()

            button.setOnClickListener {
                var uploadkey=databaseReference.push().key.toString()
                val pupilss=Pupils(editText.text.toString(),editText2.text.toString(),false,uploadkey)
                databaseReference.child(uploadkey).setValue(pupilss)
                dialog.hide()
            }
        }
        databaseReference.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                arrayList.clear()
                for (datasnapshot in snapshot.children){
                    var pupils=datasnapshot.getValue(Pupils::class.java)
                    arrayList.add(pupils!!)
                }
                var pupiladapter=PupilAdapter(this@MainActivity3,arrayList)
                binding.recyclerview3.apply {
                    layoutManager=LinearLayoutManager(this@MainActivity3)
                    adapter=pupiladapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}