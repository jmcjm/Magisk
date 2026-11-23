package com.topjohnwu.magisk.wear.fork

import android.content.SharedPreferences

/**
 * Defaults of this fork that differ from upstream, applied once on the first start.
 *
 * Update checks are turned off: the upstream channels serve the phone app, which is signed
 * with another key, so an update notification would only point to an APK that cannot replace
 * this one. The user can still turn them back on in the settings.
 */
object ForkDefaults {

    const val KEY_DONE = "wear_fork_defaults_done"

    fun apply(prefs: SharedPreferences, setCheckUpdate: (Boolean) -> Unit) {
        if (prefs.getBoolean(KEY_DONE, false)) return
        setCheckUpdate(false)
        prefs.edit().putBoolean(KEY_DONE, true).apply()
    }
}
