package com.example.ltechapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.testingapp.databinding.ActivityMainBinding
import com.example.ltechapp.utils.hide
import com.example.ltechapp.utils.show
import com.example.testingapp.MobileNavigationDirections
import com.example.testingapp.R

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private val binding: ActivityMainBinding by viewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initView()

    }

    private fun initView() {
        val navView = binding.bottomNavigationView
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment
        val navController = navHostFragment.navController

        navView.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.splashScreenFragment -> {
                    binding.bottomNavigationView.hide()
                }
                else -> binding.bottomNavigationView.show()
            }
        }

        navView.setOnItemSelectedListener {
            if (it.itemId == R.id.mainFragment) return@setOnItemSelectedListener showHome()
            else {
                val action = when (it.itemId) {
                    R.id.mainFragment -> MobileNavigationDirections.actionGlobalMainFragment()
                    R.id.stubFragment -> MobileNavigationDirections.actionGlobalStubFragment()
                    R.id.favouriteFragment -> MobileNavigationDirections.actionGlobalStubFragment()
                    else -> MobileNavigationDirections.actionGlobalMainFragment()
                }
                navController.navigate(action)
                true
            }
        }
    }

    private fun showHome(): Boolean {
        navigateToMainFragment()
        return true
    }

    private fun navigateToMainFragment() = findNavController(R.id.nav_host_fragment_content_main)
        .navigate(MobileNavigationDirections.actionGlobalMainFragment())

}
