package com.example.newtryofgallery.presentation.models

sealed interface ScreenState{
    object Loading: ScreenState
    object Content: ScreenState
    object Error: ScreenState
    object Empty: ScreenState
}