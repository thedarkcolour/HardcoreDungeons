package thedarkcolour.hardcoredungeons.item

import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.stats.Stats
import net.minecraft.tags.FluidTags
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.ItemUtils
import net.minecraft.world.item.alchemy.PotionUtils
import net.minecraft.world.item.alchemy.Potions
import net.minecraft.world.level.ClipContext
import net.minecraft.world.level.Level
import net.minecraft.world.phys.BlockHitResult
import net.minecraft.world.phys.HitResult
import thedarkcolour.hardcoredungeons.registry.items.POTION_SYRINGE_ITEM

class EmptySyringeItem(properties: Properties) : Item(properties) {
    override fun use(level: Level, playerIn: Player, hand: InteractionHand): InteractionResultHolder<ItemStack> {
        val bottle = playerIn.getItemInHand(hand)
        val result = getPlayerPOVHitResult(level, playerIn, ClipContext.Fluid.SOURCE_ONLY)

        return when (result.type!!) {
            HitResult.Type.MISS, HitResult.Type.ENTITY -> InteractionResultHolder.pass(bottle)
            HitResult.Type.BLOCK -> {
                val targetPos = (result as BlockHitResult).blockPos

                if (level.mayInteract(playerIn, targetPos) && level.getFluidState(targetPos).`is`(FluidTags.WATER)) {
                    level.playSound(playerIn, playerIn.x, playerIn.y, playerIn.z, SoundEvents.BOTTLE_FILL, SoundSource.NEUTRAL, 1.0f, 1.0f)

                    InteractionResultHolder.sidedSuccess(turnBottleIntoItem(bottle, playerIn, PotionUtils.setPotion(ItemStack(POTION_SYRINGE_ITEM), Potions.WATER)), level.isClientSide)
                } else {
                    InteractionResultHolder.pass(bottle)
                }
            }
        }
    }

    private fun turnBottleIntoItem(bottle: ItemStack, playerIn: Player, filledBottle: ItemStack): ItemStack {
        playerIn.awardStat(Stats.ITEM_USED[this])
        return ItemUtils.createFilledResult(bottle, playerIn, filledBottle)
    }
}