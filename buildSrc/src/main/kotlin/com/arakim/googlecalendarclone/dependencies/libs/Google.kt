package com.arakim.googlecalendarclone.dependencies.libs

object Google {

    object PlayServices {
        const val Version = "20.7.0"
        const val Auth = "com.google.android.gms:play-services-auth:$Version"
    }

    object Api {
        const val Version = "1.23.0"
        const val Calendar = "com.google.apis:google-api-services-calendar:v3-rev305-$Version"
        const val AndroidClient = "com.google.api-client:google-api-client-android:$Version"
    }
}