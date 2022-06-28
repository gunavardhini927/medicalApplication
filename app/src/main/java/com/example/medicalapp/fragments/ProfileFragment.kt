package com.example.medicalapp.fragments


import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.medicalapp.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class ProfileFragment : Fragment() {
    private lateinit var database: DatabaseReference


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentProfileBinding.inflate(layoutInflater)

        val user = FirebaseAuth.getInstance().currentUser
        val userid = user!!.uid

        var ref: DatabaseReference = FirebaseDatabase.getInstance().getReference("Users");

        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.hasChild(userid)) {

                    binding.fullNameUpdate.setText(
                        dataSnapshot.child(userid).child("fullname").value.toString()
                    )
                    binding.phnNoUpdate.setText(
                        dataSnapshot.child(userid).child("phoneNumber").value.toString()
                    )
                    binding.DOBUpdate.setText(
                        dataSnapshot.child(userid).child("dob").value.toString()
                    )
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                throw databaseError.toException()
            }
        })


        binding.btnUpdate.setOnClickListener {
            val fullname = binding.fullNameUpdate.text.toString()

            val dob = binding.DOBUpdate.text.toString()
            val phoneNumber = binding.phnNoUpdate.text.toString()
            UpdateData(fullname, dob, phoneNumber)

        }

        return binding.root
    }


    private fun UpdateData(fullname: String, dob: String, phoneNumber: String) {

        database = FirebaseDatabase.getInstance().getReference("Users")


        val user = mapOf<String, String>(
            "fullname" to fullname,
            "dob" to dob,
            "phoneNumber" to phoneNumber,
        )


        database.child(FirebaseAuth.getInstance().currentUser!!.uid).updateChildren(user)
            .addOnSuccessListener {


                Toast.makeText(context, "Successfully added", Toast.LENGTH_LONG).show()

            }.addOnFailureListener {

            Toast.makeText(context, "Failed to Update", Toast.LENGTH_SHORT).show()

        }

    }
}



