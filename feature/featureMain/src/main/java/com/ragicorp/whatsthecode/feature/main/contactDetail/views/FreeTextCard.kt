package com.ragicorp.whatsthecode.feature.main.contactDetail.views

import androidx.annotation.VisibleForTesting
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.ragicorp.whatsthecode.feature.main.R
import com.ragicorp.whatsthecode.feature.main.ui.theme.WhatsTheCodeTheme

@Composable
fun FreeTextCard(
    freeText: String
) {
    ContactCard(
        title = stringResource(R.string.contact_freeText)
    ) {
        Text(
            text = freeText,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

@VisibleForTesting(VisibleForTesting.PRIVATE)
@Composable
@Preview
internal fun FreeTextCardPreview() {
    WhatsTheCodeTheme {
        FreeTextCard(freeText = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum vulputate turpis justo, non congue dolor volutpat id. Nullam consectetur suscipit erat, quis consequat purus dapibus ultrices. Praesent nec convallis lorem. Nunc risus dui, varius et egestas eget, vestibulum nec magna. Suspendisse risus nisl, elementum quis justo vel, volutpat finibus magna. In mollis risus ut lorem ullamcorper ornare. Phasellus tincidunt odio risus. Sed ultricies malesuada lobortis. Morbi in porta tellus. ")
    }
}