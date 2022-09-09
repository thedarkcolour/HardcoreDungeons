package thedarkcolour.hardcoredungeons.worldgen.carver

import net.minecraft.world.level.block.state.BlockState

interface ICarver {
    fun canReplaceBlock(state: BlockState, aboveState: BlockState): Boolean
}