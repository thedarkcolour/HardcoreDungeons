package thedarkcolour.hardcoredungeons.item

import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import thedarkcolour.hardcoredungeons.HardcoreDungeons
import thedarkcolour.hardcoredungeons.registry.HItems

object Group : ItemGroup(HardcoreDungeons.ID) {
    override fun createIcon(): ItemStack {
        return ItemStack(HItems.FLAME_ROSE)
    }
}