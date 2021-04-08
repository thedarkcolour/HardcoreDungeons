package thedarkcolour.hardcoredungeons.client.dimension

import net.minecraft.client.world.DimensionRenderInfo
import net.minecraft.util.math.vector.Vector3d

object CastletonEffects : DimensionRenderInfo(Float.NaN, true, FogType.NONE, false, true) {
    override fun func_230494_a_(p_230494_1_: Vector3d, p_230494_2_: Float): Vector3d {
        return p_230494_1_
    }

    // Render thick fog?
    override fun func_230493_a_(x: Int, z: Int): Boolean {
        return false
    }

    override fun func_230492_a_(p_230492_1_: Float, p_230492_2_: Float): FloatArray? {
        return null
    }
}