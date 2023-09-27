package com.example.newtryofgallery.presentation.image_info

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
import com.example.newtryofgallery.domain.use_cases.GetPictureByIdUseCase
import com.example.newtryofgallery.domain.use_cases.GetTagsOfPictureUseCase
import com.example.newtryofgallery.presentation.add_image.AddImageFragmentDirections
import com.example.newtryofgallery.presentation.add_image.AddImageViewModel
import com.example.newtryofgallery.presentation.local_images.adapter.TagsAdapter
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ImageInfoViewModel(
    private val getPictureUseCase: GetPictureByIdUseCase,
    private val addPictureTagCrossRefUseCase: AddPictureTagCrossRefUseCase,
    private val deleteCrossRefsByPictureIdUseCase: DeleteCrossRefsByPictureIdUseCase

) : ViewModel() {

    private val _picture = MutableLiveData<Picture>()
    val picture: LiveData<Picture> = _picture




    fun getPicture(pictureId: Int) {
        viewModelScope.launch {
            _picture.value = getPictureUseCase.execute(pictureId)
        }
    }

    fun delete() {
        viewModelScope.launch {
            var id : Int = picture.value?.pictureId!!
            deleteCrossRefsByPictureIdUseCase.execute(id)
        }
    }


}