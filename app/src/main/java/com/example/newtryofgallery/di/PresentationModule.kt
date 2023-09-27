package com.example.newtryofgallery.di

import com.example.newtryofgallery.presentation.MyViewModel
import com.example.newtryofgallery.presentation.add_image.AddImageViewModel
import com.example.newtryofgallery.presentation.image_info.ImageInfoViewModel
import com.example.newtryofgallery.presentation.local_images.LocalImagesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {



    viewModel<LocalImagesViewModel>{
        LocalImagesViewModel(
            getAllPicturesUseCase = get(),
            getAllTagsUseCase = get(),
            getPicturesWithTagsUseCase = get()
        )
    }
    viewModel<AddImageViewModel>{
        AddImageViewModel(
            addPictureUseCase = get(), //
            addTagUseCase = get(), //
            getTagsOfPictureUseCase = get(),  //
            addPictureTagCrossRefUseCase = get(), //
            getAllTagsUseCase = get(), //
            editPictureUseCase = get(), //
            deleteCrossRefsByPictureIdUseCase = get()
        )
    }
    viewModel<ImageInfoViewModel>{
        ImageInfoViewModel(
            getPictureUseCase = get(),
            addPictureTagCrossRefUseCase = get(),
            deleteCrossRefsByPictureIdUseCase = get()

        )
    }

    viewModel<MyViewModel>{
        MyViewModel(
            getTagsOfPictureUseCase = get(),  //
            addPictureTagCrossRefUseCase = get(), //
            getAllTagsUseCase = get(), //
            editPictureUseCase = get(), //
            deleteCrossRefsByPictureIdUseCase = get()
        )
    }
}