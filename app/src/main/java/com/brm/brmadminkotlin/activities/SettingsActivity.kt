package com.brm.brmadminkotlin.activities

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.brm.brmadminkotlin.R
import com.brm.brmadminkotlin.presenter.SettingsPresenter
import com.brm.brmadminkotlin.view.SettingsView
import kotlinx.android.synthetic.main.activity_settings.*


class SettingsActivity : MvpAppCompatActivity(), SettingsView {
    @InjectPresenter
    lateinit var presenter: SettingsPresenter
    private lateinit var token: String
    private lateinit var town: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        token = intent.getStringExtra("token")!!
    }

    override fun startLoading() {
       activity_settings_dots_loader.visibility = View.VISIBLE
        activity_settings_ll.visibility = View.GONE
    }

    override fun endLoading() {
      activity_settings_dots_loader.visibility = View.GONE
        activity_settings_ll.visibility = View.VISIBLE
    }

    override fun errorToast(errorString: String) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_LONG).show()
    }

    override fun multiSelectionAlert(key: String, list: Array<String>) {
        val finalArrayList : ArrayList<Int> = ArrayList()
        lateinit var dialog:AlertDialog
        val mBuilder: AlertDialog.Builder = AlertDialog.Builder(this)
        val arrayChecked  = BooleanArray(list.size)
        mBuilder.setTitle("Выберите")
        mBuilder.setMultiChoiceItems(list, arrayChecked) { _, which, isChecked ->
            if (isChecked){
                finalArrayList.add(which)
            }
            else{
                finalArrayList.remove(Integer.valueOf(which))
            }
        }
        mBuilder.setPositiveButton("Ок"){ _, _ ->
            var medications = ""
            for (i in finalArrayList.indices){
                medications += list[finalArrayList[i]]
                if (i != finalArrayList.size -1){
                    medications += if (key == "region"){
                        ", "
                    } else{
                        " "
                    }
                }
            }
            presenter.sendData(data = medications, token = token, key = key)
        }
        mBuilder.setNegativeButton("Отменить"){_, _ ->}
        dialog = mBuilder.create()
        dialog.show()
    }

    override fun singleSelectionAlert(key: String, list: Array<String>) {
        lateinit var dialog:AlertDialog
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Выберите")
        builder.setSingleChoiceItems(list, -1){ _, which ->
            presenter.sendData(data = list[which], token = token, key = key)
            if (key == "town") {town = list[which]}
            dialog.dismiss()
        }
        dialog = builder.create()
        dialog.show()
    }

    fun settingsClickButton(view: View) {
        when(view.id){
            R.id.activity_settings_medications_button -> multiSelectionAlert("medications", resources.getStringArray(R.array.categoryOfMedications))
            R.id.activity_settings_town_button -> singleSelectionAlert("town", resources.getStringArray(R.array.townCategory))
            R.id.activity_settings_region_button -> presenter.loadData(town = town)
            R.id.activity_settings_manager_button -> singleSelectionAlert("manager", resources.getStringArray(R.array.managerArray))
            R.id.activity_settings_ready_button -> finish()
        }
    }
}
