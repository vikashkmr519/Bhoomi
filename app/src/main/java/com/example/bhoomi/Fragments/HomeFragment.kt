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
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*

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

        //initializeRecyclerView()
        getfromFirestore()

        return rootView
    }

    private fun getfromFirestore() {
        firestore.collection("posts").get()
            .addOnSuccessListener { document ->
                if(document != null){
                    for(doc in document){
                        val post = doc.data
                        rootView.username.text = post.get("image").toString()
                        Picasso.with(context).load(post.get("image").toString()).into(rootView.postImg)
                    }
                }
            }
            .addOnFailureListener{
                Log.d(TAG,"Error occured : ${it.message}")
            }
    }

//    private fun initializeRecyclerView() {
//        recyclerView = rootView.findViewById(R.id.postsRecyclerView)
//        fillthelist()
//        adapter = PostAdapter(list)
//
//        recyclerView.layoutManager = LinearLayoutManager(activity)
//        recyclerView.adapter = adapter
//
//    }

    private fun fillthelist() {



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