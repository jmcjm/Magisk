package com.topjohnwu.magisk.wear

import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.wear.compose.material3.MaterialTheme
import androidx.wear.compose.material3.TimeText
import androidx.wear.compose.material3.timeTextCurvedText
import com.topjohnwu.magisk.R
import com.topjohnwu.magisk.core.base.ActivityExtension
import com.topjohnwu.magisk.core.base.SplashController
import com.topjohnwu.magisk.core.base.SplashScreenHost
import com.topjohnwu.magisk.core.wrap

class MainActivity : ComponentActivity(), SplashScreenHost {

    override val extension = ActivityExtension(this)
    override val splashController = SplashController(this)

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base.wrap())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        extension.onCreate(savedInstanceState)
        splashController.preOnCreate()
        super.onCreate(savedInstanceState)
        splashController.onCreate(savedInstanceState)
    }

    override fun onApplyThemeResource(theme: Resources.Theme, resid: Int, first: Boolean) {
        super.onApplyThemeResource(theme, resid, first)
        theme.applyStyle(R.style.Main, true)
    }

    override fun onResume() {
        super.onResume()
        splashController.onResume()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        extension.onSaveInstanceState(outState)
    }

    override fun onCreateUi(savedInstanceState: Bundle?) {
        setContent {
            MaterialTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    TimeText { time -> timeTextCurvedText(time) }
                }
            }
        }
    }
}
