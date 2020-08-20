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

        presenter.startPrepare(localArray.toTypedArray())
    }

    override fun startLoading() {
        loader.visibility = View.VISIBLE
        activity_data_edit_ll.visibility = View.INVISIBLE
    }

    override fun endLoading() {
        loader.visibility = View.INVISIBLE
    }

    override fun prepare(array: Array<String>) {
        TODO("Not yet implemented")
    }

    override fun showError(error: String) {
        Toast.makeText(applicationContext, error, Toast.LENGTH_LONG).show()
    }

    override fun goBack(text: String) {
        Toast.makeText(applicationContext, text, Toast.LENGTH_LONG).show()
        finish()
    }
}