package com.example.roomforrent.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.roomforrent.adapter.MySpinnerAdapter
import com.example.roomforrent.R
import com.example.roomforrent.activity.HouseListActivity
import kotlinx.android.synthetic.main.fragment_search.view.*

class SearchFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_search, container, false)

        //create spinner adapter
        val adapter = createSpinnerAdapter(context!!)

        v.categorySpinner.adapter = adapter
        v.amountSpinner.adapter = adapter
        v.addressSpinner.adapter = adapter
        v.peroidSpinner.adapter = adapter
        v.btn_serach.setOnClickListener {
            Log.i("SearchbtnClick","click")
            startActivity(Intent(context, HouseListActivity::class.java))
        }
        return v
    }

    private fun createSpinnerAdapter(context: Context): MySpinnerAdapter {
        var arr: ArrayList<String> = ArrayList()
        arr.add("Item1")
        arr.add("Item2")
        arr.add("Item3")
        arr.add("Item4")

        var itemAdapter = MySpinnerAdapter(context,arr)
        /*var adapter: ArrayAdapter<String> = ArrayAdapter<String>(
            context,
            android.R.layout.simple_spinner_item, arr
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item)*/
        return itemAdapter
    }
}