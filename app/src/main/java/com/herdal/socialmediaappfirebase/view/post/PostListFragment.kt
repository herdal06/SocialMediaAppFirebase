package com.herdal.socialmediaappfirebase.view.post

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.herdal.socialmediaappfirebase.R
import com.herdal.socialmediaappfirebase.adapter.PostAdapter
import com.herdal.socialmediaappfirebase.databinding.FragmentPostListBinding
import com.herdal.socialmediaappfirebase.model.Post
import kotlinx.android.synthetic.main.fragment_post_list.view.*

class PostListFragment : Fragment() {

    private var _binding: FragmentPostListBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    private lateinit var postList: ArrayList<Post>
    private lateinit var postAdapter: PostAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPostListBinding.inflate(inflater, container, false)
        val view = binding.root

        setHasOptionsMenu(true)

        view.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_postListFragment_to_addPostFragment)
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth
        db = Firebase.firestore

        postList = ArrayList<Post>()

        getData()

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        postAdapter = PostAdapter(postList)
        binding.recyclerView.adapter = postAdapter

    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_menu, menu)

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.logOut) {
            auth.signOut()
            findNavController().navigate(R.id.action_postListFragment_to_loginFragment)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getData() {
        db.collection("Posts").addSnapshotListener { value, error ->
            if(error != null) {
                Toast.makeText(requireContext(),error.localizedMessage,Toast.LENGTH_SHORT).show()
            } else {
                if(value != null) {
                    if(!value.isEmpty) {
                        val documents = value.documents
                        for(document in documents) {
                            val useremail = document.get("useremail") as String
                            val comment = document.get("comment") as String

                            val post = Post(useremail,comment)
                            postList.add(post)
                        }
                        postAdapter.notifyDataSetChanged()
                    }
                }
            }
        }
    }
}