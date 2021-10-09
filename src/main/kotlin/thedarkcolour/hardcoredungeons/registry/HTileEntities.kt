package thedarkcolour.hardcoredungeons.registry

import net.minecraft.block.Block
import net.minecraft.tileentity.TileEntity
import net.minecraft.tileentity.TileEntityType
import net.minecraftforge.registries.ForgeRegistries
import thedarkcolour.hardcoredungeons.block.HBlocks
import thedarkcolour.hardcoredungeons.block.HRegistry
import thedarkcolour.hardcoredungeons.tileentity.DungeonSpawnerTileEntity
import thedarkcolour.hardcoredungeons.tileentity.MazeBossSpawnerTileEntity
import thedarkcolour.hardcoredungeons.tileentity.SootTrapControllerTileEntity
import thedarkcolour.kotlinforforge.forge.ObjectHolderDelegate
import java.util.function.Supplier

@Suppress("HasPlatformType", "NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
object HTileEntities : HRegistry<TileEntityType<*>>(ForgeRegistries.TILE_ENTITIES) {
    val DUNGEON_SPAWNER by createType("dungeon_spawner", ::DungeonSpawnerTileEntity, { HBlocks.DUNGEON_SPAWNER })
    val MAZE_BOSS_SPAWNER by createType("maze_boss_spawner", ::MazeBossSpawnerTileEntity, { HBlocks.MAZE_BOSS_SPAWNER })
    val SOOT_TRAP_CONTROLLER by createType("soot_trap_controller", ::SootTrapControllerTileEntity, { HBlocks.SOOT_TRAP_CONTROLLER })

    private fun <T : TileEntity> createType(name: String, supplier: Supplier<T>, vararg blocks: () -> Block): ObjectHolderDelegate<TileEntityType<T>> {
        return register(name) { TileEntityType.Builder.of(supplier, *blocks.map { it.invoke() }.toTypedArray()).build(null) }
    }
}