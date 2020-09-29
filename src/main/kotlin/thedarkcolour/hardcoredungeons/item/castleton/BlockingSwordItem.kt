package thedarkcolour.hardcoredungeons.item.castleton

import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.item.ItemTier
import net.minecraft.item.SwordItem
import net.minecraft.item.UseAction
import net.minecraft.util.ActionResult
import net.minecraft.util.ActionResultType
import net.minecraft.util.Hand
import net.minecraft.world.World

class BlockingSwordItem(properties: Properties) : SwordItem(ItemTier.GOLD, 9, -2.4f, properties) {
    override fun onItemRightClick(worldIn: World, playerIn: PlayerEntity, handIn: Hand): ActionResult<ItemStack> {
        val stack = playerIn.getHeldItem(handIn)
        playerIn.activeHand = handIn
        return ActionResult(ActionResultType.SUCCESS, stack)
    }

    override fun getUseAction(stack: ItemStack) = UseAction.BLOCK

    override fun getUseDuration(stack: ItemStack) = 72000
}

