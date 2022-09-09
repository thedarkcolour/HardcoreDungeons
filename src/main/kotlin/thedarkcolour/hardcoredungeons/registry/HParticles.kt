package thedarkcolour.hardcoredungeons.registry

import net.minecraft.client.particle.FlameParticle
import net.minecraft.client.particle.LavaParticle
import net.minecraft.core.particles.ParticleType
import net.minecraft.core.particles.SimpleParticleType
import net.minecraftforge.client.event.RegisterParticleProvidersEvent
import net.minecraftforge.registries.ForgeRegistries
import thedarkcolour.hardcoredungeons.particle.SoulFrayParticle

/**
 * @author thedarkcolour
 */
object HParticles : HRegistry<ParticleType<*>>(ForgeRegistries.Keys.PARTICLE_TYPES) {
    // todo make the soul_fray particle look better + rename to magic_bolt_trail
    val SOUL_FRAY by register("soul_fray") { SimpleParticleType(false) }
    val CASTLETON_TORCH_FLAME by register("castleton_torch_flame") { SimpleParticleType(false) }
    val CASTLETON_CAMPFIRE_POP by register("castleton_campfire_pop") { SimpleParticleType(false) }

    fun registerParticleFactories(event: RegisterParticleProvidersEvent) {
        event.register(SOUL_FRAY, SoulFrayParticle::Factory)
        event.register(CASTLETON_TORCH_FLAME, FlameParticle::Provider)
        event.register(CASTLETON_CAMPFIRE_POP, LavaParticle::Provider)
    }
}