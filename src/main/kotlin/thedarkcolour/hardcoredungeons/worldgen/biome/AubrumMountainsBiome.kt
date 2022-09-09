package thedarkcolour.hardcoredungeons.worldgen.biome

import net.minecraft.core.Holder
import net.minecraft.world.level.biome.Biome
import net.minecraftforge.common.world.ModifiableBiomeInfo
import team.rusty.util.biome.ModBiome

object AubrumMountainsBiome : ModBiome() {
    init {
        category = Biome.Category.MUSHROOM
        depth = 0.3625f
        scale = 1.225f
        temperature = 1.5f
        downfall = 0.0f
        effects = biomeFx(0x888888, 0x1f37f2, 0xab9560, 0xc4af7c).build()
    }

    override fun configure(
        biome: Holder<Biome>,
        info: ModifiableBiomeInfo.BiomeInfo.Builder
    ) {
        val climate = info.climateSettings
        climate.precipitation = Biome.Precipitation.NONE
    }
}