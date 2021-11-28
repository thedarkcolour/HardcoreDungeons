package thedarkcolour.hardcoredungeons.registry

import net.minecraft.sounds.SoundEvent
import net.minecraftforge.common.util.ForgeSoundType
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import thedarkcolour.hardcoredungeons.HardcoreDungeons
import thedarkcolour.hardcoredungeons.util.modLoc
import thedarkcolour.kotlinforforge.forge.registerObject
import kotlin.properties.ReadOnlyProperty

@Suppress("MemberVisibilityCanBePrivate")
object HSounds {
    val SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, HardcoreDungeons.ID)

    val ENTITY_FRAYED_SOUL_IDLE by soundEvent("frayed_soul_idle")
    val ENTITY_VOID_RUNNER_HIT by soundEvent("void_runner_hit")

    val BLOCK_SCRAP_METAL_BREAK by soundEvent("scrap_metal_break")
    val BLOCK_SCRAP_METAL_STEP by soundEvent("scrap_metal_break")
    val BLOCK_SCRAP_METAL_PLACE by soundEvent("scrap_metal_break")
    val BLOCK_SCRAP_METAL_HIT by soundEvent("scrap_metal_break")
    val BLOCK_SCRAP_METAL_FALL by soundEvent("scrap_metal_break")

    val SCRAP_METAL = ForgeSoundType(1.0f, 1.0f, ::BLOCK_SCRAP_METAL_BREAK, ::BLOCK_SCRAP_METAL_STEP, ::BLOCK_SCRAP_METAL_PLACE, ::BLOCK_SCRAP_METAL_HIT, ::BLOCK_SCRAP_METAL_FALL)

    private fun soundEvent(name: String): ReadOnlyProperty<Any, SoundEvent> {
        return SOUND_EVENTS.registerObject(name) {
            SoundEvent(modLoc(name))
        }
    }
}