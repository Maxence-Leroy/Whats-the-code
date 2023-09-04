package com.ragicorp.whatsthecode.feature.main

import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import com.android.ide.common.rendering.api.SessionParams
import org.junit.Rule

abstract class SnapshotComponentTest(
    @get:Rule val paparazziRule: Paparazzi = defaultPaparazziRule,
) {

    companion object {
        val defaultPaparazziRule: Paparazzi
            get() = Paparazzi(
                deviceConfig = DeviceConfig.NEXUS_5.copy(
                    softButtons = false,
                    screenHeight = 10000,
                    screenWidth = 10000
                ),
                renderingMode = SessionParams.RenderingMode.SHRINK,
                maxPercentDifference = 0.1,
                theme = "android:Theme.MaterialComponents.Light.NoActionBar",
            )
    }
}

abstract class SnapshotVerticalComponentTest(
    @get:Rule val paparazziRule: Paparazzi = defaultPaparazziRule,
) {

    companion object {
        val defaultPaparazziRule: Paparazzi
            get() = Paparazzi(
                deviceConfig = DeviceConfig.NEXUS_5.copy(softButtons = false, screenHeight = 1),
                renderingMode = SessionParams.RenderingMode.V_SCROLL,
                maxPercentDifference = 0.1,
                theme = "android:Theme.MaterialComponents.Light.NoActionBar",
            )
    }
}

abstract class SnapshotUiTest(
    @get:Rule val paparazziRule: Paparazzi = defaultPaparazziRule,
) {

    companion object {
        val defaultPaparazziRule: Paparazzi
            get() = Paparazzi(
                deviceConfig = DeviceConfig.NEXUS_5.copy(softButtons = false),
                renderingMode = SessionParams.RenderingMode.NORMAL,
                maxPercentDifference = 0.1,
                theme = "android:Theme.MaterialComponents.Light.NoActionBar",
            )
    }
}