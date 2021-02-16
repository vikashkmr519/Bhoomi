package com.example.bhoomi.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bhoomi.Adapters.PostAdapter
import com.example.bhoomi.Data.Posts
import com.example.bhoomi.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import java.sql.Timestamp

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    protected lateinit var rootView: View
    lateinit var recyclerView: RecyclerView
    private val list = ArrayList<Posts>()
    private lateinit var adapter :PostAdapter
    var firestore = FirebaseFirestore.getInstance()
    val TAG = "HOMEFRAGMENT"
    var auth = FirebaseAuth.getInstance()
    var firebaseStorage = FirebaseStorage.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        rootView =inflater.inflate(R.layout.fragment_home, container, false)

        initializeRecyclerView()

        return rootView
    }

    private fun getfromFirestore() {

        // please dry run this , here is error
        
        firestore.collection("posts").get()
                .addOnSuccessListener { document ->
                    if(document != null){
                        for(doc in document){
                            val docItems = doc.data
                            var username =""
                            firestore.collection("users").document(auth.uid.toString()).get().addOnSuccessListener {
                                if(it.exists()){
                                    username = it.get("userName").toString()
                                }
                            }
                            var profileImage = ""
                            firebaseStorage.getReference().child("profileImages/${auth.uid}/profileImage.jpg").downloadUrl.addOnSuccessListener {
                                if(it!=null){
                                    profileImage = it.toString()
                                }
                            }
                            var post = Posts(null,docItems.get("username").toString(),docItems.get("image").toString(), Timestamp(System.currentTimeMillis()),
                                    0,profileImage)
                            list.add(post)

                        }

                    }

                }
                .addOnFailureListener{
                    Log.d(TAG,"Error occured : ${it.message}")
                }
    }

    private fun initializeRecyclerView() {
        recyclerView = rootView.findViewById(R.id.postsRecyclerView)
        getfromFirestore()
        adapter = PostAdapter(list)

        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter

    }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                HomeFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}