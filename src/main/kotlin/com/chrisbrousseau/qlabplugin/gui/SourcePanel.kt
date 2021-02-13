package com.chrisbrousseau.qlabplugin.gui

import gui.Refreshable
import objects.notifications.Notifications
import objects.que.Que
import com.chrisbrousseau.qlabplugin.QlabPlugin
import com.chrisbrousseau.qlabplugin.queItems.QlabQueItem
import java.awt.BorderLayout
import java.lang.NullPointerException
import java.util.logging.Logger
import javax.swing.JButton
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JTextField
import javax.swing.border.EmptyBorder

class SourcePanel(private val plugin: QlabPlugin) : JPanel(), Refreshable {
    private val logger = Logger.getLogger(SourcePanel::class.java.name)

    private val textField = JTextField()

    init {
        initGui()
        GUI.register(this)
    }

    private fun addQueItem() {
        try {
            val cueNumber = textField.text!!.trim()
            if (cueNumber != "") {
                val queItem = QlabQueItem(plugin, cueNumber)

                Que.add(queItem)
                GUI.refreshQueItems()
            }

            // Clear cueNumber
            textField.text = ""
        } catch(e: NullPointerException) {
            logger.warning("Cannot add QLab QueItem to the list.")
            Notifications.add("Tried to add an invalid selection to the QueList, skipping", "QlabPlugin")
        }
    }

    private fun initGui() {
        layout = BorderLayout(10, 10)
        border = EmptyBorder(10, 10, 0, 10)

        add(JLabel("Cue Number:"), BorderLayout.PAGE_START)

        add(textField, BorderLayout.CENTER)

        val button = JButton()
        button.text = "Add"
        button.addActionListener {
            addQueItem()
        }
        add(button, BorderLayout.PAGE_END)
    }
}