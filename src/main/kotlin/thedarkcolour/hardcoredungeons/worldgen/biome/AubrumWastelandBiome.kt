package thedarkcolour.hardcoredungeons.worldgen.biome

import net.minecraft.core.Holder
import net.minecraft.world.biome.Biome
import net.minecraft.world.biome.MobSpawnInfo
import net.minecraft.world.level.biome.Biome
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder
import net.minecraftforge.common.world.ModifiableBiomeInfo
import team.rusty.util.biome.ModBiome
import team.rusty.util.worldgen.biome.AbstractBiome
import thedarkcolour.hardcoredungeons.worldgen.surfacebuilder.HConfiguredSurfaceBuilders

object AubrumWastelandBiome : ModBiome() {
    init {
        precipitation = Biome.RainType.NONE
        category = Biome.Category.PLAINS
        depth = 0.02f
        scale = 0.4f
        temperature = 1.5f
        downfall = 0.0f
        effects = biomeFx(0x669900, 0x1f37f2, 0xab9560, 0xc4af7c).build()
    }

    override fun configure(biome: Holder<Biome>, info: ModifiableBiomeInfo.BiomeInfo.Builder) {
        info.climateSettings.apply {
            precipitation = Biome.RainType.NONE
            temperature = 1.5f
            downfall = 0.0f
        }
    }
}