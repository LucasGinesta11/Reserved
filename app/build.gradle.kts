plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.reserved"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.reserved"
        minSdk = 28
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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

    // Retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")

    // Gson
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")

    // Coil para imágenes
    implementation("io.coil-kt:coil-compose:2.4.0")

    // Lifecycle (ViewModel, LiveData, etc)
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")

    // Para usar viewModel() en Jetpack Compose
    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1")

    // Navegacion
    implementation ("androidx.navigation:navigation-compose:2.5.3")

    // Compose Material 3
    implementation ("androidx.compose.material3:material3:1.1.0")  // Ajusta versión si tienes otra

    // Icons for Material
    implementation ("androidx.compose.material:material-icons-extended:1.4.3")

    implementation ("com.google.android.gms:play-services-location:21.0.1")

    // Corrutinas
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.7.3")


    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}