package com.herdal.socialmediaappfirebase.view.registration

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.herdal.socialmediaappfirebase.R
import com.herdal.socialmediaappfirebase.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        val view = binding.root

        auth = Firebase.auth

        val currentUser = auth.currentUser
        if (currentUser != null) {
            val action = SignUpFragmentDirections.actionSignUpFragmentToPostListFragment()
            Navigation.findNavController(view).navigate(action)
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




        binding.textViewGoToLogin.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
        }

        binding.buttonSignUp.setOnClickListener { view ->
            signUp(view)
        }
    }

    fun signUp(view: View) {
        val email = binding.editTextSignUpEmail.text.toString()
        val password = binding.editTextSignUpPassword.text.toString()

        if (email.equals("") || password.equals("")) {
            Toast.makeText(requireContext(), "Please fill out all the fields!", Toast.LENGTH_SHORT)
                .show()
        } else {
            auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
                val action = SignUpFragmentDirections.actionSignUpFragmentToPostListFragment()
                Navigation.findNavController(view).navigate(action)
            }.addOnFailureListener { exception ->
                Toast.makeText(requireContext(), exception.localizedMessage, Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}