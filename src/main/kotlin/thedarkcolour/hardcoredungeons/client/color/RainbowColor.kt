package thedarkcolour.hardcoredungeons.client.color

import net.minecraft.world.level.block.state.BlockState
import net.minecraft.client.Minecraft
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos
import net.minecraft.world.FoliageColors
import net.minecraft.world.IBlockDisplayReader
import java.awt.Color

object RainbowColor : HColor {
    override fun getColor(state: BlockState?, worldIn: IBlockDisplayReader?, pos: BlockPos?, tintIndex: Int): Int {
        return if (pos == null) {
            FoliageColors.getDefaultColor()
        } else {
            getColor(pos.x, pos.y, pos.z)
        }
    }

    override fun getColor(stack: ItemStack, tintIndex: Int): Int {
        return Minecraft.getInstance().player?.let { player ->
            val pos = player.blockPosition()
            getColor(pos.x, pos.y, pos.z)
        } ?: getColor(0, 70, 0)
    }

    fun getBeaconColor(pos: BlockPos): FloatArray {
        val col = getColor(pos.x, pos.y, pos.z)

        val i = (col and 0xff0000) shr 16
        val j = (col and 0x00ff00) shr 8
        val k = (col and 0x0000ff) shr 0

        return floatArrayOf(i / 255.0f, j / 255.0f, k / 255.0f)
    }

    private fun getColor(posX: Int, posY: Int, posZ: Int): Int {
        val hue: Int
        val saturation = 200
        var brightness = posY
        brightness += 160
        brightness = brightness.coerceAtMost(255)
        var x = posX % 180
        var z = posZ % 180
        val x1 = posX - x
        val z1 = posZ - z
        x += x1 shl 6 + 2
        z += z1 shl 6 + 2

        hue = x * 2 + z * 2

        return Color.HSBtoRGB(hue / 255.0f, saturation / 255.0f, brightness / 255.0f)
    }
}