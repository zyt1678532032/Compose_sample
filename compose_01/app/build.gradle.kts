import com.example.mylibrary.Libs

plugins {
    id("com.android.application") //
    id("kotlin-android") apply true
    id("kotlin-parcelize")
    id("kotlin-kapt") //Kotlin annotation processing tool
    // id 'kotlin-android-extensions' //节省findViewById(), 不需要添加任何额外代码，也不影响任何运行时体验
}

android {
    compileSdk = 30

    lint {
        isAbortOnError = false        // true by default
        isCheckAllWarnings = false
        isCheckReleaseBuilds = false
        isIgnoreWarnings = true       // false by default
        isQuiet = true                // false by default
    }

    defaultConfig {
        applicationId = "com.example.test"
        minSdk = 21
        targetSdk = 30
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildFeatures {
        // Enables Jetpack Compose for this module
        compose = true
    }

    buildTypes {
        release {
            // 启用代码压缩、优化及混淆
            isMinifyEnabled = true
            // 启用资源压缩，需配合 minifyEnabled=true 使用
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
        kotlinOptions {
            languageVersion = "1.5"
            apiVersion = "1.5"
            jvmTarget = "11"
        }
    }
    // compose编译操作
    composeOptions {
        // compose_compiler
        kotlinCompilerExtensionVersion = "1.0.2"
    }
}

/**
 * 注:需要添加一下代码,否则依赖导不进去
 */
repositories {
    google()
    mavenCentral()
}

dependencies {
    // 引入当前项目的library
    implementation(project(":library"))
    //retrofit
    implementation(Libs.Retrofit.retrofit)
    implementation(Libs.Retrofit.converter_gson)
    //kotlinx-coroutines
    implementation(Libs.Coroutines.core)
    implementation(Libs.Coroutines.android)
    // lifecycle
    implementation(Libs.Lifecycle.extensions)
    implementation(Libs.Lifecycle.livedata_ktx)
    // room
    implementation(Libs.Room.runtime)
    // optional - Kotlin Extensions and Coroutines support for Room
    implementation(Libs.Room.ktx)
    // Kotlin annotation processing tool 注解处理器插件(和annotationProcessor的区别是什么呢)
    kapt(Libs.Kapt.room_compiler)
    // Compose 导航
    implementation(Libs.Compose.navigation)
    // Compose
    implementation(Libs.Compose.ui)
    // Tooling support (Previews, etc.)
    implementation(Libs.Compose.ui_tooling)
    // Foundation (Border, Background, Box, Image, Scroll, shapes, animations, etc.)
    implementation(Libs.Compose.foundation)
    // Material Design
    implementation(Libs.Compose.material)
    // Material design icons
    implementation(Libs.Compose.material_icons_core)
    implementation(Libs.Compose.material_icons_extended)
    // Integration with activities
    implementation(Libs.Androidx.Activity.activityCompose)
    implementation(Libs.Androidx.Activity.appcompat)
    implementation(Libs.Androidx.Core.core_ktx)
    implementation(Libs.Koltin.reflect)
    // 包含主题和组件等
    implementation("com.google.android.material:material:1.4.0")
    testImplementation(Libs.Test.junit)

}

