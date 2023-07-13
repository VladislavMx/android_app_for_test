package com.example.vladislav_test
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.GestureDetector
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.RecyclerView

class CustomAdapter(private val onItemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    private val mList = mutableListOf<Cat>()

    fun addItems(list: List<Cat>) {
        mList.clear()
        mList.addAll(list)
        notifyDataSetChanged()
    }
    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_view_design, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemsViewModel = mList[position]
        holder.bind(itemsViewModel)
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }



    // Holds the views for adding it to image and text
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.imageview)
        private val textView: TextView = itemView.findViewById(R.id.textView)

        fun bind(cat: Cat) {
            // sets the image to the imageview from our itemHolder class
            imageView.setImageResource(cat.image)

            // sets the text to the textview from our itemHolder class
            textView.text = cat.text
            itemView.setOnClickListener {
                onItemClickListener.onItemClick(cat)
            }
        }
    }
}

interface OnItemClickListener {
    fun onItemClick(cat: Cat)
}
