plugins {
    id("com.android.application")



    //plugins de firebase


    // Add the Google services Gradle plugin
    id("com.google.gms.google-services")


}

android {
    namespace = "com.example.agendify"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.agendify"
        minSdk = 26
        targetSdk = 33
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")


    //dependecias externas añadidas

    //para las animaciones
    implementation ("com.airbnb.android:lottie:3.0.1")

    //dependencias de materiales
    implementation("com.google.android.material:material:1.4.0")


    //auntentificacion para fire base
    implementation("com.google.firebase:firebase-auth:20.0.1")


    //dependeciaa para el scanner
    implementation("com.journeyapps:zxing-android-embedded:4.1.0")



    //sirve para ver una estimacion de cuanta gente esta utilizando mi app
    implementation("com.google.firebase:firebase-analytics:17.2.2")

    //para el tiempo
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")


}
