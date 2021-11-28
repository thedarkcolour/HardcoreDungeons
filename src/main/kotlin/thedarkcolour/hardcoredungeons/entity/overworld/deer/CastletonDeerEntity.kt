package thedarkcolour.hardcoredungeons.entity.overworld.deer

import net.minecraft.nbt.CompoundTag
import net.minecraft.world.DifficultyInstance
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.Mob
import net.minecraft.world.entity.MobSpawnType
import net.minecraft.world.entity.SpawnGroupData
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal
import net.minecraft.world.entity.ai.goal.PanicGoal
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.level.ServerLevelAccessor
import thedarkcolour.hardcoredungeons.registry.HItems

class CastletonDeerEntity(type: EntityType<CastletonDeerEntity>, level: Level) : DeerEntity(type, level) {
    override fun isFood(stack: ItemStack): Boolean {
        return stack.item == HItems.WILD_BERROOK
    }

    override fun getDefaultType(): DeerType {
        return pickRandomPattern()
    }

    private fun pickRandomPattern(): DeerType {
        return when (random.nextFloat()) {
            in 0.00f..0.24f -> DeerType.BLUE_EYED_DOE
            in 0.25f..0.49f -> DeerType.PURPLE_SPOTTED_STAG
            in 0.50f..0.74f -> DeerType.BLUE_SPOTTED_STAG
            else -> DeerType.BLUE_EYED_STAG
        }
    }

    override fun finalizeSpawn(
        worldIn: ServerLevelAccessor,
        difficultyIn: DifficultyInstance,
        reason: MobSpawnType,
        spawnDataIn: SpawnGroupData?,
        dataTag: CompoundTag?
    ): SpawnGroupData? {
        // goals must be registered later because dataManager is null when goals are normally registered.
        if (deerType.isDoe()) {
            goalSelector.addGoal(1, PanicGoal(this, 0.7))
            //goalSelector.addGoal(1, AlertHerd(this))
        } else if (deerType.isStag()) {
            //goalSelector.addGoal(1, FightGoal(this))
            targetSelector.addGoal(1, HurtByTargetGoal(this))
            targetSelector.addGoal(2, MeleeAttackGoal(this, 0.3, false))
        }

        return super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag)
    }

    fun isSameColor(goalOwner: Mob): Boolean {
        if (goalOwner !is CastletonDeerEntity) return false

        return goalOwner.deerType.isBlue() == goalOwner.deerType.isBlue()
    }
}