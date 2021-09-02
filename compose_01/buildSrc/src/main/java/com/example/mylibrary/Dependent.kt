package com.example.mylibrary

const val kotlin_version = "1.5.30"

object Plugins{
    // gradle插件
    const val gradle =  "com.android.tools.build:gradle:7.0.0"
    // kotlin gradle插件支持
    const val kotlin_gradle = "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version"
}

class Libs {
    // retrofit
    object Retrofit {
        private const val version = "2.9.0"
        const val retrofit = "com.squareup.retrofit2:retrofit:$version"
        const val converter_gson = "com.squareup.retrofit2:converter-gson:$version"
    }

    // kotlinx_coroutines
    object Coroutines {
        private const val version = "1.4.2"
        const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
        const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
    }

    // lifecycle
    object Lifecycle {
        private const val version = "2.2.0"
        const val extensions = "androidx.lifecycle:lifecycle-extensions:$version"
        const val livedata_ktx = "androidx.lifecycle:lifecycle-livedata-ktx:$version"
    }

    // room
    object Room {
        const val version = "2.2.0"
        const val runtime = "androidx.room:room-runtime:$version"
        const val ktx = "androidx.room:room-ktx:$version"
    }

    // kapt
    object Kapt {
        const val room_compiler = "androidx.room:room-compiler:${Room.version}"
    }

    // Compose
    object Compose {
        private const val version = "1.0.0"
        const val ui = "androidx.compose.ui:ui:$version"
        const val ui_tooling = "androidx.compose.ui:ui-tooling:$version"
        const val foundation = "androidx.compose.foundation:foundation:$version"
        const val material = "androidx.compose.material:material:$version"
        const val material_icons_core = "androidx.compose.material:material-icons-core:$version"
        const val material_icons_extended =
            "androidx.compose.material:material-icons-extended:$version"
        const val navigation = "androidx.navigation:navigation-compose:2.4.0-alpha05"
    }

    object Androidx {

        object Activity {
            const val activityCompose = "androidx.activity:activity-compose:1.3.0"
            const val appcompat = "androidx.appcompat:appcompat:1.3.1"
        }

        object Core {
            const val core_ktx = "androidx.core:core-ktx:1.5.0"
        }

    }
    // kotlin 反射
    object Koltin {
        private const val version = "1.5.10"
        const val reflect = "org.jetbrains.kotlin:kotlin-reflect:$version"
    }

    object Test {
        const val junit = "junit:junit:4.12"
    }
}