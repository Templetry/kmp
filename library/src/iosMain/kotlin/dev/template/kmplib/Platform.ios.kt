package dev.template.kmplib

import platform.UIKit.UIDevice

public actual fun platformName(): String =
    UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
