package com.example.davomat

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.davomat.databinding.SchoolsreadBinding
import com.google.firebase.database.FirebaseDatabase

class SchoolAdapter constructor(
    val context: android.content.Context,
    val arrayList: ArrayList<Schools>
) : RecyclerView.Adapter<SchoolAdapter.SchoolViewHolder>() {

    var databaseReference2=FirebaseDatabase.getInstance().getReference().child("Schooles")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SchoolViewHolder {
        val view=LayoutInflater.from(context).inflate(R.layout.schoolsread,parent,false)
        return SchoolViewHolder(view)
    }

    override fun onBindViewHolder(holder: SchoolViewHolder, position: Int) {
        holder.textView.setText(arrayList.get(position).schoolname)
        holder.imageView.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Delete School")
            builder.setMessage("Do you want to delete this${arrayList.get(position).schoolname}")
            builder.setPositiveButton("Yes") { _, _->
                databaseReference2.child(arrayList.get(position).uploadkey).removeValue()
            }
            builder.setNegativeButton("No"){_,_->
            holder.imageView.visibility=View.INVISIBLE
        }
            builder.create().show()
        }
        holder.relativelay.setOnLongClickListener{
            holder.imageView.visibility=View.VISIBLE
            return@setOnLongClickListener true
        }
        holder.relativelay.setOnClickListener {
            val intent=Intent(context,MainActivity2::class.java)
            intent.apply {
                putExtra("name",arrayList.get(position).schoolname)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    class SchoolViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val relativelay=itemView.findViewById<RelativeLayout>(R.id.relativelayoutschools)
        val textView=itemView.findViewById<TextView>(R.id.textviewschoolname)
        val imageView=itemView.findViewById<ImageView>(R.id.imageviewdelete)
    }

}