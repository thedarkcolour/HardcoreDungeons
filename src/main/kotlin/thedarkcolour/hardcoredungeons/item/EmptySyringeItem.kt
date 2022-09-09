package thedarkcolour.hardcoredungeons.item

import net.minecraft.entity.player.PlayerEntity
import net.minecraft.world.item.ItemStack
import net.minecraft.potion.PotionUtils
import net.minecraft.potion.Potions
import net.minecraft.stats.Stats
import net.minecraft.tags.FluidTags
import net.minecraft.util.*
import net.minecraft.util.math.BlockRayTraceResult
import net.minecraft.util.math.RayTraceContext.FluidMode
import net.minecraft.util.math.RayTraceResult
import net.minecraft.world.item.Item
import net.minecraft.world.level.Level
import thedarkcolour.hardcoredungeons.registry.HItems

class EmptySyringeItem(properties: Properties) : Item(properties) {
    override fun use(level: Level, playerIn: PlayerEntity, hand: Hand): ActionResult<ItemStack> {
        val bottle = playerIn.getItemInHand(hand)
        val result = getPlayerPOVHitResult(level, playerIn, FluidMode.SOURCE_ONLY)

        if (result.type == RayTraceResult.Type.MISS) {
            return ActionResult.pass(bottle)
        } else {
            if (result.type == RayTraceResult.Type.BLOCK) {
                val targetPos = (result as BlockRayTraceResult).blockPos

                if (!level.mayInteract(playerIn, targetPos)) {
                    return ActionResult.pass(bottle)
                }

                if (level.getFluidState(targetPos).`is`(FluidTags.WATER)) {
                    level.playSound(playerIn, playerIn.x, playerIn.y, playerIn.z, SoundEvents.BOTTLE_FILL, SoundCategory.NEUTRAL, 1.0f, 1.0f)
                    return ActionResult.sidedSuccess(turnBottleIntoItem(bottle, playerIn, PotionUtils.setPotion(ItemStack(HItems.POTION_SYRINGE), Potions.WATER)), level.isClientSide)
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