package thedarkcolour.hardcoredungeons.item

import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.ItemStack
import thedarkcolour.hardcoredungeons.HardcoreDungeons
import thedarkcolour.hardcoredungeons.registry.HBlocks
import thedarkcolour.hardcoredungeons.util.modLoc

object Group : CreativeModeTab(HardcoreDungeons.ID) {
    init {
        backgroundImage = modLoc("textures/gui/container/creative_inventory/tab_background.png")
    }

    override fun makeIcon(): ItemStack {
        return ItemStack(HBlocks.FRAYED_SKULL)
    }
}