package com.chrisbrousseau.qlabplugin.gui

import gui.plugins.config.PluginConfigEditPanel
import java.util.logging.Logger
import gui.config.formcomponents.HeaderFormComponent
import gui.plugins.config.formInputs.NumberFormInput
import gui.plugins.config.formInputs.StringFormInput
import com.chrisbrousseau.qlabplugin.QlabPlugin

class EditPanel(val plugin: QlabPlugin): PluginConfigEditPanel() {
    private val logger = Logger.getLogger(EditPanel::class.java.name)

    override fun createFormInputs() {
        formComponents.add(HeaderFormComponent("QLab Server Configuration"))

        formComponents.add(StringFormInput(
            "qlabHostname",
            plugin.properties.hostname,
            saveCallback = { plugin.properties.hostname = it },
            labelText = "QLab Hostname",
            allowEmpty = false,
            toolTipText = "Hostname for the UDP request (i.e. \"localhost\""
        ))

        formComponents.add(NumberFormInput(
            "qlabUDPPort",
            plugin.properties.udpPort,
            saveCallback = { plugin.properties.udpPort = it },
            labelText = "QLab UDP Port",
            min = 0,
            max = 65535
        ))
    }

}