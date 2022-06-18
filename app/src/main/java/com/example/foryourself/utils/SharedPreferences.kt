package com.example.foryourself.utils

import android.app.Activity
import android.content.Context
import com.example.foryourself.data.currentUser.CurrentUser
import com.google.gson.Gson

class SharedPreferences {
    companion object {
        const val CURRENT_STUDENT_EDITOR_SAVE_KEY = "CURRENT_STUDENT_EDITOR_SAVE_KEY"
        const val CURRENT_STUDENT_SAVE_KEY = "CURRENT_STUDENT_SAVE_KEY"
    }

    fun saveCurrentUser(
        user: CurrentUser,
        activity: Activity,
    ) {
        activity.getSharedPreferences(CURRENT_STUDENT_EDITOR_SAVE_KEY, Context.MODE_PRIVATE)
            .edit()
            .putString(CURRENT_STUDENT_SAVE_KEY, Gson().toJson(user))
            .apply()
    }

    fun getCurrentUser(
        activity: Context,
    ):
            CurrentUser {
        val pref = activity.getSharedPreferences(CURRENT_STUDENT_EDITOR_SAVE_KEY, Context.MODE_PRIVATE)
        return Gson().fromJson(pref.getString(CURRENT_STUDENT_SAVE_KEY, "{}"), CurrentUser::class.java)
    }


    fun clearUser(activity: Activity) =
        activity.getSharedPreferences(CURRENT_STUDENT_EDITOR_SAVE_KEY, Context.MODE_PRIVATE).edit()
            .clear().commit()

}