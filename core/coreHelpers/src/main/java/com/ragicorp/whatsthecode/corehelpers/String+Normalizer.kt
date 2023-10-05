package com.ragicorp.whatsthecode.corehelpers

import java.text.Normalizer

fun String.removeDiacritics() =
    Normalizer.normalize(this, Normalizer.Form.NFD)
        .replace("\\p{Mn}+".toRegex(), "")