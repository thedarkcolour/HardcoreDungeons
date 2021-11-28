package thedarkcolour.hardcoredungeons.item.misc

import net.minecraft.advancements.CriteriaTriggers
import net.minecraft.world.item.ItemStack
import net.minecraft.server.level.ServerPlayer
import net.minecraft.stats.Stats
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.PotionItem
import net.minecraft.world.item.UseAnim
import net.minecraft.world.item.alchemy.PotionUtils
import net.minecraft.world.level.Level
import thedarkcolour.hardcoredungeons.registry.HItems

/**
 * Quick potion item for use in combat.
 * Can also be stacked to save on inventory space.
 */
class SyringeItem(properties: Properties) : PotionItem(properties) {
    override fun finishUsingItem(stack: ItemStack, worldIn: Level, entity: LivingEntity): ItemStack {
        val player = if (entity is Player) entity else null
        if (player is ServerPlayer) {
            CriteriaTriggers.CONSUME_ITEM.trigger(player, stack)
        }

        if (!worldIn.isClientSide) {
            val effects = PotionUtils.getMobEffects(stack)

            for (effect in effects) {
                if (effect.effect.isInstantenous) {
                    effect.effect.applyInstantenousEffect(player, player, entity, effect.amplifier, 1.0)
                } else {
                    entity.addEffect(MobEffectInstance(effect))
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

    override fun getUseAnimation(stack: ItemStack) = UseAnim.BOW
}