package thedarkcolour.hardcoredungeons.item

import net.minecraft.core.BlockPos
import net.minecraft.nbt.Tag
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.effect.MobEffects
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.PickaxeItem
import net.minecraft.world.item.Tier
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState

class MalachitePickaxeItem(
    tier: Tier,
    attackDamageIn: Int,
    attackSpeedIn: Float,
    properties: Item.Properties,
) : PickaxeItem(tier, attackDamageIn, attackSpeedIn, properties) {
    override fun mineBlock(
        stack: ItemStack,
        worldIn: Level,
        state: BlockState,
        pos: BlockPos,
        entityLiving: LivingEntity,
    ): Boolean {
        val boost = getBoost(stack) + 1

        if (boost >= 5) {
            setBoost(stack, 0)
            entityLiving.addEffect(MobEffectInstance(MobEffects.DIG_SPEED, 100, 1, false, false))
        } else {
            setBoost(stack, boost)
        }

        return super.mineBlock(stack, worldIn, state, pos, entityLiving)
    }

    // hopefully reset the boost timer
    // might not work cause its probably clientside
    // CLIENTSIDE ONLY
    override fun shouldCauseReequipAnimation(oldStack: ItemStack, newStack: ItemStack, slotChanged: Boolean): Boolean {
        if (oldStack != newStack) {
            setBoost(oldStack, 0)
        }
        return super.shouldCauseReequipAnimation(oldStack, newStack, slotChanged)
    }

    // boost mining speed
    override fun getDestroySpeed(stack: ItemStack, state: BlockState): Float {
        val speed = super.getDestroySpeed(stack, state)

        if (speed == this.speed && getBoost(stack) == 5) {
            return speed * 1.5f
        }

        return speed
    }

    companion object {
        fun getBoost(stack: ItemStack): Int {
            val compound = stack.tag

            if (compound == null || !compound.contains("Boost", Tag.TAG_INT.toInt())) {
                return 0
            }

            return compound.getInt("Boost").coerceAtMost(5)
        }

        fun setBoost(stack: ItemStack, boost: Int) {
            val compound = stack.orCreateTag
            compound.putInt("Boost", boost.coerceAtMost(5))
        }
    }
}