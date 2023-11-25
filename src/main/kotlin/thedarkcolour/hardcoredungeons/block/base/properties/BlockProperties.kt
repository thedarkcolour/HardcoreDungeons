@file:Suppress("MemberVisibilityCanBePrivate")

package thedarkcolour.hardcoredungeons.block.base.properties

import net.minecraft.tags.TagKey
import net.minecraft.world.item.DyeColor
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.SoundType
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.block.state.BlockBehaviour.OffsetType
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.properties.BlockSetType
import net.minecraft.world.level.material.MapColor
import net.minecraft.world.level.material.PushReaction
import net.minecraft.world.phys.shapes.VoxelShape
import net.minecraftforge.data.loading.DatagenModLoader
import thedarkcolour.hardcoredungeons.block.base.HBlock
import thedarkcolour.hardcoredungeons.block.base.properties.BlockProperties.Factory
import java.util.function.ToIntFunction

/**
 * Wraps the Block.Properties class to make it extensible.
 *
 * The constructor of this class should never be public. Instead of a
 * public constructor, the extending class should have a companion object
 * extending [Factory] to create instances of this class.
 *
 * The extra properties not provided by Vanilla are only guaranteed to work
 * for the accompanying [HBlock] class. If the use of these properties is needed
 * for blocks that do not extend [HBlock], implementing them is simple.
 *
 * @author thedarkcolour
 */
@Suppress("UNCHECKED_CAST")
abstract class BlockProperties<T : BlockProperties<T>> protected constructor() {
    /** The vanilla representation of this [BlockProperties] instance. */
    private lateinit var internal: BlockBehaviour.Properties
    /** The VoxelShape shape that this block uses for collision and selection boxes. */
    private var shape: VoxelShape? = null
    /** The enchantment power bonus that this block should give. */
    private var enchantmentPower = 0.0f
    private var flammable = false
    /** The BlockSetType instance which makes passing it to different methods easier */
    private var blockSetType: BlockSetType? = null

    /**
     * Set this block's collision shape to empty.
     * Yes, Mojang made a typo in "collission"
     */
    fun noCollision(): T {
        internal.noCollission()
        return this as T
    }

    /**
     * Set this block to be non-solid so that other blocks can be seen behind it.
     * Think leaves, slab, fence, door.
     */
    fun noOcclusion(): T {
        internal.noOcclusion()
        return this as T
    }

    /**
     * Set the slipperiness of this block.
     */
    fun friction(slipperinessIn: Float): T {
        internal.friction(slipperinessIn)
        return this as T
    }

    /**
     * Set the movement modifier for entities that walk on this block.
     */
    fun speedFactor(movementModifier: Float): T {
        internal.speedFactor(movementModifier)
        return this as T
    }

    /**
     * Set the jump modifier for entities that jump while on top of this block.
     */
    fun jumpFactor(jumpModifier: Float): T {
        internal.jumpFactor(jumpModifier)
        return this as T
    }

    /**
     * Set the sound that this block makes when walked on, fallen on, mined, broken, or placed.
     */
    fun sound(soundTypeIn: SoundType): T {
        internal.sound(soundTypeIn)
        return this as T
    }

    /**
     * Set the light value emitted by this block.
     */
    fun lightLevel(light: Int): T {
        internal.lightLevel { light }
        return this as T
    }

    fun lightLevel(light: ToIntFunction<BlockState>): T {
        internal.lightLevel(light)
        return this as T
    }

    /**
     * Set the hardness and resistance for this block.
     */
    fun strength(hardnessIn: Float, resistanceIn: Float): T {
        internal.strength(hardnessIn, resistanceIn)
        return this as T
    }

    /**
     * Set this block to drop instantly and to never survive explosions.
     */
    fun instabreak(): T {
        return strength(0.0f, 0.0f)
    }

    /**
     * Unbreakable and unmovable block
     */
    fun indestructible(): T {
        pushReaction(PushReaction.BLOCK)
        return strength(-1.0f, 3600000.0f)
    }

    /**
     * Set the hardness and resistance for this block.
     */
    fun strength(hardnessAndResistance: Float): T {
        internal.strength(hardnessAndResistance, hardnessAndResistance)
        return this as T
    }

    /**
     * Set this block to receive random ticks.
     */
    fun randomTicks(): T {
        internal.randomTicks()
        return this as T
    }

    /**
     * Set the BlockState for this block to not cache its BlockState properties.
     * Only used by Shulker Boxes for determining whether they are solid.
     */
    fun dynamicShape(): T {
        internal.dynamicShape()
        return this as T
    }

