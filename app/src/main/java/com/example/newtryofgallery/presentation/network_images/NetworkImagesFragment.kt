package com.example.newtryofgallery.presentation.network_images

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.taggalleryapp.R

class NetworkImagesFragment : Fragment() {

    companion object {
        fun newInstance() = NetworkImagesFragment()
    }

    private lateinit var viewModel: NetworkImagesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_network_images, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(NetworkImagesViewModel::class.java)
        // TODO: Use the ViewModel
    }

}