package thedarkcolour.hardcoredungeons.item

import net.minecraft.ChatFormatting
import net.minecraft.network.chat.Component
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.effect.MobEffects
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.SwordItem
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.level.Level

class ShroomySwordItem(properties: Properties) : SwordItem(HItemTier.SHROOMY, 3, -2.7f, properties) {
    override fun onLeftClickEntity(stack: ItemStack, player: Player, entity: Entity): Boolean {
        if (entity is LivingEntity) {
            // poison damage stacks
            val a = entity.activeEffectsMap.containsKey(MobEffects.POISON)

            entity.addEffect(MobEffectInstance(MobEffects.POISON, 80, if (a) 3 else 2))
        }
        return super.onLeftClickEntity(stack, player, entity)
    }

    override fun appendHoverText(
        pStack: ItemStack,
        pLevel: Level?,
        pTooltipComponents: MutableList<Component>,
        pIsAdvanced: TooltipFlag
    ) {
        pTooltipComponents.add(Component.translatable("$descriptionId.lore").withStyle(ChatFormatting.GRAY))
    }
}