package com.ragicorp.whatsthecode.feature.main.contactModification

import com.ragicorp.whatsthecode.feature.main.SnapshotUiTest
import com.ragicorp.whatsthecode.feature.main.contactModification.views.EmptyContactModificationScaffoldPreview
import com.ragicorp.whatsthecode.feature.main.contactModification.views.FilledContactModificationScaffoldPreview
import org.junit.Test

class ContactModificationScaffoldSnapshotUiTest : SnapshotUiTest() {
    @Test
    fun filledForm() {
        paparazziRule.snapshot {
            FilledContactModificationScaffoldPreview()
        }
    }

    @Test
    fun emptyForm() {
        paparazziRule.snapshot {
            EmptyContactModificationScaffoldPreview()
        }
    }
}