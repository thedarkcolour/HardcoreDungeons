package thedarkcolour.hardcoredungeons.registry

import net.minecraft.util.RegistryKey
import net.minecraft.util.registry.Registry
import thedarkcolour.hardcoredungeons.util.modLoc

/**
 * @author thedarkcolour
 */
@Suppress("HasPlatformType")
object HDimensions {
    @JvmField val CASTLETON_ID = modLoc("castleton")
    @JvmField val RAINBOWLAND_ID = modLoc("rainbowland")
    @JvmField val AUBRUM_ID = modLoc("aubrum")
    @JvmField val CANDYLAND_ID = modLoc("candyland")

    val CASTLETON_KEY = RegistryKey.create(Registry.DIMENSION_REGISTRY, CASTLETON_ID)
    val RAINBOWLAND_KEY = RegistryKey.create(Registry.DIMENSION_REGISTRY, RAINBOWLAND_ID)
    val AUBRUM_KEY = RegistryKey.create(Registry.DIMENSION_REGISTRY, AUBRUM_ID)
    val CANDYLAND_KEY = RegistryKey.create(Registry.DIMENSION_REGISTRY, CANDYLAND_ID)
}