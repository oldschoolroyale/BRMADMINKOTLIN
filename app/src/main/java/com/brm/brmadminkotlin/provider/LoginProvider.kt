package com.brm.brmadminkotlin.provider

import com.brm.brmadminkotlin.presenter.LoginPresenter
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth

class LoginProvider(var presenter: LoginPresenter) {
    val mAuth = FirebaseAuth.getInstance()
    fun parseUser(emailCredential: String, passwordCredential: String){
            mAuth.signInWithEmailAndPassword(emailCredential, passwordCredential).addOnCompleteListener(
                OnCompleteListener {
                    if (it.isSuccessful){
                        presenter.tryToSetUp(true)
                    }
                    else{
                        presenter.tryToSetUp(false)
                    }
                })
    }
}