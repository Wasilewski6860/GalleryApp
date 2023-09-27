package com.example.newtryofgallery.presentation

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newtryofgallery.domain.models.Picture
import com.example.newtryofgallery.domain.models.PictureTagCrossRef
import com.example.newtryofgallery.domain.models.PictureWithTags
import com.example.newtryofgallery.domain.models.Tag
import com.example.newtryofgallery.domain.use_cases.AddPictureTagCrossRefUseCase
import com.example.newtryofgallery.domain.use_cases.AddPictureUseCase
import com.example.newtryofgallery.domain.use_cases.AddTagUseCase
import com.example.newtryofgallery.domain.use_cases.DeleteCrossRefsByPictureIdUseCase
import com.example.newtryofgallery.domain.use_cases.EditPictureUseCase
import com.example.newtryofgallery.domain.use_cases.GetAllTagsUseCase
import com.example.newtryofgallery.domain.use_cases.GetTagsOfPictureUseCase
import kotlinx.coroutines.launch

class MyViewModel(
    private val getTagsOfPictureUseCase : GetTagsOfPictureUseCase,
    private val addPictureTagCrossRefUseCase: AddPictureTagCrossRefUseCase,
    private val getAllTagsUseCase: GetAllTagsUseCase,
    private val editPictureUseCase: EditPictureUseCase,
    private val deleteCrossRefsByPictureIdUseCase: DeleteCrossRefsByPictureIdUseCase

) : ViewModel() {

    private val _pictureWithTags = MutableLiveData<PictureWithTags>()
    val pictureWithTags: LiveData<PictureWithTags> = _pictureWithTags

    private val _selectedTagsList = MutableLiveData<List<Tag>>()
    val selectedTagsList: LiveData<List<Tag>> = _selectedTagsList

    private val _notSelectedTagsList = MutableLiveData<List<Tag>>()
    val notSelectedTagsList: LiveData<List<Tag>> = _notSelectedTagsList

    private val _allTagsList = MutableLiveData<List<Tag>>()
    val allTagsList: LiveData<List<Tag>> = _allTagsList





    fun fetchTags() {
        viewModelScope.launch {

            try {
                _allTagsList.value = getAllTagsUseCase.execute()
                _notSelectedTagsList.value = getAllTagsUseCase.execute()
            }
            catch (e: Exception) {

            }
        }
    }


    fun getPictureWithTags(id: Int) {
        viewModelScope.launch {
            _pictureWithTags.value = getTagsOfPictureUseCase.execute(id)
        }
    }

    fun getImageNetwork(){
//        viewModelScope.launch {
//            val uri =  Uri.parse(getImageUseCase.execute().toString())
//            saveUri(uri)
//        }
    }
    private fun updatePicture(picture: Picture) {
        viewModelScope.launch {
            editPictureUseCase.execute(picture)
            var id = picture.pictureId
            deleteCrossRefsByPictureIdUseCase.execute(id)
            if (selectedTagsList.value != null){
                var tags : List<Tag> = selectedTagsList.value!!
                for (tag in tags){
                    addPictureTagCrossRefUseCase.execute(
                        picture, tag
                    )
                }
            }
        }
    }

    fun editPicture(
        id : Int,
        name: String,
        description: String,
        uri : String
    ) {
        val editedCat = Picture(
            pictureId = id,
            url = uri,
            title = name,
            description = description
        )
        updatePicture(editedCat)
    }

    fun saveUri(it: Uri) {
        if (pictureWithTags.value != null){
            var string = it.toString()
            var cat = _pictureWithTags.value
            _pictureWithTags.value!!.picture.url = it.toString()
        }
        else{
            _pictureWithTags.value = PictureWithTags(
                Picture(pictureId = 0,it.toString(),"",""),
                emptyList()
            )
        }
    }


}