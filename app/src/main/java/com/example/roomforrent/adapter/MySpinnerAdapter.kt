package com.example.roomforrent.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.roomforrent.R
import kotlinx.android.synthetic.main.spinner_item.view.*
import java.util.ArrayList

class MySpinnerAdapter(context: Context, arr: ArrayList<String>) : BaseAdapter(){

    val arr = arr
    val context = context
    override fun getCount(): Int {
        return arr.size
    }

    override fun getItem(position: Int): Any? {
        return null
    }

    override fun getItemId(position: Int): Long {
        return  0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = LayoutInflater.from(context).inflate(R.layout.spinner_item, null)
        view.txtItem.text = arr.get(position)
        return  view
    }

}
