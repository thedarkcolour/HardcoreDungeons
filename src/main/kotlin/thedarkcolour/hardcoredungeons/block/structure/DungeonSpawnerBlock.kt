package thedarkcolour.hardcoredungeons.block.structure

import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.tileentity.TileEntity
import net.minecraft.world.IBlockReader
import thedarkcolour.hardcoredungeons.tileentity.DungeonSpawnerTileEntity

class DungeonSpawnerBlock(properties: Properties) : Block(properties) {
    override fun hasTileEntity(state: BlockState?): Boolean {
        return true
    }

    override fun createTileEntity(state: BlockState?, world: IBlockReader?): TileEntity {
        return DungeonSpawnerTileEntity()
    }
}
