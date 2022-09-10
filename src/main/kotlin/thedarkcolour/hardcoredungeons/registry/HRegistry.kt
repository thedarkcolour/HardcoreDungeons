package thedarkcolour.hardcoredungeons.registry

import net.minecraft.core.Registry
import net.minecraft.resources.ResourceKey
import net.minecraftforge.eventbus.api.EventPriority
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.RegisterEvent
import thedarkcolour.hardcoredungeons.HardcoreDungeons
import thedarkcolour.hardcoredungeons.legacy.ObjectHolderDelegate
import thedarkcolour.hardcoredungeons.legacy.registerObject
import thedarkcolour.kotlinforforge.forge.MOD_BUS

open class HRegistry<T>(registryKey: ResourceKey<Registry<T>>) {
    private val queue = ArrayDeque<() -> Unit>()
    private val registry = DeferredRegister.create(registryKey, HardcoreDungeons.ID) // do not register directly

    // register here instead
    open fun init() {
        registry.register(MOD_BUS)
        MOD_BUS.addListener(EventPriority.LOWEST, ::postRegistry)
    }

    fun onceRegistered(function: () -> Unit) {
        queue.add(function)
    }

    fun <V : T> register(name: String, supplier: () -> V): ObjectHolderDelegate<V> {
        return registry.registerObject(name, supplier)
    }

    fun createTag(name: String) = registry.createTagKey(name)

    private fun postRegistry(event: RegisterEvent) {
        if (event.registryKey == registry.registryKey) {
            while (queue.isNotEmpty()) {
                queue.removeFirst().invoke()
            }
        }
    }
}