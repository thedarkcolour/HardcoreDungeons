package thedarkcolour.hardcoredungeons.dimension.castleton.ai

import net.minecraft.entity.ai.goal.Goal
import thedarkcolour.hardcoredungeons.entity.castleton.deer.CastletonDeerEntity

/**
 * Fight goal for a castleton deer entity.
 */
class FightGoal(deer: CastletonDeerEntity) : Goal() {
    /**
     * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
     * method as well.
     */
    override fun shouldExecute(): Boolean {
        return false
    }
}