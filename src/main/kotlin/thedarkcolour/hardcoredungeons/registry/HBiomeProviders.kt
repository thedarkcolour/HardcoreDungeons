package thedarkcolour.hardcoredungeons.registry

import net.minecraft.world.biome.provider.BiomeProviderType
import net.minecraft.world.storage.WorldInfo
import net.minecraftforge.registries.IForgeRegistry
import thedarkcolour.hardcoredungeons.biomeprovider.NoiseBiomeProvider
import thedarkcolour.hardcoredungeons.biomeprovider.NoiseBiomeProviderSettings

/**
 * Biome providers for Hardcore Dungeons.
 *
 * At the moment just copies a biome
 * provider originally from Future MC.
 *
 * @author TheDarkColour
 */
object HBiomeProviders {
    val NOISE = BiomeProviderType(::NoiseBiomeProvider, ::NoiseBiomeProviderSettings).setRegistryKey("noise")

    fun registerBiomeProviders(providers: IForgeRegistry<BiomeProviderType<*, *>>) {
        providers.register(NOISE)
    }

    fun noiseProvider(worldInfo: WorldInfo, settings: (NoiseBiomeProviderSettings) -> Unit): NoiseBiomeProvider {
        val config = NOISE.createSettings(worldInfo)
        settings(config)
        return NOISE.create(config)
    }
}