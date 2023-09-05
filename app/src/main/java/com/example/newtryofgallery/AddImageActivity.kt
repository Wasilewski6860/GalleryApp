package com.example.newtryofgallery

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newtryofgallery.adapter.TagAdapter
import com.example.newtryofgallery.data.Image
import com.example.newtryofgallery.data.Tag
import com.example.newtryofgallery.data.User
import com.example.newtryofgallery.databinding.ActivityAddImageBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class AddImageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddImageBinding
    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var firebaseRef : DatabaseReference
    private lateinit var storageRef : StorageReference

    private var uri: Uri? = null
    private var imageUrl : String? = null

    private var strings_selected_tags : MutableList<String> = mutableListOf<String>()
    private var strings_all_tags : MutableList<String> = mutableListOf<String>()
    private var selected_tags : MutableList<Tag> = mutableListOf<Tag>()
    private var allTags : MutableList<Tag> = mutableListOf<Tag>()

    lateinit var selectedTagsAdapter: TagAdapter
    lateinit var allTagsAdapter: TagAdapter



    private var user : User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddImageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()



        val pickImage = registerForActivityResult(ActivityResultContracts.GetContent()){
            binding.addImageView.setImageURI(it)
            if (it != null){
                uri = it
            }
        }

        binding.loadImageButton.setOnClickListener {
            pickImage.launch("image/*")
        }
        binding.finishAdding.setOnClickListener {
            saveData()
        }

        binding.addTagButtonView.setOnClickListener {
            if (!binding.addTagEditTextView.text.toString().isEmpty() && !strings_selected_tags.contains(binding.addTagEditTextView.text.toString())){
                strings_selected_tags.add(binding.addTagEditTextView.text.toString())
                strings_all_tags.add(binding.addTagEditTextView.text.toString())
                selected_tags.add(Tag(binding.addTagEditTextView.text.toString()))
                allTags.add(Tag(binding.addTagEditTextView.text.toString()))
                binding.addTagEditTextView.setText("")
            }

            allTagsAdapter.submitList(allTags)
            selectedTagsAdapter.submitList(selected_tags)
            allTagsAdapter.notifyDataSetChanged()
            selectedTagsAdapter.notifyDataSetChanged()

            firebaseRef.child(firebaseAuth?.currentUser?.uid!!).child("all_tags").setValue(strings_all_tags)
            user!!.all_tags = strings_all_tags
        }

        initRcViewAll()
        initRcViewSelected()
        initToolbar()
        auth()

    }

    private fun auth() {
        firebaseRef = FirebaseDatabase.getInstance().getReference("Users")
        storageRef = FirebaseStorage.getInstance().getReference("Images")
        firebaseRef.child(firebaseAuth?.currentUser?.uid!!).get().addOnSuccessListener {
            if (it.exists()) {
                user = it.getValue(User::class.java)
                onChangeAllListener(firebaseRef.child(firebaseAuth?.currentUser?.uid!!).child("all_tags"))
            }
        }
    }

    private fun onChangeAllListener(dRef: DatabaseReference){
        dRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                var list : MutableList<Tag> = mutableListOf<Tag>()
                strings_all_tags = mutableListOf<String>()
                for (s in snapshot.children){
                    var tag = s.getValue(String :: class.java)
                    if (tag != null) {
                        list.add(Tag(tag))
                        strings_all_tags.add(tag)
                    }
                }
                allTags = list
                if (selected_tags.size==0 && allTags.size!=0){
                    selected_tags.add(allTags[0])
                    strings_selected_tags.add(strings_all_tags[0])
                }
                runOnUiThread {
                    allTagsAdapter.submitList(list)
                    selectedTagsAdapter.submitList(selected_tags)
                    allTagsAdapter.notifyDataSetChanged()
                    selectedTagsAdapter.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }


    private fun initRcViewSelected() = with(binding){
        addSelectedTagsRcVie.layoutManager = LinearLayoutManager(this@AddImageActivity)
        addSelectedTagsRcVie.setHasFixedSize(true)
        selectedTagsAdapter = TagAdapter()
        selectedTagsAdapter.setOnClickListener(object :
            TagAdapter.OnClickListener {
            override fun onClick(position: Int, model: Tag) {
                strings_selected_tags.removeAt(position)
                selected_tags.removeAt(position)
//                selectedTagsAdapter.submitList(selected_tags)

                firebaseRef.child(firebaseAuth?.currentUser?.uid!!).child("all_tags").setValue(strings_all_tags)
                user!!.all_tags = strings_all_tags
                Toast.makeText(this@AddImageActivity, user!!.selected_tags.size.toString(), Toast.LENGTH_SHORT).show()

                runOnUiThread {
                    allTagsAdapter.submitList(allTags)
                    selectedTagsAdapter.submitList(selected_tags)
                    allTagsAdapter.notifyDataSetChanged()
                    selectedTagsAdapter.notifyDataSetChanged()
                }
            }
        })
        addSelectedTagsRcVie.adapter = selectedTagsAdapter
    }
    private fun initRcViewAll() = with(binding){
        addAllTagsRcView.layoutManager = LinearLayoutManager(this@AddImageActivity)
        addAllTagsRcView.setHasFixedSize(true)
        allTagsAdapter = TagAdapter()
        allTagsAdapter.setOnClickListener(object :
            TagAdapter.OnClickListener {
            override fun onClick(position: Int, model: Tag) {
                var listOfAllTags = user!!.all_tags
                strings_selected_tags.add(listOfAllTags[position])
                selected_tags.add(Tag(listOfAllTags[position]))
//                selectedTagsAdapter.submitList(selected_tags)

                firebaseRef.child(firebaseAuth?.currentUser?.uid!!).child("all_tags").setValue(strings_all_tags)
                user!!.all_tags = strings_all_tags
                Toast.makeText(this@AddImageActivity, user!!.selected_tags.size.toString(), Toast.LENGTH_SHORT).show()

                runOnUiThread {
                    allTagsAdapter.submitList(allTags)
                    selectedTagsAdapter.submitList(selected_tags)
                    allTagsAdapter.notifyDataSetChanged()
                    selectedTagsAdapter.notifyDataSetChanged()
                }

            }
        })
        addAllTagsRcView.adapter = allTagsAdapter
    }

    private fun saveData() {
        val name = binding.addEditTextView.text.toString()
        storageRef = storageRef.child(firebaseAuth?.currentUser?.uid!!)

        uri?.let{
            storageRef.child(name).putFile(it)
                .addOnSuccessListener { task->
                    task.metadata!!.reference!!.downloadUrl
                        .addOnSuccessListener { url ->
                            val imgUrl = url.toString()

                            firebaseRef.child(firebaseAuth?.currentUser?.uid!!).get().addOnSuccessListener {
                                if (it.exists()) user = it.getValue(User::class.java)
                            }
                            var list = user!!.images
                            list.add(Image(name,imgUrl,strings_selected_tags))
                            user!!.images = list

                            firebaseRef.child(firebaseAuth?.currentUser?.uid!!).child("images").setValue(list)
                                .addOnCompleteListener{

                                }
                                .addOnFailureListener{error ->

                                }
                        }
                }
        }

        val intent = Intent(this@AddImageActivity, ListOfImagesActivity::class.java)
        startActivity(intent)
    }

    private fun initToolbar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        supportActionBar?.title = getIntent().getStringExtra("name")
        supportActionBar?.title = "Добавить изображение"
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                var intent = Intent(this,ListOfImagesActivity :: class.java)
                startActivity(intent)
            }
        }
        return true
    }
}