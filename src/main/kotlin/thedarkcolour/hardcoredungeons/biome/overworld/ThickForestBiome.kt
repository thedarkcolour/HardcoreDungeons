package thedarkcolour.hardcoredungeons.biome.overworld
/*
import net.minecraft.block.BlockState
import net.minecraft.entity.EntityClassification
import net.minecraft.entity.EntityType
import net.minecraft.world.biome.Biome
import net.minecraft.world.biome.DefaultBiomeFeatures
import net.minecraft.world.chunk.IChunk
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig
import thedarkcolour.hardcoredungeons.registry.HFeatures
import thedarkcolour.hardcoredungeons.registry.HSurfaceBuilders
import java.util.*

class ThickForestBiome : Biome(Builder()
    .surfaceBuilder(HSurfaceBuilders.THICK_FOREST_SURFACE, SurfaceBuilder.PODZOL_DIRT_GRAVEL_CONFIG)
    .precipitation(RainType.RAIN)
    .category(Category.FOREST)
    .depth(0.2F)
    .scale(0.25F)
    .temperature(0.7F)
    .downfall(0.8F)
    .waterColor(4159204)
    .waterFogColor(329011)
) {

    init {
        DefaultBiomeFeatures.addCarvers(this)
        DefaultBiomeFeatures.addStructures(this)
        DefaultBiomeFeatures.addMonsterRooms(this)
        DefaultBiomeFeatures.addStoneVariants(this)
        DefaultBiomeFeatures.addOres(this)
        DefaultBiomeFeatures.addSedimentDisks(this)
        DefaultBiomeFeatures.addOakAndSpruceTrees(this)
        DefaultBiomeFeatures.addTaigaLargeFerns(this)
        DefaultBiomeFeatures.addTaigaGrassAndMushrooms(this)
        DefaultBiomeFeatures.addScatteredOakTrees(this)
        DefaultBiomeFeatures.addExtraGoldOre(this)
        DefaultBiomeFeatures.addExtraDefaultFlowers(this)
        DefaultBiomeFeatures.addReedsAndPumpkins(this)
        HFeatures.addOakShrubs(this)
        addSpawn(EntityClassification.CREATURE, SpawnListEntry(EntityType.SHEEP, 12, 4, 4))
        addSpawn(EntityClassification.CREATURE, SpawnListEntry(EntityType.COW, 8, 4, 4))
        addSpawn(EntityClassification.AMBIENT, SpawnListEntry(EntityType.BAT, 10, 8, 8))
        addSpawn(EntityClassification.MONSTER, SpawnListEntry(EntityType.SPIDER, 100, 4, 4))
        addSpawn(EntityClassification.MONSTER, SpawnListEntry(EntityType.ZOMBIE, 95, 4, 4))
        addSpawn(EntityClassification.MONSTER, SpawnListEntry(EntityType.ZOMBIE_VILLAGER, 5, 1, 1))
        addSpawn(EntityClassification.MONSTER, SpawnListEntry(EntityType.SKELETON, 100, 4, 4))
        addSpawn(EntityClassification.MONSTER, SpawnListEntry(EntityType.CREEPER, 100, 4, 4))
        addSpawn(EntityClassification.MONSTER, SpawnListEntry(EntityType.SLIME, 100, 4, 4))
        addSpawn(EntityClassification.MONSTER, SpawnListEntry(EntityType.ENDERMAN, 10, 1, 4))
        addSpawn(EntityClassification.MONSTER, SpawnListEntry(EntityType.WITCH, 5, 1, 1))
    }

    // @formatter off

    // @formatter on
}*/