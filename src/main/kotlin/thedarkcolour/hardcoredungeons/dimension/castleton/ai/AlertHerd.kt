package thedarkcolour.hardcoredungeons.dimension.castleton.ai

import net.minecraft.entity.CreatureEntity
import net.minecraft.entity.ai.goal.HurtByTargetGoal
import net.minecraft.util.math.AxisAlignedBB
import thedarkcolour.hardcoredungeons.entity.castleton.deer.CastletonDeerEntity

class AlertHerd(creatureIn: CreatureEntity) : HurtByTargetGoal(creatureIn) {
    /**
     * Execute a one shot task or start executing a continuous task
     */
    override fun startExecuting() {
        alertOthers()
    }

    override fun alertOthers() {
        val distance = targetDistance
        val nearbyEntities = goalOwner.world.getLoadedEntitiesWithinAABB(CastletonDeerEntity::class.java, AxisAlignedBB(goalOwner.posX, goalOwner.posY, goalOwner.posZ, goalOwner.posX + 1.0, goalOwner.posY + 1.0, goalOwner.posZ + 1.0).grow(distance, 10.0, distance))

        val revengeTarget = goalOwner.revengeTarget ?: return

        goalOwner.attackTarget = null

        for (mob in nearbyEntities) {
            if (goalOwner != mob && mob.attackTarget == null && !mob.isOnSameTeam(revengeTarget)) {
                if (mob.isSameColor(goalOwner) && (mob.pattern.isStag() || mob.pattern.isAlpha())) {
                    mob.attackTarget = revengeTarget
                }
            }
        }
    }
}