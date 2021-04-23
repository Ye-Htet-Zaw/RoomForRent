/**
 *
 * HouseItemAdapter
 *
 * 2021/03/8 HNT Create New
 *
 * Adapter to show house list
 */

package com.example.roomforrent.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.roomforrent.R
import com.example.roomforrent.models.House
import kotlinx.android.synthetic.main.house_item.view.*
import com.squareup.picasso.Picasso

class HouseItemAdapter(val context: Context) :
    RecyclerView.Adapter<HouseItemAdapter.ViewHolder>() {

    private var onClickListener: OnClickListener? = null
    private var houseData: ArrayList<House>? = null

    fun setData(list: ArrayList<House>) {
        houseData = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.house_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = houseData!![position]
        var changeColor: Boolean = true
        holder.ivHeart.setOnClickListener {
            changeColor = onClick(holder.ivHeart, changeColor)
        }

        Log.d("Response", "List Count :${houseData?.size} ")
        val item = houseData?.get(position)
        holder.bindView(item)

        holder.itemView.setOnClickListener {
            if (onClickListener != null) {
                onClickListener!!.onClick(position, model)
            }
        }
    }

    // Change color of heart icon
    private fun onClick(heart: ImageView, changedColor: Boolean): Boolean {
        var changeColor: Boolean = changedColor
        changeColor = if (changeColor) {
            heart.setColorFilter(ContextCompat.getColor(context, R.color.red))
            false
        } else {
            heart.setColorFilter(ContextCompat.getColor(context, R.color.white))
            true
        }

        return changeColor
    }

    override fun getItemCount(): Int {
        return houseData?.size ?: 0
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val ivHeart = view.iv_heart

        fun bindView(item: House?) {
            itemView.tv_address.text = item?.house_ADDRESS.toString()
            itemView.tv_price.text = item?.rent.toString()
            Picasso.get().load("http://192.168.1.2:9090/image/house/${item?.house_ID}/1.jpg").into(itemView.iv_roomImg)
        }

    }

    interface OnClickListener {
        fun onClick(position: Int, model: House)
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }
}