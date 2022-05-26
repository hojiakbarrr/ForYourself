package com.example.foryourself.ui.fragmentsMain

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.foryourself.R
import com.example.foryourself.databinding.ProfileFragmentBinding
import com.example.foryourself.utils.toast
import com.example.foryourself.utils.uploadImage
import com.example.foryourself.viewmodels.main.ProfileViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private val binding: ProfileFragmentBinding by lazy {
        ProfileFragmentBinding.inflate(layoutInflater)
    }
    var gso: GoogleSignInOptions? = null
    var gsc: GoogleSignInClient? = null
    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
        gsc = GoogleSignIn.getClient(requireActivity(), gso!!)
        val acct = GoogleSignIn.getLastSignedInAccount(requireContext())

        if (acct != null) {
            val personName = acct.displayName
            val personEmail = acct.email
            acct.photoUrl
            Log.d("foto", acct.photoUrl.toString())
            binding.apply {
                requireContext().uploadImage(acct.photoUrl.toString(), profileImage)
                prodileName.text = personName
                prodileMail.text = personEmail
                logOut.setOnClickListener {
                    signOut()
                }
            }
        }


    }

    private fun signOut() {
        gsc!!.signOut().addOnCompleteListener {
            activity?.finish()
        }
    }
}