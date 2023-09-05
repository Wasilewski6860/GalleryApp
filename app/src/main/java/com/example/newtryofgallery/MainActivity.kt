package com.example.newtryofgallery

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newtryofgallery.adapter.TagAdapter
import com.example.newtryofgallery.data.Tag
import com.example.newtryofgallery.data.User
import com.example.newtryofgallery.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var firebaseRef : DatabaseReference
    private lateinit var storageRef : StorageReference

    private var strings_selected_tags : MutableList<String> = mutableListOf<String>()
    private var strings_all_tags : MutableList<String> = mutableListOf<String>()
    private var selected_tags : MutableList<Tag> = mutableListOf<Tag>()
    private var allTags : MutableList<Tag> = mutableListOf<Tag>()

    lateinit var selectedTagsAdapter: TagAdapter
    lateinit var allTagsAdapter: TagAdapter

    private var user : User? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        initRcViewAll()
        initRcViewSelected()
        auth()
    }

    private fun auth() {
        firebaseRef = FirebaseDatabase.getInstance().getReference("Users")
        storageRef = FirebaseStorage.getInstance().getReference("Images")
        firebaseRef.child(firebaseAuth?.currentUser?.uid!!).get().addOnSuccessListener {
            if (it.exists()) {
                user = it.getValue(User::class.java)
                onChangeAllListener(firebaseRef.child(firebaseAuth?.currentUser?.uid!!).child("all_tags"))
                onChangeSelectedListener(firebaseRef.child(firebaseAuth?.currentUser?.uid!!).child("selected_tags"))
            }
        }
    }

    private fun onChangeSelectedListener(dRef: DatabaseReference){
        dRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                var list : MutableList<Tag> = mutableListOf<Tag>()
                strings_selected_tags = mutableListOf<String>()
                for (s in snapshot.children){
                    var tag = s.getValue(String :: class.java)
                    if (tag != null) {
                        list.add(Tag(tag))
                        strings_selected_tags.add(tag)
                    }
                }
                selected_tags = list
                selectedTagsAdapter.submitList(list)
                allTagsAdapter.notifyDataSetChanged()
                selectedTagsAdapter.notifyDataSetChanged()

            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
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
                allTagsAdapter.submitList(list)
                allTagsAdapter.notifyDataSetChanged()
                selectedTagsAdapter.notifyDataSetChanged()

//                var ref = firebaseRef.child(firebaseAuth?.currentUser?.uid!!)
//                user!!.all_tags = strings_all_tags
//                user!!.selected_tags = strings_selected_tags
//                ref.setValue(user)
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    private fun initRcViewSelected() = with(binding){
        testSelected.layoutManager = LinearLayoutManager(this@MainActivity)
        testSelected.setHasFixedSize(true)
        selectedTagsAdapter = TagAdapter()
        selectedTagsAdapter.setOnClickListener(object :
            TagAdapter.OnClickListener {
            override fun onClick(position: Int, model: Tag) {
                strings_selected_tags.removeAt(position)
                selected_tags.removeAt(position)
                selectedTagsAdapter.submitList(selected_tags)

                firebaseRef.child(firebaseAuth?.currentUser?.uid!!).child("selected_tags").setValue(strings_selected_tags)
                firebaseRef.child(firebaseAuth?.currentUser?.uid!!).child("all_tags").setValue(strings_all_tags)
                user!!.selected_tags = strings_selected_tags
                user!!.all_tags = strings_all_tags
                Toast.makeText(this@MainActivity, user!!.selected_tags.size.toString(), Toast.LENGTH_SHORT).show()
                allTagsAdapter.submitList(allTags)
                selectedTagsAdapter.submitList(selected_tags)
                allTagsAdapter.notifyDataSetChanged()
                selectedTagsAdapter.notifyDataSetChanged()
            }
        })
        testSelected.adapter = selectedTagsAdapter
    }
    private fun initRcViewAll() = with(binding){
        testAll.layoutManager = LinearLayoutManager(this@MainActivity)
        testAll.setHasFixedSize(true)
        allTagsAdapter = TagAdapter()
        allTagsAdapter.setOnClickListener(object :
            TagAdapter.OnClickListener {
            override fun onClick(position: Int, model: Tag) {
                var listOfAllTags = user!!.all_tags
                strings_selected_tags.add(listOfAllTags[position])
                selected_tags.add(Tag(listOfAllTags[position]))
                selectedTagsAdapter.submitList(selected_tags)

                firebaseRef.child(firebaseAuth?.currentUser?.uid!!).child("selected_tags").setValue(strings_selected_tags)
                firebaseRef.child(firebaseAuth?.currentUser?.uid!!).child("all_tags").setValue(strings_all_tags)
                user!!.selected_tags = strings_selected_tags
                user!!.all_tags = strings_all_tags
                Toast.makeText(this@MainActivity, user!!.selected_tags.size.toString(), Toast.LENGTH_SHORT).show()
                allTagsAdapter.submitList(allTags)
                selectedTagsAdapter.submitList(selected_tags)
                allTagsAdapter.notifyDataSetChanged()
                selectedTagsAdapter.notifyDataSetChanged()

            }
        })
        testAll.adapter = allTagsAdapter
    }
}