// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") apply false
    id("org.jetbrains.kotlin.android") apply false
    id("com.google.devtools.ksp") apply false
    id("com.android.library") apply false
}

buildscript {
    dependencies {
        classpath(Google.android.openSourceLicensesPlugin)
        classpath("app.cash.paparazzi:paparazzi-gradle-plugin:_")
    }
}

task("runUnitTests") {
    dependsOn(
        ":feature:featureMain:testDebugUnitTest",
        ":feature:featureMain:verifyPaparazziDebug",
        ":library:libContact:testDebugUnitTest",
    )
}