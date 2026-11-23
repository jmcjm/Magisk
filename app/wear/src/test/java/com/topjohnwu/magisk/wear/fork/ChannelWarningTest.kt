package com.topjohnwu.magisk.wear.fork

import com.topjohnwu.magisk.core.Config
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

// Only const values of Config are used: they are inlined, so Config itself is never initialized
class ChannelWarningTest {

    @Test
    fun upstreamChannelsNeedWarning() {
        assertTrue(needsChannelWarning(Config.Value.DEFAULT_CHANNEL))
        assertTrue(needsChannelWarning(Config.Value.STABLE_CHANNEL))
        assertTrue(needsChannelWarning(Config.Value.BETA_CHANNEL))
        assertTrue(needsChannelWarning(Config.Value.DEBUG_CHANNEL))
    }

    @Test
    fun customChannelNeedsNoWarning() {
        assertFalse(needsChannelWarning(Config.Value.CUSTOM_CHANNEL))
    }
}
