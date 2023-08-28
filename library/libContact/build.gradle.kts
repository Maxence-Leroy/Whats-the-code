plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.ragicorp.whatsthecode.library.libContact"
    compileSdk = 33

    defaultConfig {
        minSdk = 21

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation(AndroidX.core.ktx)
    implementation(AndroidX.appCompat)
    implementation(AndroidX.room.runtime)
    implementation(AndroidX.room.ktx)
    implementation(KotlinX.coroutines.android)
    annotationProcessor(AndroidX.room.compiler)
    ksp(AndroidX.room.compiler)
    implementation(Koin.core)
    implementation(Koin.android)
    testImplementation(Testing.junit4)
    androidTestImplementation(AndroidX.test.ext.junit)
    androidTestImplementation(AndroidX.test.espresso.core)
}