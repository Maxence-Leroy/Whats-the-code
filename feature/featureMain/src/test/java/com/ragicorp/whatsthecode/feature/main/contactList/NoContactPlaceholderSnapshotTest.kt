package com.ragicorp.whatsthecode.feature.main.contactList

import com.ragicorp.whatsthecode.feature.main.SnapshotUiTest
import com.ragicorp.whatsthecode.feature.main.contactList.views.NoContactPlaceholderPreview
import org.junit.Test

class NoContactPlaceholderSnapshotTest : SnapshotUiTest() {
    @Test
    fun noContactPlaceholderSnapshotTest() {
        paparazziRule.snapshot {
            NoContactPlaceholderPreview()
        }
    }
}