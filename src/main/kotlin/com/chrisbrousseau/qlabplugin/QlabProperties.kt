package com.chrisbrousseau.qlabplugin

import java.util.*

class QlabProperties {
    private val properties = Properties()

    init {
        properties.load(QlabProperties::class.java.getResourceAsStream("/com/chrisbrousseau/qlabplugin/plugin.properties"))
    }

    var hostname: String
        get() = properties.getProperty("hostname")
        set(hostname) { properties.setProperty("hostname", hostname) }

    var udpPort: Int
        get() = properties.getProperty("udpPort").toInt()
        set(udpPort) { properties.setProperty("udpPort", udpPort.toString())}
}