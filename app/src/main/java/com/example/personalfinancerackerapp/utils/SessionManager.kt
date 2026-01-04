package com.example.personalfinancerackerapp.utils

import android.content.Context
import android.content.SharedPreferences

object SessionManager {

    private const val PREFS_NAME = "FinanceAppPrefs"
    private const val KEY_USER_ID = "user_id"
    private const val NO_USER = -1

    private fun getPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun saveUser(context: Context, userId: Int) {
        val editor = getPreferences(context).edit()
        editor.putInt(KEY_USER_ID, userId)
        editor.apply()
    }

    fun getUserId(context: Context): Int {
        return getPreferences(context).getInt(KEY_USER_ID, NO_USER)
    }

    fun clearSession(context: Context) {
        val editor = getPreferences(context).edit()
        editor.remove(KEY_USER_ID)
        editor.apply()
    }
}
