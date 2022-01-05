package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_sozlamalar.view.*


class SozlamalarFragment : Fragment() {


    lateinit var root:View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root =  inflater.inflate(R.layout.fragment_sozlamalar, container, false)


        root.setting_share.setOnClickListener {
            val shareBody = "Play market linki"
            val shareSub = "Descrition"

            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"

            shareIntent.putExtra(Intent.EXTRA_SUBJECT, shareSub)
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareBody)

            startActivity(shareIntent)
        }

        root.setting_addBook.setOnClickListener {
            root.findNavController().navigate(R.id.adibQoshishFragment2)
        }

        root.setting_info.setOnClickListener {
            findNavController().navigate(R.id.ilovaHaqidaFragment)
        }

        return root
    }

}