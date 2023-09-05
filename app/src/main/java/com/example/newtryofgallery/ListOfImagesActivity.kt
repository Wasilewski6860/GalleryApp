package com.example.newtryofgallery

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newtryofgallery.adapter.ImageAdapter
import com.example.newtryofgallery.data.Image
import com.example.newtryofgallery.data.Tag
import com.example.newtryofgallery.data.User
import com.example.newtryofgallery.databinding.ActivityListOfImagesBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue

class ListOfImagesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListOfImagesBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database: DatabaseReference

    lateinit var adapter: ImageAdapter

    private var user : User? = null
    private var images : MutableList<Image> = mutableListOf<Image>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListOfImagesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.apply {
            addPhotoButton.setOnClickListener{
                val intent = Intent(this@ListOfImagesActivity, AddImageActivity::class.java)
                startActivity(intent)
            }
        }

        initRcView()
        initToolbar()
        auth()
    }

    private fun auth() {
        database = FirebaseDatabase.getInstance().getReference("Users")
        database.child(firebaseAuth?.currentUser?.uid!!).get().addOnSuccessListener {
            if (it.exists()) {
                user = it.getValue(User::class.java)
                onChangeTagsListener(database.child(firebaseAuth?.currentUser?.uid!!).child("selected_tags"))
                onChangeImageListener(database.child(firebaseAuth?.currentUser?.uid!!).child("images"))
            }
            else {
                user = User(
                    mutableListOf<String>(),
                    mutableListOf<String>(),
                    mutableListOf<String>(),
                    mutableListOf<Image>(),
                    getIntent().getStringExtra("name").toString(),
                    firebaseAuth?.currentUser?.email.toString()
                )
                database.child(firebaseAuth?.currentUser?.uid!!).setValue(user)
                    .addOnSuccessListener {
//                        Toast.makeText(this@CalendarActivity, "Successfully Saved", Toast.LENGTH_SHORT)
//                            .show()
                    }.addOnFailureListener {
                    }
                var intent = Intent(this,AddImageActivity :: class.java)
                startActivity(intent)
            }
        }.addOnFailureListener {
            Toast.makeText(this@ListOfImagesActivity, "Failed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun onChangeImageListener(dRef: DatabaseReference){
        dRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var selected_tags : MutableList<String> = user!!.selected_tags
                images = mutableListOf()
                for (s in snapshot.children){
                    var image = s.getValue(Image :: class.java)
                    var intersects = image!!.tags.intersect(selected_tags)
                    if (intersects.size==selected_tags.size) images.add(image)
                }


                adapter.submitList(images)
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    private fun onChangeTagsListener(dRef: DatabaseReference){
        dRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var selected_tags : MutableList<String> = mutableListOf()

                for (s in snapshot.children){
                    var tag : String? = s.getValue(String :: class.java)
                    tag?.let { selected_tags.add(it) }
                }

                adapter.submitList(images)
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    private fun initRcView() = with(binding){
        rcViewPhotos.layoutManager = LinearLayoutManager(this@ListOfImagesActivity)
        rcViewPhotos.setHasFixedSize(true)
        adapter = ImageAdapter()
        adapter.setOnClickListener(object :
            ImageAdapter.OnClickListener {
            override fun onClick(position: Int, model: Image) {
                val intent = Intent(this@ListOfImagesActivity, EditImageActivity::class.java)
                // Passing the data to the
                // EmployeeDetails Activity
                intent.putExtra("name", model.name.toString())
                startActivity(intent)
            }
        })
        rcViewPhotos.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.gallery_menu_toolbar, menu)

        return super.onCreateOptionsMenu(menu)
    }
    private fun initToolbar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        supportActionBar?.title = getIntent().getStringExtra("name")
        supportActionBar?.title = "Галерея"
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                var intent = Intent(this,SignInActivity :: class.java)
                firebaseAuth.signOut()
                startActivity(intent)
            }
            R.id.search_item -> {
                val intent = Intent(this@ListOfImagesActivity, SearchActivity::class.java)
                startActivity(intent)
            }
        }
        return true
    }
}