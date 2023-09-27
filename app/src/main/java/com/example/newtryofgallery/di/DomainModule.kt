package com.example.newtryofgallery.di

import com.example.newtryofgallery.domain.use_cases.AddPictureTagCrossRefUseCase
import com.example.newtryofgallery.domain.use_cases.AddPictureUseCase
import com.example.newtryofgallery.domain.use_cases.AddTagUseCase
import com.example.newtryofgallery.domain.use_cases.DeleteCrossRefsByPictureIdUseCase
import com.example.newtryofgallery.domain.use_cases.EditPictureUseCase
import com.example.newtryofgallery.domain.use_cases.GetAllPicturesUseCase
import com.example.newtryofgallery.domain.use_cases.GetAllTagsUseCase
import com.example.newtryofgallery.domain.use_cases.GetPictureByIdUseCase
import com.example.newtryofgallery.domain.use_cases.GetPicturesOfTagUseCase
import com.example.newtryofgallery.domain.use_cases.GetPicturesWithTagsUseCase
import com.example.newtryofgallery.domain.use_cases.GetTagsOfPictureUseCase
import org.koin.dsl.module

val domainModule = module {

    

    factory<AddPictureUseCase> { AddPictureUseCase(picturesRepository = get()) }
    factory<AddPictureTagCrossRefUseCase> { AddPictureTagCrossRefUseCase(picturesRepository = get()) }
    factory<AddTagUseCase> { AddTagUseCase(picturesRepository = get()) }
    factory<GetPicturesOfTagUseCase> { GetPicturesOfTagUseCase(picturesRepository = get()) }
    factory<GetTagsOfPictureUseCase> { GetTagsOfPictureUseCase(picturesRepository = get()) }
    factory<GetAllPicturesUseCase> { GetAllPicturesUseCase(picturesRepository = get()) }
    factory<GetAllTagsUseCase> { GetAllTagsUseCase(picturesRepository = get()) }
    factory<GetPicturesWithTagsUseCase> { GetPicturesWithTagsUseCase(picturesRepository = get()) }
    factory<DeleteCrossRefsByPictureIdUseCase> { DeleteCrossRefsByPictureIdUseCase(picturesRepository = get()) }
    factory<EditPictureUseCase> { EditPictureUseCase(picturesRepository = get()) }
    factory<GetPictureByIdUseCase> { GetPictureByIdUseCase(picturesRepository = get()) }

//    factory<DeleteCatUseCase> { DeleteCatUseCase(catsRepository = get()) }
//    factory<EditCatUseCase> { EditCatUseCase(catsRepository = get()) }
//    factory<GetAllCatsUseCase> { GetAllCatsUseCase(catsRepository = get()) }
//    factory<GetCatsByNameUseCase> { GetCatsByNameUseCase(catsRepository = get()) }
//    factory<GetCatUseCase> { GetCatUseCase(catsRepository = get()) }
//    factory<GetImageUseCase> { GetImageUseCase(catsRepository = get()) }
//
//    factory<GetFavouriteCatsUseCase> { GetFavouriteCatsUseCase(catsRepository = get()) }
//    factory<GetFavouriteCatsByNameUseCase> { GetFavouriteCatsByNameUseCase(catsRepository = get()) }
}