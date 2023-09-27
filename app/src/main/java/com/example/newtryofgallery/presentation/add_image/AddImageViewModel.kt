package com.example.newtryofgallery.presentation.add_image

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
import com.example.newtryofgallery.presentation.models.ScreenState
import kotlinx.coroutines.launch

class AddImageViewModel(
    private val addPictureUseCase : AddPictureUseCase,
    private val addTagUseCase : AddTagUseCase,
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


    private fun insertNewPicture(picture : Picture) {
        viewModelScope.launch {
            var id : Long = addPictureUseCase.execute(picture)
            if (selectedTagsList.value != null){
                var tags : List<Tag> = selectedTagsList.value!!
                for (tag in tags){
                    addPictureTagCrossRefUseCase.execute(
                        PictureTagCrossRef(id.toInt(), tag.tagId)
                    )
                }
            }
        }
    }

    fun addNewPicture(
        name: String,
        description: String,
        uri : String
    ) {
        val newPicture = Picture(
            pictureId = 0,
            url = uri,
            title = name,
            description = description
        )
        insertNewPicture(newPicture)
    }


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
    fun fetchTags(id : Int) {
        viewModelScope.launch {

            try {

                _allTagsList.value = getAllTagsUseCase.execute()
                var selected : MutableList<Tag> = pictureWithTags.value?.subject as MutableList<Tag>
                _selectedTagsList.value = selected
                _notSelectedTagsList.value = selected.filter { tag -> !selected.any { it.tagId == tag.tagId } }
            } catch (e: Exception) {

            }
        }
    }

    fun addTag(tag: Tag){
        var tags : MutableList<Tag> = if (selectedTagsList.value != null) selectedTagsList.value!! as MutableList<Tag> else mutableListOf<Tag>()
        tags.add(tag)
        _selectedTagsList.value = tags

        if (notSelectedTagsList.value != null){
            var tags : MutableList<Tag> = notSelectedTagsList.value!! as MutableList<Tag>
            tags.removeIf { it.tagId == tag.tagId }
            _notSelectedTagsList.value = tags
        }
    }
    fun deleteTag(tag: Tag){
        if (selectedTagsList.value != null){
            var tags : MutableList<Tag> = selectedTagsList.value!! as MutableList<Tag>
            tags.removeIf { it.tagId == tag.tagId }
            _selectedTagsList.value = tags

            var notSelected = _notSelectedTagsList.value!! as MutableList<Tag>
            notSelected.add(tag)
            _notSelectedTagsList.value = notSelected
        }
    }


    fun isInputIsValid(
        name: String
    ) = (name.isNotBlank())


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