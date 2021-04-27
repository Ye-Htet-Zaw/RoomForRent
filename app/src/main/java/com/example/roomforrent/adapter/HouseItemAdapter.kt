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
import android.content.SharedPreferences
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.roomforrent.R
import com.example.roomforrent.models.Favourite
import com.example.roomforrent.models.House
import com.example.roomforrent.services.FavouriteService
import com.example.roomforrent.services.ServiceBuilder
import com.example.roomforrent.utils.Constants.USERID
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.house_item.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

class HouseItemAdapter(val context: Context) :
    RecyclerView.Adapter<HouseItemAdapter.ViewHolder>() {

    private var onClickListener: OnClickListener? = null
    private var houseData: ArrayList<House>? = null
    val favouriteService = ServiceBuilder.buildService(FavouriteService::class.java)


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
        val share: SharedPreferences = context?.getSharedPreferences(
            "myPreference",
            Context.MODE_PRIVATE
        )!!
        val model = houseData!![position]
        var changeColor: Boolean = true
        var isLogin = share.getBoolean("isLogin", false)
        var userId: String? = ""
        holder.ivHeart.setOnClickListener {
            changeColor = onClick(holder.ivHeart, changeColor)
            if (isLogin) {
                userId = share.getString(USERID, "")
                when (changeColor) {
                    true -> {
                        Log.i("TestFavourite", "unfavourite")
                        val callDeleteFavouriteItem = favouriteService.deleteFavouriteWithUserAndHouseId(userId!!, model.house_ID)
                        callDeleteFavouriteItem.enqueue(object :Callback<List<House>>{
                            override fun onResponse(
                                call: Call<List<House>>,
                                response: Response<List<House>>
                            ) {
                                Log.i("TestFavourite", "success delete fav")
                            }

                            override fun onFailure(call: Call<List<House>>, t: Throwable) {
                                Log.i("TestFavourite", "success delete fav")
                            }

                        })

                    }
                    false -> {
                        Log.i("TestFavourite", "favourite")
                        val currentDate = SimpleDateFormat(
                            "yyyy-MM-dd",
                            Locale.getDefault()
                        ).format(Date())
                        var favouriteRoom = Favourite(
                            user_id = userId!!,
                            house_id = model.house_ID,
                            creator_id = userId!!,
                            create_dateTime = currentDate
                        )
                        val callSaveFavourite =
                            favouriteService.saveFavouriteInfo(favouriteRoom)
                        callSaveFavourite.enqueue(object : Callback<List<Favourite>> {
                            override fun onResponse(
                                call: Call<List<Favourite>>,
                                response: Response<List<Favourite>>
                            ) {
                                Log.i("TestFavourite", "success save fav")
                            }

                            override fun onFailure(call: Call<List<Favourite>>, t: Throwable) {
                                Log.i("TestFavourite", "fail save fav")
                            }

                        })
                    }
                }
            }

        }

        Log.d("Response", "List Count :${houseData?.size} ")
        val item = houseData?.get(position)
        if (isLogin) {
            userId = share.getString(USERID, "")
            val callGetFavouriteItem = favouriteService.getFavouriteId(userId!!, item!!.house_ID)
            callGetFavouriteItem.enqueue(object : Callback<Favourite> {
                override fun onResponse(call: Call<Favourite>, response: Response<Favourite>) {
                    if (response.body() == null) {
                        changeColor = true
                        holder.ivHeart.setColorFilter(
                            ContextCompat.getColor(
                                context,
                                R.color.white
                            )
                        )
                    } else {
                        changeColor = false
                        holder.ivHeart.setColorFilter(ContextCompat.getColor(context, R.color.red))
                    }
                }

                override fun onFailure(call: Call<Favourite>, t: Throwable) {

                }

            })
        }
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
            Picasso.get().load("http://192.168.1.3:9090/image/house/${item?.house_ID}/1.jpg")
                .into(itemView.iv_roomImg)
        }

    }

    interface OnClickListener {
        fun onClick(position: Int, model: House)
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }
}