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
    override fun use(worldIn: World, playerIn: PlayerEntity, hand: Hand): ActionResult<ItemStack> {
        val bottle = playerIn.getItemInHand(hand)
        val result = getPlayerPOVHitResult(worldIn, playerIn, FluidMode.SOURCE_ONLY)

        if (result.type == RayTraceResult.Type.MISS) {
            return ActionResult.pass(bottle)
        } else {
            if (result.type == RayTraceResult.Type.BLOCK) {
                val targetPos = (result as BlockRayTraceResult).blockPos

                if (!worldIn.mayInteract(playerIn, targetPos)) {
                    return ActionResult.pass(bottle)
                }

                if (worldIn.getFluidState(targetPos).`is`(FluidTags.WATER)) {
                    worldIn.playSound(playerIn, playerIn.x, playerIn.y, playerIn.z, SoundEvents.BOTTLE_FILL, SoundCategory.NEUTRAL, 1.0f, 1.0f)
                    return ActionResult.sidedSuccess(turnBottleIntoItem(bottle, playerIn, PotionUtils.setPotion(ItemStack(HItems.POTION_SYRINGE), Potions.WATER)), worldIn.isClientSide)
                }
            }

            return ActionResult.pass(bottle)
        }
    }

    private fun turnBottleIntoItem(bottle: ItemStack, playerIn: PlayerEntity, filledBottle: ItemStack): ItemStack {
        playerIn.awardStat(Stats.ITEM_USED[this])
        return DrinkHelper.createFilledResult(bottle, playerIn, filledBottle)
    }
}