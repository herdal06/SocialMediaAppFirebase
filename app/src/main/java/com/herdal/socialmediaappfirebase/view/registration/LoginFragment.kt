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
import com.herdal.socialmediaappfirebase.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding.root
        auth = Firebase.auth

        val currentUser = auth.currentUser
        if (currentUser != null){
            val action = LoginFragmentDirections.actionLoginFragmentToPostListFragment()
            Navigation.findNavController(view).navigate(action)
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




        binding.textViewGoToSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
        }

        binding.buttonLogin.setOnClickListener { view ->
            login(view)
        }
    }

    fun login(view: View) {
        val email = binding.editTextLoginEmail.text.toString()
        val password = binding.editTextLoginPassword.text.toString()

        if (email.equals("") || password.equals("")) {
            Toast.makeText(requireContext(), "PLease fill out all the fields!", Toast.LENGTH_SHORT)
                .show()
        } else {
            auth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
                val action = LoginFragmentDirections.actionLoginFragmentToPostListFragment()
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