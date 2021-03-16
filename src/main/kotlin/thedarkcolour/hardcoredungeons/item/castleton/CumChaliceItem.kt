package thedarkcolour.hardcoredungeons.item.castleton

import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.UseAction
import net.minecraft.util.ActionResult
import net.minecraft.util.DrinkHelper
import net.minecraft.util.Hand
import net.minecraft.world.World

// yes i added a whole new item for this :sunglasses:
class CumChaliceItem(properties: Properties) : Item(properties) {
    override fun getUseAction(stack: ItemStack): UseAction {
        return UseAction.DRINK
    }

    override fun getUseDuration(stack: ItemStack): Int {
        return 40
    }

    override fun onItemRightClick(worldIn: World, playerIn: PlayerEntity, handIn: Hand): ActionResult<ItemStack> {
        return DrinkHelper.startDrinking(worldIn, playerIn, handIn)
    }
}