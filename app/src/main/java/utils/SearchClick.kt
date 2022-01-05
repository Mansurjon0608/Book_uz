package utils

import android.annotation.SuppressLint
import android.view.View

interface SearchClick {
    fun searchOpen()
    fun searchClose()
}

@SuppressLint("StaticFieldLeak")
object SearchViewB{
    var s: View? = null
}