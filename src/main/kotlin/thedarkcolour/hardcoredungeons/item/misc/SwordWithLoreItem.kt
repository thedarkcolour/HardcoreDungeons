package thedarkcolour.hardcoredungeons.item.misc

import net.minecraft.client.util.ITooltipFlag
import net.minecraft.item.IItemTier
import net.minecraft.item.ItemStack
import net.minecraft.item.SwordItem
import net.minecraft.util.text.ITextComponent
import net.minecraft.util.text.Style
import net.minecraft.util.text.TranslationTextComponent
import net.minecraft.world.level.Level

open class SwordWithLoreItem(
    tier: IItemTier,
    damage: Int,
    speed: Float,
    properties: Properties,
    private val style: (Style) -> Style,
) : SwordItem(tier, damage, speed, properties) {
    override fun appendHoverText(
        stack: ItemStack,
        worldIn: World?,
        tooltip: MutableList<ITextComponent>,
        flagIn: ITooltipFlag
    ) {
        tooltip.add(TranslationTextComponent("$descriptionId.lore").setStyle(style(Style.EMPTY)))
    }
}