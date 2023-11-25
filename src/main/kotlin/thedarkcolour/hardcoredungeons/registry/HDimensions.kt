package thedarkcolour.hardcoredungeons.registry

import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
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

    val CASTLETON_KEY = ResourceKey.create(Registries.DIMENSION, CASTLETON_ID)
    val RAINBOWLAND_KEY = ResourceKey.create(Registries.DIMENSION, RAINBOWLAND_ID)
    val AUBRUM_KEY = ResourceKey.create(Registries.DIMENSION, AUBRUM_ID)
    val CANDYLAND_KEY = ResourceKey.create(Registries.DIMENSION, CANDYLAND_ID)
}