package me.geckotv.ezcordutils

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import org.jetbrains.jewel.bridge.addComposeTab
import org.jetbrains.jewel.ui.component.OutlinedButton
import org.jetbrains.jewel.ui.component.Text
import kotlin.random.Random

class MyToolWindowFactory : ToolWindowFactory {
    override fun shouldBeAvailable(project: Project) = true

    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        toolWindow.addComposeTab("My Tool Window", focusOnClickInside = true) {
            LaunchedEffect(Unit) {
                // initial data loading
            }

            MyToolWindowContent()
        }
    }
}

@Composable
private fun MyToolWindowContent() {
    val labelText = remember { mutableStateOf("The random number is: ?") }

    Column(Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(labelText.value)

        OutlinedButton(onClick = {
            labelText.value = "The random number is: " + Random(System.currentTimeMillis()).nextInt(1000)
        }) { Text("Shuffle") }
    }

    val isThePersonCool = remember { mutableStateOf("Is the person who clicked the button cool?") }

    Column(Modifier.padding(20.dp).padding(top=60.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(isThePersonCool.value)

        OutlinedButton(onClick = {
            isThePersonCool.value = "The person who clicked the button is: " + if (Random(System.currentTimeMillis()).nextBoolean()) "Cool 😎" else "Not Cool 😞"
        }) { Text("Am I Cool?") }
    }
}