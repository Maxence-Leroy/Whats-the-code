package com.ragicorp.whatsthecode.feature.main

import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import app.cash.paparazzi.detectEnvironment
import com.android.ide.common.rendering.api.SessionParams
import org.junit.Rule

abstract class SnapshotComponentTest(
    @get:Rule val paparazziRule: Paparazzi = defaultPaparazziRule,
) {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    companion object {
        val defaultPaparazziRule: Paparazzi
            get() = Paparazzi(
                deviceConfig = DeviceConfig.PIXEL_6.copy(
                    softButtons = false,
                    screenHeight = 10000,
                    screenWidth = 10000
                ),
                renderingMode = SessionParams.RenderingMode.SHRINK,
                maxPercentDifference = 0.1,
                theme = "android:Theme.MaterialComponents.Light.NoActionBar",
                environment = detectEnvironment().run {
                    copy(compileSdkVersion = 33, platformDir = platformDir.replace("34", "33"))
                },
            )
    }
}

abstract class SnapshotVerticalComponentTest(
    @get:Rule val paparazziRule: Paparazzi = defaultPaparazziRule,
) {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    companion object {
        val defaultPaparazziRule: Paparazzi
            get() = Paparazzi(
                deviceConfig = DeviceConfig.PIXEL_6.copy(softButtons = false, screenHeight = 1),
                renderingMode = SessionParams.RenderingMode.V_SCROLL,
                maxPercentDifference = 0.1,
                theme = "android:Theme.MaterialComponents.Light.NoActionBar",
                environment = detectEnvironment().run {
                    copy(compileSdkVersion = 33, platformDir = platformDir.replace("34", "33"))
                },
            )
    }
}

abstract class SnapshotUiTest(
    @get:Rule val paparazziRule: Paparazzi = defaultPaparazziRule,
) {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    companion object {
        val defaultPaparazziRule: Paparazzi
            get() = Paparazzi(
                deviceConfig = DeviceConfig.PIXEL_6.copy(softButtons = false),
                renderingMode = SessionParams.RenderingMode.NORMAL,
                maxPercentDifference = 0.1,
                theme = "android:Theme.MaterialComponents.Light.NoActionBar",
                environment = detectEnvironment().run {
                    copy(compileSdkVersion = 33, platformDir = platformDir.replace("34", "33"))
                },
            )
    }
}