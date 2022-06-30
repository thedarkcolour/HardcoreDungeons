package thedarkcolour.hardcoredungeons.worldgen.biome

import net.minecraft.entity.EntityClassification
import net.minecraft.entity.EntityType
import net.minecraft.world.biome.Biome
import net.minecraft.world.biome.DefaultBiomeFeatures
import net.minecraft.world.biome.MobSpawnInfo
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder
import net.minecraftforge.common.BiomeManager
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder
import team.rusty.util.worldgen.biome.AbstractBiome
import thedarkcolour.hardcoredungeons.registry.HEntities
import thedarkcolour.hardcoredungeons.worldgen.feature.HWorldGen

// todo remove
object ThickForestBiome : AbstractBiome() {
    init {
        precipitation = Biome.RainType.RAIN
        category = Biome.Category.FOREST
        depth = 0.2f
        scale = 0.25f
        temperature = 0.7f
        downfall = 0.8f
        effects = biomeFx(0x3f76e4, 0x50533, getSkyForTemp(0.7f), 12638463)
            .foliageColorOverride(0x775729)
            .grassColorOverride(0x775729)
            .build()
    }

    override fun configure(generation: BiomeGenerationSettingsBuilder, spawns: MobSpawnInfo.Builder) {
        generation.surfaceBuilder(SurfaceBuilder.DEFAULT.configured(SurfaceBuilder.CONFIG_GRASS))
        DefaultBiomeFeatures.addDefaultCarvers(generation)
        DefaultBiomeFeatures.addDefaultOverworldLandStructures(generation)
        DefaultBiomeFeatures.addDefaultMonsterRoom(generation)
        DefaultBiomeFeatures.addDefaultUndergroundVariety(generation) // different stones
        DefaultBiomeFeatures.addDefaultOres(generation) // ores
        DefaultBiomeFeatures.addDefaultSoftDisks(generation) // sand + clay
        DefaultBiomeFeatures.addMountainEdgeTrees(generation) // spruce and oak
        DefaultBiomeFeatures.addDefaultFlowers(generation) // default flowers
        DefaultBiomeFeatures.addForestGrass(generation) // grass
        DefaultBiomeFeatures.addTaigaGrass(generation) // ferns
        DefaultBiomeFeatures.addWaterTrees(generation) // water trees
        //DefaultBiomeFeatures.addExtraGold(generation)
        DefaultBiomeFeatures.addWarmFlowers(generation) // warm flowers
        DefaultBiomeFeatures.addDefaultExtraVegetation(generation) // pumpkin + sugar cane
        HWorldGen.withOakShrubs(generation)


        spawns.addSpawn(EntityClassification.CREATURE, EntityType.SHEEP, 12, 4, 4)
        spawns.addSpawn(EntityClassification.CREATURE, HEntities.DEER, 7, 2, 3)
        spawns.addSpawn(EntityClassification.AMBIENT, EntityType.BAT, 8, 8, 8)
        spawns.addSpawn(EntityClassification.MONSTER, EntityType.SPIDER, 100, 4, 4)
        spawns.addSpawn(EntityClassification.MONSTER, EntityType.ZOMBIE, 95, 4, 4)
        spawns.addSpawn(EntityClassification.MONSTER, EntityType.ZOMBIE_VILLAGER, 5, 1, 1)
        spawns.addSpawn(EntityClassification.MONSTER, EntityType.SKELETON, 100, 4, 4)
        spawns.addSpawn(EntityClassification.MONSTER, EntityType.CREEPER, 100, 4, 4)
        spawns.addSpawn(EntityClassification.MONSTER, EntityType.SLIME, 100, 4, 4)
        spawns.addSpawn(EntityClassification.MONSTER, EntityType.ENDERMAN, 10, 1, 4)
        spawns.addSpawn(EntityClassification.MONSTER, EntityType.WITCH, 5, 1, 1)
    }

    override fun getSpawnEntries(): List<SpawnEntry> {
        return listOf(SpawnEntry(BiomeManager.BiomeType.WARM, 6))
    }
}