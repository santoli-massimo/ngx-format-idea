package com.msan.ngxformatidea

import com.intellij.ide.util.PropertiesComponent
import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.ProjectActivity
import com.msan.ngxformatidea.utils.Logger


class FileNestingStartupActivity : ProjectActivity {
    override suspend fun execute(project: Project) {
        Logger.warn("Setting up custom file nesting rules for Angular components")
        Logger.info("Setting up custom file nesting rules for Angular components")

//        val properties = PropertiesComponent.getInstance(project)
        val properties = PropertiesComponent.getInstance()
//        val properties = PropertiesComponent.getInstance()
//        val nestingKey = "project.view.file.nesting.rules"
//        val nestingKey = "project.view.file.nesting.rules"
//        val nestingKey = "application.project.view.file.nesting.rules"
        val nestingKey = "application.ProjectViewFileNesting"
//        val nestingKey = "project.structure.last.edited"
        val list = properties.getList(nestingKey)

        val existingRules = properties.getValue(nestingKey) ?: ""
//        Logger.warn("Existing rules: $existingRules")
//        Logger.warn("Existing list rules: $list")

//        val rules = readFileNestingRules(project)
//        Logger.warn("Existing rules: $existingRules")

//        val nestingKey = "project.view.file.nesting.rules"
//
//        val existingRules = properties.getValue(nestingKey) ?: ""

//        val customRules = listOf(
//            "*.component.ts => *.component.html",
//            "*.component.ts => *.component.css",
//            "*.component.ts => *.component.spec.ts"
//        )
//
//        val updatedRules = (existingRules.split("\n") + customRules)
//            .filter { it.isNotBlank() }
//            .distinct()
//            .joinToString("\n")
//
//        properties.setValue(nestingKey, updatedRules)
//
//        // Force refresh to see changes
//        ApplicationManager.getApplication().invokeLater {
//            VirtualFileManager.getInstance().refreshWithoutFileWatcher(false)
//        }
//
//        ApplicationManager.getApplication().invokeLater {
//            val projectView = ProjectView.getInstance(project)
//            projectView.refresh()
//
//            Messages.showMessageDialog(
//                project,
//                "File nesting rules updated.\nPlease restart IntelliJ IDEA to apply changes.",
//                "Restart Required",
//                Messages.getInformationIcon()
//            )
//        }
//
//        ApplicationManager.getApplication().invokeLater {
//            try {
//                val clazz = Class.forName("com.intellij.ide.projectView.impl.FileNestingBuilder")
//                val method: Method = clazz.getDeclaredMethod("reloadFileNestingSettings", Project::class.java)
//                method.isAccessible = true
//                method.invoke(null, project)
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//        }
//
//        Logger.warn("Updated file nesting rules: $updatedRules")
    }
}






