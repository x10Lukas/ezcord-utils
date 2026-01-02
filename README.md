# EzCord Utils - IntelliJ Plugin

[![Twitter Follow](https://img.shields.io/badge/follow-%40JBPlatform-1DA1F2?logo=twitter)](https://twitter.com/JBPlatform)
[![Developers Forum](https://img.shields.io/badge/JetBrains%20Platform-Join-blue)][jb:forum]

Ein leistungsstarkes IntelliJ-Plugin zur Optimierung der Discord-Bot-Entwicklung mit [EzCord](https://ezcord.readthedocs.io/en/latest/).

## ✨ Features

### 🔤 Language Key Features
- **Smart Language Key Autocomplete** - Intelligente Vorschläge für verfügbare Language Keys während der Eingabe
- **Quick Documentation** - Zeigt Übersetzungen beim Hover über Language Keys in Echtzeit an
- **One-Click Navigation** - Direkter Sprung zu Language-Definitionen in YAML-Dateien via Gutter Icons
- **File Prefix Detection** - Automatische Auflösung von Keys mit dateibasierten Präfixen

### ⚡ Live Templates
Mit den integrierten Live Templates können Sie schnell Code-Snippets für discord.py und EzCord einfügen. Geben Sie einfach die Abkürzung ein und drücken Sie `Tab`.

#### Verfügbare Templates:

| Abkürzung | Beschreibung | Verwendung |
|-----------|--------------|------------|
| `ezcordbot` | Vollständiges EzCord Bot Setup | Erstellt eine komplette Bot-Struktur mit `ezcord.Bot`, async setup_hook, cog loading und main function |
| `ezcordcog` | EzCord Cog Template | Erstellt eine neue Cog-Klasse mit setup function |
| `ezcordslash` | Slash Command | Fügt einen Discord Slash Command hinzu |
| `ezcordevent` | Event Listener | Erstellt einen Event Listener für Cogs |
| `ezcordmodal` | Discord Modal | Erstellt ein Discord Modal mit TextInput |
| `ezcordview` | Discord View mit Button | Erstellt eine View-Klasse mit Button-Callback |

#### Beispiel: `ezcordbot` Template

```python
import asyncio

import discord

import ezcord


class Bot(ezcord.Bot):
    def __init__(self):
        super().__init__(intents=discord.Intents.default())

    async def setup_hook(self):
        await super().setup_hook()
        await self.tree.sync()


async def main():
    async with Bot() as bot:
        bot.add_help_command()
        bot.load_cogs("cogs")  # Load all cogs in the "cogs" folder
        await bot.start("TOKEN")  # Replace with your bot token


if __name__ == "__main__":
    asyncio.run(main())
```

## 🚀 Installation

1. Öffnen Sie IntelliJ IDEA / PyCharm
2. Gehen Sie zu `Settings` → `Plugins`
3. Suchen Sie nach "EzCord Utils"
4. Klicken Sie auf `Install`

Oder laden Sie das Plugin manuell von der [JetBrains Marketplace](https://plugins.jetbrains.com) herunter.

## 📖 Verwendung

### Live Templates verwenden
1. Öffnen Sie eine Python-Datei
2. Geben Sie eine der Template-Abkürzungen ein (z.B. `ezcordbot`)
3. Drücken Sie `Tab`
4. Navigieren Sie mit `Tab` durch die Platzhalter und füllen Sie die Werte aus

### Language Keys verwenden
1. Konfigurieren Sie den Language-Ordner in `Settings` → `Tools` → `EzCord Settings`
2. Beginnen Sie `bot.t("` in Ihrem Python-Code zu schreiben
3. Erhalten Sie automatische Vorschläge für verfügbare Keys
4. Klicken Sie auf das Gutter-Icon, um zur Definition zu springen

## Plugin template structure

A generated project contains the following content structure:

```
.
├── .run/                   Predefined Run/Debug Configurations
├── build/                  Output build directory
├── gradle
│   ├── wrapper/            Gradle Wrapper
├── src                     Plugin sources
│   ├── main
│   │   ├── kotlin/         Kotlin production sources
│   │   └── resources/      Resources - plugin.xml, icons, messages
├── .gitignore              Git ignoring rules
├── build.gradle.kts        Gradle build configuration
├── gradle.properties       Gradle configuration properties
├── gradlew                 *nix Gradle Wrapper script
├── gradlew.bat             Windows Gradle Wrapper script
├── README.md               README
└── settings.gradle.kts     Gradle project settings
```

In addition to the configuration files, the most crucial part is the `src` directory, which contains our implementation
and the manifest for our plugin – [plugin.xml][file:plugin.xml].

> [!NOTE]
> To use Java in your plugin, create the `/src/main/java` directory.

## Plugin configuration file

The plugin configuration file is a [plugin.xml][file:plugin.xml] file located in the `src/main/resources/META-INF`
directory.
It provides general information about the plugin, its dependencies, extensions, and listeners.

You can read more about this file in the [Plugin Configuration File][docs:plugin.xml] section of our documentation.

If you're still not quite sure what this is all about, read our
introduction: [What is the IntelliJ Platform?][docs:intro]

$H$H Predefined Run/Debug configurations

Within the default project structure, there is a `.run` directory provided containing predefined *Run/Debug
configurations* that expose corresponding Gradle tasks:

| Configuration name | Description                                                                                                                                                                         |
|--------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Run Plugin         | Runs [`:runIde`][gh:intellij-platform-gradle-plugin-runIde] IntelliJ Platform Gradle Plugin task. Use the *Debug* icon for plugin debugging.                                        |
| Run Tests          | Runs [`:test`][gradle:lifecycle-tasks] Gradle task.                                                                                                                                 |
| Run Verifications  | Runs [`:verifyPlugin`][gh:intellij-platform-gradle-plugin-verifyPlugin] IntelliJ Platform Gradle Plugin task to check the plugin compatibility against the specified IntelliJ IDEs. |

> [!NOTE]
> You can find the logs from the running task in the `idea.log` tab.

## Publishing the plugin

> [!TIP]
> Make sure to follow all guidelines listed in [Publishing a Plugin][docs:publishing] to follow all recommended and
> required steps.

Releasing a plugin to [JetBrains Marketplace](https://plugins.jetbrains.com) is a straightforward operation that uses
the `publishPlugin` Gradle task provided by
the [intellij-platform-gradle-plugin][gh:intellij-platform-gradle-plugin-docs].

You can also upload the plugin to the [JetBrains Plugin Repository](https://plugins.jetbrains.com/plugin/upload)
manually via UI.

## Useful links

- [IntelliJ Platform SDK Plugin SDK][docs]
- [IntelliJ Platform Gradle Plugin Documentation][gh:intellij-platform-gradle-plugin-docs]
- [IntelliJ Platform Explorer][jb:ipe]
- [JetBrains Marketplace Quality Guidelines][jb:quality-guidelines]
- [IntelliJ Platform UI Guidelines][jb:ui-guidelines]
- [JetBrains Marketplace Paid Plugins][jb:paid-plugins]
- [IntelliJ SDK Code Samples][gh:code-samples]

[docs]: https://plugins.jetbrains.com/docs/intellij

[docs:intro]: https://plugins.jetbrains.com/docs/intellij/intellij-platform.html?from=IJPluginTemplate

[docs:plugin.xml]: https://plugins.jetbrains.com/docs/intellij/plugin-configuration-file.html?from=IJPluginTemplate

[docs:publishing]: https://plugins.jetbrains.com/docs/intellij/publishing-plugin.html?from=IJPluginTemplate

[file:plugin.xml]: ./src/main/resources/META-INF/plugin.xml

[gh:code-samples]: https://github.com/JetBrains/intellij-sdk-code-samples

[gh:intellij-platform-gradle-plugin]: https://github.com/JetBrains/intellij-platform-gradle-plugin

[gh:intellij-platform-gradle-plugin-docs]: https://plugins.jetbrains.com/docs/intellij/tools-intellij-platform-gradle-plugin.html

[gh:intellij-platform-gradle-plugin-runIde]: https://plugins.jetbrains.com/docs/intellij/tools-intellij-platform-gradle-plugin-tasks.html#runIde

[gh:intellij-platform-gradle-plugin-verifyPlugin]: https://plugins.jetbrains.com/docs/intellij/tools-intellij-platform-gradle-plugin-tasks.html#verifyPlugin

[gradle:lifecycle-tasks]: https://docs.gradle.org/current/userguide/java_plugin.html#lifecycle_tasks

[jb:github]: https://github.com/JetBrains/.github/blob/main/profile/README.md

[jb:forum]: https://platform.jetbrains.com/

[jb:quality-guidelines]: https://plugins.jetbrains.com/docs/marketplace/quality-guidelines.html

[jb:paid-plugins]: https://plugins.jetbrains.com/docs/marketplace/paid-plugins-marketplace.html

[jb:quality-guidelines]: https://plugins.jetbrains.com/docs/marketplace/quality-guidelines.html

[jb:ipe]: https://jb.gg/ipe

[jb:ui-guidelines]: https://jetbrains.github.io/ui