package thedarkcolour.hardcoredungeons.worldgen.biome

import net.minecraft.core.particles.DustParticleOptions
import net.minecraft.world.level.biome.AmbientParticleSettings
import net.minecraft.world.phys.Vec3
import thedarkcolour.hardcoredungeons.data.WorldGenProvider

object AuriPlainsBiome : ModBiome() {
    override fun configure(info: WorldGenProvider.BiomeConfiguration) {
        info.climateSettings.apply {
            // todo green poisonous rain
            setHasPrecipitation(false)
            temperature = 1.5f
            downfall = 0.0f
        }
        info.specialEffects.apply {
            defaultAubrumEffects(this)
            ambientParticle(AmbientParticleSettings(DustParticleOptions(Vec3.fromRGB24(0xffa100).toVector3f(), 1.0f), 0.02f))
        }
    }
}