package thedarkcolour.hardcoredungeons.worldgen.carver

import net.minecraft.block.BlockState

interface ICarver {
    fun canReplaceBlock(state: BlockState, aboveState: BlockState): Boolean
}