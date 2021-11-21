package com.trytest.foodorder

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import com.example.orderfood.extensions.Extensions.toast
import com.example.orderfood.utils.FirebaseUtils
import com.example.orderfood.utils.FirebaseUtils.firebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInActivity : AppCompatActivity() {
    lateinit var signInEmail: String
    lateinit var signInPassword: String
    lateinit var signInInputsArray: Array<EditText>


    lateinit var sharedPreferences: SharedPreferences
    var isRemember = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        signInInputsArray = arrayOf(loginemail,loginpassword)


        sharedPreferences = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
        isRemember = sharedPreferences.getBoolean("CHECKBOX", false)
        if(isRemember){
            val user: FirebaseUser? = FirebaseUtils.firebaseAuth.currentUser
            user?.let {
                startActivity(Intent(this, MainActivity::class.java))
                toast("welcome back")
            }
        }
        btnRegister.setOnClickListener{
            startActivity(Intent(this, CreateAccountActivity::class.java))
            finish()
        }
        btnLogin.setOnClickListener{
            signInUser()
            val checked : Boolean = rememberAc.isChecked
            val editor : SharedPreferences.Editor = sharedPreferences.edit()
            editor.putBoolean("CHECKBOX", checked)
            editor.apply()
        }
    }

//    override fun onStart() {
//        super.onStart()
//        if(rememberAc.isChecked){
//            val user: FirebaseUser? = FirebaseUtils.firebaseAuth.currentUser
//            user?.let {
//                startActivity(Intent(this, MainActivity::class.java))
//                toast("welcome back")
//            }
//        }
//    }

    private fun notEmpty(): Boolean = signInEmail.isNotEmpty() && signInPassword.isNotEmpty()

    private fun signInUser() {
        signInEmail = loginemail.text.toString().trim()
        signInPassword = loginpassword.text.toString().trim()

        if (notEmpty()) {
            firebaseAuth.signInWithEmailAndPassword(signInEmail, signInPassword)
                .addOnCompleteListener { signIn ->
                    if (signIn.isSuccessful) {
                        startActivity(Intent(this, MainActivity::class.java))
                        toast("signed in successfully")
                        finish()
                    } else {
                        toast("sign in failed")
                    }
                }
        } else {
            signInInputsArray.forEach { input ->
                if (input.text.toString().trim().isEmpty()) {
                    input.error = "${input.hint} is required"
                }
            }
        }
    }
}