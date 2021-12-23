package thedarkcolour.hardcoredungeons.feature

import com.mojang.serialization.Codec
import net.minecraft.tags.BlockTags
import net.minecraft.util.Direction
import net.minecraft.core.BlockPos
import net.minecraft.world.ISeedReader
import net.minecraft.world.gen.ChunkGenerator
import net.minecraft.world.gen.feature.Feature
import net.minecraft.world.level.LevelAccessor
import net.minecraft.world.level.chunk.ChunkGenerator
import net.minecraft.world.level.levelgen.feature.Feature
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext
import java.util.*

class CrystalFeature(codec: Codec<CrystalFeatureConfig>) : Feature<CrystalFeatureConfig>(codec) {
    override fun place(
        ctx: FeaturePlaceContext<CrystalFeatureConfig>
    ): Boolean {
        val mutable = pos.mutable().move(Direction.UP, 40)

        for (y in 40 downTo 5) {
            if (reader.isEmptyBlock(mutable)) {
                if (reader.getBlockState(mutable.move(Direction.DOWN)).`is`(BlockTags.BASE_STONE_OVERWORLD)) {
                    if (rand.nextFloat() < config.chance) {
                        setBlock(reader, mutable.move(Direction.UP), config.crystalProvider.getState(rand, mutable))
                        return true
                    }
                }
            } else {
                mutable.move(Direction.DOWN)
            }
        }

        return false
    }
}