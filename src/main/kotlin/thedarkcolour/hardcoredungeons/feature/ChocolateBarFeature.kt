package thedarkcolour.hardcoredungeons.feature

import com.mojang.serialization.Codec
import net.minecraft.core.BlockPos
import net.minecraft.world.ISeedReader
import net.minecraft.world.gen.ChunkGenerator
import net.minecraft.world.gen.feature.Feature
import net.minecraft.world.gen.feature.NoFeatureConfig
import thedarkcolour.hardcoredungeons.block.HBlocks
import java.util.*

class ChocolateBarFeature(codec: Codec<NoFeatureConfig>) : Feature<NoFeatureConfig>(codec) {
    override fun place(
        worldIn: ISeedReader,
        generator: ChunkGenerator,
        rand: Random,
        pos: BlockPos,
        config: NoFeatureConfig,
    ): Boolean {
        if (!worldIn.getBlockState(pos.below()).canOcclude()) return false

        for (a in BlockPos.betweenClosed(pos, pos.offset(2, 6, 0))) {
            setBlock(worldIn, a, HBlocks.CHOCOLATE_BLOCK.block.defaultBlockState())
        }

        return true
    }
}