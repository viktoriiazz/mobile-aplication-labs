plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.jetbrains.kotlin.serialization)
    // [KSP] To enable KSP plugin for Room DB in whole project find `build.gradle.kts` file in the root and see rows[5-6]
    id("com.google.devtools.ksp") // enabling the KSP plugin for app here
}

android {
    namespace = "com.lab4"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.lab4"
        minSdk = 29
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    // Koin core
    implementation("io.insert-koin:koin-core:3.5.6")

    // Koin Android
    implementation("io.insert-koin:koin-android:3.5.6")

    // Koin ViewModel
    implementation("io.insert-koin:koin-androidx-viewmodel:3.5.6")

    // Koin Compose
    implementation("io.insert-koin:koin-androidx-compose:3.5.6")

    // Room
    implementation("androidx.room:room-runtime:2.6.1")
    ksp("androidx.room:room-compiler:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")

    // Navigation
    implementation("androidx.navigation:navigation-compose:2.7.6")

    // Material
    implementation("androidx.compose.material3:material3:1.2.0")
}
