package com.ragicorp.whatsthecode.feature.main.ui

import com.ragicorp.whatsthecode.feature.main.SnapshotVerticalComponentTest
import org.junit.Test

class CodeTextFieldSnapshotTest : SnapshotVerticalComponentTest() {
    @Test
    fun codeTextFieldSnapshotTest() {
        paparazziRule.snapshot {
            CodeTextFieldPreview()
        }
    }
}