package thedarkcolour.hardcoredungeons.registry

import net.minecraft.util.math.Vec3d
import net.minecraftforge.common.ModDimension
import net.minecraftforge.registries.IForgeRegistry
import thedarkcolour.hardcoredungeons.dimension.HDimensionType
import thedarkcolour.hardcoredungeons.dimension.aubrum.AubrumDimension
import thedarkcolour.hardcoredungeons.dimension.castleton.CastletonDimension
import thedarkcolour.hardcoredungeons.dimension.rainbowland.RainbowlandDimension
import java.awt.Color

/**
 * @author TheDarkColour, genericrandom64
 */
object HDimensions {
    val CASTLETON = HDimensionType(::CastletonDimension).setRegistryKey("castleton")
    val RAINBOWLAND = HDimensionType(::RainbowlandDimension).setRegistryKey("rainbowland")
    val AUBRUM = HDimensionType(::AubrumDimension).setRegistryKey("aubrum")

    fun registerDimensions(dimensions: IForgeRegistry<ModDimension>) {
        dimensions.register(CASTLETON)
        dimensions.register(RAINBOWLAND)
        dimensions.register(AUBRUM)
    }

    fun registerDimensionTypes() {
        CASTLETON.register()
        RAINBOWLAND.register()
        AUBRUM.register()
    }

    fun color(r: Int, g: Int, b: Int): Vec3d {
        return Vec3d(r / 255.0, g / 255.0, b / 255.0)
    }

    fun color(color: Int): Vec3d {
        val rgb = Color(color)

        return color(rgb.red, rgb.green, rgb.blue)
    }
}