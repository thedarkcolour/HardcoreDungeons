package thedarkcolour.hardcoredungeons.item.castleton

import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.item.UseAction
import net.minecraft.util.ActionResult
import net.minecraft.util.DrinkHelper
import net.minecraft.util.Hand
import net.minecraft.world.level.Level

// yes i added a whole new item for this :sunglasses:
class CumChaliceItem(properties: Properties) : Item(properties) {
    override fun getUseAnimation(stack: ItemStack): UseAction {
        return UseAction.DRINK
    }

    override fun getUseDuration(stack: ItemStack): Int {
        return 40
    }

    override fun use(worldIn: World, playerIn: PlayerEntity, handIn: Hand): ActionResult<ItemStack> {
        return DrinkHelper.useDrink(worldIn, playerIn, handIn)
    }
}