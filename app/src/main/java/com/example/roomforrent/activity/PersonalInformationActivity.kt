package com.example.roomforrent.activity

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.example.roomforrent.R
import kotlinx.android.synthetic.main.activity_personal_information.*
import java.util.*

class PersonalInformationActivity : AppCompatActivity() {
    //this is my changes
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personal_information)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, android.R.color.transparent)
        window.setBackgroundDrawable(resources.getDrawable(R.drawable.toolbarbg))
        setUpToolbar()

        //to change gender when click on gender edittext
        et_gender.setOnClickListener {
            val dialog = Dialog(this)
            dialog.setContentView(R.layout.gender_popup_window)
            dialog.setTitle("This is my custom dialog box")
            dialog.setCancelable(false)
            dialog.show()

            //when choose male radio button
            var rbm : RadioButton = dialog.findViewById(R.id.rb_male)
            rbm.setOnClickListener() {
                Toast.makeText(this,rbm.text, Toast.LENGTH_SHORT).show()
                var genderText=findViewById<TextView>(R.id.et_gender)
                genderText.text=rbm.text
                dialog.dismiss()

            }

            //when choose female radio button
            var rbf : RadioButton = dialog.findViewById(R.id.rb_female)
            rbf.setOnClickListener() {
                Toast.makeText(this,rbf.text, Toast.LENGTH_SHORT).show()
                var genderText=findViewById<TextView>(R.id.et_gender)
                genderText.text=rbf.text
                dialog.dismiss()
            }

            //when choose other radio button
            var other : RadioButton = dialog.findViewById(R.id.rb_other)
            other.setOnClickListener() {
                Toast.makeText(this,other.text, Toast.LENGTH_SHORT).show()
                var genderText=findViewById<TextView>(R.id.et_gender)
                genderText.text=other.text
                dialog.dismiss()
            }

            //when click on edit text birth date to change date of birth
            et_birth_date.setOnClickListener(){
                    view->
                clickDatePicker(view)
            }
        }

    }

    //toolbar
    private fun setUpToolbar(){
        setSupportActionBar(toolbar_personal_info)
        val actionBar=supportActionBar
        if(actionBar !=null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_backward_icon)
            actionBar.title = "Personal Information"
        }
        toolbar_personal_info.setNavigationOnClickListener{onBackPressed()}
    }

    //to show date picker
    fun clickDatePicker(view: View){
        val myCalendar= Calendar.getInstance()
        val year=myCalendar.get(Calendar.YEAR)
        val month=myCalendar.get(Calendar.MONTH)
        val day=myCalendar.get(Calendar.DAY_OF_MONTH)

        val dpd= DatePickerDialog(this,
            DatePickerDialog.OnDateSetListener
            {view, Selectedyear, Selectedmonth, SelecteddayOfMonth ->
                Toast.makeText(this,
                    "The choosen year is $Selectedyear, the month is $Selectedmonth, and the day is $SelecteddayOfMonth ",
                    Toast.LENGTH_LONG).show()
                val selectedDate="$SelecteddayOfMonth/${Selectedmonth+1}/$Selectedyear"
                var genderText=findViewById<TextView>(R.id.et_birth_date)
                genderText.text=selectedDate
                /*val sdf= SimpleDateFormat("dd/MM/yyyy",Locale.ENGLISH)
                val theDate=sdf.parse(selectedDate)


                val selectedDateInMinutes=theDate!!.time / 60000
                val currrentDate=sdf.parse(sdf.format(System.currentTimeMillis()))
                val currentDateToMinutes=currrentDate!!.time / 60000
                val differentInMinutes=currentDateToMinutes -selectedDateInMinutes
                tvSelectedDateInMinutes.setText(differentInMinutes.toString())*/
            },
            year,
            month,
            day )
        //dpd.datePicker.setMaxDate(Date().time - 86400000)
        dpd.show()
    }


}