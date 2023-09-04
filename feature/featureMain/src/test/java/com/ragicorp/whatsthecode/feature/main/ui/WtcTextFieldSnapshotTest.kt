package com.ragicorp.whatsthecode.feature.main.ui

import com.ragicorp.whatsthecode.feature.main.SnapshotVerticalComponentTest
import org.junit.Test

class WtcTextFieldSnapshotTest : SnapshotVerticalComponentTest() {
    @Test
    fun wtcTextFieldSnapshotTest() {
        paparazziRule.snapshot {
            WtcTextFieldPreview()
        }
    }
}