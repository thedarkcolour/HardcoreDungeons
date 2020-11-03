package thedarkcolour.hardcoredungeons.registry

import net.minecraft.util.RegistryKey
import net.minecraft.util.math.vector.Vector3d
import net.minecraft.util.registry.Registry
import thedarkcolour.hardcoredungeons.util.modLoc
import java.awt.Color

/**
 * @author TheDarkColour
 */
object HDimensions {
    @JvmField val CASTLETON_ID = modLoc("castleton")
    @JvmField val RAINBOWLAND_ID = modLoc("rainbowland")
    @JvmField val AUBRUM_ID = modLoc("aubrum")

    val CASTLETON_KEY = RegistryKey.getOrCreateKey(Registry.WORLD_KEY, CASTLETON_ID)
    val RAINBOWLAND_KEY = RegistryKey.getOrCreateKey(Registry.WORLD_KEY, RAINBOWLAND_ID)
    val AUBRUM_KEY = RegistryKey.getOrCreateKey(Registry.WORLD_KEY, AUBRUM_ID)

    fun color(r: Int, g: Int, b: Int): Vector3d {
        return Vector3d(r / 255.0, g / 255.0, b / 255.0)
    }

    fun color(color: Int): Vector3d {
        val rgb = Color(color)

        return color(rgb.red, rgb.green, rgb.blue)
    }
}