package thedarkcolour.hardcoredungeons.entity.overworld.deer

import net.minecraft.core.BlockPos
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.DifficultyInstance
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.effect.MobEffects
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.Mob
import net.minecraft.world.entity.MobSpawnType
import net.minecraft.world.entity.SpawnGroupData
import net.minecraft.world.entity.ai.goal.Goal
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal
import net.minecraft.world.entity.ai.goal.PanicGoal
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.level.Level
import net.minecraft.world.level.LevelReader
import net.minecraft.world.level.ServerLevelAccessor
import thedarkcolour.hardcoredungeons.registry.block.HBlocks

class CastletonDeerEntity(type: EntityType<CastletonDeerEntity>, worldIn: Level) : DeerEntity(type, worldIn) {
    private fun addGoals(deerType: DeerType) {
        // goals must be registered later because dataManager is null when goals are normally registered.
        // without dataManager it is impossible to distinguish between doe and stag
        if (deerType.isDoe) {
            goalSelector.addGoal(1, PanicGoal(this, 0.7))
        } else if (deerType.isStag || deerType.isAlpha) {
            targetSelector.addGoal(2, MeleeAttackGoal(this, 0.7, false))

            if (deerType.isAlpha) {
                targetSelector.addGoal(3, EmpowerGoal(this))
            }
        }
    }

    override fun isFood(stack: ItemStack): Boolean {
        return stack.item == HBlocks.WILD_BERROOK.item
    }

    override fun getTemptIngredient(): Ingredient {
        return Ingredient.of(HBlocks.WILD_BERROOK.item)
    }

    // fix deer not spawning on castleton grass during world gen, since the dimension is dark and animals spawn in light
    override fun getWalkTargetValue(pos: BlockPos, level: LevelReader): Float {
        return if (level.getBlockState(pos.below()).`is`(HBlocks.CASTLETON_SOIL.grass)) 10.0f else level.getPathfindingCostFromLightLevels(pos) - 0.5f
    }

    override fun getDefaultType(): DeerType {
        return pickRandomPattern()
    }

    private fun pickRandomPattern(): DeerType {
        return if (random.nextBoolean()) DeerType.PURPLE_PATTERNS.random() else DeerType.BLUE_PATTERNS.random()
    }

    fun isSameColor(other: Mob): Boolean {
        if (other !is CastletonDeerEntity) return false

        return this.deerType.isBlue == other.deerType.isBlue
    }

    override fun finalizeSpawn(
        worldIn: ServerLevelAccessor,
        difficultyIn: DifficultyInstance,
        reason: MobSpawnType,
        spawnDataIn: SpawnGroupData?,
        dataTag: CompoundTag?
    ): SpawnGroupData {
        var group = spawnDataIn as? GroupData
        var deerType = this.deerType

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
                deerType = if (deerType.isBlue) {
                    DeerType.BLUE_ALPHA
                } else {
                    DeerType.PURPLE_ALPHA
                }
                group.hasAlpha = true
            } else if (deerType.isAlpha) {
                while (deerType.isAlpha) {
                    deerType = pickRandomPattern()
                }
            }
        }

        this.deerType = deerType

        addGoals(deerType)

        return group
    }

    override fun readAdditionalSaveData(compound: CompoundTag) {
        super.readAdditionalSaveData(compound)

        // Re-add goals as they are cleared when NBT is read
        addGoals(deerType)
    }

    class EmpowerGoal(val deer: DeerEntity) : Goal() {
        private var cooldown = 0

        override fun canUse(): Boolean {
            return (deer.target?.isAlive == true) && cooldown-- <= 0
        }

        override fun start() {
            cooldown = 50

            deer.addEffect(MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 100, 2, false, false))

            for (ally in deer.level().getEntitiesOfClass(CastletonDeerEntity::class.java, deer.boundingBox.inflate(6.0))) {
                if (ally.isSameColor(deer)) {
                    ally.addEffect(MobEffectInstance(MobEffects.DAMAGE_BOOST, 100, 2))
                }
            }
        }
    }

    // Used to ensure a group has only one alpha and all of its members are the same color
    private class GroupData(var isBlue: Boolean, var hasAlpha: Boolean = false) : SpawnGroupData
}