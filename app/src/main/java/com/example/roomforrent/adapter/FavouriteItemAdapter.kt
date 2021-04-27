/**
 *
 * FavouriteItemAdapter
 *
 * 2021/04/20 KMMN Create New
 *
 * Adapter to show favourite list
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
import com.example.roomforrent.services.FavouriteService
import com.example.roomforrent.services.ServiceBuilder
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.favourite_item.view.*
import kotlinx.android.synthetic.main.fragment_favourite.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FavouriteItemAdapter(val context: Context):RecyclerView.Adapter<FavouriteItemAdapter.ViewHolder>() {

    var favouriteHouseList : ArrayList<House>? = ArrayList()
    private var onClickListener: FavouriteItemAdapter.OnClickListener? = null
    private var onHeartClickListener : FavouriteItemAdapter.OnHeartClickListener?=null
    val favouriteService = ServiceBuilder.buildService(FavouriteService::class.java)
    var userId :String=""
    fun setData(favouriteHouseList : ArrayList<House>,userId : String){
        this.favouriteHouseList = favouriteHouseList
        this.userId = userId
        notifyDataSetChanged()
    }

    interface OnHeartClickListener{
        fun onClick(houseList : ArrayList<House>)
    }

    fun setOnHeartClickListener(onHeartClickListener: FavouriteItemAdapter.OnHeartClickListener){
        this.onHeartClickListener = onHeartClickListener
    }
    class ViewHolder(view : View): RecyclerView.ViewHolder(view) {
        val ivHeart = view.iv_heart

        fun bindView(item: House?) {
            itemView.tv_address.text = item?.house_ADDRESS.toString()
            itemView.tv_price.text = item?.rent.toString()
            Picasso.get().load("http://192.168.100.4:9090/image/house/${item?.house_ID}/1.jpg")
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
        holder.ivHeart.setColorFilter(ContextCompat.getColor(context, R.color.red))
        holder.ivHeart.setOnClickListener{
            changeColor = onClick(holder.ivHeart, changeColor)
            if(!changeColor){
                val callDeleteFavouriteItem = favouriteService.deleteFavouriteWithUserAndHouseId(userId,item!!.house_ID)
                callDeleteFavouriteItem.enqueue(object : Callback<List<House>>{
                    override fun onResponse(
                        call: Call<List<House>>,
                        response: Response<List<House>>
                    ) {
                        setData(response.body() as ArrayList<House>,userId)
                        if (onHeartClickListener != null) {
                            onHeartClickListener!!.onClick(response.body() as ArrayList<House>)
                        }
                    }

                    override fun onFailure(call: Call<List<House>>, t: Throwable) {

                    }

                })
            }
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
            heart.setColorFilter(ContextCompat.getColor(context, R.color.white))
            false
        } else {
            heart.setColorFilter(ContextCompat.getColor(context, R.color.red))
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