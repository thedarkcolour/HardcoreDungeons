package thedarkcolour.hardcoredungeons.worldgen.biome

import net.minecraft.core.Holder
import net.minecraft.data.worldgen.BiomeDefaultFeatures
import net.minecraft.world.level.biome.Biome
import net.minecraftforge.common.world.ModifiableBiomeInfo
import team.rusty.util.biome.ModBiome
import thedarkcolour.hardcoredungeons.worldgen.HWorldGen

object MushroomCliffsBiome : ModBiome() {
    override fun configure(biome: Holder<Biome>, info: ModifiableBiomeInfo.BiomeInfo.Builder) {
        info.climateSettings.apply {
            precipitation = Biome.Precipitation.NONE
            temperature = 0.9f
            downfall = 1.0f
        }
        info.specialEffects.apply {
            waterColor(0x3f76e4)
            waterFogColor(0x50533)
            skyColor(getSkyForTemp(0.9f))
            fogColor(0xc0d8ff)
        }
        info.generationSettings.apply {
            BiomeDefaultFeatures.addDefaultCarversAndLakes(this)
            BiomeDefaultFeatures.addDefaultMonsterRoom(this)
            BiomeDefaultFeatures.addDefaultUndergroundVariety(this)
            BiomeDefaultFeatures.addDefaultOres(this)
            BiomeDefaultFeatures.addMushroomFieldVegetation(this)
            BiomeDefaultFeatures.addDefaultExtraVegetation(this)
            HWorldGen.addShroomyBoulders(this)
        }
    }
}