package com.example.foryourself.ui.fragmentsDetails

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.foryourself.R
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
    var gso: GoogleSignInOptions? = null
    var gsc: GoogleSignInClient? = null

    private lateinit var viewModel: SplashViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.splash_fragment, container, false)


        gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()

        gsc = GoogleSignIn.getClient(requireActivity(), gso!!)
        val acct: GoogleSignInAccount? = GoogleSignIn.getLastSignedInAccount(requireContext())
        if (acct != null) {
            navigateToSecondFragment()
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
            val action =SplashFragmentDirections.actionSplashFragmentToHomeFragment("")
            Navigation.findNavController(requireView()).navigate(action)
        } catch (e: Exception) {
            activity?.finishAffinity()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SplashViewModel::class.java)
        // TODO: Use the ViewModel
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