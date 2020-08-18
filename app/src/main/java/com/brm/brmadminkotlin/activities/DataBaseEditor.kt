package com.brm.brmadminkotlin.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.brm.brmadminkotlin.R
import kotlinx.android.synthetic.main.activity_data_base_editor.*

class DataBaseEditor : AppCompatActivity() {
    private lateinit var localArray: ArrayList<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_base_editor)

        localArray = intent.getStringArrayListExtra("intentArray")!!

        activity_data_editor_name_edit.setText(localArray[0])
        activity_data_editor_address_edit.setText(localArray[1])
    }
}