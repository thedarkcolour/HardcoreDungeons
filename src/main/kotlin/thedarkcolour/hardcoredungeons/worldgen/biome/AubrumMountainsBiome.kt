package thedarkcolour.hardcoredungeons.worldgen.biome

import net.minecraft.core.Holder
import net.minecraft.world.level.biome.Biome
import net.minecraftforge.common.world.ModifiableBiomeInfo
import team.rusty.util.biome.ModBiome

object AubrumMountainsBiome : ModBiome() {
    override fun configure(biome: Holder<Biome>, info: ModifiableBiomeInfo.BiomeInfo.Builder) {
        info.climateSettings.apply {
            setHasPrecipitation(false)
            temperature = 1.5f
            downfall = 0.0f
        }
        info.specialEffects.apply {
            defaultAubrumEffects(this)
        }
    }
}