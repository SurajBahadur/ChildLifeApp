package com.app.lifegames

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import com.app.lifegames.data.language.LanguageList

import android.widget.Spinner
import com.app.lifegames.adapter.LanguageSpinnerAdapter





class Test : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.test)
        val countryNames = arrayOf("India", "China", "Australia", "Portugle", "America", "New Zealand")
        val flags = intArrayOf(R.drawable.uk, R.drawable.uk, R.drawable.uk, R.drawable.uk, R.drawable.uk, R.drawable.uk)


        var lang= LanguageList(R.drawable.uk,"UK")
        val spin = findViewById(R.id.simpleSpinner) as Spinner
        spin.setOnItemSelectedListener(this)

        val customAdapter = LanguageSpinnerAdapter(applicationContext, flags, countryNames)
        spin.adapter = customAdapter

    }
}
