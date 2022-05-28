package com.example.foryourself.ui.fragmentsMain

import android.app.AlertDialog
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.foryourself.R
import com.example.foryourself.databinding.ProfileFragmentBinding
import com.example.foryourself.utils.*
import com.example.foryourself.viewmodels.main.ProfileViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.bottomnavigation.BottomNavigationView
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

    override fun onResume() {
        super.onResume()
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation).visibility =
            View.VISIBLE
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

        binding.btnAddress.setOnClickListener {
            this.dialog()
        }
        binding.btnAbout.setOnClickListener {
            this.dialogabout()
        }
        binding.btnWhatsapp.setOnClickListener {
            this.dialogwhatsapp()
        }
        binding.btnWork.setOnClickListener {
            this.dialogworktime()
        }
        binding.profileBtnEdit.setOnClickListener {
            profileedit(acct?.photoUrl, acct?.displayName, acct?.email)
        }

    }

    private fun profileedit(photoUrl: Uri?, displayName: String?, email: String?) {

        val builder = AlertDialog.Builder(requireActivity())
        val inflater = layoutInflater
        val dialogLayout = inflater.inflate(R.layout.dialog_profile, null)

        var name: TextView = dialogLayout.findViewById(R.id.txt_name)
        var mail: TextView = dialogLayout.findViewById(R.id.txt_mail)
        var edit_number_txt: TextView = dialogLayout.findViewById(R.id.edittxt_number)
        var edit_number_image: ImageView = dialogLayout.findViewById(R.id.edit_number)
        var avater: ImageView = dialogLayout.findViewById(R.id.rounded_image)

        binding.apply {
            name.text = displayName
            mail.text = email
            requireContext().uploadImage(photoUrl.toString(), avater)
        }



        with(builder) {
            setPositiveButton("Сохранить") { dialog, which ->


            }.setNegativeButton("Нет") { dialog, which ->
                dialog.dismiss()
            }.setView(dialogLayout).create().show()
        }

    }

    private fun signOut() {
        gsc!!.signOut().addOnCompleteListener {
            activity?.finish()
        }
    }
}