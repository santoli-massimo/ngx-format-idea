package com.msan.ngxformatidea.utils

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.application.ex.ApplicationEx
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages
import com.jetbrains.rd.generator.nova.PredefinedType
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import java.io.File
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.transform.OutputKeys
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult


fun findNodeByAttribute(nodeList: NodeList, parentSuffix: String): Node? {
    for (i in 0..<nodeList.length) {
        val node = nodeList.item(i)
        val attributes = node.attributes
//        val childSuffixCurrent = attributes.getNamedItem("child-file-suffix").nodeValue
        val parentSuffixCurrent = attributes.getNamedItem("parent-file-suffix").nodeValue
        if(parentSuffixCurrent == parentSuffix) { return node }
    }
    return null // No matching node found
}

fun promptRestart(project: Project) {
    ApplicationManager.getApplication().invokeLater {
        val result = Messages.showYesNoDialog(
            project,
            "A restart is required for the changes to take effect.\nDo you want to restart now?",
            "Restart Required",
            Messages.getQuestionIcon()
        )

        if (result == Messages.YES) {
            val app = ApplicationManager.getApplication()
            if (app is ApplicationEx) {
                app.restart(true) // Restart IntelliJ IDEA
            }
        }
    }
}

fun readFileNestingRules(project: Project): PredefinedType.void {
    val parentSuffix = ".component.ngx"
    val childSuffixList = listOf(
        ".component.html",
        ".component.scss",
        ".component.css",
        ".component.ts",
        ".component.spec.ts",
    )

    // Adjust the path as needed
    val configDir = File(System.getProperty("user.home"), "Library/Application Support/JetBrains/IntelliJIdea2024.3/options")
    val uiXmlFile = File(configDir, "ui.lnf.xml")
    if (!uiXmlFile.exists()) {
        throw IllegalStateException("ui.lnf not found at: ${uiXmlFile.absolutePath}")
    }

    val document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(uiXmlFile)
    val nestingRules = document.getElementsByTagName("NestingRule")
    val nestingRulesContainer = document.getElementsByTagName("nesting-rules").item(0) as org.w3c.dom.Element
    val node = findNodeByAttribute(nestingRules, parentSuffix)

    if(node != null) {
        Logger.warn("Node found: $node")
    }
    else{
        Logger.warn("Node not found")
        for(childSuffix in childSuffixList) {
            val newRule = document.createElement("NestingRule")
            newRule.setAttribute("child-file-suffix", childSuffix)
            newRule.setAttribute("parent-file-suffix", parentSuffix)
            nestingRulesContainer.appendChild(newRule)
            nestingRulesContainer.appendChild(newRule)
        }


        val transformer = TransformerFactory.newInstance().newTransformer().apply {
            setOutputProperty(OutputKeys.INDENT, "yes")
        }
        transformer.transform(DOMSource(document), StreamResult(uiXmlFile))

        promptRestart(project)
    }
    return PredefinedType.void
}