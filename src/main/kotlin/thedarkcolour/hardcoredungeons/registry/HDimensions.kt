package thedarkcolour.hardcoredungeons.registry

//import net.minecraftforge.common.ModDimension
import net.minecraft.util.RegistryKey
import net.minecraft.util.ResourceLocation
import net.minecraft.util.math.vector.Vector3d
import net.minecraft.util.registry.Registry
import thedarkcolour.hardcoredungeons.HardcoreDungeons
import java.awt.Color

/**
 * @author TheDarkColour, genericrandom64
 */
object HDimensions {
    val CASTLETON = RegistryKey.getOrCreateKey(Registry.WORLD_KEY, ResourceLocation(HardcoreDungeons.ID, "castleton"))
    val RAINBOWLAND = RegistryKey.getOrCreateKey(Registry.WORLD_KEY, ResourceLocation(HardcoreDungeons.ID, "rainbowland"))
    val AUBRUM = RegistryKey.getOrCreateKey(Registry.WORLD_KEY, ResourceLocation(HardcoreDungeons.ID, "aubrum"))

    fun color(r: Int, g: Int, b: Int): Vector3d {
        return Vector3d(r / 255.0, g / 255.0, b / 255.0)
    }

    fun color(color: Int): Vector3d {
        val rgb = Color(color)

        return color(rgb.red, rgb.green, rgb.blue)
    }
}