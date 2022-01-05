package com.example.myapplication

import adapters.AdiblarAdapter
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.widget.SearchView
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.myapplication.databinding.FragmentAdibInfoBinding
import com.squareup.picasso.Picasso
import models.Adib
import utils.InternetData
import utils.MySharedPreferance


class AdibInfoFragment : Fragment() {

    lateinit var binding: FragmentAdibInfoBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAdibInfoBinding.inflate(layoutInflater)

        var adib = arguments?.getSerializable("keyKitob") as Adib

        MySharedPreferance.init(context)
        val mList = MySharedPreferance.objectString
        Picasso.get().load(adib.imageUri).into(binding.imageInfo)
        binding.txtBookName.text = adib.bookName
        binding.txtAuthor.text = adib.authorName
        binding.txtDate.text = adib.data
        binding.txtJanr.text = adib.kitobJanr.toString().trim()
        binding.bookInfo.text = adib.info
        binding.txtTil.text = adib.til.toString()
        when(adib.category){
            1->binding.txtCategory.text = "O'zbek adabiyoti"
            2->binding.txtCategory.text = "Jahon adabiyoti"
            3->binding.txtCategory.text = "Boshqa adabiyot"
        }

        var index = -1
        for (a in mList.indices) {
            if (mList[a].imageUri == adib.imageUri) {
                index = 0
                break
            }
        }

        if (index != -1) {
            binding.adibInfoLike.setImageResource(R.drawable.ic_baseline_bookmark_24)
        }

        binding.adibInfoLike.setOnClickListener {
            var index = -1
            for (a in mList.indices) {
                if (mList[a].imageUri == adib.imageUri) {
                    index = a
                    break
                }
            }
            if (index != -1) {
                mList.removeAt(index)
                MySharedPreferance.objectString = mList
                binding.adibInfoLike.setImageResource(R.drawable.ic_baseline_unbookmark_border_24)
            } else {
                mList.add(adib)
                MySharedPreferance.objectString = mList
                binding.adibInfoLike.setImageResource(R.drawable.ic_baseline_bookmark_24)
            }
        }

        binding.backAdibInfo.setOnClickListener {
            findNavController().navigate(R.id.adiblarFragment)
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                binding.bookInfo.setTextToHighlight(newText)
                binding.bookInfo.highlightColor = resources.getColor(R.color.selected)
                binding.bookInfo.setTextHighlightColor("#00B238")
                binding.bookInfo.setCaseInsensitive(true)
                binding.bookInfo.highlight()
                return true
            }

        })
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onDestroy() {
        super.onDestroy()

        var intP = arguments?.getInt("keyInt", 0)
        if (intP != 1) {
            val rvAdibAdapter = arguments?.getSerializable("keyAdapter") as AdiblarAdapter
            rvAdibAdapter.list = InternetData.list
            rvAdibAdapter.notifyDataSetChanged()
        }
    }
}
