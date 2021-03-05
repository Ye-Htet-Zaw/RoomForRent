//Test
//Hello
package com.example.roomforrent.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.roomforrent.R
import kotlinx.android.synthetic.main.activity_contract_rules.*

class DescriptionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description)
        setupActionBar()
    }

    private fun setupActionBar(){
        setSupportActionBar(toolbar_contract_rules_activity)
        var actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24)
            actionBar.title = ""
        }

        toolbar_contract_rules_activity.setNavigationOnClickListener { onBackPressed() }
    }
}
