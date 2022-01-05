package com.example.myapplication

import adapters.AdiblarAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.myapplication.databinding.FragmentUzbekAdabiyotiBinding
import models.Adib
import utils.InternetData

class UzbekAdabiyotiFragment : Fragment() {
    lateinit var binding: FragmentUzbekAdabiyotiBinding
    lateinit var adibAdapter:AdiblarAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding = FragmentUzbekAdabiyotiBinding.inflate(layoutInflater)

        var listM = ArrayList<Adib>()
        for (a in InternetData.list) {
            if (a.category == 1){
                listM.add(a)
            }
        }
        adibAdapter = AdiblarAdapter(context, listM, object : AdiblarAdapter.RvClick{
            override fun onClick(adib: Adib) {
                findNavController().navigate(R.id.adibInfoFragment, bundleOf("keyKitob" to adib, "keyAdapter" to adibAdapter))
            }
        }
        )

        binding.rvAdiblar.adapter = adibAdapter
        return binding.root
    }

}


