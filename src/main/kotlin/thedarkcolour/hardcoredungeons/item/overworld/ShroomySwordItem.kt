package thedarkcolour.hardcoredungeons.item.overworld

import net.minecraft.ChatFormatting
import net.minecraft.world.entity.Entity
import net.minecraft.world.item.ItemStack
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.effect.MobEffects
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import thedarkcolour.hardcoredungeons.item.HItemTier
import thedarkcolour.hardcoredungeons.item.misc.SwordWithLoreItem

/**
 * @author TheDarkColour
 */
class ShroomySwordItem(properties: Properties) : SwordWithLoreItem(HItemTier.SHROOMY, 3, -2.7f, properties, { it.applyFormat(ChatFormatting.GRAY) }) {
    override fun onLeftClickEntity(stack: ItemStack, player: Player, entity: Entity): Boolean {
        if (entity is LivingEntity) {
            // poison damage stacks
            val a = entity.activeEffectsMap.containsKey(MobEffects.POISON)

            entity.addEffect(MobEffectInstance(MobEffects.POISON, 80, if (a) 3 else 2))
        }
        return super.onLeftClickEntity(stack, player, entity)
    }
}