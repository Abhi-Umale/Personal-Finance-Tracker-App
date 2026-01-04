package com.example.personalfinancerackerapp.auth.authviewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class AuthViewModel: ViewModel() {

    //Create Firebase instance
    private val auth = FirebaseAuth.getInstance()

    //Login
    fun login(email:String, password: String, onResult:(Boolean) -> Unit){
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener{
            onResult(it.isSuccessful)
        }
    }

    // SignUp
    fun signup(email:String, password: String, onResult: (Boolean) -> Unit){
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            onResult(it.isSuccessful)
        }
    }


}