package thedarkcolour.hardcoredungeons.entity.overworld.deer

import net.minecraft.entity.AgeableEntity
import net.minecraft.entity.EntityType
import net.minecraft.entity.ai.attributes.AttributeModifierMap
import net.minecraft.entity.ai.attributes.Attributes
import net.minecraft.entity.ai.goal.LookAtWithoutMovingGoal
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal
import net.minecraft.entity.passive.AnimalEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.nbt.CompoundNBT
import net.minecraft.network.datasync.EntityDataManager
import net.minecraft.network.datasync.IDataSerializer
import net.minecraft.world.World
import net.minecraft.world.server.ServerWorld
import net.minecraftforge.common.ForgeMod
import thedarkcolour.hardcoredungeons.entity.castleton.deer.CastletonDeerEntity
import thedarkcolour.hardcoredungeons.entity.castleton.deer.DeerPattern
import thedarkcolour.hardcoredungeons.registry.HDataSerializers
import thedarkcolour.hardcoredungeons.util.dataParameter

open class DeerEntity(type: EntityType<out AnimalEntity>, worldIn: World) : AnimalEntity(type, worldIn) {
    var pattern by dataParameter<DeerPattern>(DATA_TYPE)

    override fun registerGoals() {
        goalSelector.addGoal(0, WaterAvoidingRandomWalkingGoal(this, 0.4))
        goalSelector.addGoal(1, LookAtWithoutMovingGoal(this, PlayerEntity::class.java, 25.0f, 0.04f))
    }

    override fun func_241840_a(worldIn: ServerWorld, parent: AgeableEntity): DeerEntity {
        val entity = type.create(worldIn) as DeerEntity
        entity.pattern = getDefaultPattern()
        return entity
    }

    override fun registerData() {
        super.registerData()
        dataManager.register(DATA_TYPE, getDefaultPattern())
    }

    /** The pattern chosen by default for newborn deer */
    open fun getDefaultPattern() = DeerPattern.FOREST_STAG

    override fun writeAdditional(compound: CompoundNBT) {
        super.writeAdditional(compound)
        compound.putString("Pattern", pattern.name)
    }

    override fun readAdditional(compound: CompoundNBT) {
        super.readAdditional(compound)
        pattern = try {
            DeerPattern.valueOf(compound.getString("Pattern"))
        } catch (e: IllegalArgumentException) {
            getDefaultPattern()
        }
    }

    companion object {
        @Suppress("UNCHECKED_CAST")
        private val DATA_TYPE = EntityDataManager.createKey(CastletonDeerEntity::class.java, HDataSerializers.DEER_PATTERN.serializer as IDataSerializer<DeerPattern>)

        val DEFAULT_ATTRIBUTES: AttributeModifierMap.MutableAttribute = AttributeModifierMap.createMutableAttribute()
            .createMutableAttribute(Attributes.MAX_HEALTH, 20.0)
            .createMutableAttribute(Attributes.KNOCKBACK_RESISTANCE, 0.2)
            .createMutableAttribute(Attributes.MOVEMENT_SPEED)
            .createMutableAttribute(Attributes.ARMOR)
            .createMutableAttribute(Attributes.ARMOR_TOUGHNESS)
            .createMutableAttribute(ForgeMod.SWIM_SPEED.get())
            .createMutableAttribute(ForgeMod.NAMETAG_DISTANCE.get())
            .createMutableAttribute(ForgeMod.ENTITY_GRAVITY.get())
            .createMutableAttribute(Attributes.FOLLOW_RANGE, 16.0)
            .createMutableAttribute(Attributes.ATTACK_KNOCKBACK)
    }
}