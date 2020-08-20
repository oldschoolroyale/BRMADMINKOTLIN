package com.brm.brmadminkotlin.activities

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.agrawalsuneet.dotsloader.loaders.LazyLoader
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.brm.brmadminkotlin.R
import com.brm.brmadminkotlin.presenter.DataEditorPresenter
import com.brm.brmadminkotlin.view.DataEditorView
import kotlinx.android.synthetic.main.activity_data_base_editor.*

class DataBaseEditor : MvpAppCompatActivity(), DataEditorView {
    private lateinit var localArray: ArrayList<String>
    private lateinit var loader: LazyLoader
    private lateinit var saveButton: Button

    @InjectPresenter
    lateinit var presenter: DataEditorPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_base_editor)

        localArray = intent.getStringArrayListExtra("intentArray")!!
        loader = findViewById(R.id.activity_data_base_editor_dots_loader)
        saveButton = findViewById(R.id.activity_data_editor_button_save)

        activity_data_editor_name_edit.setText(localArray[4])
        activity_data_editor_address_edit.setText(localArray[5])

        saveButton.setOnClickListener {
            presenter.load(arrayOf(localArray[0], localArray[1],
                localArray[2], localArray[3], activity_data_editor_name_edit.text.toString(),
                activity_data_editor_address_edit.text.toString()))
        }
    }

    override fun startLoading() {
        loader.visibility = View.VISIBLE
        activity_data_edit_ll.visibility = View.INVISIBLE
    }

    override fun endLoading() {
        loader.visibility = View.INVISIBLE
    }

    override fun showError(error: String) {
        Toast.makeText(applicationContext, error, Toast.LENGTH_LONG).show()
    }

    override fun goBack(text: String) {
        Toast.makeText(applicationContext, text, Toast.LENGTH_LONG).show()
        finish()
    }
}