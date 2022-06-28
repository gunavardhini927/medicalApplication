package com.example.medicalapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.example.medicalapp.databinding.FragmentFeedbackBinding

class FeedbackFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val bind= FragmentFeedbackBinding.inflate(layoutInflater)


            bind.rbStars.rating = 2.5f
            bind.rbStars.stepSize = .5f
            bind.rbStars.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
                Toast.makeText(context,"Rating: $rating",Toast.LENGTH_LONG).show()
            }

            return bind.root
        }



    }


