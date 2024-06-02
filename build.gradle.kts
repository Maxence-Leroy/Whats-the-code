// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.paparazzi) apply false
}

buildscript {
    dependencies {
        classpath(libs.oss.licenses.plugin)
    }
}

task("runUnitTests") {
    dependsOn(
        ":feature:featureMain:testDebugUnitTest",
        ":feature:featureMain:verifyPaparazziDebug",
        ":library:libContact:testDebugUnitTest",
    )
}