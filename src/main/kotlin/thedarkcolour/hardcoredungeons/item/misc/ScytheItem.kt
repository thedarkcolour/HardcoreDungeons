package thedarkcolour.hardcoredungeons.item.misc

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

open class ScytheItem(
    tier: IItemTier,
    attackDamage: Int,
    attackSpeed: Float,
    builder: Properties,
) : TieredItem(tier, builder), IVanishable {
    // total attack damage as float
    private val attackDamage = attackDamage + tier.attackDamage
    // entity modifiers when held in primary hand
    private val attributeModifiers = ImmutableMultimap.of(
        Attributes.ATTACK_DAMAGE, AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", this.attackDamage.toDouble(), AttributeModifier.Operation.ADDITION),
        Attributes.ATTACK_SPEED, AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", attackSpeed.toDouble(), AttributeModifier.Operation.ADDITION),
    )

    override fun canPlayerBreakBlockWhileHolding(state: BlockState, worldIn: World, pos: BlockPos, player: PlayerEntity): Boolean {
        return !player.isCreative
    }

    override fun hitEntity(stack: ItemStack, target: LivingEntity, playerIn: LivingEntity): Boolean {
        if (playerIn is PlayerEntity) {
            val f2 = playerIn.getCooledAttackStrength(0.5f)

            // if the sweep should play
            // mostly from PlayerEntity for SwordItem with a few tweaks
            if (f2 > 0.7f && !playerIn.isSprinting && (playerIn.distanceWalkedModified - playerIn.prevDistanceWalkedModified < playerIn.aiMoveSpeed)) {
                for (living in playerIn.world.getEntitiesWithinAABB(LivingEntity::class.java, target.boundingBox.grow(1.5, 0.25, 1.5))) {
                    if (living != playerIn && living != target && !playerIn.isOnSameTeam(living) && (living !is ArmorStandEntity || !living.hasMarker()) && playerIn.getDistanceSq(living) < 9.0) {
                        living.applyKnockback(0.4f, MathHelper.sin(toDegrees(playerIn.rotationYaw)).toDouble(), (-MathHelper.cos(toDegrees(playerIn.rotationYaw))).toDouble())
                        living.attackEntityFrom(DamageSource.causePlayerDamage(playerIn), (1.0f + EnchantmentHelper.getSweepingDamageRatio(playerIn) * playerIn.getAttributeValue(Attributes.ATTACK_DAMAGE).toFloat()))
                    }
                }

                playerIn.world.playSound(null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, playerIn.soundCategory, 1.0f, 1.0f)
                playerIn.spawnSweepParticles()
            }
        }
        stack.damageItem(1, playerIn) { entity ->
            entity.sendBreakAnimation(EquipmentSlotType.MAINHAND)
        }
        return true
    }

    override fun onBlockDestroyed(
        stack: ItemStack,
        worldIn: World,
        state: BlockState,
        pos: BlockPos,
        entityLiving: LivingEntity,
    ): Boolean {
        if (state.getBlockHardness(worldIn, pos) != 0.0f) {
            stack.damageItem(2, entityLiving) { entity ->
                entity.sendBreakAnimation(EquipmentSlotType.MAINHAND)
            }
        }
        return true
    }

    override fun getAttributeModifiers(slot: EquipmentSlotType, stack: ItemStack): Multimap<Attribute, AttributeModifier> {
        return if (slot == EquipmentSlotType.MAINHAND) attributeModifiers else super.getAttributeModifiers(slot, stack)
    }
}