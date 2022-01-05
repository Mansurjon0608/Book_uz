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
import com.example.myapplication.databinding.FragmentSaqlanganlarBinding
import models.Adib
import utils.MySharedPreferance
import utils.SearchViewB
import java.util.*
import kotlin.collections.ArrayList


class SaqlanganlarFragment : Fragment() {

    lateinit var binding: FragmentSaqlanganlarBinding
    lateinit var adibAdapter: AdiblarAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSaqlanganlarBinding.inflate(layoutInflater)

        MySharedPreferance.init(context)

        val list = MySharedPreferance.objectString

        binding.searchViewAdib.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                var list = ArrayList<Adib>()
                for (adib in list) {
                    for (i in 0 until adib.bookName?.length!!){
                        if (adib.bookName?.subSequence(0, i).toString()
                                .lowercase(Locale.getDefault()) == p0?.toLowerCase()){
                            list.add(adib)
                        }
                    }
                }
                adibAdapter = AdiblarAdapter(context, list, object : AdiblarAdapter.RvClick{
                    override fun onClick(adib: Adib) {
                        findNavController().navigate(R.id.adibInfoFragment, bundleOf("keyKitob" to adib, "keyInt" to 1))

                    }



                }, 1)
                binding.rvSaqlanganAdib.adapter= adibAdapter
                return true
            }

        })

        binding.searchViewAdib.setOnCloseListener {
            SearchViewB.s?.visibility = View.INVISIBLE
            false
        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        val list = MySharedPreferance.objectString
        adibAdapter = AdiblarAdapter(context, list, object : AdiblarAdapter.RvClick{
            override fun onClick(adib: Adib) {
                findNavController().navigate(R.id.adibInfoFragment, bundleOf("keyKitob" to adib, "keyInt" to 1))
            }

        }, 1)
        binding.rvSaqlanganAdib.adapter = adibAdapter
    }
}