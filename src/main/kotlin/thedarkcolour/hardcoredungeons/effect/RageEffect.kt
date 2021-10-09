package thedarkcolour.hardcoredungeons.effect

import net.minecraft.entity.LivingEntity
import net.minecraft.potion.Effect
import net.minecraft.potion.EffectType
import kotlin.math.min

// Increase eating speeds and attack speed
class RageEffect : Effect(EffectType.BENEFICIAL, 16711752) {
    override fun isDurationEffectTick(duration: Int, amplifier: Int): Boolean {
        val k = 50 shr amplifier

        return if (k > 0) {
            duration % k == 0
        } else true
    }

    override fun applyEffectTick(entityLivingBaseIn: LivingEntity, amplifier: Int) {
        if (entityLivingBaseIn.useItem.isEdible) {
            entityLivingBaseIn.useItemRemaining = min(entityLivingBaseIn.ticksUsingItem, entityLivingBaseIn.useItemRemainingTicks + 1)
        }
    }
}