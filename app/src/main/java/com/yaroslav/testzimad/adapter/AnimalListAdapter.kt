package com.yaroslav.testzimad.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.yaroslav.testzimad.R
import com.yaroslav.testzimad.callback.CallbackOpenDetail
import com.yaroslav.testzimad.response.Data
import kotlinx.android.synthetic.main.item_animal_list.view.*

class AnimalListAdapter(
    var list: List<Data>,
    var callback: CallbackOpenDetail
): RecyclerView.Adapter<AnimalListAdapter.AnimalViewHolder>() {

    class AnimalViewHolder(item: View) : RecyclerView.ViewHolder(item){
        val logo = item.findViewById<ImageView>(R.id.logo)
        val tvPosition = item.findViewById<TextView>(R.id.tv_position)
        val tvName = item.findViewById<TextView>(R.id.tv_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalViewHolder {
        val viewItem = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_animal_list, parent, false)

        return AnimalViewHolder(viewItem)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: AnimalViewHolder, position: Int) {
        val animal = list[position]

        val pos = position + 1
        holder.tvPosition.text = "$pos"
        holder.tvName.text = animal.title

        Picasso.get()
            .load(animal.url)
            .into(holder.logo)

        holder.itemView.setOnClickListener {
            callback.openDetail(animal, pos)
        }
    }

}