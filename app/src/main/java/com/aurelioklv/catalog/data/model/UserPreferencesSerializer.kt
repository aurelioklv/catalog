package com.aurelioklv.catalog.data.model

import android.util.Log
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject

class UserPreferencesSerializer @Inject constructor() : Serializer<UserPreferences> {
    override val defaultValue: UserPreferences
        get() = UserPreferences.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): UserPreferences {
        return try {
            val jsonString = input.readBytes().decodeToString()
            Log.v(TAG, "readFrom try: jsonString = $jsonString")

            Json.decodeFromString<UserPreferences>(jsonString)
        } catch (e: Exception) {
            Log.e(
                TAG,
                "readFrom catch: error: $e"
            )
            throw CorruptionException("Cannot read UserPreferences from input", e)
        }
    }

    override suspend fun writeTo(t: UserPreferences, output: OutputStream) {
        try {
            val jsonString = Json.encodeToString(t)
            Log.v(TAG, "writeTo try: jsonString = $jsonString")
            output.write(jsonString.toByteArray())
        } catch (e: Exception) {
            Log.e(TAG, "writeTo catch: error: $e")
            throw CorruptionException("Cannot write UserPreferences to output", e)
        }
    }

    companion object {
        const val TAG = "UserPreferencesSerializer"
    }

}