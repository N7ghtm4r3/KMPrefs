import com.vanniktech.maven.publish.JavadocJar
import com.vanniktech.maven.publish.KotlinMultiplatform
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidKotlinMultiplatformLibrary)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.dokka)
    alias(libs.plugins.vanniktech.mavenPublish)
}

group = "com.tecknobit.kmprefs"
version = "1.1.1"

kotlin {
    androidLibrary {
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()
        namespace = "com.tecknobit.kmprefs"
        experimentalProperties["android.experimental.kmp.enableAndroidResources"] = true

        compilations {
            compilerOptions {
                jvmTarget.set(JvmTarget.JVM_18)
            }
        }

    }

    jvm {
        compilations.all {
            this@jvm.compilerOptions {
                jvmTarget.set(JvmTarget.JVM_18)
            }
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64(),
        macosX64(),
        macosArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "kmprefs"
            isStatic = true
        }
    }

    js {
        browser()
        binaries.library()
    }

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        binaries.library()
        browser {
            webpackTask {

            }
        }
    }

    sourceSets {
        applyDefaultHierarchyTemplate()

        val androidMain by getting {
            dependencies {
                implementation(libs.androidx.startup.runtime)
                implementation(libs.equinox.core)
            }
        }

        val commonMain by getting {
            dependencies {
                implementation(libs.kotlinx.serialization.json)
                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.kassaforte)
            }
        }

        val jvmMain by getting {
            dependencies {
                implementation(libs.kotlinx.coroutines.swing)
            }
        }

        val appleMain by getting {
            dependencies {
            }
        }

        val webMain by getting {
            dependencies {
                implementation(libs.kotlinx.browser)
            }
        }

        val jsMain by getting {
            dependencies {
            }
        }

        val wasmJsMain by getting {
            dependencies {
            }
        }

    }

    jvmToolchain(18)
}

mavenPublishing {
    configure(
        platform = KotlinMultiplatform(
            javadocJar = JavadocJar.Dokka("dokkaGenerate"),
            androidVariantsToPublish = listOf("release"),
        )
    )
    coordinates(
        groupId = "io.github.n7ghtm4r3",
        artifactId = "kmprefs",
        version = "1.1.1"
    )
    pom {
        name.set("KMPrefs")
        description.set("The Kotlin Multiplatform Pref(erence)s system allows you to store, retrieve, and remove data locally on each platform. It leverages the native mechanisms provided by each platform.")
        inceptionYear.set("2026")
        url.set("https://github.com/N7ghtm4r3/KMPrefs")

        licenses {
            license {
                name.set("Apache License, Version 2.0")
                url.set("https://opensource.org/license/apache-2-0")
            }
        }
        developers {
            developer {
                id.set("N7ghtm4r3")
                name.set("Manuel Maurizio")
                email.set("maurizio.manuel2003@gmail.com")
                url.set("https://github.com/N7ghtm4r3")
            }
        }
        scm {
            url.set("https://github.com/N7ghtm4r3/KMPrefs")
        }
    }
    publishToMavenCentral()
    signAllPublications()
}