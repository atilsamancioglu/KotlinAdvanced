package com.atilsamancioglu.kotlinadvanced.view


import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.palette.graphics.Palette

import com.atilsamancioglu.kotlinadvanced.R
import com.atilsamancioglu.kotlinadvanced.ViewModel.DetailViewModel
import com.atilsamancioglu.kotlinadvanced.databinding.FragmentDetailBinding
import com.atilsamancioglu.kotlinadvanced.model.CountryPalette
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition


class DetailFragment : Fragment() {

    private var dogUuid = 0
    private lateinit var viewModel : DetailViewModel

    private lateinit var dataBinding : FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dataBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_detail,container,false)
        return dataBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //after creating arguments
        //nullable let means if let in swift
        arguments?.let {
            //it refers to whatever put before .let, which is arguments at this point
            dogUuid = DetailFragmentArgs.fromBundle(it).dogUuid

        }

        viewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)
        viewModel.fetch(dogUuid)






        observeViewModel()
    }

    private fun observeViewModel() {

        viewModel.dogLiveData.observe(viewLifecycleOwner, Observer { dog ->
            dog?.let {

                dataBinding.dog = dog

                it.imageUrl?.let {
                    setupBackgroundColor(it)
                }
                /*
                dogName.text = dog.dogBreed
                dogPurpose.text = dog.bredFor
                dogTemperament.text = dog.temperament
                dogLifespan.text = dog.lifeSpan
                dog.imageUrl?.let {
                    context?.let {

                        dogImage.loadImage(dog.imageUrl, getProgressDrawable(it))

                    }
                }
                */
            }
        })

    }

    private fun setupBackgroundColor(url: String) {
        Glide.with(this).asBitmap().load(url).into(object: CustomTarget<Bitmap>() { //Custom target is part of latest Glide library, we can just put image into a custom object to reach it later on
            override fun onLoadCleared(placeholder: Drawable?) {

            }

            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                //here is where we use pallet library
                Palette.from(resource)
                    .generate {
                        val intColor = it?.lightMutedSwatch?.rgb ?: 0
                        val myPalette = CountryPalette(intColor)
                        dataBinding.pallette = myPalette //attach pallette to layout for data binding
                    }
            }

        })
    }

}
