plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("com.google.protobuf") version "0.9.4"

    id ("kotlin-kapt")
    id ("dagger.hilt.android.plugin")
}

android {
    namespace = "com.sunaa.protoaddme"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.sunaa.protoaddme"
        minSdk = 24
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
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation("androidx.datastore:datastore-core:1.0.0")
    implementation("androidx.datastore:datastore:1.0.0")

    // view model
    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1")

    // Hilt Jetpack with Kapt
    implementation("com.google.dagger:hilt-android:2.52") // Core Hilt Library
    kapt("com.google.dagger:hilt-android-compiler:2.52") // Compiler generate Necessary code for DI
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0") // Jetpack Compose support for Hilt

    implementation  ("com.google.protobuf:protobuf-javalite:3.18.0")
}

protobuf {
    protoc {
        // Set the path to the protoc binary (use a version that is compatible with DataStore and Kotlin)
        artifact = "com.google.protobuf:protoc:3.21.12"
    }
    generateProtoTasks {
        all().forEach { task ->
            task.builtins {
                create("java") {
                    option("lite") // Use lite for smaller code generation
                }
            }
        }
    }
}