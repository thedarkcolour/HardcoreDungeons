package thedarkcolour.hardcoredungeons.block.decoration.rainbowland

import net.minecraft.block.BlockState
import net.minecraft.block.PaneBlock
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IWorldReader
import thedarkcolour.hardcoredungeons.block.base.properties.HProperties
import thedarkcolour.hardcoredungeons.client.color.RainbowColor

class RainbowGlassPaneBlock(properties: HProperties) : PaneBlock(properties.build()) {
    override fun getBeaconColorMultiplier(state: BlockState?, world: IWorldReader?, pos: BlockPos, beaconPos: BlockPos?): FloatArray {
        return RainbowColor.getBeaconColor(pos)
    }
}