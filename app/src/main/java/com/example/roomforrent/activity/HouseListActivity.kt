package com.example.roomforrent.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomforrent.adapter.HouseItemAdapter
import com.example.roomforrent.R
import com.example.roomforrent.models.HouseList
import com.example.roomforrent.services.HouseListService
import com.example.roomforrent.services.ServiceBuilder
import kotlinx.android.synthetic.main.activity_house_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HouseListActivity : AppCompatActivity() {

    lateinit var adapter: HouseItemAdapter

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_house_list)

        initAdapter()

        loadAccount()
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, android.R.color.transparent)
        window.setBackgroundDrawable(resources.getDrawable(R.drawable.toolbarbg))


        setupActionBar()
    }

    private fun initAdapter() {
        adapter = HouseItemAdapter(this)

        // Set the LayoutManager that this RecyclerView will use.
        recycler_view_items.layoutManager = LinearLayoutManager(this)
        recycler_view_items.adapter = adapter

        adapter.setOnClickListener(object : HouseItemAdapter.OnClickListener{
            override fun onClick() {
                startActivity(Intent(this@HouseListActivity, HouseDetailActivity::class.java))
            }
        })
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

    private fun loadAccount() {
        //initiate the service
        val destinationService  = ServiceBuilder.buildService(HouseListService::class.java)
        val requestCall =destinationService.getHouseList()
        //make network call asynchronously
        requestCall.enqueue(object : Callback<List<HouseList>> {
            override fun onResponse(call: Call<List<HouseList>>, response: Response<List<HouseList>>) {
                Log.d("Response", "onResponse: ${response.body()}")
                if (response.isSuccessful){
                    val houseList  = response.body()!!
                    Log.d("Response", "houseList size : ${houseList.size}")
                    adapter.setData(response.body()!! as ArrayList<HouseList>)
                }else{
                    Toast.makeText(this@HouseListActivity, "Something went wrong ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<List<HouseList>>, t: Throwable) {
                Toast.makeText(this@HouseListActivity, "Something went wrong $t", Toast.LENGTH_SHORT).show()
            }
        })
    }

}