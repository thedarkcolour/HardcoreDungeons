package thedarkcolour.hardcoredungeons.effect

import net.minecraft.entity.LivingEntity
import net.minecraft.potion.Effect
import net.minecraft.potion.EffectType
import kotlin.math.min

class RageEffect : Effect(EffectType.BENEFICIAL, 16711752) {
    override fun isReady(duration: Int, amplifier: Int): Boolean {
        val k = 50 shr amplifier

        return if (k > 0) {
            duration % k == 0
        } else true
    }

    override fun performEffect(entityLivingBaseIn: LivingEntity, amplifier: Int) {
        if (entityLivingBaseIn.activeItemStack.isFood) {
            entityLivingBaseIn.activeItemStackUseCount = min(entityLivingBaseIn.itemInUseMaxCount, entityLivingBaseIn.itemInUseCount + 1)
        }
    }
}