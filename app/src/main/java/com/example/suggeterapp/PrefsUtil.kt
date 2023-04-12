package com.example.suggeterapp

import android.content.Context
import android.content.SharedPreferences

object PrefsUtil {

    private const val SHARE_PREFS_NAME="MySharePrefs"
    private const val MY_DATA="keyData"
    private const val MY_TESHIRT="keyTeShirt"


    private var sharedPreferences:SharedPreferences?=null
    fun initPrefsUtil(context: Context){
        sharedPreferences=context.getSharedPreferences(SHARE_PREFS_NAME,Context.MODE_PRIVATE)

    }
    var TeShirt:Int
        get() = sharedPreferences!!.getInt(MY_TESHIRT,0)
        set(value) = sharedPreferences!!.edit().putInt(MY_TESHIRT, value).apply()

    var date: String?
        get() = sharedPreferences?.getString(MY_DATA, "")
        set(value) = sharedPreferences?.edit()?.putString(MY_DATA, value)?.apply()!!


}