package com.example.googleform

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.example.googleform.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {

            val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
                    as NavHostFragment
            val navController = navHostFragment.navController

            val navInflater = navController.navInflater
            val graph = navInflater.inflate(R.navigation.main_nav)
            navController.setGraph(graph, null)
        }
    }
}