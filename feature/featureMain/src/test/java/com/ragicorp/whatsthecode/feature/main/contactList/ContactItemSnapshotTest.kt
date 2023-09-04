package com.ragicorp.whatsthecode.feature.main.contactList

import com.ragicorp.whatsthecode.feature.main.SnapshotVerticalComponentTest
import com.ragicorp.whatsthecode.feature.main.contactList.views.ContactWithoutAddressPreview
import com.ragicorp.whatsthecode.feature.main.contactList.views.ContactWithoutNamePreview
import com.ragicorp.whatsthecode.feature.main.contactList.views.FullContactItemPreview
import org.junit.Test

class ContactItemSnapshotTest : SnapshotVerticalComponentTest() {
    @Test
    fun fullContact() {
        paparazziRule.snapshot {
            FullContactItemPreview()
        }
    }

    @Test
    fun contactWithoutAddress() {
        paparazziRule.snapshot {
            ContactWithoutAddressPreview()
        }
    }

    @Test
    fun contactWithoutName() {
        paparazziRule.snapshot {
            ContactWithoutNamePreview()
        }
    }
}