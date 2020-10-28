package thedarkcolour.hardcoredungeons.entity.castleton.deer

import net.minecraft.entity.*
import net.minecraft.entity.ai.goal.*
import net.minecraft.entity.passive.AnimalEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.nbt.CompoundNBT
import net.minecraft.network.datasync.EntityDataManager
import net.minecraft.network.datasync.IDataSerializer
import net.minecraft.world.DifficultyInstance
import net.minecraft.world.IServerWorld
import net.minecraft.world.World
import net.minecraft.world.server.ServerWorld
import thedarkcolour.hardcoredungeons.registry.HDataSerializers
import thedarkcolour.hardcoredungeons.registry.HEntities
import thedarkcolour.hardcoredungeons.registry.HItems
import thedarkcolour.hardcoredungeons.util.dataParameter

class CastletonDeerEntity(type: EntityType<CastletonDeerEntity>, worldIn: World) : AnimalEntity(type, worldIn) {
    var pattern by dataParameter<DeerPattern>(DATA_TYPE)

    override fun registerData() {
        super.registerData()

        val p = pickRandomPattern()
        dataManager.register(DATA_TYPE, p)
    }

    /**
     * Checks if the parameter is an item which this animal can be fed to breed it (wheat, carrots or seeds depending on
     * the animal type)
     */
    override fun isBreedingItem(stack: ItemStack): Boolean {
        return stack.item == HItems.WILD_BERROOK
    }

    override fun registerGoals() {
        goalSelector.addGoal(0, WaterAvoidingRandomWalkingGoal(this, 0.4))
        goalSelector.addGoal(1, LookAtWithoutMovingGoal(this, PlayerEntity::class.java, 25.0f, 0.04f))
    }

    private fun pickRandomPattern(): DeerPattern {
        return when (rand.nextFloat()) {
            in 0.00f..0.24f -> DeerPattern.BLUE_EYED_DOE
            in 0.25f..0.49f -> DeerPattern.PURPLE_SPOTTED_STAG
            in 0.50f..0.74f -> DeerPattern.BLUE_SPOTTED_STAG
            else -> DeerPattern.BLUE_EYED_STAG
        }
    }

    override fun writeAdditional(compound: CompoundNBT) {
        super.writeAdditional(compound)
        compound.putString("Pattern", pattern.name)
    }

    override fun readAdditional(compound: CompoundNBT) {
        super.readAdditional(compound)
        pattern = try {
            DeerPattern.valueOf(compound.getString("Pattern"))
        } catch (e: IllegalArgumentException) {
            DeerPattern.BLUE_EYED_STAG
        }
    }

    override fun func_241840_a(worldIn: ServerWorld, parent: AgeableEntity): AgeableEntity? {
        val a = HEntities.CASTLETON_DEER.create(worldIn)!!
        a.pattern = pickRandomPattern()
        return a
    }

    override fun onInitialSpawn(
        worldIn: IServerWorld,
        difficultyIn: DifficultyInstance,
        reason: SpawnReason,
        spawnDataIn: ILivingEntityData?,
        dataTag: CompoundNBT?
    ): ILivingEntityData? {

        // goals must be registered later because dataManager is null when goals are normally registered.
        if (pattern.isFemale()) {
            goalSelector.addGoal(1, PanicGoal(this, 0.7))
            //goalSelector.addGoal(1, AlertHerd(this))
        } else if (pattern.isStag()) {
            //goalSelector.addGoal(1, FightGoal(this))
            targetSelector.addGoal(1, HurtByTargetGoal(this))
            targetSelector.addGoal(2, MeleeAttackGoal(this, 0.3, false))
        }

        return super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag)
    }

    fun isSameColor(goalOwner: MobEntity): Boolean {
        if (goalOwner !is CastletonDeerEntity) return false

        return goalOwner.pattern.isBlue() == goalOwner.pattern.isBlue()
    }

    companion object {
        @Suppress("UNCHECKED_CAST")
        private val DATA_TYPE = EntityDataManager.createKey(CastletonDeerEntity::class.java, HDataSerializers.DEER_PATTERN.serializer as IDataSerializer<DeerPattern>)
    }
}