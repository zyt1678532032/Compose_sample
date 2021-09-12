import com.android.build.api.dsl.LibraryDefaultConfig

plugins {
    id("com.android.library")
    id("kotlin-android")
}

android {
    compileSdk = 30

    defaultConfig {
        minSdk = 21
        targetSdk = 30
        // buildConfigField("int", "versionCode", "1")
        // buildConfigField("String", "versionName", "\"1.0\"")
        // defaultConfig.versionName = "1.0"
        // defaultConfig.versionCode = 1
        // versionCode = 1
        // versionName = "1.0"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

/**
 * 1.创建一个library后需要添加一下代码,否则该library中导不进入依赖
 */
repositories {
    google()
    mavenCentral()
}

dependencies {
    implementation("androidx.core:core-ktx:1.6.0")
    implementation("androidx.appcompat:appcompat:1.3.1")
    implementation("com.google.android.material:material:1.4.0")
    testImplementation("junit:junit:4.12")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}

var LibraryDefaultConfig.versionCode: Int?
    get() = null
    set(value) {
        buildConfigField("int", "versionCode", "$value")
    }

var LibraryDefaultConfig.versionName: String?
    get() = null
    set(value) {
        buildConfigField("String", "versionName", "\"$value\"")
    }