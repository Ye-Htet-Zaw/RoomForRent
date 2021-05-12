/**
 *
 * SearchFragment
 *
 * 2021/03/8 KMMN Create New
 *
 * search wanted rooms by filling room information
 */
package com.example.roomforrent.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import com.example.roomforrent.R
import com.example.roomforrent.activity.ChooseAddressActivity
import com.example.roomforrent.activity.HouseListActivity
import com.example.roomforrent.adapter.MySpinnerAdapter
import com.example.roomforrent.utils.Constants.Amount
import com.example.roomforrent.utils.Constants.CALLAPI
import com.example.roomforrent.utils.Constants.GetAllRoomList
import com.example.roomforrent.utils.Constants.GetRoomListByAddressAndAmountAndPeriod
import com.example.roomforrent.utils.Constants.GetRoomListByAll
import com.example.roomforrent.utils.Constants.GetRoomListByAmount
import com.example.roomforrent.utils.Constants.GetRoomListByAmountAndPeriod
import com.example.roomforrent.utils.Constants.GetRoomListByCategory
import com.example.roomforrent.utils.Constants.GetRoomListByCategoryAndAddressAndAmount
import com.example.roomforrent.utils.Constants.GetRoomListByCategoryAndAddressAndPeriod
import com.example.roomforrent.utils.Constants.GetRoomListByCategoryAndAmount
import com.example.roomforrent.utils.Constants.GetRoomListByCategoryAndAmountAndPeriod
import com.example.roomforrent.utils.Constants.GetRoomListByCategoryAndPeriod
import com.example.roomforrent.utils.Constants.GetRoomListByCategoryAndTownShip
import com.example.roomforrent.utils.Constants.GetRoomListByPeriod
import com.example.roomforrent.utils.Constants.GetRoomListByTownShipAndPeriod
import com.example.roomforrent.utils.Constants.GetRoomListByTownShipAndRent
import com.example.roomforrent.utils.Constants.GetRoomListByTownship
import com.example.roomforrent.utils.Constants.SelectedAddress
import com.example.roomforrent.utils.Constants.SelectedCategory
import com.example.roomforrent.utils.Constants.SelectedPeroid
import com.example.roomforrent.utils.Constants.categoryArr
import com.example.roomforrent.utils.Constants.periodArr
import com.example.roomforrent.utils.Constants.townshipArr
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : BaseFragment() {

    lateinit var categoryAdapter: MySpinnerAdapter
    lateinit var townshipAdapter: MySpinnerAdapter
    lateinit var periodAdapter: MySpinnerAdapter
    var selectedCategory: String = ""
    var selectedAddress: String = ""
    var selectedPeroid: String = ""
    var amount: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        townshipAdapter = context?.let { createSpinnerAdapter(it, townshipArr) }!!
        categoryAdapter = context?.let { createSpinnerAdapter(it, categoryArr) }!!
        periodAdapter = context?.let { createSpinnerAdapter(it, periodArr) }!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //set Adapter for spinner
        categorySpinner.adapter = categoryAdapter
        addressSpinner.adapter = townshipAdapter
        peroidSpinner.adapter = periodAdapter

        categorySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedCategory = categoryArr.get(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }

        addressSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedAddress = townshipArr.get(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }

        peroidSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedPeroid = periodArr.get(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }

        btn_serach.setOnClickListener {
            amount = edt_amount.text.toString().trim()
            if (selectedCategory.equals("Select") && selectedAddress.equals("Select")
                && selectedPeroid.equals("Select") && amount.equals("")
            ) {
                showHouseList(GetAllRoomList)
            } else if (!selectedCategory.equals("Select") && selectedAddress.equals("Select")
                && selectedPeroid.equals("Select") && amount.equals("")
            ) {
                showHouseList(GetRoomListByCategory)
            } else if (selectedCategory.equals("Select") && !selectedAddress.equals("Select")
                && selectedPeroid.equals("Select") && amount.equals("")
            ) {
                showHouseList(GetRoomListByTownship)
            } else if (selectedCategory.equals("Select") && selectedAddress.equals("Select")
                && !selectedPeroid.equals("Select") && amount.equals("")
            ) {
                showHouseList(GetRoomListByPeriod)
            } else if (selectedCategory.equals("Select") && selectedAddress.equals("Select")
                && selectedPeroid.equals("Select") && !amount.equals("")
            ) {
                showHouseList(GetRoomListByAmount)
            } else if (!selectedCategory.equals("Select") && !selectedAddress.equals("Select")
                && selectedPeroid.equals("Select") && amount.equals("")
            ) {
                showHouseList(GetRoomListByCategoryAndTownShip)
            } else if (!selectedCategory.equals("Select") && selectedAddress.equals("Select")
                && !selectedPeroid.equals("Select") && amount.equals("")
            ) {
                showHouseList(GetRoomListByCategoryAndPeriod)
            } else if (!selectedCategory.equals("Select") && selectedAddress.equals("Select")
                && selectedPeroid.equals("Select") && !amount.equals("")
            ) {
                showHouseList(GetRoomListByCategoryAndAmount)
            } else if (selectedCategory.equals("Select") && !selectedAddress.equals("Select")
                && selectedPeroid.equals("Select") && !amount.equals("")
            ) {
                showHouseList(GetRoomListByTownShipAndRent)
            } else if (selectedCategory.equals("Select") && !selectedAddress.equals("Select")
                && !selectedPeroid.equals("Select") && amount.equals("")
            ) {
                showHouseList(GetRoomListByTownShipAndPeriod)
            } else if (selectedCategory.equals("Select") && selectedAddress.equals("Select")
                && !selectedPeroid.equals("Select") && !amount.equals("")
            ) {
                showHouseList(GetRoomListByAmountAndPeriod)
            } else if (!selectedCategory.equals("Select") && !selectedAddress.equals("Select")
                && !selectedPeroid.equals("Select") && amount.equals("")
            ) {
                showHouseList(GetRoomListByCategoryAndAddressAndPeriod)
            } else if (!selectedCategory.equals("Select") && !selectedAddress.equals("Select")
                && selectedPeroid.equals("Select") && !amount.equals("")
            ) {
                showHouseList(GetRoomListByCategoryAndAddressAndAmount)
            } else if (!selectedCategory.equals("Select") && selectedAddress.equals("Select")
                && !selectedPeroid.equals("Select") && !amount.equals("")
            ) {
                showHouseList(GetRoomListByCategoryAndAmountAndPeriod)
            } else if (selectedCategory.equals("Select") && !selectedAddress.equals("Select")
                && !selectedPeroid.equals("Select") && !amount.equals("")
            ) {
                showHouseList(GetRoomListByAddressAndAmountAndPeriod)
            } else {
                showHouseList(GetRoomListByAll)
            }
        }
    }

    /**
     * load  house list by clicking search button
     */
    private fun showHouseList(serviceName: String) {
        if(checkConnection()){
            val intent = Intent(context, HouseListActivity::class.java)
            intent.putExtra(CALLAPI, serviceName)
            intent.putExtra(SelectedCategory, selectedCategory)
            intent.putExtra(SelectedAddress, selectedAddress)
            intent.putExtra(SelectedPeroid, selectedPeroid)
            intent.putExtra(Amount, amount)
            startActivity(intent)
        }
        //startActivity(Intent(context,ChooseAddressActivity::class.java))
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