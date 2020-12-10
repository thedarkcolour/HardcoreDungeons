@file:Suppress("MemberVisibilityCanBePrivate")

package thedarkcolour.hardcoredungeons.block.properties

import net.minecraft.block.AbstractBlock
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.SoundType
import net.minecraft.block.material.Material
import net.minecraft.block.material.MaterialColor
import net.minecraft.item.DyeColor
import net.minecraft.util.math.shapes.VoxelShape
import net.minecraftforge.common.ToolType
import thedarkcolour.hardcoredungeons.block.HBlock
import thedarkcolour.hardcoredungeons.block.properties.Properties.Factory
import java.util.function.ToIntFunction

/**
 * Wraps the Block.Properties class to make it extensible.
 *
 * The constructor of this class should be private in non-open classes.
 * Instead of a public constructor, the extending class should have its
 * companion object extend [Factory] and return a new instance of the
 * extending class to make construction easier and extensible.
 *
 * @author TheDarkColour
 */
@Suppress("FunctionName", "UNCHECKED_CAST")
abstract class Properties<T : Properties<T>> protected constructor() {
    /** The vanilla representation of this [Properties] instance. */
    private lateinit var internal: AbstractBlock.Properties
    /** The VoxelShape shape that this block uses for collision and selection boxes. */
    private var shape: VoxelShape? = null
    /** The enchantment power bonus that this block should give. */
    private var enchantmentPower = 0.0f

    /**
     * Set this block's collision shape to empty.
     */
    fun doesNotBlockMovement(): T {
        internal.doesNotBlockMovement()
        return this as T
    }

    /**
     * Set this block to be non-solid.
     */
    fun nonSolid(): T {
        internal.notSolid()
        return this as T
    }

    /**
     * Set the slipperiness of this block.
     */
    fun slipperiness(slipperinessIn: Float): T {
        internal.slipperiness(slipperinessIn)
        return this as T
    }

    /**
     * Set the movement modifier for entities that walk on this block.
     */
    fun movementModifier(movementModifier: Float): T {
        internal.speedFactor(movementModifier)
        return this as T
    }

    /**
     * Set the jump modifier for entities that jump while on top of this block.
     */
    fun jumpModifier(jumpModifier: Float): T {
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
    fun light(light: Int): T {
        internal.setLightLevel { light }
        return this as T
    }

    fun light(light: ToIntFunction<BlockState>): T {
        internal.setLightLevel(light)
        return this as T
    }

    /**
     * Set the hardness and resistance for this block.
     */
    fun hardnessAndResistance(hardnessIn: Float, resistanceIn: Float): T {
        internal.hardnessAndResistance(hardnessIn, resistanceIn)
        return this as T
    }

    /**
     * Set this block to drop instantly and to never survive explosions.
     */
    fun zeroHardnessAndResistance(): T {
        return hardnessAndResistance(0.0f, 0.0f)
    }

    /**
     * Set the hardness and resistance for this block.
     */
    fun hardnessAndResistance(hardnessAndResistance: Float): T {
        internal.hardnessAndResistance(hardnessAndResistance, hardnessAndResistance)
        return this as T
    }

    /**
     * Set this block to receive random ticks.
     */
    fun tickRandomly(): T {
        internal.tickRandomly()
        return this as T
    }

    /**
     * Set the BlockState for this block to not cache itself.
     */
    fun noStateCache(): T {
        internal.variableOpacity()
        return this as T
    }

    /**
     * Set the harvest level for this block.
     */
    fun harvestLevel(harvestLevel: Int): T {
        internal.harvestLevel(harvestLevel)
        return this as T
    }

    /**
     * Set the harvest tool for this block.
     */
    fun harvestTool(harvestTool: ToolType): T {
        internal.harvestTool(harvestTool)
        return this as T
    }

    /**
     * Set the loot table of this block to empty.
     */
    fun noDrops(): T {
        internal.noDrops()
        return this as T
    }

    /**
     * Set the loot table this block uses to be the same as another block.
     */
    fun lootFrom(blockIn: Block): T {
        internal.lootFrom(blockIn)
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
        this.shape = block.writeProperties(HProperties.create(Material.ROCK)).shape
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

    /**
     * Returns a Block.Properties for use in vanilla blocks.
     */
    fun build(): AbstractBlock.Properties {
        return internal
    }

    /**
     * The factory that should be a companion object of
     * the implementing [Properties] class
     */
    abstract fun getFactory(): Factory<T>

    /**
     * The base factory class for constructing new instances of properties.
     *
     * Implement in the companion object of your properties class.
     * This is meant to be a factory
     *
     * @author TheDarkColour
     *
     * @see PlantProperties has plant related properties
     * @see HProperties the default implementation
     */
    abstract class Factory<T : Properties<T>> {
        /**
         * Returns a new [Properties] with the specified material.
         *
         * Overloads of this function refer to different constructors
         * of the Block.Properties class and all use the result
         * from the [createProperties] function.
         */
        fun create(material: Material): T {
            val properties = createProperties()
            properties.internal = AbstractBlock.Properties.create(material)
            return properties
        }

        /**
         * Returns a new [Properties] with the specified material and color.
         *
         * Overloads of this function refer to different constructors
         * of the Block.Properties class and all use the result
         * from the [createProperties] function.
         */
        fun create(material: Material, color: DyeColor): T {
            val properties = createProperties()
            properties.internal = AbstractBlock.Properties.create(material, color)
            return properties
        }

        /**
         * Returns a new [Properties] with the specified material and color.
         *
         * Overloads of this function refer to different constructors
         * of the Block.Properties class and all use the result
         * from the [createProperties] function.
         */
        fun create(material: Material, color: MaterialColor): T {
            val properties = createProperties()
            properties.internal = AbstractBlock.Properties.create(material, color)
            return properties
        }

        /**
         * Returns a new instance of [T] using properties from
         * the specified [block].
         *
         * **Does not copy over any non-vanilla properties.**
         */
        fun fromVanilla(block: Block): T {
            val properties = createProperties()
            properties.internal = AbstractBlock.Properties.from(block)
            return properties
        }

        /**
         * Returns a new instance of [T] using properties from
         * the specified [block] **including non-vanilla properties.**
         */
        fun from(block: HBlock): T {
            val properties = fromVanilla(block)
            block.writeProperties(properties)
            return properties
        }

        /**
         * Returns a new instance of [T].
         */
        protected abstract fun createProperties(): T
    }
}