package thedarkcolour.hardcoredungeons.block.decoration.rainbowland

import net.minecraft.world.level.block.state.BlockState
import net.minecraft.block.GlassBlock
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IWorldReader
import thedarkcolour.hardcoredungeons.block.base.properties.HProperties
import thedarkcolour.hardcoredungeons.client.color.RainbowColor

class RainbowGlassBlock(properties: HProperties) : GlassBlock(properties.build()) {
    override fun getBeaconColorMultiplier(state: BlockState?, world: IWorldReader?, pos: BlockPos, beaconPos: BlockPos?): FloatArray {
        return RainbowColor.getBeaconColor(pos)
    }
}