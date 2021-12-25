package thedarkcolour.hardcoredungeons.block

import net.minecraft.block.Block
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.eventbus.api.EventPriority
import net.minecraftforge.registries.IForgeRegistry
import net.minecraftforge.registries.IForgeRegistryEntry
import thedarkcolour.hardcoredungeons.HardcoreDungeons
import thedarkcolour.kotlinforforge.forge.KDeferredRegister
import thedarkcolour.kotlinforforge.forge.MOD_BUS
import thedarkcolour.kotlinforforge.forge.ObjectHolderDelegate

open class HRegistry<T : IForgeRegistryEntry<T>>(forgeRegistry: IForgeRegistry<T>) {
    private val queue = ArrayDeque<() -> Unit>()
    val registry = KDeferredRegister(forgeRegistry, HardcoreDungeons.ID) // do not register directly

    // register here instead
    fun init() {
        registry.register(MOD_BUS)
        MOD_BUS.addGenericListener(EventPriority.LOWEST, ::postRegistry)
    }

    fun onceRegistered(function: () -> Unit) {
        queue.add(function)
    }

    open fun <V : T> register(name: String, supplier: () -> V): ObjectHolderDelegate<V> {
        return registry.registerObject(name, supplier)
    }

    protected fun postRegistry(event: RegistryEvent.Register<Block>) {
        while (queue.isNotEmpty()) {
            queue.removeFirst().invoke()
        }
    }
}