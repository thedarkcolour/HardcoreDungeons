package thedarkcolour.hardcoredungeons.registry

import net.minecraft.tileentity.TileEntityType
import net.minecraftforge.registries.IForgeRegistry
import thedarkcolour.hardcoredungeons.tileentity.DungeonSpawnerTileEntity
import thedarkcolour.hardcoredungeons.tileentity.MazeBossSpawnerTileEntity
import thedarkcolour.hardcoredungeons.tileentity.SpawnerChestTileEntity

@Suppress("HasPlatformType", "NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
object HTileEntities {
    val DUNGEON_SPAWNER = TileEntityType.Builder.create(::DungeonSpawnerTileEntity, HBlocks.DUNGEON_SPAWNER).build(null).setRegistryKey("dungeon_spawner")

    //val LUMLIGHT_CAMPFIRE = TileEntityType.Builder.create(::LumlightCampfireTileEntity, HBlocks.LUMLIGHT_CAMPFIRE).build(null).setRegistryKey("")
    //val EXTRACTOR = TileEntityType.Builder.create(::ExtractorTileEntity, HBlocks.EXTRACTOR).build(null).setRegistryKey("extractor")
    val SPAWNER_CHEST = TileEntityType.Builder.create(::SpawnerChestTileEntity, HBlocks.CASTLETON_DUNGEON_CHEST).build(null).setRegistryKey("spawner_chest")
    //val TROPHY_BED = TileEntityType.Builder.create(::TrophyBedTileEntity, ).build(null).setRegistryKey("trophy_bed")
    val MAZE_BOSS_SPAWNER = TileEntityType.Builder.create(::MazeBossSpawnerTileEntity, HBlocks.MAZE_BOSS_SPAWNER).build(null).setRegistryKey("maze_boss_spawner")

    fun registerTileEntities(tileEntities: IForgeRegistry<TileEntityType<*>>) {
        tileEntities.register(DUNGEON_SPAWNER)
        tileEntities.register(MAZE_BOSS_SPAWNER)
        //tileEntities.register(LUMLIGHT_CAMPFIRE)
        //tileEntities.register(EXTRACTOR)
    }
/*
    fun registerTileEntityRenderers() {
        //ClientRegistry.bindTileEntityRenderer()
    }*/
}