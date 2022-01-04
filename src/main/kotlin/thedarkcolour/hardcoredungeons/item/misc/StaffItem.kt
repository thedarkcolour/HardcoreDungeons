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
    override fun getUseDuration(stack: ItemStack) = 25
    override fun getUseAnimation(stack: ItemStack) = UseAction.BLOCK

    override fun use(worldIn: World, playerIn: PlayerEntity, hand: Hand): ActionResult<ItemStack> {
        val stack = playerIn.getItemInHand(hand)
        playerIn.startUsingItem(hand)
        return ActionResult.consume(stack)
    }

    override fun finishUsingItem(stack: ItemStack, worldIn: World, entity: LivingEntity): ItemStack {
        if (!worldIn.isClientSide) {
            val vec = entity.getViewVector(1.0f)
            val magic = MagicBoltEntity(HEntities.MAGIC_BOLT, worldIn)

            magic.shoot(entity, entity.x, entity.eyeY - 0.1, entity.z, vec.x, vec.y, vec.z)
        }

        return stack
    }

    /**
     * Return the enchantability factor of the item, most of the time is based on material.
     */
    override fun getEnchantmentValue() = 1
}