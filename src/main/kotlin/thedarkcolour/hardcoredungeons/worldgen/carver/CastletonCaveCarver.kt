package thedarkcolour.hardcoredungeons.worldgen.carver

import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.Blocks
import net.minecraft.core.Direction
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.biome.Biome
import net.minecraft.world.chunk.IChunk
import net.minecraft.world.gen.carver.CaveWorldCarver
import net.minecraft.world.gen.feature.ProbabilityConfig
import net.minecraft.world.level.biome.Biome
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.levelgen.carver.CaveCarverConfiguration
import net.minecraft.world.level.levelgen.carver.CaveWorldCarver
import org.apache.commons.lang3.mutable.MutableBoolean
import thedarkcolour.hardcoredungeons.registry.HBlocks
import java.util.*
import java.util.function.Function

class CastletonCaveCarver : CaveWorldCarver(CaveCarverConfiguration.CODEC), ICarver {
    init {
        replaceableBlocks = CASTLETON_BLOCKS_FOR_CARVING
    }

    override fun getThickness(random: Random): Float {
        return super.getThickness(random) * 1.2f
    }

    override fun getYScale(): Double {
        return 3.0
    }

    override fun canReplaceBlock(state: BlockState, aboveState: BlockState): Boolean {
        return super.canReplaceBlock(state, aboveState)
    }

    override fun carveBlock(chunk: IChunk, getBiome: Function<BlockPos, Biome>, carveMask: BitSet, random: Random, pos1: BlockPos.Mutable, pos2: BlockPos.Mutable, pos3: BlockPos.Mutable, p_230358_8_: Int, p_230358_9_: Int, p_230358_10_: Int, p_230358_11_: Int, p_230358_12_: Int, p_230358_13_: Int, p_230358_14_: Int, p_230358_15_: Int, p_230358_16_: MutableBoolean): Boolean {
        return Companion.carveBlockNoLava(this, chunk, getBiome, carveMask, pos1, pos2, pos3, p_230358_11_, p_230358_12_, p_230358_13_, p_230358_14_, p_230358_15_, p_230358_16_)
    }

    companion object {
        val CASTLETON_BLOCKS_FOR_CARVING = setOf(HBlocks.CASTLETON_SOIL.soil, HBlocks.CASTLETON_SOIL.loam, HBlocks.CASTLETON_SOIL.grass, HBlocks.CASTLETON_STONE.stone.block)

        fun carveBlockNoLava(carver: ICarver, chunk: IChunk, getBiome: Function<BlockPos, Biome>, carveMask: BitSet, pos1: BlockPos.Mutable, pos2: BlockPos.Mutable, pos3: BlockPos.Mutable, p_230358_11_: Int, p_230358_12_: Int, p_230358_13_: Int, p_230358_14_: Int, p_230358_15_: Int, p_230358_16_: MutableBoolean): Boolean {
            val i = p_230358_13_ or (p_230358_15_ shl 4) or (p_230358_14_ shl 8)
            return if (carveMask[i]) {
                false
            } else {
                carveMask.set(i)
                pos1[p_230358_11_, p_230358_14_] = p_230358_12_
                val blockstate = chunk.getBlockState(pos1)
                val blockstate1 = chunk.getBlockState(pos2.setWithOffset(pos1, Direction.UP))
                if (blockstate.`is`(Blocks.GRASS_BLOCK) || blockstate.`is`(Blocks.MYCELIUM)) {
                    p_230358_16_.setTrue()
                }
                if (!carver.canReplaceBlock(blockstate, blockstate1)) {
                    false
                } else {
                    if (p_230358_14_ < 11) {
                        chunk.setBlockState(pos1, WATER.createLegacyBlock(), false) // water instead of lava
                    } else {
                        chunk.setBlockState(pos1, CAVE_AIR, false)
                        if (p_230358_16_.isTrue) {
                            pos3.setWithOffset(pos1, Direction.DOWN)
                            if (chunk.getBlockState(pos3).`is`(Blocks.DIRT)) {
                                chunk.setBlockState(pos3, getBiome.apply(pos1).generationSettings.topMaterial, false)
                            }
                        }
                    }
                    true
                }
            }
        }
    }
}