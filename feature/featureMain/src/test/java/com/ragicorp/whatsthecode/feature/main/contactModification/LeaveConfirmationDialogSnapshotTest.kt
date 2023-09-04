package com.ragicorp.whatsthecode.feature.main.contactModification

import com.ragicorp.whatsthecode.feature.main.SnapshotComponentTest
import com.ragicorp.whatsthecode.feature.main.contactModification.views.LeaveConfirmationDialogPreview
import org.junit.Test

class LeaveConfirmationDialogSnapshotTest : SnapshotComponentTest() {
    @Test
    fun leaveConfirmationDialogSnapshot() {
        paparazziRule.snapshot {
            LeaveConfirmationDialogPreview()
        }
    }
}