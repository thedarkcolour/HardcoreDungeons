package thedarkcolour.hardcoredungeons.entity.overworld.deer

import net.minecraft.entity.EntityType
import net.minecraft.entity.ILivingEntityData
import net.minecraft.entity.MobEntity
import net.minecraft.entity.SpawnReason
import net.minecraft.entity.ai.goal.Goal
import net.minecraft.entity.ai.goal.HurtByTargetGoal
import net.minecraft.entity.ai.goal.MeleeAttackGoal
import net.minecraft.entity.ai.goal.PanicGoal
import net.minecraft.item.ItemStack
import net.minecraft.nbt.CompoundNBT
import net.minecraft.potion.EffectInstance
import net.minecraft.potion.Effects
import net.minecraft.util.math.BlockPos
import net.minecraft.world.DifficultyInstance
import net.minecraft.world.IServerWorld
import net.minecraft.world.IWorldReader
import net.minecraft.world.World
import thedarkcolour.hardcoredungeons.registry.HBlocks

class CastletonDeerEntity(type: EntityType<CastletonDeerEntity>, worldIn: World) : DeerEntity(type, worldIn) {
    /**
     * Checks if the parameter is an item which this animal can be fed to breed it (wheat, carrots or seeds depending on
     * the animal type)
     */
    override fun isFood(stack: ItemStack): Boolean {
        return stack.item == HBlocks.WILD_BERROOK.item
    }

    override fun getDefaultType(): DeerType {
        return pickRandomPattern()
    }

    private fun pickRandomPattern(): DeerType {
        return if (random.nextBoolean()) DeerType.PURPLE_PATTERNS.random() else DeerType.BLUE_PATTERNS.random()
    }

    // fix deer not spawning on castleton grass
    override fun getWalkTargetValue(pos: BlockPos, level: IWorldReader): Float {
        return if (level.getBlockState(pos.below()).`is`(HBlocks.CASTLETON_SOIL.grass)) 10.0f else level.getBrightness(pos) - 0.5f
    }

    override fun finalizeSpawn(
        worldIn: IServerWorld,
        difficultyIn: DifficultyInstance,
        reason: SpawnReason,
        spawnDataIn: ILivingEntityData?,
        dataTag: CompoundNBT?
    ): ILivingEntityData {
        var group = spawnDataIn as? GroupData

        // todo move the pattern picking to this method
        if (group == null) {
            group = GroupData(deerType.isBlue, deerType.isAlpha)
        } else {
            // Match to the color of the group
            if (group.isBlue) {
                if (!deerType.isBlue) {
                    deerType = deerType.toBlue()
                }
            } else {
                if (deerType.isBlue) {
                    deerType = deerType.toPurple()
                }
            }

            if (!group.hasAlpha) {
                if (deerType.isBlue) {
                    deerType = DeerType.BLUE_ALPHA
                } else {
                    deerType = DeerType.PURPLE_ALPHA
                }
                group.hasAlpha = true
            } else if (deerType.isAlpha) {
                var deerType = deerType

                while (deerType.isAlpha) {
                    deerType = pickRandomPattern()
                }

                this.deerType = deerType
            }
        }

        // goals must be registered later because dataManager is null when goals are normally registered.
        // without dataManager it is impossible to distinguish between doe and stag
        if (deerType.isDoe) {
            goalSelector.addGoal(1, PanicGoal(this, 0.7))
            //goalSelector.addGoal(1, AlertHerd(this))
        } else if (deerType.isStag || deerType.isAlpha) {
            //goalSelector.addGoal(1, FightGoal(this))
            targetSelector.addGoal(1, HurtByTargetGoal(this))
            targetSelector.addGoal(2, MeleeAttackGoal(this, 0.7, false))

            if (deerType.isAlpha) {
                targetSelector.addGoal(3, EmpowerGoal(this))
            }
        }

        return group
    }

    fun isSameColor(other: MobEntity): Boolean {
        if (other !is CastletonDeerEntity) return false

        return this.deerType.isBlue == other.deerType.isBlue
    }

    class EmpowerGoal(val deer: DeerEntity) : Goal() {
        private var cooldown = 50

        override fun canUse(): Boolean {
            return cooldown-- <= 0
        }

        override fun start() {
            cooldown = 50

            deer.addEffect(EffectInstance(Effects.DAMAGE_RESISTANCE, 100, 2, false, false))

            for (ally in deer.level.getEntitiesOfClass(CastletonDeerEntity::class.java, deer.boundingBox.inflate(6.0))) {
                if (ally.isSameColor(deer)) {
                    ally.addEffect(EffectInstance(Effects.DAMAGE_BOOST, 100, 2))
                    ally.target = deer.target
                }
            }
        }
    }

    private class GroupData(var isBlue: Boolean, var hasAlpha: Boolean = false) : ILivingEntityData
}