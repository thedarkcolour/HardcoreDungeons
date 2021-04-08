package thedarkcolour.hardcoredungeons.entity.overworld.deer

import net.minecraft.entity.EntityType
import net.minecraft.entity.ILivingEntityData
import net.minecraft.entity.MobEntity
import net.minecraft.entity.SpawnReason
import net.minecraft.entity.ai.goal.HurtByTargetGoal
import net.minecraft.entity.ai.goal.MeleeAttackGoal
import net.minecraft.entity.ai.goal.PanicGoal
import net.minecraft.item.ItemStack
import net.minecraft.nbt.CompoundNBT
import net.minecraft.world.DifficultyInstance
import net.minecraft.world.IServerWorld
import net.minecraft.world.World
import thedarkcolour.hardcoredungeons.registry.HItems

class CastletonDeerEntity(type: EntityType<CastletonDeerEntity>, worldIn: World) : DeerEntity(type, worldIn) {

    /**
     * Checks if the parameter is an item which this animal can be fed to breed it (wheat, carrots or seeds depending on
     * the animal type)
     */
    override fun isBreedingItem(stack: ItemStack): Boolean {
        return stack.item == HItems.WILD_BERROOK
    }

    override fun getDefaultType(): DeerType {
        return pickRandomPattern()
    }

    private fun pickRandomPattern(): DeerType {
        return when (rand.nextFloat()) {
            in 0.00f..0.24f -> DeerType.BLUE_EYED_DOE
            in 0.25f..0.49f -> DeerType.PURPLE_SPOTTED_STAG
            in 0.50f..0.74f -> DeerType.BLUE_SPOTTED_STAG
            else -> DeerType.BLUE_EYED_STAG
        }
    }

    override fun onInitialSpawn(
        worldIn: IServerWorld,
        difficultyIn: DifficultyInstance,
        reason: SpawnReason,
        spawnDataIn: ILivingEntityData?,
        dataTag: CompoundNBT?
    ): ILivingEntityData? {

        // goals must be registered later because dataManager is null when goals are normally registered.
        if (deerType.isDoe()) {
            goalSelector.addGoal(1, PanicGoal(this, 0.7))
            //goalSelector.addGoal(1, AlertHerd(this))
        } else if (deerType.isStag()) {
            //goalSelector.addGoal(1, FightGoal(this))
            targetSelector.addGoal(1, HurtByTargetGoal(this))
            targetSelector.addGoal(2, MeleeAttackGoal(this, 0.3, false))
        }

        return super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag)
    }

    fun isSameColor(goalOwner: MobEntity): Boolean {
        if (goalOwner !is CastletonDeerEntity) return false

        return goalOwner.deerType.isBlue() == goalOwner.deerType.isBlue()
    }
}