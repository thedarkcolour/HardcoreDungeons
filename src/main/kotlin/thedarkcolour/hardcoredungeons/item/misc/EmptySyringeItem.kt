package thedarkcolour.hardcoredungeons.item.misc

import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.potion.PotionUtils
import net.minecraft.potion.Potions
import net.minecraft.stats.Stats
import net.minecraft.tags.FluidTags
import net.minecraft.util.*
import net.minecraft.util.math.BlockRayTraceResult
import net.minecraft.util.math.RayTraceContext.FluidMode
import net.minecraft.util.math.RayTraceResult
import net.minecraft.world.World
import thedarkcolour.hardcoredungeons.registry.HItems

class EmptySyringeItem(properties: Properties) : Item(properties) {
    override fun onItemRightClick(worldIn: World, playerIn: PlayerEntity, hand: Hand): ActionResult<ItemStack> {
        val bottle = playerIn.getHeldItem(hand)
        val result = rayTrace(worldIn, playerIn, FluidMode.SOURCE_ONLY)

        if (result.type == RayTraceResult.Type.MISS) {
            return ActionResult.resultPass(bottle)
        } else {
            if (result.type == RayTraceResult.Type.BLOCK) {
                val targetPos = (result as BlockRayTraceResult).pos

                if (!worldIn.isBlockModifiable(playerIn, targetPos)) {
                    return ActionResult.resultPass(bottle)
                }

                // @formatter:off
                if (worldIn.getFluidState(targetPos).isTagged(FluidTags.WATER)) {
                    worldIn.playSound(playerIn, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.NEUTRAL, 1.0f, 1.0f)
                    return ActionResult.func_233538_a_(turnBottleIntoItem(bottle, playerIn, PotionUtils.addPotionToItemStack(ItemStack(HItems.POTION_SYRINGE), Potions.WATER)), worldIn.isRemote())
                }
                // @formatter:on
            }

            return ActionResult.resultPass(bottle)
        }
    }

    private fun turnBottleIntoItem(bottle: ItemStack, playerIn: PlayerEntity, filledBottle: ItemStack): ItemStack {
        playerIn.addStat(Stats.ITEM_USED[this])
        return DrinkHelper.fill(bottle, playerIn, filledBottle)
    }
}