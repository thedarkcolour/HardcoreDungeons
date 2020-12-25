package thedarkcolour.hardcoredungeons.registry

import net.minecraft.util.RegistryKey
import net.minecraft.util.registry.Registry
import thedarkcolour.hardcoredungeons.util.modLoc

/**
 * @author TheDarkColour
 */
@Suppress("HasPlatformType")
object HDimensions {
    @JvmField val CASTLETON_ID = modLoc("castleton")
    @JvmField val RAINBOWLAND_ID = modLoc("rainbowland")
    @JvmField val AUBRUM_ID = modLoc("aubrum")
    @JvmField val CANDYLAND_ID = modLoc("candyland")

    val CASTLETON_KEY = RegistryKey.getOrCreateKey(Registry.WORLD_KEY, CASTLETON_ID)
    val RAINBOWLAND_KEY = RegistryKey.getOrCreateKey(Registry.WORLD_KEY, RAINBOWLAND_ID)
    val AUBRUM_KEY = RegistryKey.getOrCreateKey(Registry.WORLD_KEY, AUBRUM_ID)
    val CANDYLAND_KEY = RegistryKey.getOrCreateKey(Registry.WORLD_KEY, CANDYLAND_ID)
}