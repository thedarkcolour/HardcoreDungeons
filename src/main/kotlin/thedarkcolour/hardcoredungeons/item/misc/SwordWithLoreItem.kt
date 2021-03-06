package thedarkcolour.hardcoredungeons.item.misc

import net.minecraft.client.util.ITooltipFlag
import net.minecraft.item.IItemTier
import net.minecraft.item.ItemStack
import net.minecraft.item.SwordItem
import net.minecraft.util.text.ITextComponent
import net.minecraft.util.text.TranslationTextComponent
import net.minecraft.world.World

open class SwordWithLoreItem(
    tier: IItemTier,
    damage: Int,
    speed: Float,
    properties: Properties,
    //style: Consumer<Style>,
) : SwordItem(tier, damage, speed, properties) {
    override fun addInformation(
        stack: ItemStack,
        worldIn: World?,
        tooltip: MutableList<ITextComponent>,
        flagIn: ITooltipFlag
    ) {
        tooltip.add(TranslationTextComponent("$translationKey.lore")/*.applyTextStyle(style)*/)
    }
}