package com.example.roomforrent.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.roomforrent.R
import com.example.roomforrent.models.House
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.favourite_item.view.*

class FavouriteItemAdapter(val context: Context):RecyclerView.Adapter<FavouriteItemAdapter.ViewHolder>() {

    var favouriteHouseList : ArrayList<House>? = ArrayList()
    private var onClickListener: FavouriteItemAdapter.OnClickListener? = null

    fun setData(favouriteHouseList : ArrayList<House>){
        this.favouriteHouseList = favouriteHouseList
        notifyDataSetChanged()
    }

    class ViewHolder(view : View): RecyclerView.ViewHolder(view) {
        val ivHeart = view.iv_heart

        fun bindView(item: House?) {
            itemView.tv_address.text = item?.house_ADDRESS.toString()
            itemView.tv_price.text = item?.rent.toString()
            Picasso.get().load("http://192.168.100.21:9097/image/house/${item?.house_ID}/1.jpg")
                .into(itemView.iv_roomImg)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return FavouriteItemAdapter.ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.favourite_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var changeColor: Boolean = true
        var item = favouriteHouseList?.get(position)
        holder.ivHeart.setOnClickListener{
            changeColor = onClick(holder.ivHeart, changeColor)
        }
        holder.bindView(item)
        holder.itemView.setOnClickListener {
            if (onClickListener != null) {
                if (item != null) {
                    onClickListener!!.onClick(position, item)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return favouriteHouseList?.size ?: 0
    }

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

    interface OnClickListener {
        fun onClick(position: Int, model: House)
    }

    fun setOnClickListener(onClickListener: FavouriteItemAdapter.OnClickListener) {
        this.onClickListener = onClickListener
    }
}