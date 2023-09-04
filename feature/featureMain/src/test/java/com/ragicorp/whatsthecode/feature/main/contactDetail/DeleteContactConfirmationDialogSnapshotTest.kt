package com.ragicorp.whatsthecode.feature.main.contactDetail

import com.ragicorp.whatsthecode.feature.main.SnapshotComponentTest
import com.ragicorp.whatsthecode.feature.main.contactDetail.views.DeleteContactConfirmationDialogPreview
import org.junit.Test

class DeleteContactConfirmationDialogSnapshotTest : SnapshotComponentTest() {
    @Test
    fun deleteContactConfirmationDialogSnapshotTest() {
        paparazziRule.snapshot {
            DeleteContactConfirmationDialogPreview()
        }
    }
}