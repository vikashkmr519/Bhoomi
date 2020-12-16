package com.example.bhoomi.LoginLogout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.bhoomi.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_forgot_password.*

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        auth = FirebaseAuth.getInstance()

        resetBtn.setOnClickListener{
            sendResetLink(emailToResetpassword.text.toString())
        }
        goBackBtn.setOnClickListener{
            finish()
        }
    }

//    override fun onClick(v: View?) {
//        when(v?.id){
//            R.id.resetBtn ->{
//
//            }
//
//            R.id.goBackBtn ->{
//                finish()
//            }
//        }
//    }

    private fun sendResetLink(email: String) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener(this, OnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Reset link sent to your email", Toast.LENGTH_LONG)
                        .show()
                } else {
                    Toast.makeText(this, "Unable to send reset mail", Toast.LENGTH_LONG)
                        .show()
                }
            })

    }


}