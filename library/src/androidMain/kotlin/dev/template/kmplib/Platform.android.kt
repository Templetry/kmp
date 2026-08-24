package dev.template.kmplib

public actual fun platformName(): String = "Android ${android.os.Build.VERSION.SDK_INT}"
