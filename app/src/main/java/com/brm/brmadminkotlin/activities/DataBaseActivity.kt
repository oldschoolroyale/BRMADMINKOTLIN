package com.brm.brmadminkotlin.activities

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.brm.brmadminkotlin.R
import com.brm.brmadminkotlin.adapter.DataBaseAdapter
import com.brm.brmadminkotlin.model.DataBaseModel
import com.brm.brmadminkotlin.presenter.DataBasePresenter
import com.brm.brmadminkotlin.view.DataBaseView
import kotlinx.android.synthetic.main.activity_data_base.*

class DataBaseActivity : MvpAppCompatActivity(), DataBaseView {

    @InjectPresenter
    lateinit var presenter: DataBasePresenter

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: DataBaseAdapter
    private val localArray: ArrayList<DataBaseModel> = ArrayList()
    private lateinit var regionArray: List<String>
    private lateinit var spinner: Spinner

    private lateinit var town: String
    private lateinit var region: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_base)

        //Toolbar
        setSupportActionBar(activity_data_base_toolbar)
        val toolbar : Toolbar = findViewById(R.id.activity_data_base_toolbar)
        spinner = toolbar.findViewById(R.id.activity_data_base_spinner)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        //getIntent
        town = intent.getStringExtra("town")!!
        regionArray = intent.getStringExtra("region")!!.split(", ")
        region = regionArray[0]

        //Spinner Adapter
        val categoryAdapter : ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(this, R.array.dataCategory, R.layout.custom_spinner)
        categoryAdapter.setDropDownViewResource(R.layout.custom_spinner_dropdown)
        spinner.adapter = categoryAdapter

        //RecyclerView
        adapter = DataBaseAdapter(this)
        recyclerView = findViewById(R.id.activity_data_base_recyclerView)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(applicationContext, RecyclerView.VERTICAL, false)
        recyclerView.setHasFixedSize(true)

        //Spinner Click
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(applicationContext, "Ничего не выбранно!", Toast.LENGTH_LONG).show()
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when(parent?.id){
                    R.id.activity_data_base_spinner -> {
                        presenter.startLoading(type = parent.getItemAtPosition(position).toString(), town = town, region = region)
                    }
                }
            }
        }

        activity_data_base_fb.setOnClickListener{
            singleSelection(regionArray.toTypedArray())
        }
    }

    private fun singleSelection(list: Array<String>) {
        lateinit var dialog: AlertDialog
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Выберите")
        builder.setSingleChoiceItems(list, -1){ _, which ->
            region = list[which]
            if (spinner.selectedItemPosition == 0){
                presenter.startLoading("Doctor", town = town, region = region)
            }
            else{
                spinner.setSelection(0)
            }
            dialog.dismiss()
        }
        dialog = builder.create()
        dialog.show()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    override fun startLoading() {
        activity_data_base_dots_loader.visibility = View.VISIBLE
        activity_data_base_no_data_constraint.visibility = View.INVISIBLE
        activity_data_base_recyclerView.visibility = View.INVISIBLE
    }

    override fun endLoading() {
        activity_data_base_dots_loader.visibility = View.INVISIBLE
    }

    override fun showError(error: String) {
        Toast.makeText(applicationContext, error, Toast.LENGTH_LONG).show()
    }

    override fun loadList(list: ArrayList<DataBaseModel>) {
        activity_data_base_recyclerView.visibility = View.VISIBLE
        adapter.loadList(list)
        localArray.clear()
        localArray.addAll(list)
    }

    override fun loadEmptyList() {
        activity_data_base_no_data_constraint.visibility = View.VISIBLE
    }

    override fun itemClick(position: Int) {
        val intent = Intent(applicationContext, DataBaseEditor::class.java)
        val stringArray = arrayListOf(town, region, spinner.selectedItem.toString(), localArray[position].id, localArray[position].name!!,
            localArray[position].address!!)
        intent.putStringArrayListExtra("intentArray", stringArray)
        startActivity(intent)
        finish()
    }
}