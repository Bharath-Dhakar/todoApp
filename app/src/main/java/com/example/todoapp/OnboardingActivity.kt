package com.example.todoapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class OnboardingActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    private lateinit var dotsLayout: LinearLayout
    private lateinit var skipButton: Button
    private lateinit var dots: Array<TextView?>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

        // Initialize view components
        viewPager = findViewById(R.id.viewPager)
        dotsLayout = findViewById(R.id.dotsLayout)
        skipButton = findViewById<Button>(R.id.skipbtn)

        // Set up ViewPager with the adapter
        val onboardingPagerAdapter = OnboardingPagerAdapter(this)
        viewPager.adapter = onboardingPagerAdapter

        // Set up dots indicator
        setupDots(0)  // Starting with the first screen
        viewPager.registerOnPageChangeCallback(pageChangeCallback)

        // Skip button functionality
        skipButton.setOnClickListener {
            // Go to main activity or the next part of the app
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()  // Finish onboarding activity
        }
    }

    // Setting up dots for each page of onboarding
    private fun setupDots(position: Int) {
        dots = arrayOfNulls(3)  // Replace 3 with the number of onboarding screens
        dotsLayout.removeAllViews()  // Clear the previous dots

        for (i in dots.indices) {
            dots[i] = TextView(this)
            dots[i]?.text = "•"
            dots[i]?.textSize = 35f
            dots[i]?.setTextColor(ContextCompat.getColor(this, R.color.inactive_dot))  // Inactive dot color
            dotsLayout.addView(dots[i])
        }

        if (dots.isNotEmpty()) {
            dots[position]?.setTextColor(ContextCompat.getColor(this, R.color.active_dot))  // Active dot color
        }
    }

    // ViewPager page change callback
    private val pageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            setupDots(position)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewPager.unregisterOnPageChangeCallback(pageChangeCallback)
    }

    }
