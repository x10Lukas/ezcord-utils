package me.geckotv.ezcordutils.settings

import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory
import com.intellij.openapi.options.Configurable
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.TextFieldWithBrowseButton
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBTextField
import com.intellij.util.ui.FormBuilder
import javax.swing.JComponent
import javax.swing.JPanel

/**
 * Settings UI for EzCord plugin configuration.
 */
class EzCordSettingsConfigurable(private val project: Project) : Configurable {

    private var languageFolderField: TextFieldWithBrowseButton? = null
    private var defaultLanguageField: JBTextField? = null

    override fun getDisplayName(): String = "EzCord-Utils Settings"

    override fun createComponent(): JComponent {
        val settings = EzCordSettings.getInstance(project)

        // Create folder picker with browse button
        languageFolderField = TextFieldWithBrowseButton().apply {
            text = settings.state.languageFolderPath
            val descriptor = FileChooserDescriptorFactory.createSingleFolderDescriptor()
            descriptor.title = "Select Language Folder"
            descriptor.description = "Choose the folder containing your language files (.yml, .yaml)"
            addActionListener {
                val chooser = com.intellij.openapi.fileChooser.FileChooser.chooseFile(
                    descriptor,
                    project,
                    null
                )
                chooser?.let {
                    this.text = it.path
                }
            }
        }

        defaultLanguageField = JBTextField(settings.state.defaultLanguage)

        return FormBuilder.createFormBuilder()
            .addLabeledComponent(JBLabel("Language folder path:"), languageFolderField!!, 1, false)
            .addLabeledComponent(JBLabel("Default language:"), defaultLanguageField!!, 1, false)
            .addComponentFillVertically(JPanel(), 0)
            .panel
    }

    override fun isModified(): Boolean {
        val settings = EzCordSettings.getInstance(project)
        return languageFolderField?.text != settings.state.languageFolderPath ||
               defaultLanguageField?.text != settings.state.defaultLanguage
    }

    override fun apply() {
        val settings = EzCordSettings.getInstance(project)
        settings.state.languageFolderPath = languageFolderField?.text ?: "local"
        settings.state.defaultLanguage = defaultLanguageField?.text ?: "en"
    }

    override fun reset() {
        val settings = EzCordSettings.getInstance(project)
        languageFolderField?.text = settings.state.languageFolderPath
        defaultLanguageField?.text = settings.state.defaultLanguage
    }
}

