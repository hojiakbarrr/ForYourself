package com.example.foryourself.ui.fragmentsDetails

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.foryourself.data.currentUser.CurrentUser
import com.example.foryourself.R
import com.example.foryourself.utils.SharedPreferences
import com.example.foryourself.databinding.SplashFragmentBinding
import com.example.foryourself.utils.toast
import com.example.foryourself.viewmodels.detail.SplashViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SplashFragment : Fragment() {
    private val binding: SplashFragmentBinding by lazy {
        SplashFragmentBinding.inflate(layoutInflater)
    }
    private val gso: GoogleSignInOptions by lazy {
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail()
            .build()
    }
    var gsc: GoogleSignInClient? = null
    private lateinit var personName: String
    private lateinit var personEmail: String
    private lateinit var idgoogle: String

    private val viewModel: SplashViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.splash_fragment, container, false)


        gsc = GoogleSignIn.getClient(requireActivity(), gso!!)
        val acct: GoogleSignInAccount? = GoogleSignIn.getLastSignedInAccount(requireContext())
        if (acct != null) {
            personName = acct.displayName!!
            personEmail = acct.email!!
            idgoogle = acct.id!!
            viewModel.getallOrders(requireActivity()).observe(viewLifecycleOwner) {}
            if (acct.displayName != null && acct.email != null){
                viewModel.getuserOrder(acct.email!!, acct.displayName!!,idgoogle, requireActivity())
                SharedPreferences().saveCurrentUser(user = CurrentUser(email = acct.email!!, name = acct.displayName!!, id = acct.id!!),requireActivity())
                navigateToSecondFragment()
            }
        } else toast("Пройдите регистрацию в Google аккаунте")


        view.findViewById<TextView>(R.id.start).setOnClickListener {
            signIn()
        }



        return view
    }


    private fun signIn() {
        val signInIntent: Intent = gsc!!.signInIntent
        startActivityForResult(signInIntent, 1000)
    }

    private fun navigateToSecondFragment() {
        try {
            val action = SplashFragmentDirections.actionSplashFragmentToHomeFragment("")
            Navigation.findNavController(requireView()).navigate(action)
        } catch (e: Exception) {
            requireActivity().finishAffinity()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getallOrders(requireActivity()).observe(viewLifecycleOwner) {}
        if (personName != null && personEmail != null){ viewModel.getuserOrder(personEmail!!, personName!!, idgoogle,requireActivity()) }
    }


    override fun onResume() {
        super.onResume()
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation).visibility =
            View.GONE
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1000) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                task.getResult(ApiException::class.java)
                navigateToSecondFragment()
            } catch (e: ApiException) {
                Toast.makeText(requireContext(), "Поврежден ваш Google аккаунт", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

}