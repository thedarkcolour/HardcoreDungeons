package thedarkcolour.hardcoredungeons.item

import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import thedarkcolour.hardcoredungeons.HardcoreDungeons
import thedarkcolour.hardcoredungeons.block.HBlocks
import thedarkcolour.hardcoredungeons.util.modLoc

object Group : ItemGroup(HardcoreDungeons.ID) {
    init {
        backgroundImage = modLoc("textures/gui/container/creative_inventory/tab_background.png")
    }

    override fun makeIcon(): ItemStack {
        return ItemStack(HBlocks.FRAYED_SKULL)
    }
}