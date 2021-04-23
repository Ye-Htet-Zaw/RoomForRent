/**
 *
 * PostHouseFragment
 *
 * 2021/04/22 HNT Create New
 *
 * insert house information into house table
 */
package com.example.roomforrent.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.roomforrent.R
import com.example.roomforrent.adapter.MySpinnerAdapter
import com.example.roomforrent.utils.Constants
import kotlinx.android.synthetic.main.fragment_post_house.*

class PostHouseFragment : Fragment() {

    lateinit var categoryAdapter: MySpinnerAdapter
    lateinit var townshipAdapter: MySpinnerAdapter
    lateinit var periodAdapter: MySpinnerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        townshipAdapter = context?.let { createSpinnerAdapter(it, Constants.townshipArr) }!!
        categoryAdapter = context?.let { createSpinnerAdapter(it, Constants.categoryArr) }!!
        periodAdapter = context?.let { createSpinnerAdapter(it, Constants.periodArr) }!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //set Adapter for spinner
        ph_categorySpinner.adapter = categoryAdapter
        ph_addressSpinner.adapter = townshipAdapter
        ph_periodSpinner.adapter = periodAdapter
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_post_house, container, false)
    }

    private fun createSpinnerAdapter(context: Context, arr: ArrayList<String>): MySpinnerAdapter {
        return MySpinnerAdapter(context, arr)
    }
}