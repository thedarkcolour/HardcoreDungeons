package thedarkcolour.hardcoredungeons.entity.misc.cheeky

import net.minecraft.entity.CreatureEntity
import net.minecraft.entity.EntityType
import net.minecraft.entity.ai.goal.LookAtGoal
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.world.World

@Suppress("SpellCheckingInspection")
class CheekyEntity(type: EntityType<out CreatureEntity>, worldIn: World) : CreatureEntity(type, worldIn) {
    var isSpeaking = true
    var speech = "aaaaaaaaaaaaa"

    override fun registerGoals() {
        goalSelector.addGoal(0, LookAtGoal(this, PlayerEntity::class.java, 26f, 1f))
    }
}