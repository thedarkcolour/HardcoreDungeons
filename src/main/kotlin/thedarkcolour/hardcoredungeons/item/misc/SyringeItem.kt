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
import net.minecraft.world.World
import thedarkcolour.hardcoredungeons.registry.HItems

/**
 * Extremely quick potion item for use in combat.
 * Can also be stacked to save on inventory space.
 */
class SyringeItem(properties: Properties) : PotionItem(properties) {
    override fun onItemUseFinish(stack: ItemStack, worldIn: World, entity: LivingEntity): ItemStack {
        val player = if (entity is PlayerEntity) entity else null
        if (player is ServerPlayerEntity) {
            CriteriaTriggers.CONSUME_ITEM.trigger(player, stack)
        }

        if (!worldIn.isRemote) {
            val effects = PotionUtils.getEffectsFromStack(stack)

            for (effect in effects) {
                if (effect.potion.isInstant) {
                    effect.potion.affectEntity(player, player, entity, effect.amplifier, 1.0)
                } else {
                    entity.addPotionEffect(EffectInstance(effect))
                }
            }
        }

        if (player != null) {
            player.addStat(Stats.ITEM_USED[this])

            if (!player.abilities.isCreativeMode) {
                stack.shrink(1)
            }
        }

        if (player == null || !player.abilities.isCreativeMode) {
            if (stack.isEmpty) {
                return ItemStack(HItems.SYRINGE)
            }

            player?.inventory?.addItemStackToInventory(ItemStack(HItems.SYRINGE))
        }

        return stack
    }

    override fun getUseDuration(stack: ItemStack) = 10

    override fun getUseAction(stack: ItemStack) = UseAction.BOW
}