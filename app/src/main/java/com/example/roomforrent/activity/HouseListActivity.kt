package com.example.roomforrent.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomforrent.adapter.HouseItemAdapter
import com.example.roomforrent.R
import kotlinx.android.synthetic.main.activity_house_detail.*
import kotlinx.android.synthetic.main.activity_house_list.*

class HouseListActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_house_list)

        // Set the LayoutManager that this RecyclerView will use.
        recycler_view_items.layoutManager = LinearLayoutManager(this)

        val itemAdapter = HouseItemAdapter(this, getAddressesList(), getPricesList(), getImageList())
        recycler_view_items.adapter = itemAdapter

        itemAdapter.setOnClickListener(object : HouseItemAdapter.OnClickListener{
            override fun onClick() {
                startActivity(Intent(this@HouseListActivity, HouseDetailActivity::class.java))
            }
        })

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, android.R.color.transparent)
        window.setBackgroundDrawable(resources.getDrawable(R.drawable.toolbarbg))


        setupActionBar()
    }


    @SuppressLint("ResourceAsColor")
    private fun setupActionBar(){
        setSupportActionBar(toolBarMain)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_backward_icon)
            actionBar.title = "House List"
        }
        toolBarMain.setNavigationOnClickListener { onBackPressed() }
    }

    private fun getAddressesList(): ArrayList<String> {
        val list = ArrayList<String>()

        for (i in 1..6) {
            list.add("No-123B/Mya Thi Da 12st 13Ward/ San Gyaung")
        }

        return list
    }

    private fun getPricesList(): ArrayList<String> {
        val list = ArrayList<String>()

        for (i in 1..6) {
            list.add("$100/month")
        }

        return list
    }

    private fun getImageList(): ArrayList<Int> {
        val list = ArrayList<Int>()
        list.add(R.drawable.house2)
        list.add(R.drawable.house3)
        list.add(R.drawable.house4)
        list.add(R.drawable.house5)
        list.add(R.drawable.house6)
        list.add(R.drawable.house7)
        return list
    }


}