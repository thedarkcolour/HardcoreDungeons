package thedarkcolour.hardcoredungeons.registry

import net.minecraft.core.Registry
import net.minecraft.resources.ResourceLocation
import net.minecraft.sounds.SoundEvent
import net.minecraftforge.common.util.ForgeSoundType
import thedarkcolour.hardcoredungeons.HardcoreDungeons
import thedarkcolour.kotlinforforge.forge.ObjectHolderDelegate

@Suppress("MemberVisibilityCanBePrivate")
object HSounds : HRegistry<SoundEvent>(Registry.SOUND_EVENT_REGISTRY) {
    val ENTITY_FRAYED_SOUL_IDLE by soundEvent("frayed_soul_idle")

    val BLOCK_SCRAP_METAL_BREAK by soundEvent("scrap_metal_break")
    val BLOCK_SCRAP_METAL_STEP by soundEvent("scrap_metal_step")
    val BLOCK_SCRAP_METAL_PLACE by soundEvent("scrap_metal_place")
    val BLOCK_SCRAP_METAL_HIT by soundEvent("scrap_metal_hit")
    val BLOCK_SCRAP_METAL_FALL by soundEvent("scrap_metal_fall")

    // todo remove this in the future
    val SCRAP_METAL = ForgeSoundType(1.0f, 1.0f, { BLOCK_SCRAP_METAL_BREAK }, { BLOCK_SCRAP_METAL_STEP }, { BLOCK_SCRAP_METAL_PLACE }, { BLOCK_SCRAP_METAL_HIT }, { BLOCK_SCRAP_METAL_FALL })

    private fun soundEvent(name: String): ObjectHolderDelegate<SoundEvent> {
        val registryName = ResourceLocation(HardcoreDungeons.ID, name)
        return register(name) { SoundEvent(registryName) }
    }
}