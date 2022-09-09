package thedarkcolour.hardcoredungeons.worldgen.carver

import net.minecraft.world.level.block.state.BlockState
import net.minecraft.core.BlockPos
import net.minecraft.world.biome.Biome
import net.minecraft.world.chunk.IChunk
import net.minecraft.world.gen.carver.CanyonWorldCarver
import net.minecraft.world.gen.feature.ProbabilityConfig
import org.apache.commons.lang3.mutable.MutableBoolean
import java.util.*
import java.util.function.Function

class CastletonCanyonCarver : CanyonWorldCarver(ProbabilityConfig.CODEC), ICarver {
    init {
        replaceableBlocks = CastletonCaveCarver.CASTLETON_BLOCKS_FOR_CARVING
    }

    override fun canReplaceBlock(state: BlockState, aboveState: BlockState): Boolean {
        return super.canReplaceBlock(state, aboveState)
    }

    override fun carveBlock(chunk: IChunk, getBiome: Function<BlockPos, Biome>, carveMask: BitSet, random: Random, pos1: BlockPos.Mutable, pos2: BlockPos.Mutable, pos3: BlockPos.Mutable, p_230358_8_: Int, p_230358_9_: Int, p_230358_10_: Int, p_230358_11_: Int, p_230358_12_: Int, p_230358_13_: Int, p_230358_14_: Int, p_230358_15_: Int, p_230358_16_: MutableBoolean): Boolean {
        return CastletonCaveCarver.carveBlockNoLava(this, chunk, getBiome, carveMask, pos1, pos2, pos3, p_230358_11_, p_230358_12_, p_230358_13_, p_230358_14_, p_230358_15_, p_230358_16_)
    }
}