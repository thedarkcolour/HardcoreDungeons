package thedarkcolour.hardcoredungeons.item.overworld

import net.minecraft.entity.Entity
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.potion.EffectInstance
import net.minecraft.potion.Effects
import net.minecraft.util.text.Color
import thedarkcolour.hardcoredungeons.item.HItemTier
import thedarkcolour.hardcoredungeons.item.misc.SwordWithLoreItem

/**
 * @author TheDarkColour
 */
class ShroomySwordItem(properties: Properties) : SwordWithLoreItem(HItemTier.SHROOMY, 3, -2.7f, properties, {
    it.color = Color.fromInt(0xaa00)
}) {
    override fun onLeftClickEntity(stack: ItemStack, player: PlayerEntity, entity: Entity): Boolean {
        if (entity is LivingEntity) {
            // poison damage stacks
            val a = entity.activePotionMap.containsKey(Effects.POISON)

            if (a || (player.rng.nextFloat() > 0.3f)) {
                entity.addPotionEffect(EffectInstance(Effects.POISON, 80, if (a) 3 else 2))
            }
        }
        return super.onLeftClickEntity(stack, player, entity)
    }
}