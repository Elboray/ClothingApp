package com.example.suggeterapp.util

import android.content.Context
import android.content.SharedPreferences

object PrefsUtil {

    private const val SHARE_PREFS_NAME="MySharePrefs"
    private const val MY_TESHIRT="keyTeShirt"
    private const val myDataKey = "MY_DATA"
    private const val MODE = "MODE"
    private var sharedPreferences:SharedPreferences?=null
    fun initPrefsUtil(context: Context){
        sharedPreferences =context.getSharedPreferences(SHARE_PREFS_NAME,Context.MODE_PRIVATE)

    }
    var darkModeId:Int
        get() = sharedPreferences!!.getInt(MODE,Context.MODE_PRIVATE)
        set(value) = sharedPreferences!!.edit().putInt(MODE, value).apply()


    var clothesId:Int
        get() = sharedPreferences!!.getInt(MY_TESHIRT,0)
        set(value) = sharedPreferences!!.edit().putInt(MY_TESHIRT, value).apply()


    var date: String?
        get() = sharedPreferences?.getString(myDataKey, "")
        set(value) {
            sharedPreferences?.edit()?.putString(myDataKey, value)?.apply()
        }


}