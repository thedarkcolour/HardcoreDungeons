package thedarkcolour.hardcoredungeons.tileentity

import net.minecraft.tileentity.TileEntity
import net.minecraft.tileentity.TileEntityType
import thedarkcolour.hardcoredungeons.registry.HTileEntities

class DungeonSpawnerTileEntity(tileEntityTypeIn: TileEntityType<*> = HTileEntities.DUNGEON_SPAWNER) : TileEntity(tileEntityTypeIn) {
    val remainingKills = 0


}