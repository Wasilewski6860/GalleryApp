package com.example.newtryofgallery.presentation.image_info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.newtryofgallery.domain.models.Picture
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.squareup.picasso.Picasso
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.taggalleryapp.R
import com.example.taggalleryapp.databinding.FragmentImageInfoBinding


class ImageInfoFragment : Fragment() {

    private var _binding: FragmentImageInfoBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ImageInfoViewModel by viewModel()
    private var pictureId = -1

    private val REQUEST_CODE = 123
    private lateinit var picture : Picture

    val pickImage = registerForActivityResult(ActivityResultContracts.GetContent()){
        var requestOptions = RequestOptions()
        requestOptions = requestOptions.transforms(CenterCrop(), RoundedCorners(54))

        Glide.with(requireContext())
            .load(it)
            .apply(requestOptions)
            .into(binding.imageView)
        if (it != null){

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            pictureId = it.getInt(ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentImageInfoBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        picture = Picture(0,"","","")
        if (pictureId > 0) {
            viewModel.getPicture(pictureId)
            viewModel.picture.observe(viewLifecycleOwner) {
                picture = it
                bindViews(it)
            }

        }

        binding.bottomNavigationViewInfo.setOnNavigationItemSelectedListener { item ->
            when (item.getItemId()) {
                R.id.addImageFragment ->  {
                    val action =
                        ImageInfoFragmentDirections.actionImageInfoFragmentToAddImageFragment(id = pictureId)
                    findNavController().navigate(action)
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.deleteImage ->  {
                    showDialog()
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.menuInfo ->    {
//                    val action =
//                        ImageInfoFragmentDirections.actionImageInfoFragmentToImageFragment()
//                    findNavController().navigate(action)
                    return@setOnNavigationItemSelectedListener true
                }

                else -> return@setOnNavigationItemSelectedListener false
            }
        }



    }




    private fun bindViews(picture : Picture) {

        binding.apply {


            var requestOptions = RequestOptions()
            requestOptions = requestOptions.transforms(CenterCrop(), RoundedCorners(54))

//            Picasso.get().load(cat.url).into(imageButton)

            Glide.with(requireContext())
                .load(picture.url)
                .apply(requestOptions)
                .into(imageView)
//            val imageUri = "https://i.imgur.com/tGbaZCY.jpg"
//
//            Picasso.get().load(
//                picture.url
////                imageUri
//            ).into(imageView)

//            Picasso.get().load(File(picture.url)).into(imageView)

//            val iconUrl ="http://openweathermap.org/img/w/04n.png"
//            Picasso.get().load(iconUrl).placeholder(R.drawable.ic_camera)// Place
////            holder image from drawable folder
//                .error(R.drawable.error_image).resize(110, 110).centerCrop()
//                .into(imageView);

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val ID = "id"
    }





    private fun showDialog() {

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Delete image")
            .setMessage("Are you sure to delete this image")
            .setCancelable(false)
            .setNegativeButton("Delete") { _, _ ->
                viewModel.delete()
                val action =
                    ImageInfoFragmentDirections.actionImageInfoFragmentToLocalImagesFragment()
                findNavController().navigate(action)
            }
            .setPositiveButton("No") { _, _ ->

            }
            .show()
    }


    private fun checkAndRequestPermissions() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), REQUEST_CODE)
        } else {
            bindViews(picture)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                bindViews(picture)
            } else {
                // Разрешение не было предоставлено, обработайте этот случай
            }
        }
    }

}