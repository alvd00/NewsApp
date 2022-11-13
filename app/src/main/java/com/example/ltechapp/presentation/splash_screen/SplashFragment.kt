package com.example.ltechapp.presentation.splash_screen

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.testingapp.R

class SplashFragment : Fragment() {
    private companion object {
        const val DELAY = 3000L
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Handler(Looper.myLooper()!!).postDelayed({
            val navController = findNavController()
            val action = SplashFragmentDirections
                .actionSplashScreenFragmentToMainFragment()
            navController.navigate(action)
        }, DELAY)
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }
}