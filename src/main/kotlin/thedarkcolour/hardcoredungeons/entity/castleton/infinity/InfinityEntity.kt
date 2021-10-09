package thedarkcolour.hardcoredungeons.entity.castleton.infinity

import net.minecraft.entity.EntityType
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.ai.attributes.AttributeModifierMap
import net.minecraft.entity.ai.attributes.Attributes
import net.minecraft.entity.ai.goal.Goal
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.inventory.EquipmentSlotType
import net.minecraft.item.ItemStack
import net.minecraft.util.DamageSource
import net.minecraft.util.HandSide
import net.minecraft.util.math.vector.Vector3d
import net.minecraft.world.World
import net.minecraftforge.common.ForgeMod
import net.minecraftforge.common.util.FakePlayer
import thedarkcolour.hardcoredungeons.registry.HEntities

class InfinityEntity(entityType: EntityType<out LivingEntity>, worldIn: World) : LivingEntity(entityType, worldIn) {
    /** Whether the boss is still awaiting a player to fight */
    private var isIdle = true

    /** The **single** player this boss is fighting */
    private var engagedPlayer: PlayerEntity? = null

    override fun getArmorSlots(): Iterable<ItemStack> {
        return emptyList()
    }

    override fun setItemSlot(slotIn: EquipmentSlotType, stack: ItemStack) = Unit

    override fun getItemBySlot(slotIn: EquipmentSlotType): ItemStack = ItemStack.EMPTY

    override fun getMainArm(): HandSide {
        return HandSide.LEFT
    }

    override fun hurt(source: DamageSource, damage: Float): Boolean {
        return if (source.entity != source.directEntity) {
            false
        } else if (source is PlayerEntity && source !is FakePlayer && engagedPlayer == source) {
            // all hits deal one damage
            actuallyHurt(source, 0.5f)
            true
        } else false
    }

    /**
     * Find the trajectory from the boss to the player
     */
    fun getTrajectory(target: PlayerEntity): Vector3d {
        return target.position().subtract(this.position())
    }

    open class InfinityGoal(val infinity: InfinityEntity) : Goal() {
        override fun canUse(): Boolean {
            return !infinity.isIdle && infinity.engagedPlayer != null
        }
    }

    companion object {
        val ATTRIBUTES: AttributeModifierMap.MutableAttribute = AttributeModifierMap.builder()
            .add(Attributes.MAX_HEALTH, 300.0)
            .add(Attributes.KNOCKBACK_RESISTANCE, 1.0)
            .add(Attributes.MOVEMENT_SPEED)
            .add(Attributes.ARMOR)
            .add(Attributes.ARMOR_TOUGHNESS)
            .add(ForgeMod.SWIM_SPEED.get())
            .add(ForgeMod.NAMETAG_DISTANCE.get())
            .add(ForgeMod.ENTITY_GRAVITY.get())
            .add(Attributes.FOLLOW_RANGE, 16.0)
            .add(Attributes.ATTACK_KNOCKBACK)
    }

    class AwaitPlayerAI {
        // todo detect player and close off temple
    }

    class ShootBlackStars(infinity: InfinityEntity, val target: PlayerEntity) : InfinityGoal(infinity) {
        /** The duration of this task in ticks */
        var progress = 0

        override fun canContinueToUse(): Boolean {
            return canUse() && progress++ < 200
        }

        override fun stop() {
            progress = 0
        }

        override fun tick() {
            if (progress % 4 == 0) {
                val entity = HEntities.BLACK_STAR(infinity.level)
                val trajectory = infinity.getTrajectory(target)

                // todo have an offset so we don't spawn stars inside of the boss
                entity.shoot(infinity, infinity.position(), trajectory.multiply(0.1, 0.1, 0.1))
            }
        }
    }

    class CrossAttack()
}