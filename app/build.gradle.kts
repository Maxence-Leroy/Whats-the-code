import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("com.google.android.gms.oss-licenses-plugin")
}

fun getProps(path: String): Properties {
    val props = Properties()
    val propsFile = File(path)
    if (propsFile.exists()) {
        props.load(FileInputStream(propsFile))
    }
    return props
}

android {
    namespace = "com.ragicorp.whatsthecode"
    compileSdk = 34

    buildFeatures.buildConfig = true

    defaultConfig {
        applicationId = "com.ragicorp.whatsthecode"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "0.0.0-development"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    signingConfigs {
        create("release") {
            val props = getProps("$rootDir/config/signing.properties")
            keyAlias = props["keyAlias"] as String
            keyPassword = props["keyPassword"] as String
            storeFile = file(props["storeFile"] as String)
            storePassword = props["storePassword"] as String
        }
    }

    buildTypes {
        release {
            signingConfig = signingConfigs["release"]
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    flavorDimensions.add("distribute")
    productFlavors {
        create("dev") {
            dimension = "distribute"
            applicationIdSuffix = ".dev"
            resValue("string", "app_name", "@string/app_name_dev")
        }
        create("staging") {
            dimension = "distribute"
            applicationIdSuffix = ".staging"
            resValue("string", "app_name", "@string/app_name_staging")
        }
        create("prod") {
            dimension = "distribute"
            applicationIdSuffix = ".prod"
            resValue("string", "app_name", "@string/app_name_prod")
        }
    }

    productFlavors.forEach { flavor ->
        // Escape early if flavor is not on the distribute dimension
        if (flavor.dimension != "distribute") return@forEach

        // Add each prop to build config
        val props = getProps("$rootDir/config/${flavor.name}.properties")
        props.forEach { p ->
            val key = p.key as String
            val value = p.value
            flavor.buildConfigField("String", key, "\"$value\"")
            flavor.manifestPlaceholders[key] = value
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity.compose)

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.compose)

    implementation(platform(libs.koin.bom))
    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation(libs.koin.compose)

    implementation(libs.play.services.oss.licenses)

    "stagingImplementation"(libs.appcenter.distribute)

    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.uiautomator)

    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation(project(":core:coreHelpers"))
    implementation(project(":feature:featureMain"))
    implementation(project(":library:libContact"))
}