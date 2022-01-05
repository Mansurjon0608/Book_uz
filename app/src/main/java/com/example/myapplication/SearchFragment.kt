package com.example.myapplication

import adapters.AdiblarAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.myapplication.databinding.FragmentSearchBinding
import models.Adib
import utils.InternetData
import java.util.*
import kotlin.collections.ArrayList


class SearchFragment : Fragment() {

    lateinit var binding:FragmentSearchBinding
    lateinit var rvAdapter: AdiblarAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
      binding = FragmentSearchBinding.inflate(layoutInflater)

        binding.searchAdib.setQuery("",true);
        binding.searchAdib.setFocusable(true);
        binding.searchAdib.setIconified(false);
        binding.searchAdib.requestFocusFromTouch();
        rvAdapter = AdiblarAdapter(context, InternetData.list, object : AdiblarAdapter.RvClick{

            override fun onClick(adib: Adib) {
                findNavController().navigate(R.id.adibInfoFragment, bundleOf("keyAdib" to adib, "keyInt" to 1))
            }
        })
        binding.rvSearch.adapter = rvAdapter

        binding.searchAdib.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                var list = ArrayList<Adib>()
                for (adib in InternetData.list) {
                    for (i in 0 until adib.bookName?.length!!){
                        if (adib.bookName?.subSequence(0, i).toString()
                                .lowercase(Locale.getDefault()) == p0?.toLowerCase()){
                            list.add(adib)
                        }
                    }
                }
                rvAdapter = AdiblarAdapter(context, list, object : AdiblarAdapter.RvClick{
                    override fun onClick(adib: Adib) {
                        findNavController().navigate(R.id.adibInfoFragment, bundleOf("keyAdib" to adib, "keyInt" to 1))
                    }

                })
                binding.rvSearch.adapter= rvAdapter
                return true
            }
        })

        return binding.root
    }


}