package com.example.myapplication

import adapters.AdiblarAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.myapplication.databinding.FragmentBoshqaAdabiyotBinding
import models.Adib
import utils.InternetData


class BoshqaAdabiyotFragment : Fragment() {

    lateinit var binding: FragmentBoshqaAdabiyotBinding
    lateinit var adibAdapter:AdiblarAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBoshqaAdabiyotBinding.inflate(layoutInflater)

        val listMy = ArrayList<Adib>()
        for (a in InternetData.list){
            if (a.category==3){
                listMy.add(a)
            }
        }
        adibAdapter = AdiblarAdapter(context, listMy, object :AdiblarAdapter.RvClick{
            override fun onClick(adib: Adib) {
                findNavController().navigate(R.id.adibInfoFragment, bundleOf("keyKitob" to adib, "keyAdapter" to adibAdapter))
            }

        })
        binding.rvAdiblar.adapter = adibAdapter
        return binding.root

    }


}