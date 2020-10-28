package thedarkcolour.hardcoredungeons.registry

import net.minecraft.tileentity.TileEntityType
import net.minecraftforge.registries.IForgeRegistry
import thedarkcolour.hardcoredungeons.tileentity.LumlightCampfireTileEntity
import thedarkcolour.hardcoredungeons.tileentity.SpawnerChestTileEntity

@Suppress("HasPlatformType", "NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
object HTileEntities {
    // TODO Rename to lumlight campfire in registry name
    val LUMLIGHT_CAMPFIRE = TileEntityType.Builder.create(::LumlightCampfireTileEntity, HBlocks.LUMLIGHT_CAMPFIRE).build(null).setRegistryKey("lumlight_campfire")
    //val EXTRACTOR = TileEntityType.Builder.create(::ExtractorTileEntity, HBlocks.EXTRACTOR).build(null).setRegistryKey("extractor")
    val SPAWNER_CHEST = TileEntityType.Builder.create(::SpawnerChestTileEntity, HBlocks.CASTLETON_DUNGEON_CHEST).build(null).setRegistryKey("spawner_chest")

    fun registerTileEntities(tileEntities: IForgeRegistry<TileEntityType<*>>) {
        tileEntities.register(LUMLIGHT_CAMPFIRE)
        //tileEntities.register(EXTRACTOR)
    }
/*
    fun registerTileEntityRenderers() {
        //ClientRegistry.bindTileEntityRenderer()
    }*/
}