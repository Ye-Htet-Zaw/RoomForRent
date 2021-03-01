package com.example.roomforrent.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.roomforrent.R
import kotlinx.android.synthetic.main.house_item.view.*

class HouseItemAdapter(val context: Context, val addresses: ArrayList<String>, val prices: ArrayList<String>, val houseImage: ArrayList<Int>) :
    RecyclerView.Adapter<HouseItemAdapter.ViewHolder>() {

    private var onClickListener: OnClickListener? = null

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
        var changeColor:Boolean = true
        holder.ivHeart.setOnClickListener {
            changeColor=onClick(holder.ivHeart,changeColor)
        }

        val address = addresses.get(position)
        holder.tvAddress.text = address

        val price = prices.get(position)
        holder.tvPrice.text = price

        val image = houseImage.get(position)
        holder.ivImage.setImageResource(image)

        holder.itemView.setOnClickListener {
            if (onClickListener != null){
                onClickListener!!.onClick()
            }
        }

        // Updating the background color according to the odd/even positions in list.
    }

    private fun onClick(heart: ImageView, changedColor:Boolean): Boolean{
        var changeColor: Boolean=changedColor
        changeColor = if (changeColor){
            heart.setColorFilter(ContextCompat.getColor(context, R.color.red))
            false
        }else{
            heart.setColorFilter(ContextCompat.getColor(context, R.color.white))
            true
        }

        return changeColor
    }
    override fun getItemCount(): Int {
        return addresses.size
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Holds the TextView that will add each item to
        val ivHeart = view.iv_heart
        val ivImage = view.iv_roomImg
        val tvAddress = view.tv_address
        val tvPrice = view.tv_price
        val cardViewItem = view.cv_house_list
    }

    interface OnClickListener {
        fun onClick()
    }

    fun setOnClickListener(onClickListener: OnClickListener){
        this.onClickListener = onClickListener
    }
}