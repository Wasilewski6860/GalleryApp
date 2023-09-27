package com.example.newtryofgallery.presentation.local_images

import com.example.newtryofgallery.domain.use_cases.GetPicturesWithTagsUseCase


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newtryofgallery.domain.models.Picture
import com.example.newtryofgallery.domain.models.Tag
import com.example.newtryofgallery.domain.use_cases.GetAllPicturesUseCase
import com.example.newtryofgallery.domain.use_cases.GetAllTagsUseCase
import com.example.newtryofgallery.presentation.models.ScreenState
import kotlinx.coroutines.launch

class LocalImagesViewModel(
    private val getAllPicturesUseCase: GetAllPicturesUseCase,
    private val getPicturesWithTagsUseCase: GetPicturesWithTagsUseCase,
    private val getAllTagsUseCase: GetAllTagsUseCase
//    private val getAllCatsUseCase: GetAllCatsUseCase,
//    private val getCatsByNameUseCase: GetCatsByNameUseCase,
//    private val editCatUseCase: EditCatUseCase
) : ViewModel() {

    private val _screenState = MutableLiveData<ScreenState>()
    val screenState: LiveData<ScreenState> = _screenState

    private val _picturesList = MutableLiveData<List<Picture>>()
    val picturesList: LiveData<List<Picture>> = _picturesList

    private val _selectedTagsList = MutableLiveData<List<Tag>>()
    val selectedTagsList: LiveData<List<Tag>> = _selectedTagsList

    private val _notSelectedTagsList = MutableLiveData<List<Tag>>()
    val notSelectedTagsList: LiveData<List<Tag>> = _notSelectedTagsList

    private val _allTagsList = MutableLiveData<List<Tag>>()
    val allTagsList: LiveData<List<Tag>> = _allTagsList

    fun fetchPictures() {
        _screenState.value = ScreenState.Loading
        viewModelScope.launch {

            try {
                if (selectedTagsList.value?.isEmpty() == false){
                    _picturesList.value = getPicturesWithTagsUseCase.execute(selectedTagsList.value!!)
                }
                else{
                    _picturesList.value = getAllPicturesUseCase.execute()
                }
                if (allTagsList.value === null || allTagsList.value?.isEmpty() == true){
                    _allTagsList.value = getAllTagsUseCase.execute()
                    _notSelectedTagsList.value = getAllTagsUseCase.execute()
                }

                _screenState.value =
                    if (picturesList.value?.isEmpty() == true) ScreenState.Empty else ScreenState.Content

            } catch (e: Exception) {
                _screenState.value = ScreenState.Error
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


}