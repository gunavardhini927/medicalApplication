package com.example.medicalapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.medicalapp.databinding.ActivityDoctorLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class DoctorLoginActivity : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityDoctorLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth

        binding = DataBindingUtil.setContentView(this, R.layout.activity_doctor_login)

        binding.medico = resources.getString(R.string.medico)
        binding.drEmail = resources.getString(R.string.enter_email_id)
        binding.drPassword = resources.getString(R.string.enter_password)
        binding.drLogin = resources.getString(R.string.login)
        binding.noAccountText = resources.getString(R.string.you_dont_have_medico_account_yet)
        binding.drSignUpNow = resources.getString(R.string.sign_up_now)

        binding.IdDrSignUpNow.setOnClickListener {
            val intent = Intent(this, DoctorRegistrationActivity::class.java)
            startActivity(intent)
        }

        binding.IdDrLoginBtn.setOnClickListener {
            if (binding.doctorEmailId.text.toString()
                    .trim() == "" || binding.doctorPassword.text.toString().trim() == ""
            ) {
                Toast.makeText(
                    this,
                    "Enter User Credentials",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                auth.signInWithEmailAndPassword(
                    binding.doctorEmailId.text.toString().trim(),
                    binding.doctorPassword.text.toString().trim()
                ).addOnCompleteListener(this) {
                    if (it.isSuccessful) {
                        val intent = Intent(this@DoctorLoginActivity, DoctorNavigation::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(
                            this,
                            "Invalid User Credentials",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

        }
    }
}