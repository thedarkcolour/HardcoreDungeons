package thedarkcolour.hardcoredungeons.client.color

import net.minecraft.client.color.item.ItemColor
import net.minecraft.client.renderer.color.IItemColor
import net.minecraft.world.item.ItemStack
import net.minecraft.potion.PotionUtils
import net.minecraft.world.item.alchemy.PotionUtils

object PotionColor : ItemColor {
    override fun getColor(stack: ItemStack, tintIndex: Int): Int {
        return if (tintIndex > 0) -1 else PotionUtils.getColor(stack)
    }
}