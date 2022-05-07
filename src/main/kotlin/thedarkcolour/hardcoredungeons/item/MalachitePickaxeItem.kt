package thedarkcolour.hardcoredungeons.item

import net.minecraft.block.BlockState
import net.minecraft.entity.LivingEntity
import net.minecraft.item.IItemTier
import net.minecraft.item.ItemStack
import net.minecraft.item.PickaxeItem
import net.minecraft.potion.EffectInstance
import net.minecraft.potion.Effects
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraftforge.common.util.Constants

class MalachitePickaxeItem(
    tier: IItemTier,
    attackDamageIn: Int,
    attackSpeedIn: Float,
    properties: Properties,
) : PickaxeItem(tier, attackDamageIn, attackSpeedIn, properties) {
    override fun mineBlock(
        stack: ItemStack,
        worldIn: World,
        state: BlockState,
        pos: BlockPos,
        entityLiving: LivingEntity,
    ): Boolean {
        val boost = getBoost(stack) + 1

        if (boost >= 5) {
            setBoost(stack, 0)
            entityLiving.addEffect(EffectInstance(Effects.DIG_SPEED, 100, 1, false, false))
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

            if (compound == null || !compound.contains("Boost", Constants.NBT.TAG_INT)) {
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