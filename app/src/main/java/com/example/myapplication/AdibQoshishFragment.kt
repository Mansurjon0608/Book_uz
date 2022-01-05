package com.example.myapplication

import android.Manifest
import android.app.AlertDialog
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.github.florent37.runtimepermission.kotlin.askPermission
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.fragment_adib_qoshish.*
import kotlinx.android.synthetic.main.fragment_adib_qoshish.view.*
import models.Adib

class AdibQoshishFragment : Fragment() {
    lateinit var root: View

    lateinit var firebaseFirestore: FirebaseFirestore
    lateinit var firebaseStorage: FirebaseStorage
    lateinit var reference: StorageReference
    private val TAG = "MainActivity"

    var imageUrl: String = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_adib_qoshish, container, false)
        firebaseFirestore = FirebaseFirestore.getInstance()
        firebaseStorage = FirebaseStorage.getInstance()

        reference = firebaseStorage.getReference("images")

      //  root.back_btn.setOnClickListener {
//            val callback = object : OnBackPressedCallback(true){
//            override fun handleOnBackPressed() {
//            findNavController().navigate(R.id.sozlamalarFragment)
//            }
//            }
//            requireActivity().onBackPressed()

//            findNavController().popBackStack()
//        }



        root.rasm_upload.setOnClickListener {
            askPermission(Manifest.permission.READ_EXTERNAL_STORAGE) {
                //all permissions already granted or just granted
                getImageContent.launch("image/*")
            }.onDeclined { e ->
                if (e.hasDenied()) {

                    AlertDialog.Builder(context)
                        .setMessage("Please accept our permissions")
                        .setPositiveButton("yes") { dialog, which ->
                            e.askAgain();
                        } //ask again
                        .setNegativeButton("no") { dialog, which ->
                            dialog.dismiss();
                        }
                        .show();
                }

                if (e.hasForeverDenied()) {
                    //the list of forever denied permissions, user has check 'never ask again'

                    // you need to open setting manually if you really need it
                    e.goToSettings();
                }
            }
        }

        root.img_kitob.setOnClickListener {
            askPermission(Manifest.permission.READ_EXTERNAL_STORAGE) {
                //all permissions already granted or just granted
                getImageContent.launch("image/*")
            }.onDeclined { e ->
                if (e.hasDenied()) {

                    AlertDialog.Builder(context)
                        .setMessage("Please accept our permissions")
                        .setPositiveButton("yes") { dialog, which ->
                            e.askAgain();
                        } //ask again
                        .setNegativeButton("no") { dialog, which ->
                            dialog.dismiss();
                        }
                        .show();
                }

                if (e.hasForeverDenied()) {
                    //the list of forever denied permissions, user has check 'never ask again'

                    // you need to open setting manually if you really need it
                    e.goToSettings();
                }
            }
        }

        root.btn_save_new_adib.setOnClickListener {

            val bookName = root.edt_kitob_nomi.text.toString().trim()
            val infoBook = root.edt_malumot.text.toString().trim()
            val kitobMuallifi = root.edt_Author_name.text.toString().trim()
            val category = root.spinner_category.selectedItemPosition
            val janr = root.spinner_janr.selectedItem.toString().trim()
            val til = root.spinner_til.selectedItem.toString().trim()

            Toast.makeText(context, "Yuklash boshlandi", Toast.LENGTH_SHORT).show()

            if (imageUrl != "" && bookName != "" && infoBook != "") {
                val kitob =
                    Adib(imageUrl, bookName, kitobMuallifi, category, infoBook, janr, til)
                firebaseFirestore.collection("kitoblar")
                    .add(kitob)
                    .addOnSuccessListener {
                        Toast.makeText(
                            context,
                            "Muvaffaqiyatli yuklandi \n${bookName} ",
                            Toast.LENGTH_SHORT
                        ).show()
                        findNavController().popBackStack()
                    }
                    .addOnFailureListener {
                        Toast.makeText(context, "${it.message}da xatolik yuz berdi", Toast.LENGTH_SHORT)
                            .show()
                    }
            } else {
                Toast.makeText(context, "Ma'lumot yetarli emas", Toast.LENGTH_SHORT).show()
            }
        }





        return root
    }


    private var getImageContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->

            val m = System.currentTimeMillis()

            if (uri != null) {
                val uploadTask = reference.child(m.toString()).putFile(uri)

                root.progressbar.visibility = View.VISIBLE

                uploadTask.addOnSuccessListener {
                    if (it.task.isSuccessful) {
                        val downloadUrl = it.metadata?.reference?.downloadUrl
                        downloadUrl?.addOnSuccessListener { imageUri ->
                            root.img_kitob.setImageURI(uri)
                            imageUrl = imageUri.toString()
                          root.progressbar.visibility = View.GONE
                        }
                    }
                }.addOnFailureListener {
                    Toast.makeText(context, "Error \n${it.message}", Toast.LENGTH_SHORT).show()
                    root.progressbar.visibility = View.GONE
                }
            }
        }
}
