plugins {
    alias(libs.plugins.android.application)
    kotlin("plugin.parcelize")
    alias(libs.plugins.compose.compiler)
}

setupMainApk()

android {
    buildFeatures {
        compose = true
    }

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
    }

    packaging {
        jniLibs {
            excludes += "lib/*/libandroidx.graphics.path.so"
        }
    }

    defaultConfig {
        // Wear Compose does not support older API levels
        minSdk = 25
        proguardFile("proguard-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
        }
    }
}

dependencies {
    implementation(project(":core"))
    implementation(project(":shared"))
    coreLibraryDesugaring(libs.jdk.libs)

    // Compose
    implementation(libs.compose.ui)
    implementation(libs.compose.foundation)
    implementation(libs.compose.material.icons.extended)
    implementation(libs.activity.compose)
    implementation(libs.lifecycle.runtime.compose)
    implementation(libs.lifecycle.viewmodel.compose)
    implementation(libs.core.splashscreen)

    // Wear OS
    implementation(libs.wear.compose.foundation)
    implementation(libs.wear.compose.material3)
    implementation(libs.wear.compose.navigation)

    testImplementation(libs.junit)
}
