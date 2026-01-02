package me.geckotv.ezcordutils.language

import com.intellij.lang.documentation.AbstractDocumentationProvider
import com.intellij.openapi.editor.Editor
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.jetbrains.python.psi.PyStringLiteralExpression
import me.geckotv.ezcordutils.utils.LanguageUtils

/**
 * Provides documentation for language keys in Python files.
 * Shows the translated text when hovering over strings like "general.test".
 */
class LanguageDocumentationProvider : AbstractDocumentationProvider() {

    @Suppress("UnstableApiUsage")
    override fun generateDoc(element: PsiElement?, originalElement: PsiElement?): String? {
        val pyString = when {
            element is PyStringLiteralExpression -> element
            originalElement is PyStringLiteralExpression -> originalElement
            else -> { return null }
        }

        val stringValue = pyString.stringValue.trim()

        if (stringValue.isBlank()) {
            return null
        }

        val resolver = LanguageResolver(pyString.project)
        val filePrefix = LanguageUtils().getFilePrefix(pyString.containingFile.name)

        // Extract all language keys from strings with placeholders like {container.title}
        val keyPattern = Regex("""[{]([a-zA-Z0-9_.]+)[}]""")
        val keyMatches = keyPattern.findAll(stringValue).toList()

        if (keyMatches.isNotEmpty()) {
            // Multiple keys found in format {key1}\n{key2}
            val translations = mutableMapOf<String, String>()

            for (match in keyMatches) {
                val key = match.groupValues[1]
                var translatedText = resolver.resolve(key)
                var finalKey = key

                if (translatedText == null) {
                    finalKey = "$filePrefix.$key"
                    translatedText = resolver.resolve(finalKey)
                }

                if (translatedText != null) {
                    translations[finalKey] = translatedText
                }
            }

            if (translations.isEmpty()) {
                return null
            }

            if (translations.size == 1) {
                val (singleKey, singleTranslation) = translations.entries.first()
                return buildDocumentation(singleKey, singleTranslation)
            }

            return buildMultiDocumentation(translations)
        }

        var translatedText = resolver.resolve(stringValue)
        var finalKey = stringValue

        if (translatedText == null) {
            finalKey = "$filePrefix.$stringValue"
            translatedText = resolver.resolve(finalKey)
            if (translatedText == null) {
                return null
            }
        }

        return buildDocumentation(finalKey, translatedText)
    }

    override fun getCustomDocumentationElement(
        editor: Editor,
        file: PsiFile,
        contextElement: PsiElement?,
        targetOffset: Int
    ): PsiElement? {
        // Check if we're hovering over a Python string or inside one
        val element = when {
            contextElement is PyStringLiteralExpression -> {
                contextElement
            }
            contextElement?.parent is PyStringLiteralExpression -> {
                contextElement.parent as PyStringLiteralExpression
            }
            else -> {
                null
            }
        }

        return element
    }

    /**
     * Builds the HTML documentation to display for a single key.
     */
    private fun buildDocumentation(key: String, translation: String): String {
        return buildString {
            append("<div class='definition'><pre>")
            append("<b>Language Key:</b> $key")
            append("</pre></div>")
            append("<div class='content'>")
            append("<p><b>Translation:</b></p>")
            append("<p style='margin-left: 10px; font-style: italic;'>")
            append(escapeHtml(translation))
            append("</p>")
            append("</div>")
        }
    }

    /**
     * Builds the HTML documentation to display for multiple keys.
     */
    private fun buildMultiDocumentation(translations: Map<String, String>): String {
        return buildString {
            append("<div class='definition'><pre>")
            append("<b>Language Keys Found:</b> ${translations.size}")
            append("</pre></div>")
            append("<div class='content'>")

            translations.forEach { (key, translation) ->
                append("<hr style='margin: 8px 0; border: 0; border-top: 1px solid #ccc;'>")
                append("<p><b>Key:</b> $key</p>")
                append("<p style='margin-left: 10px; font-style: italic;'>")
                append(escapeHtml(translation))
                append("</p>")
            }

            append("</div>")
        }
    }

    /**
     * Escapes HTML special characters.
     */
    private fun escapeHtml(text: String): String {
        return text
            .replace("&", "&amp;")
            .replace("<", "&lt;")
            .replace(">", "&gt;")
            .replace("\"", "&quot;")
            .replace("'", "&#39;")
    }
}
