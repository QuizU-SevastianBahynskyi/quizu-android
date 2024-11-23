package com.quizu.android_client

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SecureStorage @Inject constructor(@ApplicationContext context: Context) {
    companion object {
        private const val PREFERENCES_FILE="quizu_secure_storage"
        private const val JWT_KEY="jwt_token"
    }

    private val sharedPreferences = EncryptedSharedPreferences.create(
        context,
        PREFERENCES_FILE,
        MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build(),
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    fun saveJwtToken(token: String) {
        sharedPreferences.edit()
            .putString(JWT_KEY, token)
            .apply()
    }

    // Retrieve JWT Token
    fun getJwtToken(): String? {
        return sharedPreferences.getString(JWT_KEY, null)
    }

    // Clear JWT Token
    fun clearJwtToken() {
        sharedPreferences.edit()
            .remove(JWT_KEY)
            .apply()
    }
}