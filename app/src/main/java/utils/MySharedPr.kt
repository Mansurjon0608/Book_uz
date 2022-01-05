package utils

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import models.Adib

object MySharedPreferance {

    private const val NAME = "Cache"
    private const val MODE = Context.MODE_PRIVATE
    private lateinit var preferences: SharedPreferences

    fun init(context: Context?) {
        preferences = context?.getSharedPreferences(NAME, MODE)!!

    }

    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = edit()

        operation(editor)
        editor.apply()
    }

    var objectString: ArrayList<Adib>
        get() = gsonStringToArray(preferences.getString("obekt", "[]")!!)
        set(value) = preferences.edit {
            if (value != null) {
                it.putString("obekt", arrayToGsonString(value))
            }
        }

    fun arrayToGsonString(arrayList: ArrayList<Adib>): String {
        return Gson().toJson(arrayList)
    }

    fun gsonStringToArray(gsonString: String): ArrayList<Adib> {

        val typeToken = object : TypeToken<ArrayList<Adib>>() {}.type

        return Gson().fromJson(gsonString, typeToken)
    }
}


// implementation 'com.google.code.gson:gson:2.8.7'

