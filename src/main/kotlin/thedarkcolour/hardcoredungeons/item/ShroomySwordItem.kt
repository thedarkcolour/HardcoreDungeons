package thedarkcolour.hardcoredungeons.item

import net.minecraft.entity.Entity
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.world.item.ItemStack
import net.minecraft.potion.EffectInstance
import net.minecraft.potion.Effects
import net.minecraft.util.text.TextFormatting

class ShroomySwordItem(properties: Properties) : SwordWithLoreItem(HItemTier.SHROOMY, 3, -2.7f, properties, { it.applyFormat(TextFormatting.GRAY) }) {
    override fun onLeftClickEntity(stack: ItemStack, player: PlayerEntity, entity: Entity): Boolean {
        if (entity is LivingEntity) {
            // poison damage stacks
            val a = entity.activeEffectsMap.containsKey(Effects.POISON)

            entity.addEffect(EffectInstance(Effects.POISON, 80, if (a) 3 else 2))
        }
        return super.onLeftClickEntity(stack, player, entity)
    }
}