package thedarkcolour.hardcoredungeons.item.misc

import net.minecraft.network.chat.Component
import net.minecraft.network.chat.Style
import net.minecraft.network.chat.TranslatableComponent
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.SwordItem
import net.minecraft.world.item.Tier
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.level.Level

open class SwordWithLoreItem(
    tier: Tier,
    damage: Int,
    speed: Float,
    properties: Properties,
    private val style: (Style) -> Style,
) : SwordItem(tier, damage, speed, properties) {
    override fun appendHoverText(
        stack: ItemStack,
        worldIn: Level?,
        tooltip: MutableList<Component>,
        flagIn: TooltipFlag
    ) {
        tooltip.add(TranslatableComponent("$descriptionId.lore").setStyle(style(Style.EMPTY)))
    }
}