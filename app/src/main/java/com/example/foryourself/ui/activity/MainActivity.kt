package com.example.foryourself.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.foryourself.R
import com.example.foryourself.databinding.ActivityMainBinding
import com.example.foryourself.db.ProductDao
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
//    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    @Inject
    lateinit var dao : ProductDao

//    override fun onBackPressed() {
//        super.onBackPressed()
//        finishAffinity()
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        val navController = Navigation.findNavController(this, R.id.host_fragment)
        NavigationUI.setupWithNavController(bottomNavigation, navController)

    }

    override fun onStop() {
        super.onStop()
//        GlobalScope.launch {
//            dao.clearTable()
//            Log.d("rtr", dao.getProductsFromDATABASE().toString())
//        }
    }

    override fun onDestroy() {
        super.onDestroy()
//        GlobalScope.launch {
//            dao.clearTable()
//            Log.d("rtr", dao.getProductsFromDATABASE().toString())
//        }
    }
}