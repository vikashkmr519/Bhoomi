package com.example.bhoomi.LoginLogout

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.bhoomi.MainActivity
import com.example.bhoomi.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {
    private val TAG = "LoginActivity"
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)



        auth = FirebaseAuth.getInstance()

        loginBtn.setOnClickListener{

            signIn(username.text.toString(), password.text.toString())
        }

        signupBtn.setOnClickListener{
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }

        TvForgotPassword.setOnClickListener{
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if(currentUser != null){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun signIn(email: String, password: String){
        Log.d(TAG, "signIn: $email")
        if(!validateForm()){
            Log.d(TAG,"Not validated form during Sign in")
            return
        }

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this){ task ->

            Log.d(TAG,"Inside Oncomplete Sign in")
            if(task.isSuccessful){
                Log.d(TAG, "signInWithEmail:success")
                Toast.makeText(this, "signed in", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()

            }else{
                Log.d(TAG, "signInWithEmail:Failure")
                Toast.makeText(this, "Sign In Failure", Toast.LENGTH_SHORT).show()

            }
        }

    }

    private fun validateForm():Boolean{
        var valid = true
        val email = username.text.toString()
        val pass = password.text.toString()

        if(TextUtils.isEmpty(email)){
            username.error="Required"
            valid = false

        }else if(TextUtils.isEmpty(pass)){
            password.error ="Required"
            valid= false
        }

        return valid


    }


//   override fun onClick(v: View){
//
//        when(v.id){
//            R.id.loginBtn->{
//                signIn(username.text.toString(),password.text.toString())
//            }
//            R.id.signupBtn ->{
//                val intent = Intent(this,SignUpActivity::class.java)
//                startActivity(intent)
//                finish()
//            }
//
//            R.id.TvForgotPassword->{
//                val intent = Intent(this,ForgotPasswordActivity::class.java)
//                startActivity(intent)
//                finish()
//            }
//        }
//
//    }
}