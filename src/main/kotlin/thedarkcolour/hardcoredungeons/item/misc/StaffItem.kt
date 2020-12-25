package thedarkcolour.hardcoredungeons.item.misc

import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.UseAction
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.world.World
import thedarkcolour.hardcoredungeons.entity.projectile.magic.MagicBoltEntity
import thedarkcolour.hardcoredungeons.registry.HEntities

class StaffItem(properties: Properties) : Item(properties) {
    /**
     * How long it takes to use or consume an item
     */
    override fun getUseDuration(stack: ItemStack) = 25

    /**
     * returns the action that specifies what animation to play when the items is being used
     */
    override fun getUseAction(stack: ItemStack) = UseAction.BLOCK

    /**
     * Called to trigger the item's "innate" right click behavior.
     * To handle when this item is used on a Block, see [onItemUse]
     */
    override fun onItemRightClick(worldIn: World, playerIn: PlayerEntity, hand: Hand): ActionResult<ItemStack> {
        val stack = playerIn.getHeldItem(hand)
        playerIn.activeHand = hand
        return ActionResult.resultConsume(stack)
    }

    /**
     * Called when the player finishes using this Item (E.g. finishes eating.). Not called when the player stops using
     * the Item before the action is complete.
     */
    override fun onItemUseFinish(stack: ItemStack, worldIn: World, playerIn: LivingEntity): ItemStack {
        if (!worldIn.isRemote) {
            val vec = playerIn.lookVec
            val magic = MagicBoltEntity(HEntities.MAGIC_BOLT, worldIn)

            magic.shoot(playerIn, playerIn.posX, playerIn.posYEye - 0.1, playerIn.posZ, vec.x, vec.y, vec.z)
        }

        return stack
    }

    /**
     * Return the enchantability factor of the item, most of the time is based on material.
     */
    override fun getItemEnchantability() = 1
}