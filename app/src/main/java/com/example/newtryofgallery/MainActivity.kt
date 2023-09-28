package com.example.newtryofgallery

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.newtryofgallery.data.db.PicturesDatabase
import com.example.newtryofgallery.data.db.dto.PictureDto
import com.example.newtryofgallery.data.db.dto.TagDto
import com.example.newtryofgallery.data.db.dto.relations.PictureTagCrossRefDto
import com.example.taggalleryapp.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var navHostFragment: NavHostFragment

    private val sharedPreferences: SharedPreferences by inject()
    private val sharedPreferencesEditor: SharedPreferences.Editor by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        navHostFragment = supportFragmentManager
            .findFragmentById(R.id.navHostFragment) as NavHostFragment

        setSupportActionBar(findViewById(R.id.toolbar))
        var navigationView : NavigationView = findViewById(R.id.navigation_view)


        val dao = PicturesDatabase.getInstance(this).dao
        var tagsCount = 1000
        val rnds : Int = (0..10).random()
        var i = 0
        lifecycleScope.launch {
            while (i<tagsCount){
                var tagDto =
                    TagDto(
                        tagId = 0,
                        name = i.toString()
                    )

                var tagId = dao.insertTag(
                   tagDto
                )

                i++
            }
        }


        navigationView.setupWithNavController(navHostFragment.findNavController())

        navHostFragment.findNavController()
            .addOnDestinationChangedListener { _, destination, _ ->
                when(destination.id) {
                    R.id.localImagesFragment,R.id.networkImagesFragment ->
                        navigationView.visibility = View.VISIBLE
                    else -> navigationView.visibility = View.GONE
                }
            }
    }

}