package thedarkcolour.hardcoredungeons.worldgen.biome

import net.minecraft.core.Holder
import net.minecraft.world.level.biome.Biome
import net.minecraftforge.common.world.ModifiableBiomeInfo
import team.rusty.util.biome.ModBiome
import thedarkcolour.hardcoredungeons.worldgen.HWorldGen

object GumdropFieldsBiome : ModBiome() {
    override fun configure(biome: Holder<Biome>, info: ModifiableBiomeInfo.BiomeInfo.Builder) {
        info.climateSettings.apply {
            precipitation = Biome.Precipitation.NONE
            temperature = 1.5f
            downfall = 0.4f
        }
        info.specialEffects.apply {
            waterColor(0xd19bdc)
            waterFogColor(0x50533)
            skyColor(getSkyForTemp(0.9f))
            fogColor(12638463)
        }
        info.generationSettings.apply {
            HWorldGen.withSparseCandyCanes(this)
            HWorldGen.withSparseChocolateBars(this)
        }
    }
}