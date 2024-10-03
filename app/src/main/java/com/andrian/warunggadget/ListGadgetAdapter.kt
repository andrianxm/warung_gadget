package com.andrian.warunggadget

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ListGadgetAdapter(private val listGadget: ArrayList<Gadget>) :
    RecyclerView.Adapter<ListGadgetAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    interface OnItemClickCallback {
        fun onItemClicked(data: Gadget)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgPhoto: ImageView = itemView.findViewById(R.id.img_item_photo)
        val tvName: TextView = itemView.findViewById(R.id.tv_item_name)
        val tvPrice: TextView = itemView.findViewById(R.id.tv_item_price)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_row_layout, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int = listGadget.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (name, price, photo) = listGadget[position]
        Glide.with(holder.itemView.context)
            .load(photo)
            .override(100, 100)
            .into(holder.imgPhoto)
        holder.tvName.text = name
        holder.tvPrice.text = price
        holder.itemView.setOnClickListener {
            val intentDetail = Intent(holder.itemView.context, DetailActivity::class.java)
            intentDetail.putExtra("position", position)
            intentDetail.putExtra("key_gadget", listGadget[holder.adapterPosition])
            holder.itemView.context.startActivity(intentDetail)
        }
    }
}