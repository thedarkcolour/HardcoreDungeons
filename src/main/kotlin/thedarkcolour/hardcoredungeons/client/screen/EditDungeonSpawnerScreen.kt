package thedarkcolour.hardcoredungeons.client.screen

import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.gui.widget.button.Button
import net.minecraft.util.text.StringTextComponent
import net.minecraft.util.text.TranslationTextComponent
import thedarkcolour.hardcoredungeons.registry.HBlocks

class EditDungeonSpawnerScreen : Screen(TranslationTextComponent(HBlocks.DUNGEON_SPAWNER.translationKey)) {
    private var saveButton: Button? = null

    override fun init() {
        getMinecraft().keyboardListener.enableRepeatEvents(true)
        saveButton = addButton(Button(width / 2 - 4 - 150, 210, 150, 20, SAVE_TEXT) { button ->
            button.active = false
        })
    }

    companion object {
        val SAVE_TEXT = StringTextComponent("Save changes")
    }
}