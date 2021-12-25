package thedarkcolour.hardcoredungeons.biome

import net.minecraft.world.biome.Biome
import net.minecraft.world.biome.MobSpawnInfo
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder
import team.rusty.util.worldgen.biome.AbstractBiome
import thedarkcolour.hardcoredungeons.surfacebuilder.HConfiguredSurfaceBuilders

object AubrumWastelandBiome : AbstractBiome() {
    init {
        precipitation = Biome.RainType.NONE
        category = Biome.Category.PLAINS
        depth = 0.02f
        scale = 0.4f
        temperature = 1.5f
        downfall = 0.0f
        effects = biomeFx(0x669900, 0x1f37f2, 0xab9560, 0xc4af7c).build()
    }

    override fun configure(generation: BiomeGenerationSettingsBuilder, spawns: MobSpawnInfo.Builder) {
        generation.surfaceBuilder(HConfiguredSurfaceBuilders.AUBRUM_WASTELAND_SURFACE)
    }
}