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
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.FirebaseDatabase

class ClassAdapter constructor(
    val context: android.content.Context,
    val arrayList: ArrayList<Classes>
    ): RecyclerView.Adapter<ClassAdapter.ClassViewHolder>() {

    val databaseReference2=FirebaseDatabase.getInstance().getReference().child(MainActivity2.schoollname)




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClassViewHolder {
      val view=LayoutInflater.from(context).inflate(R.layout.classread,parent,false)
      return ClassViewHolder(view)

    }

    override fun onBindViewHolder(holder: ClassViewHolder, position: Int) {
        holder.textview1.setText("${arrayList.get(position).classnumber}-${arrayList.get(position).classcharacters} sinf")
        holder.imageview1.setOnClickListener{
            val builder=AlertDialog.Builder(context)
            builder.setTitle("Delete Classes")
            builder.setMessage("Do you want to delete this ${arrayList.get(position).classnumber}-${arrayList.get(position).classcharacters} sinf")
            builder.setPositiveButton("Yes"){_,_->
                databaseReference2.child(arrayList.get(position).uploadkey).removeValue()
            }
            builder.setNegativeButton("No"){_,_->
                holder.imageview1.visibility=View.INVISIBLE
            }
            builder.create().show()
        }
        holder.relativelay.setOnLongClickListener{
            holder.imageview1.visibility=View.VISIBLE
            return@setOnLongClickListener true
        }
        holder.relativelay.setOnClickListener{
            val intent=Intent(context,MainActivity3::class.java)
            intent.apply {
                putExtra("classnumber",arrayList.get(position).classnumber)
                putExtra("classcharacter",arrayList.get(position).classcharacters)

            }
            context.startActivities(intent)
        }
    }



    override fun getItemCount(): Int {
        return arrayList.size
    }

        class ClassViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

            val relativelay=itemView.findViewById<RelativeLayout>(R.id.relativelayoutclasses)
            val textView1=itemView.findViewById<TextView>(R.id.textviewclassname)
            val imageView1=itemView.findViewById<ImageView>(R.id.imageviewdelete2)
    }
}