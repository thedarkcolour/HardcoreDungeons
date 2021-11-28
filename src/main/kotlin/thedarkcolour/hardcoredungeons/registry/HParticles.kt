package thedarkcolour.hardcoredungeons.registry

import net.minecraft.client.Minecraft
import net.minecraft.client.particle.FlameParticle
import net.minecraft.client.particle.LavaParticle
import net.minecraft.core.particles.SimpleParticleType
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import thedarkcolour.hardcoredungeons.HardcoreDungeons
import thedarkcolour.hardcoredungeons.particle.SoulFrayParticle
import thedarkcolour.kotlinforforge.forge.registerObject

/**
 * @author TheDarkColour
 */
object HParticles {
    val PARTICLE_TYPES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, HardcoreDungeons.ID)

    val SOUL_FRAY by PARTICLE_TYPES.registerObject("soul_fray") { SimpleParticleType(false) }
    val CASTLETON_TORCH_FLAME by PARTICLE_TYPES.registerObject("castleton_torch_flame") { SimpleParticleType(false) }
    val CASTLETON_CAMPFIRE_POP by PARTICLE_TYPES.registerObject("castleton_campfire_pop") { SimpleParticleType(false) }

    fun registerParticleFactories() {
        val manager = Minecraft.getInstance().particleEngine

        manager.register(SOUL_FRAY, SoulFrayParticle::Factory)
        manager.register(CASTLETON_TORCH_FLAME, FlameParticle::Provider)
        manager.register(CASTLETON_CAMPFIRE_POP, LavaParticle::Provider)
    }
}