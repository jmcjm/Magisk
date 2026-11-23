package com.topjohnwu.magisk.wear.files

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class DocumentPickerTest {

    private val wearStub = "com.google.android.wearable.frameworkpackagestubs"
    private val documentsUi = "com.google.android.documentsui"

    @Test
    fun noHandler() {
        assertNull(documentPickerPackage(emptyList()))
    }

    @Test
    fun onlyStub() {
        assertNull(documentPickerPackage(listOf(wearStub)))
        assertNull(documentPickerPackage(listOf("com.android.frameworkpackagestubs")))
    }

    @Test
    fun realPicker() {
        assertEquals(documentsUi, documentPickerPackage(listOf(documentsUi)))
    }

    @Test
    fun realPickerAfterStub() {
        assertEquals(documentsUi, documentPickerPackage(listOf(wearStub, documentsUi)))
    }

    @Test
    fun keepsResolutionOrder() {
        assertEquals(
            "com.example.files",
            documentPickerPackage(listOf("com.example.files", documentsUi)),
        )
    }
}
