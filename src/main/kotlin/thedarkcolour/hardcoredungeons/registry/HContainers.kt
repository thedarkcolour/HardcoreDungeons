package thedarkcolour.hardcoredungeons.registry

import net.minecraft.client.gui.ScreenManager
import net.minecraft.inventory.container.ContainerType
import net.minecraft.util.ResourceLocation
import net.minecraftforge.registries.IForgeRegistry
import net.minecraftforge.registries.IForgeRegistryEntry
import net.minecraftforge.registries.ObjectHolderRegistry
import net.minecraftforge.registries.RegistryManager
import thedarkcolour.hardcoredungeons.client.screen.ExtractorScreen
import thedarkcolour.hardcoredungeons.container.DungeonLootContainer
import thedarkcolour.hardcoredungeons.container.ExtractorContainer
import thedarkcolour.hardcoredungeons.container.HContainerType
import java.util.*
import java.util.function.Consumer
import java.util.function.Predicate
import kotlin.collections.HashMap
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

// todo make custom container type that makes doing #openContainer more simple
@Suppress("HasPlatformType")
object HContainers {
    val EXTRACTOR = HContainerType(::ExtractorContainer).setRegistryKey("extractor")
    val DUNGEON_LOOT = HContainerType(::DungeonLootContainer).setRegistryKey("dungeon_loot")

    @Suppress("UnstableApiUsage")
    inline fun <reified T : IForgeRegistryEntry<in T>> objectHolder(loc: ResourceLocation): ReadOnlyProperty<Any?, T> {
        return ObjectHolderDelegate(loc, ObjectHolderDelegate.getRegistry(T::class.java))
    }

    fun registerContainerTypes(containers: IForgeRegistry<ContainerType<*>>) {
        containers.register(EXTRACTOR)
        containers.register(HContainerType(::ExtractorContainer).setRegistryKey("a"))
    }

    fun registerScreens() {
        ScreenManager.registerFactory(EXTRACTOR, ::ExtractorScreen)
    }

    data class ObjectHolderDelegate<T : IForgeRegistryEntry<in T>>(
        private val registryName: ResourceLocation,
        private val registry: IForgeRegistry<*>,
    ) : ReadOnlyProperty<Any?, T>, Consumer<Predicate<ResourceLocation>> {

        private lateinit var value: T

        init {
            ObjectHolderRegistry.addHandler(this)
        }

        override fun getValue(thisRef: Any?, property: KProperty<*>): T {
            return value
        }

        override fun accept(filter: Predicate<ResourceLocation>) {
            if (!filter.test(registry.registryName)) {
                return
            }

            if (registry.containsKey(registryName)) {
                val tempValue = registry.getValue(registryName)

                if (tempValue != null) {
                    value = tempValue as T
                } /*else {
                    LOGGER.debug("Unable to lookup value for $this, likely just mod options.")
                }*/
            }
        }

        companion object {
            private val TYPE_2_REGISTRY = HashMap<Class<*>, IForgeRegistry<*>>()

            fun getRegistry(clazz: Class<*>): IForgeRegistry<*> {
                return TYPE_2_REGISTRY.computeIfAbsent(clazz, ::findRegistry)
            }

            private fun findRegistry(clazz: Class<*>): IForgeRegistry<*> {
                val typeQueue = LinkedList<Class<*>>()
                var registry: IForgeRegistry<*>? = null

                typeQueue.add(clazz)

                while (typeQueue.isNotEmpty() && registry == null) {
                    val type = typeQueue.remove()
                    typeQueue.addAll(type.interfaces)

                    println(type.name)

                    if (IForgeRegistryEntry::class.java.isAssignableFrom(type)) {
                        registry = RegistryManager.ACTIVE.getRegistry(type)

                        val parent = type.superclass

                        if (parent != null) {
                            typeQueue.add(parent)
                        }
                    }
                }

                return registry
                    ?: throw IllegalArgumentException("Object holder delegate must represent a type that implements IForgeRegistryEntry")
            }
        }
    }
}