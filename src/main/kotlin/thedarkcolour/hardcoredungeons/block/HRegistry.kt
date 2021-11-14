package thedarkcolour.hardcoredungeons.block

import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.eventbus.api.EventPriority
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.IForgeRegistry
import net.minecraftforge.registries.IForgeRegistryEntry
import thedarkcolour.hardcoredungeons.HardcoreDungeons
import thedarkcolour.kotlinforforge.forge.MOD_BUS
import thedarkcolour.kotlinforforge.forge.registerObject
import kotlin.properties.ReadOnlyProperty

open class HRegistry<T : IForgeRegistryEntry<T>>(forgeRegistry: IForgeRegistry<T>) {
    private val queue = ArrayDeque<() -> Unit>()
    private val type = forgeRegistry.registrySuperType
    val registry = DeferredRegister.create(forgeRegistry, HardcoreDungeons.ID) // do not register directly

    // register here instead
    fun init() {
        registry.register(MOD_BUS)
        MOD_BUS.addGenericListener(type, EventPriority.LOWEST, ::postRegistry)
    }

    fun onceRegistered(function: () -> Unit) {
        queue.add(function)
    }

    fun <V : T> register(name: String, supplier: () -> V): ReadOnlyProperty<Any, T> {
        return registry.registerObject(name, supplier)
    }

    private fun postRegistry(event: RegistryEvent.Register<T>) {
        while (queue.isNotEmpty()) {
            queue.removeFirst().invoke()
        }
    }
}