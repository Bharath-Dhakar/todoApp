package com.example.todoapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2

class OnboardingActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    private lateinit var dotsLayout: LinearLayout
    private lateinit var getStart: Button
    private lateinit var skipButton: Button
    private lateinit var dots: Array<TextView?>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

        // Initialize view components
        viewPager = findViewById(R.id.viewPager)
        dotsLayout = findViewById(R.id.dotsLayout)
        skipButton = findViewById(R.id.skipbtn)
        getStart = findViewById(R.id.getStart)

        // Set up ViewPager with the adapter
        val onboardingPagerAdapter = OnboardingPagerAdapter(this)
        viewPager.adapter = onboardingPagerAdapter

        // Set up dots indicator for the first page
        setupDots(0)

        // Register page change callback
        viewPager.registerOnPageChangeCallback(pageChangeCallback)

        // Skip button functionality
        skipButton.setOnClickListener {
            // Go to the main activity or the next part of the app
            val intent = Intent(this, EntryActivity::class.java)
            startActivity(intent)
            finish()  // Finish onboarding activity
        }

        // Get Started button functionality
        getStart.setOnClickListener {
            // Go to the main activity or the next part of the app
            val intent = Intent(this, EntryActivity::class.java)
            startActivity(intent)
            finish()  // Finish onboarding activity
        }
    }

    // Function to set up dots for each page of onboarding
    private fun setupDots(position: Int) {
        val numPages = 3 // Number of onboarding pages
        dots = arrayOfNulls(numPages)
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

            // If the user is on the last page, hide the skip button and dots
            if (position == 2) {
                skipButton.visibility = Button.GONE  // Hide skip button
                dotsLayout.visibility = LinearLayout.GONE  // Hide dots
                getStart.visibility = Button.VISIBLE  // Show Get Started button
            } else {
                skipButton.visibility = Button.VISIBLE  // Show skip button
                dotsLayout.visibility = LinearLayout.VISIBLE  // Show dots
                getStart.visibility = Button.GONE  // Hide Get Started button
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewPager.unregisterOnPageChangeCallback(pageChangeCallback)
    }
}
