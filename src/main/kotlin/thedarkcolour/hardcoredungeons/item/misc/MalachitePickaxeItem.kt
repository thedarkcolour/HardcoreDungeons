package thedarkcolour.hardcoredungeons.item.misc

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

// hopefully we can make a speedy pickaxe with this
// this was inspired from an idea i had today about doing a vanilla playthrough
// but then i realized how fucking boring it would be if i just sat there with a diamond pickaxe
// being a god or whatever
// so i decided we need more epic pickaxes
class MalachitePickaxeItem(
    tier: IItemTier,
    attackDamageIn: Int,
    attackSpeedIn: Float,
    properties: Properties,
) : PickaxeItem(tier, attackDamageIn, attackSpeedIn, properties) {
    override fun onBlockDestroyed(
        stack: ItemStack,
        worldIn: World,
        state: BlockState,
        pos: BlockPos,
        entityLiving: LivingEntity,
    ): Boolean {
        val boost = getBoost(stack) + 1

        if (boost >= 5) {
            setBoost(stack, 0)
            entityLiving.addPotionEffect(EffectInstance(Effects.HASTE, 100, 1, false, false))
        } else {
            setBoost(stack, boost)
        }

        return super.onBlockDestroyed(stack, worldIn, state, pos, entityLiving)
    }

    // hopefully reset the boost timer
    // might not work cause its probably clientside
    // CLIENTSIDE ONLY
    override fun shouldCauseReequipAnimation(
        oldStack: ItemStack,
        newStack: ItemStack,
        slotChanged: Boolean,
    ): Boolean {
        if (oldStack != newStack) {
            setBoost(oldStack, 0)
        }
        return super.shouldCauseReequipAnimation(oldStack, newStack, slotChanged)
    }

    // boost mining speed
    override fun getDestroySpeed(stack: ItemStack, state: BlockState): Float {
        val speed = super.getDestroySpeed(stack, state)

        if (speed == efficiency && getBoost(stack) == 5) {
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