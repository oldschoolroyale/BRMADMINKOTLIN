package com.brm.brmadminkotlin.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import br.com.simplepass.loadingbutton.customViews.CircularProgressButton
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.brm.brmadminkotlin.R
import com.brm.brmadminkotlin.presenter.LoginPresenter
import com.brm.brmadminkotlin.view.LoginView
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : MvpAppCompatActivity(), LoginView {

    private lateinit var mAnimButton: CircularProgressButton
    private lateinit var mLoginEditText: EditText
    private lateinit var mPasswordEditText: EditText

    private lateinit var mCurrentUser: String

    @InjectPresenter
    lateinit var presenter: LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val mFirebaseAuth = FirebaseAuth.getInstance().currentUser
        if (mFirebaseAuth != null) {
            mCurrentUser = mFirebaseAuth.uid
            sendHome()
        }

        mAnimButton = findViewById(R.id.activity_login_btn)
        mLoginEditText = findViewById(R.id.activity_login_login)
        mPasswordEditText = findViewById(R.id.activity_login_password)

        mAnimButton.setOnClickListener(View.OnClickListener {
            if (mLoginEditText.text.isNotEmpty() && mPasswordEditText.text.isNotEmpty()){
                presenter.load(mLoginEditText.text.toString(), mPasswordEditText.text.toString())
            }
        })
    }

    override fun startLoading() {
        mAnimButton.startAnimation()
    }

    override fun endLoading() {
        mAnimButton.revertAnimation()
    }

    override fun showError(errorString: String) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_LONG).show()
    }

    override fun sendHome() {
        startActivity(Intent(applicationContext, MainActivity::class.java))
        finish()
    }
}
