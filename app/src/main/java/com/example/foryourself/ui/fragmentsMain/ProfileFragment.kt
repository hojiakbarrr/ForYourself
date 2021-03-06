package com.example.foryourself.ui.fragmentsMain

import android.app.AlertDialog
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.foryourself.R
import com.example.foryourself.data.retrofitResponse.users.postUser.PutUsers
import com.example.foryourself.databinding.ProfileFragmentBinding
import com.example.foryourself.ui.fragmentsDetails.DetaillFragmentDirections
import com.example.foryourself.ui.fragmentsDetails.SplashFragment
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
    private lateinit var objectID: String
    private lateinit var accountEmail: String
    private var nomer: String = ""
    private lateinit var personName: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
        gsc = GoogleSignIn.getClient(requireActivity(), gso!!)
        val acct = GoogleSignIn.getLastSignedInAccount(requireContext())

        if (acct != null) {
            personName = acct.displayName!!
            accountEmail = acct.email.toString()
            Log.d("foto", acct.photoUrl.toString())
            binding.apply {
                requireContext().uploadImage(acct.photoUrl.toString(), profileImage)
                prodileName.text = personName
                prodileMail.text = accountEmail
                logOut.setOnClickListener {
                    signOut()
                }
                profileBtnEdit.setOnClickListener {
                    profileedit(acct.photoUrl, acct.displayName, acct.email)
                }
            }
        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation).visibility =
            View.VISIBLE
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.apply {
            btnAddress.setOnClickListener {
                dialog()
            }
            btnAbout.setOnClickListener {
                dialogabout()
            }
            btnWhatsapp.setOnClickListener {
                dialogwhatsapp()
            }
            btnWork.setOnClickListener {
                dialogworktime()
            }
        }


    }

    private fun profileedit(photoUrl: Uri?, displayName: String?, email: String?) {

        val builder = AlertDialog.Builder(requireActivity())
        val inflater = layoutInflater
        val dialogLayout = inflater.inflate(R.layout.dialog_profile, null)

        var name: TextView = dialogLayout.findViewById(R.id.txt_name)
        var mail: TextView = dialogLayout.findViewById(R.id.txt_mail)
        var edit_number_txt: EditText = dialogLayout.findViewById(R.id.edittxt_number)
        var avater: ImageView = dialogLayout.findViewById(R.id.rounded_image)

        binding.apply {
            name.text = displayName
            mail.text = email
            requireContext().uploadImage(photoUrl.toString(), avater)
            edit_number_txt.setText(nomer)
        }

        with(builder) {
            setPositiveButton("??????????????????") { dialog, which ->


                nomer = edit_number_txt.text.toString()


            }.setNegativeButton("??????") { dialog, which ->
                dialog.dismiss()
            }.setView(dialogLayout).create().show()
        }

    }


    private fun signOut() {
        gsc!!.signOut().addOnCompleteListener {
            activity?.finish()
//            val actionss = ProfileFragmentDirections.fromProfileFragment2ToSplashFragment()
//            Navigation.findNavController(requireView()).navigate(actionss)
//
//            val manager: FragmentManager = requireActivity().supportFragmentManager
//            manager.beginTransaction().add(R.id.profileFragment2, SplashFragment()).addToBackStack(null).commit()
        }
    }
}