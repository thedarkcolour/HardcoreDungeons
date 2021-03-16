package thedarkcolour.hardcoredungeons.feature

import com.mojang.serialization.Codec
import net.minecraft.tags.BlockTags
import net.minecraft.util.Direction
import net.minecraft.util.math.BlockPos
import net.minecraft.world.ISeedReader
import net.minecraft.world.gen.ChunkGenerator
import net.minecraft.world.gen.feature.Feature
import java.util.*

class CrystalFeature(codec: Codec<CrystalFeatureConfig>) : Feature<CrystalFeatureConfig>(codec) {
    override fun generate(
        reader: ISeedReader,
        generator: ChunkGenerator,
        rand: Random,
        pos: BlockPos,
        config: CrystalFeatureConfig,
    ): Boolean {
        val mutable = pos.toMutable().move(Direction.UP, 40)

        for (y in 40 downTo 5) {
            if (reader.isAirBlock(mutable)) {
                if (reader.getBlockState(mutable.move(Direction.DOWN)).isIn(BlockTags.BASE_STONE_OVERWORLD)) {
                    if (rand.nextFloat() < config.chance) {
                        setBlockState(reader, mutable.move(Direction.UP), config.crystalProvider.getBlockState(rand, mutable))
                        //println(mutable)
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