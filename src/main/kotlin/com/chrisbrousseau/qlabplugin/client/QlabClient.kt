package com.chrisbrousseau.qlabplugin.client

import com.illposed.osc.OSCMessage
import com.illposed.osc.transport.udp.OSCPortOut
import objects.notifications.Notifications
import com.chrisbrousseau.qlabplugin.QlabPlugin
import com.chrisbrousseau.qlabplugin.queItems.QlabQueItem
import java.net.InetAddress
import java.util.logging.Logger

class QlabClient(val plugin: QlabPlugin) {
    private val logger = Logger.getLogger(QlabClient::class.java.name)

    fun triggerRemoteCue(queItem: QlabQueItem) {
        val cueTriggerBody = "/cue/${queItem.cueNumber}/go"
        logger.info("Triggering QLab $cueTriggerBody")
        send(cueTriggerBody)
    }

    private fun send(message: String) {
        try {
            val netAddress = InetAddress.getByName(plugin.properties.hostname)
            val socketConnection = OSCPortOut(netAddress, plugin.properties.udpPort)

            logger.fine("Sending UDP message to QLab with payload: $message")
            socketConnection.send(OSCMessage(message))
        } catch (e: com.illposed.osc.OSCSerializeException) {
            logger.warning("Couldn't send message to QLab, failed with OSCSerializeException")
            throw Exception("Failed to send message to QLab due to serializer exception")
        } catch (e: java.io.IOException) {
            logger.warning("Couldn't send message to QLab, failed with Network IO Exception")
            throw Exception("Failed to send message to QLab due to network exception")
        }
    }
}