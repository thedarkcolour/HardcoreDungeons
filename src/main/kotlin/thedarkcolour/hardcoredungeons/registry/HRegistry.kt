package thedarkcolour.hardcoredungeons.registry

import net.minecraft.core.Registry
import net.minecraft.resources.ResourceKey
import net.minecraftforge.eventbus.api.EventPriority
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.RegisterEvent
import thedarkcolour.hardcoredungeons.HardcoreDungeons
import thedarkcolour.hardcoredungeons.legacy.ObjectHolderDelegate
import thedarkcolour.kotlinforforge.forge.*

open class HRegistry<T>(forgeRegistry: ResourceKey<Registry<T>>) {
    private val queue = ArrayDeque<() -> Unit>()
    private val registry = DeferredRegister.create(forgeRegistry, HardcoreDungeons.ID) // do not register directly

    // register here instead
    fun init() {
        registry.register(MOD_BUS)
        MOD_BUS.addListener(EventPriority.LOWEST, ::postRegistry)
    }

    fun onceRegistered(function: () -> Unit) {
        queue.add(function)
    }

    open fun <V : T> register(name: String, supplier: () -> V): ObjectHolderDelegate<V> {
        return registry.registerObject(name, supplier)
    }

    private fun postRegistry(event: RegisterEvent) {
        if (event.registryKey == registry.registryKey) {
            while (queue.isNotEmpty()) {
                queue.removeFirst().invoke()
            }
        }
    }
}