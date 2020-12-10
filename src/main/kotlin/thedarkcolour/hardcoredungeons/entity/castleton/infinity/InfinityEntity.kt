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

// DO NOT USE MY NAME IN VAIN !!!!
class InfinityEntity(entityType: EntityType<out LivingEntity>, worldIn: World) : LivingEntity(entityType, worldIn) {
    /** Whether the boss is still awaiting a player to fight */
    private var isIdle = true

    /** The **single** player this boss is fighting */
    private var engagedPlayer: PlayerEntity? = null

    override fun getArmorInventoryList(): Iterable<ItemStack> {
        return emptyList()
    }

    override fun setItemStackToSlot(slotIn: EquipmentSlotType, stack: ItemStack) = Unit

    override fun getItemStackFromSlot(slotIn: EquipmentSlotType): ItemStack = ItemStack.EMPTY

    override fun getPrimaryHand(): HandSide {
        return HandSide.LEFT
    }

    override fun attackEntityFrom(source: DamageSource, damage: Float): Boolean {
        return if (source.trueSource != source.immediateSource) {
            false
        } else if (source is PlayerEntity && source !is FakePlayer && engagedPlayer == source) {
            // all hits deal one damage
            damageEntity(source, 0.5f)
            true
        } else false
    }

    /**
     * Find the trajectory from the boss to the player
     */
    fun getTrajectory(target: PlayerEntity): Vector3d {
        return target.positionVec.subtract(positionVec)
    }

    open class InfinityGoal(val infinity: InfinityEntity) : Goal() {
        override fun shouldExecute(): Boolean {
            return !infinity.isIdle && infinity.engagedPlayer != null
        }
    }

    companion object {
        val ATTRIBUTES: AttributeModifierMap.MutableAttribute = AttributeModifierMap.createMutableAttribute()
            .createMutableAttribute(Attributes.MAX_HEALTH, 300.0)
            .createMutableAttribute(Attributes.KNOCKBACK_RESISTANCE, 1.0)
            .createMutableAttribute(Attributes.MOVEMENT_SPEED)
            .createMutableAttribute(Attributes.ARMOR)
            .createMutableAttribute(Attributes.ARMOR_TOUGHNESS)
            .createMutableAttribute(ForgeMod.SWIM_SPEED.get())
            .createMutableAttribute(ForgeMod.NAMETAG_DISTANCE.get())
            .createMutableAttribute(ForgeMod.ENTITY_GRAVITY.get())
            .createMutableAttribute(Attributes.FOLLOW_RANGE, 16.0)
            .createMutableAttribute(Attributes.ATTACK_KNOCKBACK)
    }

    class AwaitPlayerAI {
        // todo detect player and close off temple
    }

    class ShootBlackStars(infinity: InfinityEntity, val target: PlayerEntity) : InfinityGoal(infinity) {
        /** The duration of this task in ticks */
        var progress = 0

        override fun shouldContinueExecuting(): Boolean {
            return shouldExecute() && progress++ < 200
        }

        override fun resetTask() {
            progress = 0
        }

        override fun tick() {
            if (progress % 4 == 0) {
                val entity = HEntities.BLACK_STAR(infinity.world)
                val trajectory = infinity.getTrajectory(target)

                // todo have an offset so we don't spawn stars inside of the boss
                entity.shoot(infinity, infinity.positionVec, trajectory.mul(0.1, 0.1, 0.1))
            }
        }
    }

    class CrossAttack()
}