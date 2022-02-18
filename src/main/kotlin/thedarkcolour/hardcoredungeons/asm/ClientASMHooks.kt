package thedarkcolour.hardcoredungeons.asm

import net.minecraft.client.Minecraft
import net.minecraft.util.math.vector.Vector3f
import thedarkcolour.hardcoredungeons.registry.HDimensions

object ClientASMHooks {
    val minecraft = Minecraft.getInstance()

    @JvmStatic
    fun modifyLightTexture(vec: Vector3f?, f6: Float) {
        if (minecraft.level?.dimension() == HDimensions.CASTLETON_KEY) {
            val f6 = f6 * 0.7f
            val f7 = f6 * ((f6 * 0.6f + 0.4f) * 0.6f + 0.4f)
            val f8 = f6 * (f6 * f6 * 0.6f + 0.4f)

            vec?.set(f7, f8, f6)
        }
    }
}