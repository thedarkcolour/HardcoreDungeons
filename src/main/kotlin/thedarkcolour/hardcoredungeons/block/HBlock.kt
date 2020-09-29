package thedarkcolour.hardcoredungeons.block

import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.shapes.ISelectionContext
import net.minecraft.util.math.shapes.VoxelShape
import net.minecraft.util.math.shapes.VoxelShapes
import net.minecraft.world.IBlockReader
import net.minecraft.world.IWorldReader
import thedarkcolour.hardcoredungeons.block.properties.HProperties

/**
 * Base block with an expanded version of [Block.Properties].
 *
 * @property enchantmentPower the enchantment power of this block
 * @property shape the (collision and selection) shape of this block
 *
 * @author TheDarkColour
 */
open class HBlock(properties: HProperties) : Block(properties.build()) {
    /** The VoxelShape that is used for collision and selection boxes. */
    val shape = properties.getShape() ?: VoxelShapes.fullCube()
    /** The enchantment power of this block. */
    private val enchantmentPower = properties.getEnchantmentPower()

    /**
     * Returns the enchantment power bonus of this block. (Bookshelf returns 1)
     */
    override fun getEnchantPowerBonus(state: BlockState?, world: IWorldReader?, pos: BlockPos?): Float {
        return enchantmentPower
    }

    /**
     * Returns the collision and selection shape of this block.
     */
    override fun getShape(state: BlockState?, worldIn: IBlockReader?, pos: BlockPos?, ctx: ISelectionContext?): VoxelShape {
        return shape
    }

    /**
     * Writes the properties of this block to the supplied [HProperties]/
     */
    open fun writeProperties(properties: thedarkcolour.hardcoredungeons.block.properties.Properties<*>): thedarkcolour.hardcoredungeons.block.properties.Properties<*> {
        properties.shape(shape)
        properties.enchantmentPower(enchantmentPower)

        return properties
    }
}