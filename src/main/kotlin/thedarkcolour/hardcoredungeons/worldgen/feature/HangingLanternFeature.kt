package thedarkcolour.hardcoredungeons.worldgen.feature

import com.mojang.serialization.Codec
import net.minecraft.util.math.BlockPos
import net.minecraft.world.ISeedReader
import net.minecraft.world.gen.ChunkGenerator
import net.minecraft.world.gen.feature.BlockStateFeatureConfig
import net.minecraft.world.gen.feature.Feature
import java.util.*

class HangingLanternFeature(codec: Codec<BlockStateFeatureConfig>) : Feature<BlockStateFeatureConfig>(codec) {
    override fun place(
        level: ISeedReader,
        generator: ChunkGenerator,
        random: Random,
        pos: BlockPos,
        config: BlockStateFeatureConfig
    ): Boolean {
        TODO()
    }
}