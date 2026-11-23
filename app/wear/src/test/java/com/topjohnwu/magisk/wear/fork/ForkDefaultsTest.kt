package com.topjohnwu.magisk.wear.fork

import android.content.SharedPreferences
import org.junit.Assert.assertEquals
import org.junit.Test

class ForkDefaultsTest {

    @Test
    fun firstStartDisablesUpdateChecks() {
        val prefs = FakePrefs()
        val calls = mutableListOf<Boolean>()

        ForkDefaults.apply(prefs) { calls += it }

        assertEquals(listOf(false), calls)
        assertEquals(true, prefs.values[ForkDefaults.KEY_DONE])
    }

    @Test
    fun laterStartsLeaveUpdateChecksAlone() {
        val prefs = FakePrefs()
        ForkDefaults.apply(prefs) {}
        val calls = mutableListOf<Boolean>()

        ForkDefaults.apply(prefs) { calls += it }

        assertEquals(emptyList<Boolean>(), calls)
        assertEquals(true, prefs.values[ForkDefaults.KEY_DONE])
    }

    /** An in-memory SharedPreferences, enough for what ForkDefaults uses. */
    private class FakePrefs : SharedPreferences {
        val values = mutableMapOf<String, Any?>()

        override fun getAll(): Map<String, *> = values.toMap()
        override fun getString(key: String, defValue: String?) =
            values[key] as? String ?: defValue
        @Suppress("UNCHECKED_CAST")
        override fun getStringSet(key: String, defValues: Set<String>?) =
            values[key] as? Set<String> ?: defValues
        override fun getInt(key: String, defValue: Int) = values[key] as? Int ?: defValue
        override fun getLong(key: String, defValue: Long) = values[key] as? Long ?: defValue
        override fun getFloat(key: String, defValue: Float) = values[key] as? Float ?: defValue
        override fun getBoolean(key: String, defValue: Boolean) =
            values[key] as? Boolean ?: defValue
        override fun contains(key: String) = values.containsKey(key)
        override fun edit(): SharedPreferences.Editor = Editor()
        override fun registerOnSharedPreferenceChangeListener(
            listener: SharedPreferences.OnSharedPreferenceChangeListener
        ) = Unit
        override fun unregisterOnSharedPreferenceChangeListener(
            listener: SharedPreferences.OnSharedPreferenceChangeListener
        ) = Unit

        private inner class Editor : SharedPreferences.Editor {
            private val changes = mutableMapOf<String, Any?>()
            private var clear = false

            override fun putString(key: String, value: String?) = put(key, value)
            override fun putStringSet(key: String, values: Set<String>?) = put(key, values)
            override fun putInt(key: String, value: Int) = put(key, value)
            override fun putLong(key: String, value: Long) = put(key, value)
            override fun putFloat(key: String, value: Float) = put(key, value)
            override fun putBoolean(key: String, value: Boolean) = put(key, value)
            override fun remove(key: String) = put(key, null)
            override fun clear(): SharedPreferences.Editor {
                clear = true
                return this
            }
            override fun commit(): Boolean {
                apply()
                return true
            }

            override fun apply() {
                if (clear) values.clear()
                changes.forEach { (key, value) ->
                    if (value == null) values.remove(key) else values[key] = value
                }
            }

            private fun put(key: String, value: Any?): SharedPreferences.Editor {
                changes[key] = value
                return this
            }
        }
    }
}
