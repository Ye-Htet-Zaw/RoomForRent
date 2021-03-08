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
import com.example.roomforrent.utils.Constants.categoryArr
import com.example.roomforrent.utils.Constants.periodArr
import com.example.roomforrent.utils.Constants.townshipArr
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_search.view.*

class SearchFragment : Fragment() {

    lateinit var categoryAdapter: MySpinnerAdapter
    lateinit var townshipAdapter: MySpinnerAdapter
    lateinit var periodAdapter: MySpinnerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        townshipAdapter = context?.let { createSpinnerAdapter(it, townshipArr) }!!
        categoryAdapter = context?.let { createSpinnerAdapter(it, categoryArr) }!!
        periodAdapter = context?.let { createSpinnerAdapter(it, periodArr) }!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        categorySpinner.adapter = categoryAdapter
        addressSpinner.adapter = townshipAdapter
        peroidSpinner.adapter = periodAdapter
        btn_serach.setOnClickListener {
            startActivity(Intent(context, HouseListActivity::class.java))

        }
    }

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    private fun createSpinnerAdapter(context: Context, arr: ArrayList<String>): MySpinnerAdapter {
        return MySpinnerAdapter(context, arr)
    }
}