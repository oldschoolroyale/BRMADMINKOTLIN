package com.brm.brmadminkotlin.activities

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.brm.brmadminkotlin.R
import com.brm.brmadminkotlin.adapter.NotesAdapter
import com.brm.brmadminkotlin.model.NotesModel
import com.brm.brmadminkotlin.presenter.NotesPresenter
import com.brm.brmadminkotlin.view.NotesView
import com.google.android.material.appbar.AppBarLayout
import com.squareup.picasso.Picasso
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import kotlinx.android.synthetic.main.activity_notes.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class NotesActivity : MvpAppCompatActivity(), NotesView, DatePickerDialog.OnDateSetListener, AppBarLayout.OnOffsetChangedListener {
    private lateinit var token: String
    private lateinit var town: String
    private lateinit var region: String

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: NotesAdapter
    private var localArray: ArrayList<NotesModel> = ArrayList()
    //date
    private var yearString: String = SimpleDateFormat("yyyy").format(Calendar.getInstance().time)
    private var monthString: String = SimpleDateFormat("M").format(Calendar.getInstance().time)
    private var dayString: String = SimpleDateFormat("d").format(Calendar.getInstance().time)
    //ToolbarText
    private lateinit var toolbarTextView: TextView
    private var mMaxScrollSize: Int = 0
    private val PERCENTAGE_TO_ANIMATE_AVATAR = 20
    private var mIsAvatarShown: Boolean = true

    private lateinit var imageView: ImageView

    @InjectPresenter
    lateinit var presenter: NotesPresenter

    @SuppressLint("SimpleDateFormat", "SetTextI18n")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)
        //IntentExtras
        token = intent.getStringExtra("token")!!
        town = intent.getStringExtra("town")!!
        region = intent.getStringExtra("region")!!
        val image: String = intent.getStringExtra("image")!!
        val name: String = intent.getStringExtra("name")!!
        val medications: String = intent.getStringExtra("medications")!!
        //Toolbar
        setSupportActionBar(activity_notes_toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        toolbarTextView = findViewById(R.id.activity_notes_date_text)
        toolbarTextView.text = "$dayString/$monthString/$yearString"
        toolbarTextView.setOnClickListener{
            val now : Calendar = Calendar.getInstance()
            val dpd : DatePickerDialog = DatePickerDialog.newInstance(
                this,
                now[Calendar.YEAR], // Initial year selection
                // Initial year selection
                now[Calendar.MONTH], // Initial month selection
                // Initial month selection
                now[Calendar.DAY_OF_MONTH] // Inital day selection

            )
            dpd.show(supportFragmentManager, "Datepickerdialog")
        }

        //AppBar
        val appBarLayout: AppBarLayout = findViewById(R.id.activity_notes_app_bar)
        appBarLayout.addOnOffsetChangedListener(this)
        mMaxScrollSize = appBarLayout.totalScrollRange
        imageView = findViewById(R.id.activity_notes_circle_image)
        image?.let { url -> Picasso.with(this).load(url).placeholder(R.drawable.ic_baseline_person_outline_24).into(imageView)  }
        activity_notes_name.text = name
        activity_notes_name.setOnClickListener{
            nameTextClick(name = name)
        }
        activity_notes_region.text = region
        activity_notes_medications.text = medications

        //Recycler
        adapter = NotesAdapter(this)
        recyclerView = findViewById(R.id.activity_notes_recycler_view)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(applicationContext, RecyclerView.VERTICAL, false)
        recyclerView.setHasFixedSize(true)
        presenter.loadData(token = token, year = yearString, month = monthString, day = dayString)
    }

    private fun nameTextClick(name: String) {
        val builder = AlertDialog.Builder(this)
        val view = LayoutInflater.from(this).inflate(R.layout.nav_alert_dialog, null)
        val editText: EditText = view.findViewById(R.id.nav_alert_dialog_name_edit_text)
        editText.setText(name)
        builder.setMessage("Имя")
            .setView(view)
            .setPositiveButton("Ок"
            ) { dialog, which -> presenter.setName(name = editText.text.toString(), token = token)}
            .setNegativeButton("Отменить", null)
            .setCancelable(true)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.notes_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.notes_menu_settings -> {
                val intent = Intent(applicationContext, SettingsActivity::class.java)
                intent.putExtra("token", token)
                finish()
                startActivity(intent)
            }
            R.id.notes_menu_data_base -> {
                val intent = Intent(applicationContext, DataBaseActivity::class.java)
                intent.putExtra("token", token)
                intent.putExtra("town", town)
                intent.putExtra("region", region)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun startLoading() {
        activity_notes_dots_loader.visibility = View.VISIBLE
        activity_notes_no_data_constraint.visibility = View.INVISIBLE
        recyclerView.visibility = View.INVISIBLE
    }

    override fun endLoading() {
        activity_notes_dots_loader.visibility = View.INVISIBLE
    }

    override fun showError(error: String) {
        Toast.makeText(applicationContext, error, Toast.LENGTH_LONG).show()
    }

    override fun loadEmptyList() {
        activity_notes_no_data_constraint.visibility = View.VISIBLE
    }

    override fun loadList(list: ArrayList<NotesModel>) {
        activity_notes_recycler_view.visibility = View.VISIBLE
        adapter.setUpUserList(list)
        localArray.clear()
        localArray.addAll(list)
    }

    override fun itemClick(position: Int) {
       val intent = Intent(applicationContext, DetailsActivity::class.java)
        intent.putExtra("token", token)
        intent.putExtra("id", localArray[position].id)
        intent.putExtra("year", yearString)
        intent.putExtra("month", monthString)
        intent.putExtra("day", dayString)
        startActivity(intent)
    }

    override fun onDateSet(view: DatePickerDialog?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        yearString = year.toString()
        dayString = dayOfMonth.toString()
        monthString = (monthOfYear + 1).toString()

        toolbarTextView.text = "$dayString/$monthString/$yearString"
        presenter.loadData(token = token, year = yearString, month = monthString, day = dayString)
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
        if (mMaxScrollSize == 0) mMaxScrollSize = appBarLayout!!.totalScrollRange

        val percentage: Int = Math.abs(verticalOffset) * 100 / mMaxScrollSize

        if (percentage >= PERCENTAGE_TO_ANIMATE_AVATAR && mIsAvatarShown) {
            mIsAvatarShown = false
            imageView.animate()
                .scaleY(0f).scaleX(0f)
                .setDuration(200)
                .start()
        }

        if (percentage <= PERCENTAGE_TO_ANIMATE_AVATAR && !mIsAvatarShown) {
            mIsAvatarShown = true
            imageView.animate()
                .scaleY(1f).scaleX(1f)
                .start()
        }
    }
}