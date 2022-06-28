package com.example.medicalapp.doctorfragment

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.medicalapp.databinding.FragmentDrProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class DrProfileFragment : Fragment() {
    private lateinit var database: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val binding = FragmentDrProfileBinding.inflate(layoutInflater)
        val user = FirebaseAuth.getInstance().currentUser
        val userid = user!!.uid
        var ref: DatabaseReference = FirebaseDatabase.getInstance().getReference("Doctors");

        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.hasChild(userid)) {
                    binding.docFullName.setText(
                        dataSnapshot.child(userid).child("fullname").value.toString()
                    )
                    binding.updateQualification.setText(
                        dataSnapshot.child(userid).child("qualification").value.toString()
                    )
                    binding.updateExperience.setText(
                        dataSnapshot.child(userid).child("experience").value.toString()
                    )
                    binding.drPhoneno.setText(
                        dataSnapshot.child(userid).child("phoneNumber").value.toString()
                    )
                }

            }

            override fun onCancelled(databaseError: DatabaseError) {
                throw databaseError.toException() // Don't ignore errors
            }
        })
        binding.updateBtnDr.setOnClickListener {
            val fullname = binding.docFullName.text.toString()
            val qualification = binding.updateQualification.text.toString()
            val experience = binding.updateExperience.text.toString()
            val phoneNumber = binding.drPhoneno.text.toString()
            UpdateData(fullname, qualification, experience,phoneNumber)

        }

        return binding.root
    }


    private fun UpdateData(
        fullname: String,
        qualification: String,
        experience:String,
        phoneNumber: String,
    ) {


        database = FirebaseDatabase.getInstance().getReference("Doctors")


        val user = mapOf<String, String>(
            "fullname" to fullname,
            "qualification" to qualification,
            "experience" to experience,
            "phoneNumber" to phoneNumber,
        )


        database.child(FirebaseAuth.getInstance().currentUser!!.uid).updateChildren(user)
            .addOnSuccessListener {
                println(user.entries)
                Toast.makeText(context, "Successfully added", Toast.LENGTH_LONG).show()

            }.addOnFailureListener {

                Toast.makeText(context, "Failed to Update", Toast.LENGTH_SHORT).show()

            }

    }
}




