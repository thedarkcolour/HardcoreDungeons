package thedarkcolour.hardcoredungeons.registry

import net.minecraft.client.Minecraft
import net.minecraft.client.particle.FlameParticle
import net.minecraft.client.particle.LavaParticle
import net.minecraft.particles.BasicParticleType
import net.minecraft.particles.ParticleType
import net.minecraftforge.registries.IForgeRegistry
import thedarkcolour.hardcoredungeons.particle.SoulFrayParticle

/**
 * @author TheDarkColour
 */
object HParticles {
    val SOUL_FRAY = BasicParticleType(false).setRegistryKey("soul_fray")
    val CASTLETON_TORCH_FLAME = BasicParticleType(false).setRegistryKey("castleton_torch_flame")
    val CASTLETON_CAMPFIRE_POP = BasicParticleType(false).setRegistryKey("castleton_campfire_pop")

    fun registerParticles(particles: IForgeRegistry<ParticleType<*>>) {
        //particles.register(SOUL_FRAY)
        //particles.register(CASTLETON_TORCH_FLAME)
        //particles.register(CASTLETON_CAMPFIRE_POP)
    }

    fun registerParticleFactories() {
        val manager = Minecraft.getInstance().particles

        //manager.registerFactory(SOUL_FRAY, SoulFrayParticle::Factory)
        //manager.registerFactory(CASTLETON_TORCH_FLAME, FlameParticle::Factory)
        //manager.registerFactory(CASTLETON_CAMPFIRE_POP, LavaParticle::Factory)
    }
}



//
// val sprites = ObfuscationReflectionHelper.getPrivateValue<MutableMap<ResourceLocation, Any>, ParticleManager>(ParticleManager::class.java, manager, "field_215242_i")!!
// sprites[ForgeRegistries.PARTICLE_TYPES.getKey(SOUL_FRAY)!!] = sprites[ForgeRegistries.PARTICLE_TYPES.getKey(ParticleTypes.CLOUD)!!]!!