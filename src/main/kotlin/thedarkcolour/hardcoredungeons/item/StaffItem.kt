package thedarkcolour.hardcoredungeons.item

import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.UseAnim
import net.minecraft.world.level.Level
import thedarkcolour.hardcoredungeons.entity.projectile.magic.MagicBoltEntity
import thedarkcolour.hardcoredungeons.registry.HEntities

class StaffItem(properties: Properties) : Item(properties) {
    override fun getUseDuration(stack: ItemStack) = 25
    override fun getUseAnimation(stack: ItemStack) = UseAnim.BLOCK

    override fun use(worldIn: Level, playerIn: Player, hand: InteractionHand): InteractionResultHolder<ItemStack> {
        val stack = playerIn.getItemInHand(hand)
        playerIn.startUsingItem(hand)
        return InteractionResultHolder.consume(stack)
    }

    override fun finishUsingItem(stack: ItemStack, worldIn: Level, entity: LivingEntity): ItemStack {
        if (!worldIn.isClientSide) {
            val vec = entity.getViewVector(1.0f)
            val magic = MagicBoltEntity(HEntities.MAGIC_BOLT, worldIn)

            magic.shoot(entity, entity.x, entity.eyeY - 0.1, entity.z, vec.x, vec.y, vec.z)
        }

        return stack
    }

    override fun getEnchantmentValue() = 1
}