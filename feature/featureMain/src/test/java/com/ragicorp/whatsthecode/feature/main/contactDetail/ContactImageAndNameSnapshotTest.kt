package com.ragicorp.whatsthecode.feature.main.contactDetail

import com.ragicorp.whatsthecode.feature.main.SnapshotVerticalComponentTest
import com.ragicorp.whatsthecode.feature.main.contactDetail.views.ContactImageAndNamePreview
import org.junit.Test

class ContactImageAndNameSnapshotTest : SnapshotVerticalComponentTest() {
    @Test
    fun contactImageAndNameSnapshot() {
        paparazziRule.snapshot {
            ContactImageAndNamePreview()
        }
    }
}