package thedarkcolour.hardcoredungeons.item

import com.google.common.collect.ImmutableMultimap
import com.google.common.collect.Multimap
import net.minecraft.block.BlockState
import net.minecraft.enchantment.EnchantmentHelper
import net.minecraft.enchantment.IVanishable
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.ai.attributes.Attribute
import net.minecraft.entity.ai.attributes.AttributeModifier
import net.minecraft.entity.ai.attributes.Attributes
import net.minecraft.entity.item.ArmorStandEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.inventory.EquipmentSlotType
import net.minecraft.item.IItemTier
import net.minecraft.item.ItemStack
import net.minecraft.item.TieredItem
import net.minecraft.util.DamageSource
import net.minecraft.util.SoundEvents
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.MathHelper
import net.minecraft.world.World
import thedarkcolour.hardcoredungeons.util.toDegrees

/**
 * Area of effect melee weapon.
 */
open class ScytheItem(
    tier: IItemTier,
    attackDamage: Int,
    attackSpeed: Float,
    builder: Properties,
) : TieredItem(tier, builder), IVanishable {
    // total attack damage as float
    private val attackDamage = attackDamage + tier.attackDamageBonus
    // entity modifiers when held in primary hand
    private val attributeModifiers = ImmutableMultimap.of(
        Attributes.ATTACK_DAMAGE, AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", this.attackDamage.toDouble(), AttributeModifier.Operation.ADDITION),
        Attributes.ATTACK_SPEED, AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", attackSpeed.toDouble(), AttributeModifier.Operation.ADDITION),
    )

    override fun canAttackBlock(state: BlockState, worldIn: World, pos: BlockPos, player: PlayerEntity): Boolean {
        return !player.isCreative
    }

    override fun hurtEnemy(stack: ItemStack, target: LivingEntity, playerIn: LivingEntity): Boolean {
        if (playerIn is PlayerEntity) {
            val f2 = playerIn.getAttackStrengthScale(0.5f)

            // if the sweep should play
            // mostly from PlayerEntity for SwordItem with a few tweaks
            if (f2 > 0.05f && !playerIn.isSprinting && (playerIn.walkDist - playerIn.walkDistO < playerIn.speed)) {
                for (living in playerIn.level.getEntitiesOfClass(LivingEntity::class.java, target.boundingBox.inflate(1.5, 0.25, 1.5))) {
                    if (living != playerIn && living != target && !playerIn.isAlliedTo(living) && (living !is ArmorStandEntity || !living.isMarker) && playerIn.distanceToSqr(living) < 9.0) {
                        living.knockback(0.4f, MathHelper.sin(toDegrees(playerIn.yRot)).toDouble(), (-MathHelper.cos(toDegrees(playerIn.yRot))).toDouble())
                        living.hurt(DamageSource.playerAttack(playerIn), (1.0f + EnchantmentHelper.getSweepingDamageRatio(playerIn) * playerIn.getAttributeValue(Attributes.ATTACK_DAMAGE).toFloat()))
                    }
                }

                playerIn.level.playSound(null, playerIn.x, playerIn.y, playerIn.z, SoundEvents.PLAYER_ATTACK_SWEEP, playerIn.soundSource, 1.0f, 1.0f)
                playerIn.sweepAttack()
            }
        }
        stack.hurtAndBreak(1, playerIn) { entity ->
            entity.broadcastBreakEvent(EquipmentSlotType.MAINHAND)
        }
        return true
    }

    override fun mineBlock(
        stack: ItemStack,
        worldIn: World,
        state: BlockState,
        pos: BlockPos,
        entityLiving: LivingEntity,
    ): Boolean {
        if (state.getDestroySpeed(worldIn, pos) != 0.0f) {
            stack.hurtAndBreak(2, entityLiving) { entity ->
                entity.broadcastBreakEvent(EquipmentSlotType.MAINHAND)
            }
        }
        return true
    }

    override fun getAttributeModifiers(slot: EquipmentSlotType, stack: ItemStack): Multimap<Attribute, AttributeModifier> {
        return if (slot == EquipmentSlotType.MAINHAND) attributeModifiers else super.getAttributeModifiers(slot, stack)
    }
}