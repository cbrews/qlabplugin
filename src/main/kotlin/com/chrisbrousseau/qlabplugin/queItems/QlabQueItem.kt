package com.chrisbrousseau.qlabplugin.queItems

import objects.notifications.Notifications
import objects.que.JsonQueue
import objects.que.QueItem
import com.chrisbrousseau.qlabplugin.QlabPlugin
import java.awt.Color
import java.util.logging.Logger

class QlabQueItem(override val plugin: QlabPlugin, val cueNumber: String): QueItem {
    private val logger = Logger.getLogger(QlabQueItem::class.java.name)

    override val name = cueNumber
    override var executeAfterPrevious = false
    override var quickAccessColor: Color? = plugin.quickAccessColor
    override val icon = plugin.icon

    override fun activate(){
        logger.info("Activating queItem $cueNumber")
        try {
            plugin.client.triggerRemoteCue(this)
        } catch(e: Exception) {
            Notifications.add(e.message ?: "", "QlabPlugin")
        }
    }

    override fun toString() = "QLab: $cueNumber"
    override fun renderText() = "QLab: $cueNumber"

    override fun toConfigString(): String {
        return cueNumber
    }

    companion object {
        fun fromJson(plugin: QlabPlugin, jsonQueueItem: JsonQueue.QueueItem): QlabQueItem {
            return QlabQueItem(plugin, jsonQueueItem.data["cueNumber"] ?: "")
        }
    }

    override fun toJson(): JsonQueue.QueueItem {
        val json = super.toJson()
        json.data["cueNumber"] = cueNumber
        return json
    }
}