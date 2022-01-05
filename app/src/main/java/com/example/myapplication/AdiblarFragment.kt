package com.example.myapplication

import adapters.ViewPagerAdapter2
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayout
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.fragment_adiblar.view.*
import kotlinx.android.synthetic.main.fragment_adiblar.view.view_pager
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.item_tab_category.view.*
import models.Adib
import utils.InternetData
import utils.SearchClick
import utils.SearchViewB

class AdiblarFragment : Fragment(), SearchClick {

    lateinit var root: View

    lateinit var firebaseFirestore: FirebaseFirestore
    lateinit var firebaseStorage: FirebaseStorage
    lateinit var reference: StorageReference
    private val TAG = "AdiblarFragment"
    lateinit var adapter2: ViewPagerAdapter2

    var tabSelected = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_adiblar, container, false)

        root.btn_search_list.setOnClickListener {
            findNavController().navigate(R.id.searchFragment)
        }

        SearchViewB.s = root.bottomBar

        return root
    }

    override fun onResume() {
        super.onResume()
        firebaseFirestore = FirebaseFirestore.getInstance()
        firebaseStorage = FirebaseStorage.getInstance()

        reference = firebaseStorage.getReference("images")

        InternetData.list.clear()
        firebaseFirestore.collection("kitoblar")
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val result = it.result
                    result?.forEach { queryDocumentSnapshot ->
                        val adib = queryDocumentSnapshot.toObject(Adib::class.java)
                        InternetData.list.add(adib)


                    }
                }
                println(InternetData.list)
                adapter2 = ViewPagerAdapter2(childFragmentManager)
                root.view_pager.adapter = adapter2
                root.tab_category.setupWithViewPager(root.view_pager)
                adapter2.notifyDataSetChanged()
                setTabs()

            }

root.tab_category.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
    override fun onTabSelected(tab: TabLayout.Tab?) {
        tabSelected = tab?.position!!
        val inflate = tab?.customView
       // inflate?.item_category_back?.background = resources.getDrawable(R.drawable.ic_tab_background)
        inflate?.txt_item_tab_category?.setTextColor(resources.getColor(R.color.white))

    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {
        val inflate = tab?.customView
        inflate?.item_category_back?.background =null
        inflate?.txt_item_tab_category?.setTextColor(resources.getColor(R.color.black_2))
    }

    override fun onTabReselected(tab: TabLayout.Tab?) {

    }

})

    }

    private fun setTabs() {

        val tabCount = root.tab_category.tabCount

        for (i in 0 until tabCount){
            val tabView = LayoutInflater.from(context).inflate(R.layout.item_tab_category, null, false)
            val tab = root.tab_category.getTabAt(i)
            tab?.customView = tabView

            when (i) {
                0 -> {
                    tabView.txt_item_tab_category.text = "O'zbek adabiyoti"
                    tabView.txt_item_tab_category.setTextColor(resources.getColor(R.color.black_2))
                    tabView.item_category_back.background = null
                }
                1 -> {
                    tabView.txt_item_tab_category.text = "Jahon adabiyoti"
                    tabView.txt_item_tab_category.setTextColor(resources.getColor(R.color.black_2))
                    tabView.item_category_back.background = null
                }
                2 -> {
                    tabView.txt_item_tab_category.text = "Boshqa adabiyot"
                    tabView.txt_item_tab_category.setTextColor(resources.getColor(R.color.black_2))
                    tabView.item_category_back.background = null
                }

            }
            if (i == tabSelected){
//                tabView.item_category_back.background =
//                   resources.getDrawable(R.drawable.ic_tab_background)
               tabView.txt_item_tab_category.setTextColor(resources.getColor(R.color.white))
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        tabSelected = 0
    }


   override fun searchOpen(){
       root.bottomBar.visibility = View.INVISIBLE
       Toast.makeText(context, "Invisible", Toast.LENGTH_SHORT).show()

}

    override fun searchClose() {
        root.bottomBar.visibility = View.GONE
        onResume()
    }

}