/**
 *
 * PostHouseFragment
 *
 * 2021/04/22 HNT Create New
 *
 * insert house information into house table
 */
package com.example.roomforrent.fragment

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.RadioButton
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import com.example.roomforrent.R
import com.example.roomforrent.adapter.MySpinnerAdapter
import com.example.roomforrent.models.House
import com.example.roomforrent.utils.Constants
import kotlinx.android.synthetic.main.activity_house_detail.*
import kotlinx.android.synthetic.main.activity_personal_information.*
import kotlinx.android.synthetic.main.fragment_post_house.*
import kotlinx.android.synthetic.main.fragment_search.*
import java.util.*
import kotlin.collections.ArrayList

class PostHouseFragment : Fragment() {

    lateinit var categoryAdapter: MySpinnerAdapter
    lateinit var townshipAdapter: MySpinnerAdapter
    lateinit var periodAdapter: MySpinnerAdapter
    var selectedCategory: String = ""
    var selectedAddress: String = ""
    var selectedPeriod: String = ""

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

        ph_categorySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedCategory = Constants.categoryArr.get(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }

        ph_addressSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedAddress = Constants.townshipArr.get(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }

        ph_periodSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedPeriod = Constants.periodArr.get(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }
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

    private fun checkHouseData(){
        var createHouseLiveDate: LiveData<House>?=null

        var categoryId = selectedCategory
        var houseAddress = et_address.text.toString().trim()
        var township = selectedAddress
        var noOfGuest = et_guest.text.toString().trim()
        var noOfRoom = et_room.text.toString().trim()
        var noOfBath = et_bath.text.toString().trim()
        var noOfToilet = et_toilet.text.toString().trim()
        var area = et_area.text.toString().trim()
        var noOfFloor = et_floor.text.toString().trim()
        var noOfAircon = et_aircon.text.toString().trim()
        var wifi: Int= onRadioButtonClicked(rg_radio)
        var phoneOne = et_phone_num1.text.toString().trim()
        var phoneTwo = et_phone_num2.text.toString().trim()
        TODO("add available date and ....")

        var rent = et_rent.text.toString().trim().toInt()
        var deposit = et_deposit.text.toString().trim().toInt()
        var recommendedPoint = et_recommended.text.toString().trim()
        var contractRule = et_contract_rule.text.toString().trim()
        var period = selectedPeriod
    }

//    private fun clickDataPicker(view: View) {
//        val c = Calendar.getInstance()
//        val year =
//            c.get(Calendar.YEAR) // Returns the value of the given calendar field. This indicates YEAR
//        val month = c.get(Calendar.MONTH) // This indicates the Month
//        val day = c.get(Calendar.DAY_OF_MONTH) // This indicates the Day
//        val dpd = DatePickerDialog(
//            ,
//            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
//                val selectedDate = "$dayOfMonth/${monthOfYear + 1}/$year"
//
//                // Selected date it set to the TextView to make it visible to user.
//                et_available_date.setText(selectedDate)
//            },
//            year,
//            month,
//            day
//        )
//        // 86400000 is milliseconds of 24 Hours. Which is used to restrict the user to select today and future day.
//        dpd.datePicker.setMaxDate(Date().time - 86400000)
//        dpd.show() // It is used to show the datePicker Dialog.
//    }
    private fun onRadioButtonClicked(view: View): Int {
        var wifi = 0
        if (view is RadioButton) {
            // Is the button now checked?
            val checked = view.isChecked

            // Check which radio button was clicked
            when (view.getId()) {
                R.id.rb_yes ->
                    if (checked) {
                        wifi = 1
                    }
                R.id.rb_no ->
                    if (checked) {
                        wifi = 0
                    }
            }
        }
        return wifi
    }
}