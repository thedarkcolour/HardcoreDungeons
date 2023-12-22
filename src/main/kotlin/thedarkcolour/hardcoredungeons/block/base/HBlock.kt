package thedarkcolour.hardcoredungeons.block.base

import net.minecraft.core.BlockPos
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.LevelReader
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.Shapes
import net.minecraft.world.phys.shapes.VoxelShape
import thedarkcolour.hardcoredungeons.block.base.properties.BlockProperties
import thedarkcolour.hardcoredungeons.block.base.properties.PropertiesView

/**
 * Base block with an expanded version of BlockProperties.
 *
 * @property enchantmentPower the enchantment power of this block
 * @property shape the (collision and selection) shape of this block
 *
 * @author thedarkcolour
 */
open class HBlock(properties: PropertiesView) : Block(properties.build()) {
    /** The [VoxelShape] used for collision and selection boxes. */
    val shape: VoxelShape = properties.getShape() ?: Shapes.block()
    /** The enchantment power of this block. */
    private val enchantmentPower = properties.getEnchantmentPower()

    override fun getEnchantPowerBonus(state: BlockState?, world: LevelReader?, pos: BlockPos?): Float {
        return enchantmentPower
    }

    override fun getShape(state: BlockState?, level: BlockGetter?, pos: BlockPos?, ctx: CollisionContext?): VoxelShape {
        return shape
    }

    /**
     * Writes the properties of this block to the supplied [BlockProperties]
     */
    open fun writeProperties(properties: BlockProperties<*>): BlockProperties<*> {
        properties.shape(shape)
        properties.enchantmentPower(enchantmentPower)
        if (properties.getFlammable()) {
            properties.flammable()
        }
        if (properties.getBlockSetType() != null) {
            properties.blockSetType(properties.getBlockSetType()!!)
        }

        return properties
    }
}