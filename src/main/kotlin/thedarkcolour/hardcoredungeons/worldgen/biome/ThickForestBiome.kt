package thedarkcolour.hardcoredungeons.worldgen.biome

import net.minecraft.core.Holder
import net.minecraft.world.level.biome.Biome
import net.minecraftforge.common.world.ModifiableBiomeInfo
import team.rusty.util.biome.ModBiome

// todo remove
object ThickForestBiome : ModBiome() {
    init {
        precipitation = Biome.Precipitation.RAIN
        //category = Biome.Category.FOREST
        //depth = 0.2f
        //scale = 0.25f
        temperature = 0.7f
        downfall = 0.8f
        effects = biomeFx(0x3f76e4, 0x50533, getSkyForTemp(0.7f), 12638463)
            .foliageColorOverride(0x775729)
            .grassColorOverride(0x775729)
            .build()
    }

    override fun configure(
        biome: Holder<Biome>,
        info: ModifiableBiomeInfo.BiomeInfo.Builder
    ) {
//        generation.surfaceBuilder(SurfaceBuilder.DEFAULT.configured(SurfaceBuilder.CONFIG_GRASS))
//        BiomeDefaultFeatures.addDefaultCarvers(generation)
//        BiomeDefaultFeatures.addDefaultOverworldLandStructures(generation)
//        BiomeDefaultFeatures.addDefaultMonsterRoom(generation)
//        BiomeDefaultFeatures.addDefaultUndergroundVariety(generation) // different stones
//        BiomeDefaultFeatures.addDefaultOres(generation) // ores
//        BiomeDefaultFeatures.addDefaultSoftDisks(generation) // sand + clay
//        BiomeDefaultFeatures.addMountainTrees(generation) // spruce and oak
//        BiomeDefaultFeatures.addDefaultFlowers(generation) // default flowers
//        BiomeDefaultFeatures.addForestGrass(generation) // grass
//        BiomeDefaultFeatures.addTaigaGrass(generation) // ferns
//        BiomeDefaultFeatures.addWaterTrees(generation) // water trees
//        //net.minecraft.data.worldgen.BiomeDefaultFeatures.addExtraGold(generation)
//        BiomeDefaultFeatures.addWarmFlowers(generation) // warm flowers
//        BiomeDefaultFeatures.addDefaultExtraVegetation(generation) // pumpkin + sugar cane
//        HWorldGen.withOakShrubs(generation)
//
//
//        spawns.addSpawn(MobCategory.CREATURE, EntityType.SHEEP, 12, 4, 4)
//        spawns.addSpawn(MobCategory.CREATURE, HEntities.DEER, 7, 2, 3)
//        spawns.addSpawn(MobCategory.AMBIENT, EntityType.BAT, 8, 8, 8)
//        spawns.addSpawn(MobCategory.MONSTER, EntityType.SPIDER, 100, 4, 4)
//        spawns.addSpawn(MobCategory.MONSTER, EntityType.ZOMBIE, 95, 4, 4)
//        spawns.addSpawn(MobCategory.MONSTER, EntityType.ZOMBIE_VILLAGER, 5, 1, 1)
//        spawns.addSpawn(MobCategory.MONSTER, EntityType.SKELETON, 100, 4, 4)
//        spawns.addSpawn(MobCategory.MONSTER, EntityType.CREEPER, 100, 4, 4)
//        spawns.addSpawn(MobCategory.MONSTER, EntityType.SLIME, 100, 4, 4)
//        spawns.addSpawn(MobCategory.MONSTER, EntityType.ENDERMAN, 10, 1, 4)
//        spawns.addSpawn(MobCategory.MONSTER, EntityType.WITCH, 5, 1, 1)
    }
}