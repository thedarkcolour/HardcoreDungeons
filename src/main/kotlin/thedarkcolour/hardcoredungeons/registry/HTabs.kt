package thedarkcolour.hardcoredungeons.registry

import net.minecraft.core.registries.Registries
import net.minecraft.network.chat.Component
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.ItemStack
import thedarkcolour.hardcoredungeons.data.TranslationKeys
import thedarkcolour.hardcoredungeons.registry.block.HBlocks
import thedarkcolour.hardcoredungeons.registry.items.HItems
import thedarkcolour.hardcoredungeons.util.modLoc

object HTabs : HRegistry<CreativeModeTab>(Registries.CREATIVE_MODE_TAB) {
    val TAB by register("creative_tab", ::mainTab)

    private fun mainTab(): CreativeModeTab {
        val builder = CreativeModeTab.Builder(CreativeModeTab.Row.TOP, 0)
        builder.icon { ItemStack(HBlocks.FRAYED_SKULL) }
        builder.title(Component.translatable(TranslationKeys.MAIN_CREATIVE_TAB))
        builder.withBackgroundLocation(modLoc("textures/gui/container/creative_inventory/tab_background.png"))
        builder.displayItems { _, output -> addMainTabItems(output)}
        return builder.build()
    }

    private fun addMainTabItems(output: CreativeModeTab.Output) {
        for (obj in HItems.values()) {
            output.accept(obj.get())
        }
    }
}
