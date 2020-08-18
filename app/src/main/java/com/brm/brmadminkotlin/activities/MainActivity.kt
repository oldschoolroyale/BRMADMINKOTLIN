package com.brm.brmadminkotlin.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.agrawalsuneet.dotsloader.loaders.LazyLoader
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.brm.brmadminkotlin.R
import com.brm.brmadminkotlin.adapter.UsersAdapter
import com.brm.brmadminkotlin.model.UsersModel
import com.brm.brmadminkotlin.presenter.MainPresenter
import com.brm.brmadminkotlin.view.MainView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : MvpAppCompatActivity(), MainView {

    private lateinit var mProgressBar: LazyLoader
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: UsersAdapter
    private lateinit var mSpinner: Spinner
    private var localList: ArrayList<UsersModel> = ArrayList()
    private lateinit var mLinearLayout: LinearLayout


    @InjectPresenter
    lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        mProgressBar = findViewById(R.id.activity_main_dots_loader)
        mRecyclerView = findViewById(R.id.activity_main_recycler_view)
        mSpinner = findViewById(R.id.activity_main_spinner)
        mLinearLayout = findViewById(R.id.activity_main_linear_layout)

        val mAuth = FirebaseAuth.getInstance().currentUser
        val currentUser = mAuth?.uid

        setSupportActionBar(activity_main_toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        if (currentUser!! == "HWAWZvCDvyYOX89VEuBt6EePUvG2"){
            mSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    when(parent?.id){
                        R.id.activity_main_spinner -> {
                            presenter.checkManager(parent.getItemAtPosition(position).toString())
                        }
                    }
                }
            }
        }
        else{
            mSpinner.visibility = View.GONE
            presenter.load(currentUser)
        }



        val categoryAdapter : ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(this, R.array.managerArray, R.layout.custom_spinner)
        categoryAdapter.setDropDownViewResource(R.layout.custom_spinner_dropdown)
        mSpinner.adapter = categoryAdapter

        mAdapter = UsersAdapter(this)

        mRecyclerView.adapter = mAdapter
        mRecyclerView.layoutManager = LinearLayoutManager(applicationContext, RecyclerView.VERTICAL, false)
        mRecyclerView.setHasFixedSize(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.main_menu_exit -> logOut()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun logOut() {
        FirebaseAuth.getInstance().signOut()
        val intent = Intent(applicationContext, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        finish()
        startActivity(intent)
    }

    override fun startLoading() {
        mProgressBar.visibility = View.VISIBLE
        mRecyclerView.visibility = View.GONE
        mLinearLayout.visibility = View.GONE
    }

    override fun endLoading() {
        mProgressBar.visibility = View.GONE
    }

    override fun loadData(list: ArrayList<UsersModel>) {
        mRecyclerView.visibility = View.VISIBLE
        mAdapter.setUpUserList(list)
        localList.clear()
        localList.addAll(list)
    }

    override fun loadEmptyList() {
        mLinearLayout.visibility = View.VISIBLE
    }

    override fun showError(errorString: String) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_LONG).show()
    }

    override fun itemClick(position: Int) {
        val intent = Intent(applicationContext, NotesActivity::class.java)
        intent.putExtra("token", localList[position].token)
        intent.putExtra("town", localList[position].town)
        intent.putExtra("region", localList[position].region)
        intent.putExtra("image", localList[position].image)
        intent.putExtra("medications", localList[position].medications)
        intent.putExtra("name", localList[position].name)
        startActivity(intent)
    }
}
