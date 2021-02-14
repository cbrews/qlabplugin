package com.chrisbrousseau.qlabplugin

import gui.plugins.config.ConfigWindow
import gui.utils.getMainFrameComponent
import objects.que.JsonQueue
import objects.que.QueItem
import plugins.common.QueItemBasePlugin
import com.chrisbrousseau.qlabplugin.client.QlabClient
import com.chrisbrousseau.qlabplugin.gui.EditPanel
import com.chrisbrousseau.qlabplugin.gui.SourcePanel
import com.chrisbrousseau.qlabplugin.queItems.QlabQueItem
import java.awt.Color
import java.net.URL
import java.util.logging.Logger
import javax.swing.*

@Suppress("unused")
class QlabPlugin : QueItemBasePlugin {
    private val logger = Logger.getLogger(QlabPlugin::class.java.name)

    val properties: QlabProperties = QlabProperties()
    val client: QlabClient = QlabClient(this)

    override val name = "QLab Plugin"
    override val description = "Trigger QLab cues from OBS Scene Queue"
    override val version = "0.0.1"
    override val icon: Icon? = createImageIcon("/com/chrisbrousseau/qlabplugin/icon-14.png")
    override val tabName = "QLab"
    internal val quickAccessColor = Color(229, 238, 255)

    override fun sourcePanel(): JComponent {
        return SourcePanel(this)
    }

    override fun configStringToQueItem(value: String): QueItem {
        return QlabQueItem(this, value)
    }

    override fun createMenu(menu: JMenu): Boolean {
        val settingsItem = JMenuItem("Settings")
        settingsItem.addActionListener {
            val menuInvoker = (menu.popupMenu.invoker.parent as JPopupMenu).invoker
            ConfigWindow(getMainFrameComponent(menuInvoker), "QLab Plugin Settings", EditPanel(this))
        }
        menu.add(settingsItem)

        return true
    }

    override fun jsonToQueItem(jsonQueueItem: JsonQueue.QueueItem): QueItem {
        return when (jsonQueueItem.className) {
            QlabQueItem::class.java.simpleName -> QlabQueItem.fromJson(this, jsonQueueItem)
            else -> throw IllegalArgumentException("Invalid QLab queue item: ${jsonQueueItem.className}")
        }
    }

    private fun createImageIcon(path: String): ImageIcon? {
        val imgURL: URL? = javaClass.getResource(path)
        if (imgURL != null) {
            return ImageIcon(imgURL)
        }

        logger.severe("Couldn't find imageIcon: $path")
        return null
    }
}