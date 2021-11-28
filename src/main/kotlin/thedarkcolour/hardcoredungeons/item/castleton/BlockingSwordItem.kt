package thedarkcolour.hardcoredungeons.item.castleton

import net.minecraft.entity.player.PlayerEntity
import net.minecraft.world.item.ItemStack
import net.minecraft.item.ItemTier
import net.minecraft.item.SwordItem
import net.minecraft.item.UseAction
import net.minecraft.util.ActionResult
import net.minecraft.util.ActionResultType
import net.minecraft.util.Hand
import net.minecraft.world.level.Level

class BlockingSwordItem(properties: Properties) : SwordItem(ItemTier.GOLD, 9, -2.4f, properties) {
    override fun use(worldIn: World, playerIn: PlayerEntity, handIn: Hand): ActionResult<ItemStack> {
        val stack = playerIn.getItemInHand(handIn)
        playerIn.startUsingItem(handIn)
        return ActionResult(ActionResultType.SUCCESS, stack)
    }

    override fun getUseAnimation(stack: ItemStack) = UseAction.BLOCK

    override fun getUseDuration(stack: ItemStack) = 72000
}

