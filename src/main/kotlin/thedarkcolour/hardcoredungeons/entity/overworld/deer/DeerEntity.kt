package thedarkcolour.hardcoredungeons.entity.overworld.deer

import net.minecraft.entity.AgeableEntity
import net.minecraft.entity.EntityType
import net.minecraft.entity.ai.attributes.AttributeModifierMap
import net.minecraft.entity.ai.attributes.Attributes
import net.minecraft.entity.ai.goal.*
import net.minecraft.entity.passive.AnimalEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.item.crafting.Ingredient
import net.minecraft.nbt.CompoundNBT
import net.minecraft.network.datasync.DataSerializers
import net.minecraft.network.datasync.EntityDataManager
import net.minecraft.network.datasync.IDataSerializer
import net.minecraft.util.text.ITextComponent
import net.minecraft.world.World
import net.minecraft.world.server.ServerWorld
import net.minecraftforge.common.ForgeMod
import net.minecraftforge.common.Tags
import thedarkcolour.hardcoredungeons.registry.HDataSerializers
import thedarkcolour.hardcoredungeons.util.dataParameterDelegate
import kotlin.experimental.and
import kotlin.experimental.inv
import kotlin.experimental.or

open class DeerEntity(type: EntityType<out AnimalEntity>, worldIn: World) : AnimalEntity(type, worldIn) {
    var deerType by dataParameterDelegate<DeerType>(DEER_TYPE)
    var deerFlags by dataParameterDelegate<Byte>(DEER_FLAGS)

    override fun isBreedingItem(stack: ItemStack): Boolean {
        return stack.item == Items.WHEAT || stack.item.isIn(Tags.Items.MUSHROOMS)
    }

    override fun registerGoals() {
        goalSelector.addGoal(0, SwimGoal(this))
        goalSelector.addGoal(2, BreedGoal(this, 0.9))
        goalSelector.addGoal(3, TemptGoal(this, 0.9, false, Ingredient.fromTag(Tags.Items.MUSHROOMS)))
        goalSelector.addGoal(3, TemptGoal(this, 0.9, false, Ingredient.fromItems(Items.WHEAT)))
        goalSelector.addGoal(4, FollowParentGoal(this, 0.7))
        goalSelector.addGoal(5, WaterAvoidingRandomWalkingGoal(this, 0.4))
        goalSelector.addGoal(6, LookAtWithoutMovingGoal(this, PlayerEntity::class.java, 25.0f, 0.04f))
        goalSelector.addGoal(6, LookRandomlyGoal(this))

        //if (deerType.isDoe()) {
            goalSelector.addGoal(1, PanicGoal(this, 0.9)) // todo have the stags retaliate
        //}
    }

    override fun setCustomName(name: ITextComponent?) {
        super.setCustomName(name)
        setDeerFlag(IS_THEDARKCOLOUR, name?.unformattedComponentText?.toLowerCase() == "thedarkcolour")
    }

    override fun func_241840_a(worldIn: ServerWorld, parent: AgeableEntity): DeerEntity {
        val entity = type.create(worldIn) as DeerEntity
        entity.deerType = getDefaultType()
        entity.setIsTheDarkColour(isTheDarkColour() || (parent as DeerEntity).isTheDarkColour())
        return entity
    }

    override fun registerData() {
        super.registerData()
        dataManager.register(DEER_TYPE, getDefaultType())
        dataManager.register(DEER_FLAGS, 0)
    }

    fun isTheDarkColour(): Boolean {
        return getDeerFlag(IS_THEDARKCOLOUR)
    }

    fun setIsTheDarkColour(value: Boolean) {
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
    open fun getDefaultType() = if (rand.nextBoolean()) DeerType.FOREST_STAG else DeerType.FOREST_DOE

    override fun writeAdditional(compound: CompoundNBT) {
        super.writeAdditional(compound)
        compound.putString("Pattern", deerType.name)
        compound.putByte("Flags", deerFlags)
    }

    override fun readAdditional(compound: CompoundNBT) {
        super.readAdditional(compound)

        deerFlags = compound.getByte("Flags")

        deerType = try {
            DeerType.valueOf(compound.getString("Pattern"))
        } catch (e: IllegalArgumentException) {
            getDefaultType()
        }
    }

    companion object {
        @Suppress("UNCHECKED_CAST")
        private val DEER_TYPE = EntityDataManager.createKey(DeerEntity::class.java, HDataSerializers.DEER_TYPE.serializer as IDataSerializer<DeerType>)
        private val DEER_FLAGS = EntityDataManager.createKey(DeerEntity::class.java, DataSerializers.BYTE)

        // flags
        protected const val IS_THEDARKCOLOUR = 0x1

        val DEFAULT_ATTRIBUTES: AttributeModifierMap.MutableAttribute = AttributeModifierMap.createMutableAttribute()
            .createMutableAttribute(Attributes.MAX_HEALTH, 20.0)
            .createMutableAttribute(Attributes.KNOCKBACK_RESISTANCE, 0.2)
            .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.45)
            .createMutableAttribute(Attributes.ARMOR)
            .createMutableAttribute(Attributes.ARMOR_TOUGHNESS)
            .createMutableAttribute(ForgeMod.SWIM_SPEED.get())
            .createMutableAttribute(ForgeMod.NAMETAG_DISTANCE.get())
            .createMutableAttribute(ForgeMod.ENTITY_GRAVITY.get())
            .createMutableAttribute(Attributes.FOLLOW_RANGE, 16.0)
            .createMutableAttribute(Attributes.ATTACK_KNOCKBACK)
    }
}