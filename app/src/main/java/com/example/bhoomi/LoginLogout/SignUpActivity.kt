package com.example.bhoomi.LoginLogout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.bhoomi.MainActivity
import com.example.bhoomi.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_sign_up.*

import java.nio.file.FileVisitResult

class SignUpActivity : AppCompatActivity()  {

    private val TAG = "SignUpActivity"
    //firebase auth declared
    private lateinit var auth :FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        auth = FirebaseAuth.getInstance()

        signupBtnRegister.setOnClickListener{
            createAccount(SignupUsername.text.toString(),SignupPassword.text.toString())
        }

        loginBtnRegister.setOnClickListener{
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    // this fun will check if user is already signed in or not
    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if(currentUser != null)
        {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun createAccount(email:String ,password : String){
        Log.d(TAG,"create account : $email")
        if(!validateForm()){
            return
        }

        //[Start ## create user with email and password
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this){ task ->
                if(task.isSuccessful){
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = auth.currentUser
                    val intent = Intent(this,MainActivity::class.java)
                    startActivity(intent)

                    finish()


                }else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()

                }


        }
        //[END  create user with email]
    }

    private fun signIn(email: String,password: String){
        Log.d(TAG,"signIn: $email")
        if(!validateForm()){
            return
        }

        //[Start sign in with email]
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this){ task ->
            if(task.isSuccessful){
                // Sign in success, update UI with the signed-in user's information
                Log.d(TAG, "signInWithEmail:success")
                val intent = Intent(this,MainActivity::class.java)
                startActivity(intent)
                finish()
            }else{
                // If sign in fails, display a message to the user.
                Log.w(TAG, "signInWithEmail:failure", task.exception)
                Toast.makeText(baseContext, "Authentication failed.",
                    Toast.LENGTH_SHORT).show()

            }

        }
        //[END sign in with email]
    }

    //sign out
    private fun signOut() {
        auth.signOut()
       val intent = Intent(this,LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun validateForm() : Boolean{
        var valid = true
        val email = SignupUsername.text.toString()
        val password = SignupPassword.text.toString()
        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
            SignupUsername.error ="Required"
            valid = false
        }
        return valid

    }

//    override fun onClick(v: View) {
//        when(v.id){
//            R.id.signupBtn ->{
//                createAccount(SignupUsername.text.toString(),SignupPassword.text.toString())
//            }
//            R.id.loginBtn ->{
//                val intent = Intent(this,LoginActivity::class.java)
//                startActivity(intent)
//                finish()
//            }
//        }
//
//    }


}