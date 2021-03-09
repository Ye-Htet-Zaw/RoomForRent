package com.example.roomforrent.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.roomforrent.R
import com.example.roomforrent.utils.Constants
import kotlinx.android.synthetic.main.activity_contract_rules.*
import kotlinx.android.synthetic.main.activity_house_information.*

class ContractRulesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contract_rules)
        if (intent.hasExtra(Constants.CONTRACT_RULE)){
            val contractRule = intent.getStringExtra(Constants.CONTRACT_RULE).toString()
            contract_rules.text = contractRule
        }

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