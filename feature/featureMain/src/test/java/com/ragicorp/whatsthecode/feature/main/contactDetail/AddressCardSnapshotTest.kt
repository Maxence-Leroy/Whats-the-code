package com.ragicorp.whatsthecode.feature.main.contactDetail

import com.ragicorp.whatsthecode.feature.main.SnapshotVerticalComponentTest
import com.ragicorp.whatsthecode.feature.main.contactDetail.views.AddressAlonePreview
import com.ragicorp.whatsthecode.feature.main.contactDetail.views.CodesAlonePreview
import com.ragicorp.whatsthecode.feature.main.contactDetail.views.DescriptionAlonePreview
import com.ragicorp.whatsthecode.feature.main.contactDetail.views.FullAddressCardPreview
import org.junit.Test

class AddressCardSnapshotTest : SnapshotVerticalComponentTest() {
    @Test
    fun addressAlone() {
        paparazziRule.snapshot {
            AddressAlonePreview()
        }
    }

    @Test
    fun codesAlone() {
        paparazziRule.snapshot {
            CodesAlonePreview()
        }
    }

    @Test
    fun descriptionAlone() {
        paparazziRule.snapshot {
            DescriptionAlonePreview()
        }
    }

    @Test
    fun fullAddressCard() {
        paparazziRule.snapshot {
            FullAddressCardPreview()
        }
    }
}