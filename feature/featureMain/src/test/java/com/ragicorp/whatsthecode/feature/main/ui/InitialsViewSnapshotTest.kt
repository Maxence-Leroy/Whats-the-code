package com.ragicorp.whatsthecode.feature.main.ui

import com.ragicorp.whatsthecode.feature.main.SnapshotComponentTest
import org.junit.Test

class InitialsViewSnapshotTest : SnapshotComponentTest() {
    @Test
    fun bigInitialsSnapshotTest() {
        paparazziRule.snapshot {
            BigInitialsPreview()
        }
    }

    @Test
    fun smallInitialsSnapshotTest() {
        paparazziRule.snapshot {
            SmallInitialsPreview()
        }
    }
}