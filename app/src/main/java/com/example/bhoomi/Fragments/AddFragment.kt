package com.example.bhoomi.Fragments

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.bhoomi.Data.Posts
import com.example.bhoomi.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.fragment_account.view.*
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_add.view.*
import java.net.URI
import java.sql.Date
import java.sql.Timestamp

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var rootView: View
    val pickImage =100
    val firestore = FirebaseFirestore.getInstance()
    val db = FirebaseAuth.getInstance()
    lateinit var filePath : Uri
    val TAG = "ADDFRAGMENT"
    lateinit var imageURi : Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView =inflater.inflate(R.layout.fragment_add, container, false)

        rootView.btnSelectImage.setOnClickListener{
            startFileChooser()
        }
        rootView.btnUploadImage.setOnClickListener{
            uploadFile()
        }


        return rootView
    }

    private fun uploadFile() {
        if(filePath != null){
            var pd = ProgressDialog(rootView.context)
            pd.setTitle("Uploading")
            pd.show()

            var imageRef = FirebaseStorage.getInstance().reference.child("images/${db.uid}"+"/"+"${filePath.lastPathSegment}")
            imageRef.putFile(filePath)
                .addOnSuccessListener {
                    pd.dismiss()
                    imageURi = filePath
                    imageRef.downloadUrl.addOnSuccessListener {
                        imageURi = it
                        firestore.collection("posts").add(Posts(db.uid,"Fake username",it.toString(),
                                Timestamp(System.currentTimeMillis())
                        ))
                                .addOnSuccessListener {
                                    Toast.makeText(context, "Uploaded Successfully", Toast.LENGTH_SHORT).show()
                                }
                                .addOnFailureListener{
                                    Toast.makeText(context, "Upload Failed", Toast.LENGTH_SHORT).show()
                                    Log.d(TAG,"Upload Failed : ${it.message.toString()}")
                                }
                        Toast.makeText(context,"image uri : ${imageURi}",Toast.LENGTH_LONG).show()
                    }.addOnFailureListener{
                        Toast.makeText(context,"no uri",Toast.LENGTH_LONG).show()
                    }





                }
                .addOnFailureListener{
                    pd.dismiss()
                    Toast.makeText(context,it.message.toString(),Toast.LENGTH_LONG).show()
                    Log.d(TAG,it.message.toString());
                }
                .addOnProgressListener {
                    var progress = (100.0 * it.bytesTransferred)/ (it.totalByteCount)
                    pd.setMessage("Uplaoded ${progress.toInt()} %")

                }
        }
    }

    private fun startFileChooser() {
        var i = Intent()
        i.setType("image/*")
        i.setAction(Intent.ACTION_GET_CONTENT)
        startActivityForResult(Intent.createChooser(i,"Choose Picture"),111)


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode ==111 && resultCode == Activity.RESULT_OK && data != null){
            filePath = data.data!!
            rootView.addPostImage.setImageURI(filePath)
           rootView.addPostImage.setImageURI(filePath)


        }
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AddFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}