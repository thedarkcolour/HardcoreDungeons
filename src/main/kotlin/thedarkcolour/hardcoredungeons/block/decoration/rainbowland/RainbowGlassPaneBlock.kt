package thedarkcolour.hardcoredungeons.block.decoration.rainbowland

import net.minecraft.world.level.block.state.BlockState
import net.minecraft.core.BlockPos
import net.minecraft.world.level.LevelReader
import net.minecraft.world.level.block.IronBarsBlock
import thedarkcolour.hardcoredungeons.block.base.properties.HProperties
import thedarkcolour.hardcoredungeons.client.color.RainbowColor

class RainbowGlassPaneBlock(properties: HProperties) : IronBarsBlock(properties.build()) {
    override fun getBeaconColorMultiplier(state: BlockState?, world: LevelReader?, pos: BlockPos, beaconPos: BlockPos?): FloatArray {
        return RainbowColor.getBeaconColor(pos)
    }
}