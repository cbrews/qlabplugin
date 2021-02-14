package com.chrisbrousseau.qlabplugin.gui

import gui.Refreshable
import handles.QueItemTransferHandler
import objects.que.Que
import com.chrisbrousseau.qlabplugin.QlabPlugin
import com.chrisbrousseau.qlabplugin.queItems.QlabQueItem
import java.awt.BorderLayout
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.util.logging.Logger
import javax.swing.*
import javax.swing.border.EmptyBorder

class SourcePanel(private val plugin: QlabPlugin) : JPanel(), Refreshable {
    private val logger = Logger.getLogger(SourcePanel::class.java.name)

    private val textField = JTextField()

    init {
        initGui()
        GUI.register(this)
    }

    private fun createQueItem(): QlabQueItem? {
        val cueNumber = textField.text!!.trim()
        if (cueNumber != "") {
            return QlabQueItem(plugin, cueNumber)
        }
        return null
    }

    private fun initGui() {
        layout = BorderLayout(10, 10)
        border = EmptyBorder(10, 10, 0, 10)

        val panel = JPanel(BorderLayout(10, 10))
        panel.add(JLabel("Enter QLab Cue Number:"), BorderLayout.PAGE_START)
        panel.add(textField, BorderLayout.CENTER)

        val button = JButton()
        button.text = "Add"
        button.addActionListener {
            val queItem = createQueItem()
            queItem?.let {
                Que.add(it)
                GUI.refreshQueItems()
            }
            textField.text = ""
        }
        button.transferHandler = QueItemTransferHandler()
        button.addMouseMotionListener(object: MouseAdapter() {
            override fun mouseDragged(e: MouseEvent) {
                val queItem = createQueItem()
                queItem?.let {
                    val transferHandler = (e.source as JButton).transferHandler as QueItemTransferHandler
                    transferHandler.queItem = it
                    transferHandler.exportAsDrag(e.source as JComponent, e, TransferHandler.COPY)
                }
                textField.text = ""
            }
        })
        panel.add(button, BorderLayout.PAGE_END)

        add(panel, BorderLayout.PAGE_START)
    }
}