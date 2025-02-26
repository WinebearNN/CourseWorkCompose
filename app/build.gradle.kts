plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("com.google.dagger.hilt.android") // Плагин Hilt
    kotlin("kapt") // добавляем плагин для ObjectBox
}

android {
    namespace = "com.hse.courseworkcompose"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.hse.courseworkcompose"
        minSdk = 26
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.10"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {


/////ObjectBox
    implementation(libs.objectbox.android) // проверь последнюю версию
    implementation(libs.objectbox.kotlin) // последняя версия библиотеки
    kapt(libs.objectbox.processor) // для генерации кода, используем kapt

/////Hilt & viewModel compose

    implementation("androidx.compose.material:material:1.7.8") // или последняя версия


    implementation("io.coil-kt:coil-compose:2.2.2")

    implementation(libs.hilt.android.v2511) // Hilt
    kapt(libs.hilt.android.compiler.v2511) // Компилятор Hilt
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")// Для поддержки ViewModel в Compose


    // https://mvnrepository.com/artifact/com.getkeepsafe.relinker/relinker
    implementation(libs.relinker)


/////Retrofit 2.0
    // https://mvnrepository.com/artifact/com.squareup.retrofit2/retrofit
    implementation(libs.retrofit)
    // https://mvnrepository.com/artifact/com.squareup.retrofit2/converter-gson
    implementation(libs.converter.gson)







    implementation(libs.androidx.navigation.compose.v275)

    implementation(libs.androidx.navigation.runtime.android)
    // https://mvnrepository.com/artifact/androidx.navigation/navigation-fragment-ktx
    runtimeOnly(libs.androidx.navigation.fragment.ktx)

    // https://mvnrepository.com/artifact/androidx.navigation/navigation-compose
    runtimeOnly(libs.androidx.navigation.compose)

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
}
java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}
