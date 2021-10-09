package thedarkcolour.hardcoredungeons.registry

import net.minecraft.client.Minecraft
import net.minecraft.client.particle.FlameParticle
import net.minecraft.client.particle.LavaParticle
import net.minecraft.particles.BasicParticleType
import net.minecraftforge.registries.ForgeRegistries
import thedarkcolour.hardcoredungeons.HardcoreDungeons
import thedarkcolour.hardcoredungeons.particle.SoulFrayParticle
import thedarkcolour.kotlinforforge.forge.KDeferredRegister

/**
 * @author TheDarkColour
 */
object HParticles {
    val PARTICLE_TYPES = KDeferredRegister(ForgeRegistries.PARTICLE_TYPES, HardcoreDungeons.ID)

    val SOUL_FRAY by PARTICLE_TYPES.registerObject("soul_fray") { BasicParticleType(false) }
    val CASTLETON_TORCH_FLAME by PARTICLE_TYPES.registerObject("castleton_torch_flame") { BasicParticleType(false) }
    val CASTLETON_CAMPFIRE_POP by PARTICLE_TYPES.registerObject("castleton_campfire_pop") { BasicParticleType(false) }

    fun registerParticleFactories() {
        val manager = Minecraft.getInstance().particleEngine

        manager.register(SOUL_FRAY, SoulFrayParticle::Factory)
        manager.register(CASTLETON_TORCH_FLAME, FlameParticle::Factory)
        manager.register(CASTLETON_CAMPFIRE_POP, LavaParticle::Factory)
    }
}