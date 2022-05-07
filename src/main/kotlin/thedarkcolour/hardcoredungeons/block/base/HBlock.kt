package thedarkcolour.hardcoredungeons.block.base

import net.minecraft.block.AbstractBlock
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.shapes.ISelectionContext
import net.minecraft.util.math.shapes.VoxelShape
import net.minecraft.util.math.shapes.VoxelShapes
import net.minecraft.world.IBlockReader
import net.minecraft.world.IWorldReader
import thedarkcolour.hardcoredungeons.block.base.properties.BlockProperties
import thedarkcolour.hardcoredungeons.block.base.properties.HProperties

/**
 * Base block with an expanded version of [AbstractBlock.Properties].
 *
 * @property enchantmentPower the enchantment power of this block
 * @property shape the (collision and selection) shape of this block
 *
 * @author TheDarkColour
 */
open class HBlock(properties: HProperties) : Block(properties.build()) {
    /** The [VoxelShape] used for collision and selection boxes. */
    val shape: VoxelShape = properties.getShape() ?: VoxelShapes.block()
    /** The enchantment power of this block. */
    private val enchantmentPower = properties.getEnchantmentPower()

    override fun getEnchantPowerBonus(state: BlockState?, world: IWorldReader?, pos: BlockPos?): Float {
        return enchantmentPower
    }

    override fun getShape(state: BlockState?, worldIn: IBlockReader?, pos: BlockPos?, ctx: ISelectionContext?): VoxelShape {
        return shape
    }

    /**
     * Writes the properties of this block to the supplied [BlockProperties]
     */
    open fun writeProperties(properties: BlockProperties<*>): BlockProperties<*> {
        properties.shape(shape)
        properties.enchantmentPower(enchantmentPower)

        return properties
    }
}