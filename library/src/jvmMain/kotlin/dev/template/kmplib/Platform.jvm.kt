package dev.template.kmplib

public actual fun platformName(): String =
    "JVM ${System.getProperty("java.version")}"
