package com.topjohnwu.magisk.wear.fork

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.wear.compose.material3.AlertDialog
import androidx.wear.compose.material3.AlertDialogDefaults
import androidx.wear.compose.material3.Text
import com.topjohnwu.magisk.R
import com.topjohnwu.magisk.core.Config

/**
 * Whether picking the update channel [channelIndex] needs a warning first. Every channel but
 * the custom one serves upstream builds, which are signed with another key than this fork.
 */
fun needsChannelWarning(channelIndex: Int): Boolean =
    channelIndex != Config.Value.CUSTOM_CHANNEL

/**
 * Warns that [channel] serves upstream builds, which magiskd of this build uninstalls because
 * of their signature. Shown while [channel] is not null; [onConfirm] gets the channel to save.
 */
@Composable
fun ChannelWarningDialog(channel: Int?, onConfirm: (Int) -> Unit, onDismiss: () -> Unit) {
    AlertDialog(
        visible = channel != null,
        onDismissRequest = onDismiss,
        title = { Text(stringResource(R.string.wear_fork_channel_warning_title)) },
        text = { Text(stringResource(R.string.wear_fork_channel_warning_msg)) },
        confirmButton = {
            AlertDialogDefaults.ConfirmButton(onClick = { channel?.let(onConfirm) })
        },
        dismissButton = {
            AlertDialogDefaults.DismissButton(onClick = onDismiss)
        },
    )
}
