package thedarkcolour.hardcoredungeons.worldgen.biome

import net.minecraft.world.biome.Biome
import net.minecraft.world.biome.DefaultBiomeFeatures
import net.minecraft.world.biome.MobSpawnInfo
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder
import team.rusty.util.worldgen.biome.AbstractBiome
import thedarkcolour.hardcoredungeons.worldgen.feature.HWorldGen

object MushroomCliffsBiome : AbstractBiome() {
    init {
        precipitation = Biome.RainType.NONE
        category = Biome.Category.MUSHROOM
        depth = 0.3625f
        scale = 1.225f
        temperature = 0.9f
        downfall = 1.0f
        effects = biomeFx(0x3f76e4, 0x50533, getSkyForTemp(0.9f), 12638463).build()
    }

    override fun configure(generation: BiomeGenerationSettingsBuilder, spawns: MobSpawnInfo.Builder) {
        generation.surfaceBuilder(SurfaceBuilder.DEFAULT.configured(SurfaceBuilder.CONFIG_MYCELIUM))

        DefaultBiomeFeatures.addDefaultCarvers(generation) // canyon + cave
        DefaultBiomeFeatures.addDefaultOverworldLandStructures(generation) // stronghold etc.
        DefaultBiomeFeatures.addDefaultMonsterRoom(generation) // dungeon
        DefaultBiomeFeatures.addDefaultUndergroundVariety(generation) // different stones
        DefaultBiomeFeatures.addDefaultOres(generation) // ores
        DefaultBiomeFeatures.addDefaultSoftDisks(generation) // clay + sand
        DefaultBiomeFeatures.addMushroomFieldVegetation(generation) // mushroom island vegetation
        DefaultBiomeFeatures.addDefaultMushrooms(generation) // mushrooms
        DefaultBiomeFeatures.addDefaultExtraVegetation(generation) // pumpkin + sugar cane

        HWorldGen.addShroomyBoulders(generation)
    }
}