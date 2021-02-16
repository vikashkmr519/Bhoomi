package com.example.bhoomi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.bhoomi.Fragments.AccountFragment
import com.example.bhoomi.Fragments.AddFragment
import com.example.bhoomi.Fragments.HomeFragment
import com.example.bhoomi.Fragments.SearchFragment
import com.example.bhoomi.LoginLogout.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var auth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()
//        logout.setOnClickListener{
//            auth.signOut()
//            val intent = Intent(this, LoginActivity::class.java)
//            startActivity(intent)
//            finish()
//        }

        val homeFragment = HomeFragment()
        val accountFragment = AccountFragment()
        val searchFragment = SearchFragment()
        val addFragment = AddFragment()

        makeFirstFragment(HomeFragment())
        bottomNavigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.homeNav -> makeFirstFragment(homeFragment)
                R.id.uploadNav -> makeFirstFragment(addFragment)
                R.id.searchNav -> makeFirstFragment(searchFragment)
                R.id.profileNav -> makeFirstFragment(accountFragment)

            }
            true
        }
        
    }

    private fun makeFirstFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container,fragment)
            commit()
        }


    }


}