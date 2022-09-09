package thedarkcolour.hardcoredungeons.worldgen.biome

import net.minecraft.world.biome.Biome
import net.minecraft.world.biome.MobSpawnInfo
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder
import team.rusty.util.worldgen.biome.AbstractBiome
import thedarkcolour.hardcoredungeons.worldgen.HWorldGen
import thedarkcolour.hardcoredungeons.worldgen.surfacebuilder.HConfiguredSurfaceBuilders

object AuriPlainsBiome : AbstractBiome() {
    init {
        precipitation = Biome.RainType.NONE
        category = Biome.Category.MUSHROOM
        depth = 0.02f
        scale = 0.1f
        temperature = 1.5f
        downfall = 0.0f
        effects = biomeFx(0x999966, 0x1f37f2, 0xab9560, 0xc4af7c).build()
    }

    override fun configure(generation: BiomeGenerationSettingsBuilder, spawns: MobSpawnInfo.Builder) {
        generation.surfaceBuilder(HConfiguredSurfaceBuilders.AUBRUM_WASTELAND_SURFACE)

        HWorldGen.withAubrumFlowers(generation)
    }
}