package models

import android.annotation.SuppressLint
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*

class Adib : Serializable {

    var imageUri: String? = null
    var bookName: String? = null
    var authorName: String? = null
    var category: Int? = null //Uzbek, Jahon yoki Boshqa adabiyot
    var info:String? = null
    var kitobJanr:String? = null //Badiiy, Detektiv va hk
    var til:String? = null
    var like = 0
    @SuppressLint("SimpleDateFormat")
    var data = SimpleDateFormat("d.M.y").format(Date())
    //var categorys:String? = null

    constructor(
        imageUri: String?,
        bookName: String?,
        authorName: String?,
        category: Int?,
        info: String?,
        kitobJanr: String?,
        til: String?,
        // categorys:String?
    ) {
        this.imageUri = imageUri
        this.bookName = bookName
        this.authorName = authorName
        this.category = category
        this.info = info
        this.kitobJanr = kitobJanr
        this.til = til
        //this.categorys = categorys
    }

    constructor()

    override fun toString(): String {
        return "Adib(imageUri=$imageUri, bookName=$bookName, authorName=$authorName, category=$category, info=$info, kitobJanr=$kitobJanr, til=$til, like=$like, data='$data')"
    }


}
