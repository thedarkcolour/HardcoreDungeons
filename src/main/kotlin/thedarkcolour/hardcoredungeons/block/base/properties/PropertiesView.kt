package thedarkcolour.hardcoredungeons.block.base.properties

import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.block.state.properties.BlockSetType
import net.minecraft.world.phys.shapes.VoxelShape

interface PropertiesView {
    fun build(): BlockBehaviour.Properties

    /**
     * Returns the shape of this block.
     */
    fun getShape(): VoxelShape?

    /**
     * Returns the enchantment power bonus of this block.
     */
    fun getEnchantmentPower(): Float
    fun getFlammable(): Boolean
    fun getBlockSetType(): BlockSetType?
}