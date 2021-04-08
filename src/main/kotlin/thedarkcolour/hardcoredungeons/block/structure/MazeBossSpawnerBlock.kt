package thedarkcolour.hardcoredungeons.block.structure

import net.minecraft.block.BlockState
import net.minecraft.tileentity.TileEntity
import net.minecraft.world.IBlockReader
import thedarkcolour.hardcoredungeons.block.decoration.HorizontalBlock
import thedarkcolour.hardcoredungeons.block.properties.HProperties
import thedarkcolour.hardcoredungeons.tileentity.MazeBossSpawnerTileEntity

class MazeBossSpawnerBlock(properties: HProperties) : HorizontalBlock(properties) {
    override fun createTileEntity(state: BlockState?, world: IBlockReader?): TileEntity {
        return MazeBossSpawnerTileEntity()
    }

    override fun hasTileEntity(state: BlockState?): Boolean {
        return true
    }
}