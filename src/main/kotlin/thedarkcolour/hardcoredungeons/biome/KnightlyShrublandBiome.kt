package thedarkcolour.hardcoredungeons.biome

import net.minecraft.entity.EntityClassification
import net.minecraft.world.biome.Biome
import net.minecraft.world.biome.MobSpawnInfo
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder
import team.rusty.util.worldgen.biome.AbstractBiome
import thedarkcolour.hardcoredungeons.feature.HConfiguredFeatures
import thedarkcolour.hardcoredungeons.registry.HEntities
import thedarkcolour.hardcoredungeons.surfacebuilder.HConfiguredSurfaceBuilders

object KnightlyShrublandBiome : AbstractBiome() {
    init {
        precipitation = Biome.RainType.NONE
        category = Biome.Category.PLAINS
        depth = 0.1f
        scale = 0.3f
        temperature = 1.5f
        downfall = 0.0f
        effects = HBiomeMaker.DEFAULT_CASTLETON_EFFECTS.build()
    }

    override fun configure(generation: BiomeGenerationSettingsBuilder, spawns: MobSpawnInfo.Builder) {
        generation.surfaceBuilder(HConfiguredSurfaceBuilders.CASTLETON_SURFACE)

        HConfiguredFeatures.withLumlightCabin(generation)
        HConfiguredFeatures.withLumlightShrubs(generation)

        spawns.addSpawn(EntityClassification.CREATURE, HEntities.CASTLETON_DEER, 6, 2, 6)
    }
}