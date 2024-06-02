// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.paparazzi) apply false
    alias(libs.plugins.compose) apply false
}

buildscript {
    dependencies {
        classpath(libs.oss.licenses.plugin)
    }
}

subprojects {
    plugins.withId("app.cash.paparazzi") {
        // Defer until afterEvaluate so that testImplementation is created by Android plugin.
        afterEvaluate {
            dependencies.constraints {
                add("testImplementation", "com.google.guava:guava") {
                    attributes {
                        attribute(
                            TargetJvmEnvironment.TARGET_JVM_ENVIRONMENT_ATTRIBUTE,
                            objects.named(
                                TargetJvmEnvironment::class.java,
                                TargetJvmEnvironment.STANDARD_JVM
                            )
                        )
                    }
                    because(
                        "LayoutLib and sdk-common depend on Guava's -jre published variant." +
                                "See https://github.com/cashapp/paparazzi/issues/906"
                    )
                }
            }
        }
    }
}

task("runUnitTests") {
    dependsOn(
        ":feature:featureMain:testDebugUnitTest",
        ":feature:featureMain:verifyPaparazziDebug",
        ":library:libContact:testDebugUnitTest",
    )
}