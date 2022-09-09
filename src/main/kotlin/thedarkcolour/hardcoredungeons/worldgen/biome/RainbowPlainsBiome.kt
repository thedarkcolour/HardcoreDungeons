package thedarkcolour.hardcoredungeons.worldgen.biome

import net.minecraft.core.Holder
import net.minecraft.world.level.biome.Biome
import net.minecraftforge.common.world.ModifiableBiomeInfo
import team.rusty.util.biome.ModBiome

object RainbowPlainsBiome : ModBiome() {
    override fun configure(biome: Holder<Biome>, info: ModifiableBiomeInfo.BiomeInfo.Builder) {
        info.climateSettings.apply {
            precipitation = Biome.Precipitation.NONE
            temperature = 1.5f
            downfall = 0.0f
        }
        info.specialEffects.apply {
            waterColor(0x5008b2)
            waterFogColor(0x621ff2)
            skyColor(0xcfb3ff)
            fogColor(0xe388cf)
        }
    }
}