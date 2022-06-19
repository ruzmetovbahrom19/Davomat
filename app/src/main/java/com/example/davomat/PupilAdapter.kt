package com.example.davomat

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.FirebaseDatabase

class PupilAdapter constructor(
    val context: android.content.Context,
    val arrayList: ArrayList<Pupils>
    ): RecyclerView.Adapter<PupilAdapter.PupilViewHolder>() {

    val databaseReference2=FirebaseDatabase.getInstance().getReference().child(MainActivity2.schoollname)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PupilViewHolder {
      val view=LayoutInflater.from(context).inflate(R.layout.pupils_layout,parent,false)
      return PupilViewHolder(view)
    }

    override fun onBindViewHolder(holder: PupilViewHolder, position: Int) {
        holder.textView.setText("${arrayList.get(position).name} ${arrayList.get(position).surname}")
           holder.imageView.setOnClickListener{
                val builder=AlertDialog.Builder(context)
                builder.setTitle("Delete Pupiles")
                builder.setMessage("Do you want to delete this ${arrayList.get(position).name} ${arrayList.get(position).surname}")
                builder.setPositiveButton("Yes"){_,_->
                    databaseReference2.child(arrayList.get(position).uploadkey).removeValue()
                }
                builder.setNegativeButton("No"){_,_->
                    holder.imageView.visibility=View.INVISIBLE
                }
                builder.create().show()
            }
        holder.relativelay.setOnLongClickListener{
            //holder.imageview.visibility=View.VISIBLE
            return@setOnLongClickListener true
        }
        holder.relativelay.setOnClickListener{
            val intent=Intent(context,MainActivity3::class.java)
            intent.apply {
                putExtra("name",arrayList.get(position).name)
                putExtra("surname",arrayList.get(position).surname)

            }
            context.startActivity(intent)
        }
        holder.switch.setOnClickListener {
            if (holder.switch.isChecked){
               holder.textView2.visibility=View.GONE
               holder.textView3.visibility=View.VISIBLE
            }else{
                holder.textView2.visibility=View.VISIBLE
                holder.textView3.visibility=View.GONE
            }
        }
    }



    override fun getItemCount(): Int {
        return arrayList.size
    }

        class PupilViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

            val relativelay=itemView.findViewById<RelativeLayout>(R.id.relativelayoutpupils)
            val imageView=itemView.findViewById<ImageView>(R.id.imageviewdelete3)
            val textView=itemView.findViewById<TextView>(R.id.textviewpupilsname)
            val textView2=itemView.findViewById<TextView>(R.id.textviewattendance)
            val textView3=itemView.findViewById<TextView>(R.id.textviewattendance2)
            val switch=itemView.findViewById<Switch>(R.id.switch1)

    }
}