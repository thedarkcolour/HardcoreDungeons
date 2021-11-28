package thedarkcolour.hardcoredungeons.client.color

import net.minecraft.client.renderer.color.IItemColor
import net.minecraft.world.item.ItemStack
import net.minecraft.potion.PotionUtils

object PotionColor : IItemColor {
    override fun getColor(stack: ItemStack, tintIndex: Int): Int {
        return if (tintIndex > 0) -1 else PotionUtils.getColor(stack)
    }
}