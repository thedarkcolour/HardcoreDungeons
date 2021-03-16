package thedarkcolour.hardcoredungeons.item.castleton

import net.minecraft.entity.LivingEntity
import net.minecraft.entity.passive.CowEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.ActionResultType
import net.minecraft.util.Hand
import thedarkcolour.hardcoredungeons.registry.HItems

// extra functionality hehehe
class ChaliceItem(properties: Properties) : Item(properties) {
    override fun itemInteractionForEntity(
        stack: ItemStack,
        playerIn: PlayerEntity,
        target: LivingEntity,
        hand: Hand,
    ): ActionResultType {
        if (target is CowEntity) {
            stack.shrink(1)
            playerIn.addItemStackToInventory(ItemStack(HItems.CUM_CHALICE))
            return ActionResultType.func_233537_a_(playerIn.world.isRemote)
        }
        
        return ActionResultType.PASS
    }
}