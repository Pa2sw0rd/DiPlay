package com.shilapi.xcertplay.security

object NativeSecurity {
    init {
        try {
            System.loadLibrary("local_hotspot_radio")
        } catch (_: Throwable) {
        }
    }

    @JvmStatic
    external fun verify(context: Any): Int
}
