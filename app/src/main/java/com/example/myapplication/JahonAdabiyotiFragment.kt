package com.example.myapplication

import adapters.AdiblarAdapter
import adapters.ViewPagerAdapter2
import android.os.Binder
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.myapplication.databinding.FragmentJahonAdabiyotiBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.fragment_adiblar.view.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import models.Adib
import utils.InternetData
import utils.SearchViewB


class JahonAdabiyotiFragment : Fragment() {

    lateinit var binding: FragmentJahonAdabiyotiBinding
    lateinit var adibAdapter: AdiblarAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentJahonAdabiyotiBinding.inflate(layoutInflater)

        val listMy = ArrayList<Adib>()
        for (a in InternetData.list) {
            if (a.category == 2) {
                listMy.add(a)
            }
        }
        adibAdapter = AdiblarAdapter(context, listMy, object : AdiblarAdapter.RvClick {
            override fun onClick(adib: Adib) {
                findNavController().navigate(
                    R.id.adibInfoFragment,
                    bundleOf("keyKitob" to adib, "keyAdapter" to adibAdapter)
                )
            }

        })
        binding.rvAdiblar.adapter = adibAdapter
        return binding.root

    }


}