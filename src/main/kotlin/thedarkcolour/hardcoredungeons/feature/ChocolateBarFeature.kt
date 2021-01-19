package thedarkcolour.hardcoredungeons.feature

import com.mojang.serialization.Codec
import net.minecraft.util.math.BlockPos
import net.minecraft.world.ISeedReader
import net.minecraft.world.gen.ChunkGenerator
import net.minecraft.world.gen.feature.Feature
import net.minecraft.world.gen.feature.NoFeatureConfig
import thedarkcolour.hardcoredungeons.registry.HBlocks
import java.util.*

class ChocolateBarFeature(codec: Codec<NoFeatureConfig>) : Feature<NoFeatureConfig>(codec) {
    override fun func_241855_a(
        worldIn: ISeedReader,
        generator: ChunkGenerator,
        rand: Random,
        pos: BlockPos,
        config: NoFeatureConfig,
    ): Boolean {
        if (!worldIn.getBlockState(pos.down()).isSolid) return false

        for (a in BlockPos.getAllInBoxMutable(pos, pos.add(2, 6, 0))) {
            setBlockState(worldIn, a, HBlocks.CHOCOLATE_BLOCK.defaultState)
        }

        return true
    }
}