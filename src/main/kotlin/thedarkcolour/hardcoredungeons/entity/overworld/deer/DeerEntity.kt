package thedarkcolour.hardcoredungeons.entity.overworld.deer

import net.minecraft.nbt.CompoundTag
import net.minecraft.network.chat.Component
import net.minecraft.network.syncher.EntityDataSerializers
import net.minecraft.network.syncher.SynchedEntityData
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.entity.AgeableMob
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.ai.attributes.AttributeSupplier
import net.minecraft.world.entity.ai.attributes.Attributes
import net.minecraft.world.entity.ai.goal.*
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal
import net.minecraft.world.entity.animal.Animal
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.level.Level
import net.minecraftforge.common.ForgeMod
import net.minecraftforge.common.Tags
import thedarkcolour.hardcoredungeons.registry.HDataSerializers
import thedarkcolour.hardcoredungeons.util.dataParameterDelegate
import java.util.*
import kotlin.experimental.and
import kotlin.experimental.inv
import kotlin.experimental.or

open class DeerEntity(type: EntityType<out Animal>, worldIn: Level) : Animal(type, worldIn) {
    var deerType by dataParameterDelegate<DeerType>(DEER_TYPE)
    var deerFlags by dataParameterDelegate<Byte>(DEER_FLAGS)

    override fun isFood(stack: ItemStack): Boolean {
        return stack.item == Items.WHEAT || stack.`is`(Tags.Items.MUSHROOMS)
    }

    /**
     * An ingredient to decide whether to follow a player holding an item
     */
    open fun getTemptIngredient(): Ingredient {
        return Ingredient.merge(listOf(Ingredient.of(Items.WHEAT), Ingredient.of(Tags.Items.MUSHROOMS)))
    }

    override fun registerGoals() {
        goalSelector.addGoal(0, FloatGoal(this))
        goalSelector.addGoal(0, PanicGoal(this, 1.1))
        goalSelector.addGoal(2, BreedGoal(this, 0.9))
        goalSelector.addGoal(3, TemptGoal(this, 0.9, getTemptIngredient(), false))
        goalSelector.addGoal(4, FollowParentGoal(this, 0.7))
        goalSelector.addGoal(5, WaterAvoidingRandomStrollGoal(this, 0.4))
        goalSelector.addGoal(6, LookAtPlayerGoal(this, Player::class.java, 12.0f))
        goalSelector.addGoal(6, RandomLookAroundGoal(this))

        targetSelector.addGoal(1, HurtByTargetGoal(this).setAlertOthers(DeerEntity::class.java))
    }

    override fun setCustomName(name: Component?) {
        super.setCustomName(name)
        setDeerFlag(IS_THEDARKCOLOUR, name?.string?.lowercase(Locale.getDefault()) == "thedarkcolour")
    }

    override fun getBreedOffspring(worldIn: ServerLevel, parent: AgeableMob): DeerEntity {
        val entity = type.create(worldIn) as DeerEntity
        entity.deerType = getDefaultType()
        entity.setIsMe(isMe() || (parent as DeerEntity).isMe())
        return entity
    }

    override fun defineSynchedData() {
        super.defineSynchedData()
        entityData.define(DEER_TYPE, getDefaultType())
        entityData.define(DEER_FLAGS, 0)
    }

    fun isMe(): Boolean {
        return getDeerFlag(IS_THEDARKCOLOUR)
    }

    private fun setIsMe(value: Boolean) {
        setDeerFlag(IS_THEDARKCOLOUR, value)
    }

    protected fun getDeerFlag(flag: Int): Boolean {
        return deerFlags and flag.toByte() != 0.toByte()
    }

    protected fun setDeerFlag(flag: Int, value: Boolean) {
        deerFlags = if (value) {
            deerFlags or flag.toByte()
        } else {
            deerFlags and flag.toByte().inv()
        }
    }

    /** The pattern chosen by default for new deer (natural spawning, spawn egg, /summon, etc.) */
    open fun getDefaultType() = if (random.nextBoolean()) DeerType.FOREST_STAG else DeerType.FOREST_DOE

    override fun addAdditionalSaveData(compound: CompoundTag) {
        super.addAdditionalSaveData(compound)
        compound.putString("Pattern", deerType.name)
        compound.putByte("Flags", deerFlags)
    }

    override fun readAdditionalSaveData(compound: CompoundTag) {
        super.readAdditionalSaveData(compound)

        deerFlags = compound.getByte("Flags")

        deerType = try {
            DeerType.valueOf(compound.getString("Pattern"))
        } catch (e: IllegalArgumentException) {
            getDefaultType()
        }
    }

    companion object {
        @Suppress("UNCHECKED_CAST")
        private val DEER_TYPE = SynchedEntityData.defineId(DeerEntity::class.java, HDataSerializers.DEER_TYPE)
        private val DEER_FLAGS = SynchedEntityData.defineId(DeerEntity::class.java, EntityDataSerializers.BYTE)

        // flags
        protected const val IS_THEDARKCOLOUR = 0x1

        val DEFAULT_ATTRIBUTES: AttributeSupplier.Builder = AttributeSupplier.builder()
            .add(Attributes.MAX_HEALTH, 20.0)
            .add(Attributes.KNOCKBACK_RESISTANCE, 0.2)
            .add(Attributes.MOVEMENT_SPEED, 0.45)
            .add(Attributes.ARMOR)
            .add(Attributes.ARMOR_TOUGHNESS)
            .add(ForgeMod.SWIM_SPEED.get())
            .add(ForgeMod.NAMETAG_DISTANCE.get())
            .add(ForgeMod.ENTITY_GRAVITY.get())
            .add(Attributes.FOLLOW_RANGE, 16.0)
            .add(Attributes.ATTACK_KNOCKBACK)
            .add(Attributes.ATTACK_DAMAGE, 2.0)
    }

    // If a doe is attacked, it should alert any stags nearby
    // If there are no stags nearby or a stag is attacked, then the doe retaliates
    //inner class PanicGoal {}
}