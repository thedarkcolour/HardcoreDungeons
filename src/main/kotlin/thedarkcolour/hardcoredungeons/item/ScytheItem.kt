package thedarkcolour.hardcoredungeons.item

import com.google.common.collect.ImmutableMultimap
import com.google.common.collect.Multimap
import net.minecraft.core.BlockPos
import net.minecraft.sounds.SoundEvents
import net.minecraft.util.Mth
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.ai.attributes.Attribute
import net.minecraft.world.entity.ai.attributes.AttributeModifier
import net.minecraft.world.entity.ai.attributes.Attributes
import net.minecraft.world.entity.decoration.ArmorStand
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Tier
import net.minecraft.world.item.TieredItem
import net.minecraft.world.item.Vanishable
import net.minecraft.world.item.enchantment.EnchantmentHelper
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState
import thedarkcolour.hardcoredungeons.util.toDegrees

/**
 * Area of effect melee weapon.
 */
open class ScytheItem(
    tier: Tier,
    attackDamage: Int,
    attackSpeed: Float,
    builder: Properties,
) : TieredItem(tier, builder), Vanishable {
    // total attack damage as float
    private val attackDamage = attackDamage + tier.attackDamageBonus
    // entity modifiers when held in primary hand
    private val attributeModifiers = ImmutableMultimap.of(
        Attributes.ATTACK_DAMAGE, AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", this.attackDamage.toDouble(), AttributeModifier.Operation.ADDITION),
        Attributes.ATTACK_SPEED, AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", attackSpeed.toDouble(), AttributeModifier.Operation.ADDITION),
    )

    override fun canAttackBlock(state: BlockState, worldIn: Level, pos: BlockPos, player: Player): Boolean {
        return !player.isCreative
    }

    override fun hurtEnemy(stack: ItemStack, target: LivingEntity, playerIn: LivingEntity): Boolean {
        if (playerIn is Player) {
            val f2 = playerIn.getAttackStrengthScale(0.5f)

            // if the sweep should play
            // mostly from PlayerEntity for SwordItem with a few tweaks
            if (f2 > 0.05f && !playerIn.isSprinting && (playerIn.walkDist - playerIn.walkDistO < playerIn.speed)) {
                val level = playerIn.level()

                for (living in level.getEntitiesOfClass(LivingEntity::class.java, target.boundingBox.inflate(1.5, 0.25, 1.5))) {
                    if (living != playerIn && living != target && !playerIn.isAlliedTo(living) && (living !is ArmorStand || !living.isMarker) && playerIn.distanceToSqr(living) < 9.0) {
                        living.knockback(0.4, Mth.sin(toDegrees(playerIn.yRot)).toDouble(), (-Mth.cos(toDegrees(playerIn.yRot))).toDouble())
                        living.hurt(level.damageSources().playerAttack(playerIn), (1.0f + EnchantmentHelper.getSweepingDamageRatio(playerIn) * playerIn.getAttributeValue(Attributes.ATTACK_DAMAGE).toFloat()))
                    }
                }

                level.playSound(null, playerIn.x, playerIn.y, playerIn.z, SoundEvents.PLAYER_ATTACK_SWEEP, playerIn.soundSource, 1.0f, 1.0f)
                playerIn.sweepAttack()
            }
        }
        stack.hurtAndBreak(1, playerIn) { entity ->
            entity.broadcastBreakEvent(EquipmentSlot.MAINHAND)
        }
        return true
    }

    override fun mineBlock(
        stack: ItemStack,
        worldIn: Level,
        state: BlockState,
        pos: BlockPos,
        entityLiving: LivingEntity,
    ): Boolean {
        if (state.getDestroySpeed(worldIn, pos) != 0.0f) {
            stack.hurtAndBreak(2, entityLiving) { entity ->
                entity.broadcastBreakEvent(EquipmentSlot.MAINHAND)
            }
        }
        return true
    }

    override fun getAttributeModifiers(slot: EquipmentSlot, stack: ItemStack): Multimap<Attribute, AttributeModifier> {
        return if (slot == EquipmentSlot.MAINHAND) {
            attributeModifiers
        } else {
            super.getAttributeModifiers(slot, stack)
        }
    }
}