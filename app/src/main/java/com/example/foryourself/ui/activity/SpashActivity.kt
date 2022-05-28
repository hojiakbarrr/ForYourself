package com.example.foryourself.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.foryourself.databinding.ActivitySpashBinding
import com.example.foryourself.utils.toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SpashActivity : AppCompatActivity() {
    private val binding: ActivitySpashBinding by lazy {
        ActivitySpashBinding.inflate(layoutInflater)
    }
    var gso: GoogleSignInOptions? = null
    var gsc: GoogleSignInClient? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()

        gsc = GoogleSignIn.getClient(this, gso!!)
        val acct: GoogleSignInAccount? = GoogleSignIn.getLastSignedInAccount(this)
        if (acct != null) {
            navigateToSecondActivity()
        }
        else toast("Пройдите регистрацию в Google аккаунте")
        binding.start.setOnClickListener {
            signIn()
        }
    }

    private fun signIn() {
        val signInIntent: Intent = gsc!!.signInIntent
        startActivityForResult(signInIntent, 1000)
    }

    private fun navigateToSecondActivity() {
        finish()
        startActivity(Intent(this@SpashActivity, MainActivity::class.java))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1000) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                task.getResult(ApiException::class.java)
                navigateToSecondActivity()
            } catch (e: ApiException) {
                Log.d("fail",e.toString())
                Toast.makeText(applicationContext, "Something went wrong", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}