package com.example.newtryofgallery.presentation.local_images

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newtryofgallery.domain.models.Picture
import com.example.newtryofgallery.domain.models.Tag
import com.example.newtryofgallery.presentation.local_images.adapter.ImagesAdapter
import com.example.newtryofgallery.presentation.local_images.adapter.TagsAdapter
import com.example.newtryofgallery.presentation.models.ScreenState
import org.koin.androidx.viewmodel.ext.android.viewModel

import androidx.navigation.fragment.findNavController
import com.example.taggalleryapp.R
import com.example.taggalleryapp.databinding.FragmentLocalImagesBinding

class LocalImagesFragment : Fragment() {

    private var _binding: FragmentLocalImagesBinding? = null
    private val binding get() = _binding!!



    private val viewModel: LocalImagesViewModel by viewModel()
    private lateinit var imagesAdapter: ImagesAdapter

    private lateinit var selectedTagsAdapter : TagsAdapter
    private lateinit var notSelectedTagsAdapter: TagsAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLocalImagesBinding.inflate(layoutInflater, container, false)
//        _main_binding = ActivityMainBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initRcView()
        viewModel.fetchPictures()

        viewModel.picturesList.observe(viewLifecycleOwner) { CatsList ->
            imagesAdapter.submitList(CatsList.toMutableList())
        }

        viewModel.selectedTagsList.observe(viewLifecycleOwner) { CatsList ->
            selectedTagsAdapter.submitList(CatsList.toMutableList())
        }
        viewModel.notSelectedTagsList.observe(viewLifecycleOwner) { CatsList ->
            notSelectedTagsAdapter.submitList(CatsList.toMutableList())
        }

        viewModel.selectedTagsList.observe(viewLifecycleOwner) { CatsList ->
            viewModel.fetchPictures()
        }


        viewModel.screenState.observe(viewLifecycleOwner) { screenState ->

            when (screenState) {
                ScreenState.Content -> showContent()
                ScreenState.Empty -> showEmptyScreen()
                ScreenState.Error -> showError()
                ScreenState.Loading -> showLoading()
            }
        }


        binding.buttonAdd.setOnClickListener {
            val action = LocalImagesFragmentDirections.actionLocalImagesFragmentToAddImageFragment()
            findNavController().navigate(action)
        }

    }

    private fun initRcView() {

        imagesAdapter =
            ImagesAdapter(requireContext(), object : ImagesAdapter.ImageActionListener {

                override fun onClickItem(picture: Picture) {
                    val action =
//                        LocalImagesFragmentDirections.actionLocalImagesFragmentToImageInfoFragment(id = picture.pictureId)
                        LocalImagesFragmentDirections.actionLocalImagesFragmentToMyFragment(picture.pictureId)
                    findNavController().navigate(action)
                }

            })

        binding.recyclerView.adapter = imagesAdapter
        binding.recyclerView.setHasFixedSize(true)

        val parentActivity: Activity? = activity

        if (parentActivity != null) {
            // Теперь можно использовать findViewById для поиска view в разметке активности
            val navigationView = parentActivity.findViewById<View>(R.id.navigation_view)


            val firstRecyclerView: RecyclerView = navigationView.findViewById(R.id.firstRecyclerView)
            val secondRecyclerView: RecyclerView = navigationView.findViewById(R.id.secondRecyclerView)
            val layoutManager = LinearLayoutManager(requireContext())
            val layoutManagerBottom = LinearLayoutManager(requireContext())
            firstRecyclerView.layoutManager = layoutManager
            secondRecyclerView.layoutManager = layoutManagerBottom

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
            firstRecyclerView.adapter = selectedTagsAdapter
            secondRecyclerView.adapter = notSelectedTagsAdapter
        }




    }

    private fun showContent() {
        with(binding) {
            noContentLayout.visibility = View.GONE
            contentLayout.visibility = View.VISIBLE
            errorLayout.visibility = View.GONE
        }
    }

    private fun showLoading() {
        with(binding) {
            noContentLayout.visibility = View.GONE
            contentLayout.visibility = View.GONE
            errorLayout.visibility = View.GONE
        }
    }

    private fun showEmptyScreen() {
        with(binding) {
            noContentLayout.visibility = View.VISIBLE
            contentLayout.visibility = View.GONE
            errorLayout.visibility = View.GONE
        }
    }

    private fun showError() {
        with(binding) {
            noContentLayout.visibility = View.GONE
            contentLayout.visibility = View.GONE
            errorLayout.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}