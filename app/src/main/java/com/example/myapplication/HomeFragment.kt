package com.example.myapplication

import adapters.HomeAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.databinding.FragmentHomeBinding
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_home.view.*
import utils.MySharedPreferance


class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    lateinit var root: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(layoutInflater)
        root = inflater.inflate(R.layout.fragment_home, container, false)

        MySharedPreferance.init(binding.root.context)

        binding.viewPager.adapter = HomeAdapter(childFragmentManager, lifecycle)

        binding.viewPager.isUserInputEnabled = false

        binding.bottomBar.setOnItemSelectedListener { position: Int ->
            when (position) {
                0 -> binding.viewPager.setCurrentItem(0, true)
                1 -> binding.viewPager.setCurrentItem(1, true)
                2 -> binding.viewPager.setCurrentItem(2, true)
            }
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        binding.bottomBar.itemActiveIndex = 0
        binding.viewPager.currentItem = 0

    }
}