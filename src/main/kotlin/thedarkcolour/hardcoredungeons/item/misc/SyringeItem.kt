package thedarkcolour.hardcoredungeons.item.misc

import net.minecraft.advancements.CriteriaTriggers
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.ServerPlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.item.PotionItem
import net.minecraft.item.UseAction
import net.minecraft.potion.EffectInstance
import net.minecraft.potion.PotionUtils
import net.minecraft.stats.Stats
import net.minecraft.world.level.Level
import thedarkcolour.hardcoredungeons.registry.HItems

/**
 * Quick potion item for use in combat.
 * Can also be stacked to save on inventory space.
 */
class SyringeItem(properties: Properties) : PotionItem(properties) {
    override fun finishUsingItem(stack: ItemStack, worldIn: World, entity: LivingEntity): ItemStack {
        val player = if (entity is PlayerEntity) entity else null
        if (player is ServerPlayerEntity) {
            CriteriaTriggers.CONSUME_ITEM.trigger(player, stack)
        }

        if (!worldIn.isClientSide) {
            val effects = PotionUtils.getMobEffects(stack)

            for (effect in effects) {
                if (effect.effect.isInstantenous) {
                    effect.effect.applyInstantenousEffect(player, player, entity, effect.amplifier, 1.0)
                } else {
                    entity.addEffect(EffectInstance(effect))
                }
            }
        }

        if (player != null) {
            player.awardStat(Stats.ITEM_USED[this])

            if (!player.abilities.instabuild) {
                stack.shrink(1)
            }
        }

        if (player == null || !player.abilities.instabuild) {
            if (stack.isEmpty) {
                return ItemStack(HItems.SYRINGE)
            }

            player?.inventory?.add(ItemStack(HItems.SYRINGE))
        }

        return stack
    }

    override fun getUseDuration(stack: ItemStack) = 10

    override fun getUseAnimation(stack: ItemStack) = UseAction.BOW
}