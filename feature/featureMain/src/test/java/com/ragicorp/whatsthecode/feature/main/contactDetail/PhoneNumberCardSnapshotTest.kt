package com.ragicorp.whatsthecode.feature.main.contactDetail

import com.ragicorp.whatsthecode.feature.main.SnapshotVerticalComponentTest
import com.ragicorp.whatsthecode.feature.main.contactDetail.views.PhoneNumberCardPreview
import org.junit.Test

class PhoneNumberCardSnapshotTest : SnapshotVerticalComponentTest() {
    @Test
    fun phoneNumberCardSnapshotTest() {
        paparazziRule.snapshot {
            PhoneNumberCardPreview()
        }
    }
}