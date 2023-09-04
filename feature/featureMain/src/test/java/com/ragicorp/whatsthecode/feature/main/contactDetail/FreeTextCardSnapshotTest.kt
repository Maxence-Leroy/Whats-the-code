package com.ragicorp.whatsthecode.feature.main.contactDetail

import com.ragicorp.whatsthecode.feature.main.SnapshotVerticalComponentTest
import com.ragicorp.whatsthecode.feature.main.contactDetail.views.FreeTextCardPreview
import org.junit.Test

class FreeTextCardSnapshotTest : SnapshotVerticalComponentTest() {
    @Test
    fun freeTextCardSnapshotTest() {
        paparazziRule.snapshot {
            FreeTextCardPreview()
        }
    }
}