    /**
     * Set the harvest level for this block.
     */
    fun harvestLevel(harvestLevel: TagKey<Block>): T {
        if (DatagenModLoader.isRunningDataGen()) {

        }
        return this as T
    }

    /**
     * Set the harvest tool for this block.
     */
    fun harvestTool(harvestTool: TagKey<Block>): T {

        return this as T
    }

    /**
     * Set the loot table of this block to empty.
     */
    fun noDrops(): T {
        internal.noLootTable()
        return this as T
    }

    /**
     * Set the loot table this block uses to be the same as another block.
     */
    fun lootFrom(blockIn: () -> Block): T {
        internal.lootFrom(blockIn)
        return this as T
    }

    fun air(): T {
        internal.air()
        return this as T
    }

    fun requiresCorrectToolForDrops(): T {
        internal.requiresCorrectToolForDrops()
        return this as T
    }

    /**
     * Set the shape this block uses for collision and selection boxes.
     */
    fun shape(shape: VoxelShape): T {
        this.shape = shape
        return this as T
    }

    /**
     * Sets the shape to use the same shape from the specified block.
     */
    fun shapeFrom(block: HBlock): T {
        this.shape = block.shape
        return this as T
    }

    /**
     * Returns the shape of this block.
     */
    fun getShape(): VoxelShape? {
        return shape
    }

    /**
     * Set the enchantment power for this block.
     */
    fun enchantmentPower(enchantmentPower: Float): T {
        this.enchantmentPower = enchantmentPower
        return this as T
    }

    /**
     * Returns the enchantment power bonus of this block.
     */
    fun getEnchantmentPower(): Float {
        return enchantmentPower
    }

    fun pushReaction(pushReaction: PushReaction): T {
        this.internal.pushReaction(pushReaction)
        return this as T
    }

    fun flammable(): T {
        flammable = true
        return this as T
    }

    fun getFlammable(): Boolean {
        return flammable
    }

    fun blockSetType(blockSetType: BlockSetType): T {
        this.blockSetType = blockSetType
        return this as T
    }

    fun getBlockSetType(): BlockSetType? {
        return this.blockSetType
    }

    fun offsetType(offsetType: OffsetType) {
        internal.offsetType(offsetType)
    }

    /**
     * Returns a Block.Properties for use in vanilla blocks.
     */
    fun build(): BlockBehaviour.Properties {
        return internal
    }

    /**
     * The factory that should be a companion object of
     * the implementing [BlockProperties] class
     */
    abstract fun getFactory(): Factory<T>

    /**
     * The base factory class for constructing new instances of properties.
     *
     * Implement in the companion object of your properties class.
     * This is meant to be a factory
     *
     * @author thedarkcolour
     *
     * @see PlantProperties has plant related properties
     * @see HProperties the default implementation
     */
    abstract class Factory<T : BlockProperties<T>> {
        /**
         * Returns a new [BlockProperties] with the specified material.
         *
         * Overloads of this function refer to different constructors
         * of the Block.Properties class and all use the result
         * from the [createProperties] function.
         */
        fun of(): T {
            val properties = createProperties()
            properties.internal = BlockBehaviour.Properties.of()
            return properties
        }

        /**
         * Returns a new [BlockProperties] with the specified material and color.
         *
         * Overloads of this function refer to different constructors
         * of the Block.Properties class and all use the result
         * from the [createProperties] function.
         */
        fun of(color: DyeColor): T {
            val properties = createProperties()
            properties.internal = BlockBehaviour.Properties.of().mapColor(color)
            return properties
        }

        /**
         * Returns a new [BlockProperties] with the specified material and color.
         *
         * Overloads of this function refer to different constructors
         * of the Block.Properties class and all use the result
         * from the [createProperties] function.
         */
        fun of(color: MapColor): T {
            val properties = createProperties()
            properties.internal = BlockBehaviour.Properties.of().mapColor(color)
            return properties
        }

        fun of(color: (BlockState) -> MapColor): T {
            val properties = createProperties()
            properties.internal = BlockBehaviour.Properties.of().mapColor(color)
            return properties
        }

        /**
         * Returns a new instance of [T] using properties from
         * the specified [block].
         */
        fun copy(block: Block): T {
            val properties = createProperties()
            properties.internal = BlockBehaviour.Properties.copy(block)

            // Copy custom properties
            if (block is HBlock) {
                block.writeProperties(properties)
            }

            return properties
        }

        /**
         * Returns a new instance of [T].
         */
        protected abstract fun createProperties(): T
    }
}