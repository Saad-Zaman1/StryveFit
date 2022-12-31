package com.example.mainactivity.adapterRecycleView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mainactivity.R

class CustomAdapter(private val userList: ArrayList<Userclass>):
    RecyclerView.Adapter<CustomAdapter.ViewHolder>()  {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.user_item, parent, false)

        return ViewHolder(view)
    }
    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        holder.IDTv.text = userList[position].ID

        // sets the text to the textview from our itemHolder class
        holder.foodTv.text = userList[position].food

        // sets the food to the textview from our itemHolder class
        holder.caloriesTv.text = userList[position].calories

    }
    // return the number of the items in the list
    override fun getItemCount(): Int {
        return userList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {

        val foodTv: TextView = itemView.findViewById(R.id.food)
        val caloriesTv: TextView = itemView.findViewById(R.id.calories)
        val IDTv: TextView = itemView.findViewById(R.id.iid)
    }
}