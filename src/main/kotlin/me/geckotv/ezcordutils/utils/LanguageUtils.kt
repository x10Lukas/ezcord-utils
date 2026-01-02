package me.geckotv.ezcordutils.utils

class LanguageUtils {
    /**
     * Extracts the prefix from a filename (e.g., "welcome" from "welcome.py").
     * Handles formats like "welcome.py", "welcome.container.py", etc.
     */
    fun getFilePrefix(filename: String): String? {
        if (!filename.endsWith(".py")) return null

        val nameWithoutExtension = filename.removeSuffix(".py")

        // If the filename contains dots, take everything before the last component
        // e.g., "welcome.container.py" -> "welcome.container"
        // e.g., "welcome.py" -> "welcome"
        return nameWithoutExtension.ifEmpty { null }
    }
}