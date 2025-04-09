package com.example.lji_task.sessionManagers

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.example.lji_task.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserSessionManager
@Inject constructor(@ApplicationContext context: Context) {

    private val prefs: SharedPreferences = EncryptedSharedPreferences.create(
        context,
        context.getString(R.string.app_user_pref),
        MasterKey.Builder(context).setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build(),
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    private val prefEditor: SharedPreferences.Editor = prefs.edit()



    var userToken: String?
        get() = prefs.getString("user_token", null)
        set(userToken) {
            prefEditor.putString("user_token", userToken)
            prefEditor.apply()
        }


    fun clearPrefs() {
        prefs.all.forEach {
            prefEditor.remove(it.key)
        }
        prefEditor.clear()
        prefEditor.apply()
    }
}