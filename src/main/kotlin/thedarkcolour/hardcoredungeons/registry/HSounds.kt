package thedarkcolour.hardcoredungeons.registry

import net.minecraft.block.SoundType
import net.minecraft.resources.ResourceLocation
import net.minecraft.util.SoundEvent
import net.minecraftforge.registries.IForgeRegistry
import thedarkcolour.hardcoredungeons.HardcoreDungeons

@Suppress("MemberVisibilityCanBePrivate")
object HSounds {
    val ENTITY_FRAYED_SOUL_IDLE = soundEvent("frayed_soul_idle")
    val ENTITY_VOID_RUNNER_HIT = soundEvent("void_runner_hit")

    val BLOCK_SCRAP_METAL_BREAK = soundEvent("scrap_metal_break")
    val BLOCK_SCRAP_METAL_STEP = soundEvent("scrap_metal_break")
    val BLOCK_SCRAP_METAL_PLACE = soundEvent("scrap_metal_break")
    val BLOCK_SCRAP_METAL_HIT = soundEvent("scrap_metal_break")
    val BLOCK_SCRAP_METAL_FALL = soundEvent("scrap_metal_break")

    val SCRAP_METAL = SoundType(1.0f, 1.0f, BLOCK_SCRAP_METAL_BREAK, BLOCK_SCRAP_METAL_STEP, BLOCK_SCRAP_METAL_PLACE, BLOCK_SCRAP_METAL_HIT, BLOCK_SCRAP_METAL_FALL)

    private fun soundEvent(name: String): SoundEvent {
        val registryName = ResourceLocation(HardcoreDungeons.ID, name)
        return SoundEvent(registryName).setRegistryName(registryName)
    }

    fun registerSounds(sounds: IForgeRegistry<SoundEvent>) {
        sounds.register(ENTITY_FRAYED_SOUL_IDLE)
        sounds.register(ENTITY_VOID_RUNNER_HIT)
    }
}