package com.example.todoapp


import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class OnboardingPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        // Return the total number of fragments (in your case, 3)
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        // Return the fragment based on the current position
        return when (position) {
            0 -> OnboardingSlide1Fragment() // Replace with your fragment classes
            1 -> OnboardingSlide2Fragment()
            2 -> OnboardingSlide3Fragment()
            else -> OnboardingSlide1Fragment() // Default case
        }
    }
}
