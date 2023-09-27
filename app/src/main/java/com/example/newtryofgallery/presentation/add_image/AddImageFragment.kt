package com.example.newtryofgallery.presentation.add_image

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.newtryofgallery.domain.models.PictureWithTags
import com.example.newtryofgallery.domain.models.Tag
import com.example.newtryofgallery.presentation.local_images.adapter.TagsAdapter
import com.example.taggalleryapp.databinding.FragmentAddImageBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddImageFragment : Fragment() {

    private var _binding: FragmentAddImageBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AddImageViewModel by viewModel()
    private var pictureId = -1
    private var uri : String = ""

    private lateinit var selectedTagsAdapter : TagsAdapter
    private lateinit var notSelectedTagsAdapter: TagsAdapter

    val pickImage = registerForActivityResult(ActivityResultContracts.GetContent()){
        var requestOptions = RequestOptions()
        requestOptions = requestOptions.transforms(CenterCrop(), RoundedCorners(54))

        Glide.with(requireContext())
            .load(it)
            .apply(requestOptions)
            .into(binding.imageButton)
        if (it != null){
            viewModel.saveUri(it)
            uri = it.toString()
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
        _binding = FragmentAddImageBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRcView()
        if (pictureId > 0) {
            viewModel.getPictureWithTags(pictureId)
            viewModel.fetchTags(pictureId)
            viewModel.pictureWithTags.observe(viewLifecycleOwner) {
                bindViews(it)
            }
            binding.buttonSave.setOnClickListener { savePicture(pictureId) }

        } else {
            viewModel.fetchTags()
            binding.buttonSave.setOnClickListener { savePicture(pictureId) }
        }

        viewModel.selectedTagsList.observe(viewLifecycleOwner) { CatsList ->
            selectedTagsAdapter.submitList(CatsList.toMutableList())
        }
        viewModel.notSelectedTagsList.observe(viewLifecycleOwner) { CatsList ->
            notSelectedTagsAdapter.submitList(CatsList.toMutableList())
        }

        binding.imageButton.setOnClickListener {
//            pickImage.launch("image/*")

            showDialog()
        }

        viewModel.pictureWithTags.observe(viewLifecycleOwner) {
            if (it != null && !it.picture.url.isEmpty()){
                var requestOptions = RequestOptions()
                requestOptions = requestOptions.transforms(CenterCrop(), RoundedCorners(54))

                Glide.with(requireContext())
                    .load(it.picture.url)
                    .apply(requestOptions)
                    .into(binding.imageButton)
                uri = it.picture.url.toString()
            }
        }

        binding.buttonCancel.setOnClickListener { findNavController().navigateUp() }

        clearErrorMessage(binding.editPictureName)

    }

    private fun initRcView() {

        selectedTagsAdapter =
            TagsAdapter(requireContext(), object : TagsAdapter.TagActionListener {

                override fun onClickItem(tag: Tag) {
                    viewModel.deleteTag(tag)
                }

            })
        notSelectedTagsAdapter =
            TagsAdapter(requireContext(), object : TagsAdapter.TagActionListener {

                override fun onClickItem(tag: Tag) {
                    viewModel.addTag(tag)
                }

            })

        binding.recyclerViewRight.adapter = notSelectedTagsAdapter
        binding.recyclerViewRight.setHasFixedSize(true)

        binding.recyclerViewLeft.adapter = selectedTagsAdapter
        binding.recyclerViewLeft.setHasFixedSize(true)


    }

    private fun isInputValid(): Boolean {
        return viewModel.isInputIsValid(
            name = binding.editPictureName.text.toString(),
        )
    }

    private fun hideKeyboard() {
        val inputMethodManager =
            activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(binding.scrollView.windowToken, 0)
    }

    private fun showErrorMessage(view: TextInputEditText) {

        if (view.text?.isBlank() == true) {
            when (view) {
                binding.editPictureName -> binding.layoutPictureName.error =
                    "Add Picture"
            }
            binding.scrollView.smoothScrollTo(0, 0)
            hideKeyboard()
        }
    }

    private fun clearErrorMessage(view: TextInputEditText) {

        view.addTextChangedListener {
            if (it?.isNotBlank() == true) {
                when (view) {
                    binding.editPictureName -> binding.layoutPictureName.error = null
                }
            }
        }
    }

    private fun bindViews(pictureWithTags: PictureWithTags) {

        binding.apply {
            editPictureName.setText(pictureWithTags.picture.title, TextView.BufferType.SPANNABLE)
            editPictureDescription.setText(pictureWithTags.picture.description, TextView.BufferType.SPANNABLE)
            var requestOptions = RequestOptions()
            requestOptions = requestOptions.transforms(CenterCrop(), RoundedCorners(54))

//            Picasso.get().load(cat.url).into(imageButton)

            Glide.with(requireContext())
                .load(pictureWithTags.picture.url)
                .apply(requestOptions)
                .into(imageButton)
        }
        uri = pictureWithTags.picture.url
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val ID = "id"
    }

    private fun savePicture(pictureId: Int) {

        if (isInputValid() && !uri.isEmpty()) {

            if (pictureId > 0) {
                viewModel.editPicture(
                    id = pictureId,
                    name = binding.editPictureName.text.toString(),
                    description = binding.editPictureDescription.text.toString(),
                    uri = uri
                )

                val action =
                    AddImageFragmentDirections.actionAddImageFragmentToLocalImagesFragment()
                findNavController().navigate(action)

            } else {

                viewModel.addNewPicture(
                    name = binding.editPictureName.text.toString(),
                    description = binding.editPictureDescription.text.toString(),
                    uri = uri
                )
                val action = AddImageFragmentDirections.actionAddImageFragmentToLocalImagesFragment()
                findNavController().navigate(action)
            }


        } else {
            showErrorMessage(binding.editPictureName)

        }

    }

    private fun showDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Select source")
            .setMessage("Select where to get image")
            .setCancelable(false)
            .setNegativeButton("Local") { _, _ ->

                pickImage.launch("image/*")
            }
            .setPositiveButton("Internet") { _, _ ->
                viewModel.getImageNetwork()
            }
            .show()
    }
}