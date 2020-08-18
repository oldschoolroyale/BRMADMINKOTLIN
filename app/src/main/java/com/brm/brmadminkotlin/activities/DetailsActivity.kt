package com.brm.brmadminkotlin.activities

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.brm.brmadminkotlin.R
import com.brm.brmadminkotlin.adapter.StockAdapter
import com.brm.brmadminkotlin.model.StockModel
import com.brm.brmadminkotlin.presenter.DetailsPresenter
import com.brm.brmadminkotlin.view.DetailsView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_details.*
import java.lang.Exception

class DetailsActivity : MvpAppCompatActivity(), DetailsView, OnMapReadyCallback {

    private lateinit var token: String
    private lateinit var id: String
    private lateinit var year: String
    private lateinit var month: String
    private lateinit var day: String
    private var lat: Double = 29.757977
    private var lon: Double = 95.367439

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: StockAdapter

    @InjectPresenter
    lateinit var presenter: DetailsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        //Toolbar
        setSupportActionBar(activity_details_toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        //IntentExtras
        token = intent.getStringExtra("token")!!
        id = intent.getStringExtra("id")!!
        year = intent.getStringExtra("year")!!
        month = intent.getStringExtra("month")!!
        day = intent.getStringExtra("day")!!

        //Recycler
        adapter = StockAdapter()
        recyclerView = findViewById(R.id.activity_details_recycler)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(applicationContext, RecyclerView.HORIZONTAL, false)
        recyclerView.setHasFixedSize(true)

        presenter.load(token = token, id = id, year = year, month = month, day = day)
    }

    override fun startLoading() {
        activity_details_dots_loader.visibility = View.VISIBLE
        activity_details_constraint.visibility = View.INVISIBLE
        activity_details_recycler.visibility = View.INVISIBLE
    }

    override fun endLoading() {
        activity_details_dots_loader.visibility = View.INVISIBLE
    }

    override fun showError(error: String) {
        Toast.makeText(applicationContext, error, Toast.LENGTH_LONG).show()
    }

    override fun loadDetails(list: HashMap<String, String>) {
        activity_details_constraint.visibility = View.VISIBLE
        activity_details_name_text.text = list["name"]
        activity_details_address_text.text = list["address"]
        activity_details_start_text.text = list["time_start"]
        activity_details_end_text.text = list["time_end"]
        activity_details_medications_text.text = list["medications"]
        activity_details_comment_text.text = list["comment"]
        try {
            lon = list["lon"]!!.toDouble()
            lat = list["lat"]!!.toDouble()
        }
        catch (e: Exception){
            showError(error = "Ошибка: Геолокация мед. представителя выключенна!")
        }
        addMap()
    }

    override fun loadRecycler(list: ArrayList<StockModel>) {
        activity_details_recycler.visibility = View.VISIBLE
        adapter.setUpStockList(list)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        onBackPressed()
        return true
    }

    override fun onMapReady(p0: GoogleMap?) {
        val map = p0
        val location = LatLng(lat, lon)
        map?.addMarker(MarkerOptions().position(location).title("position"))
        map?.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 18f), 5000, null)
    }

    fun addMap(){
        val supportMapFragment = supportFragmentManager
            .findFragmentById(R.id.activity_details_map) as SupportMapFragment?
        supportMapFragment!!.getMapAsync(this)
    }
}