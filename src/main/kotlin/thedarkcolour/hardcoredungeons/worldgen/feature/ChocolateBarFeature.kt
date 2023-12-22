package thedarkcolour.hardcoredungeons.worldgen.feature

import net.minecraft.core.BlockPos
import net.minecraft.world.level.WorldGenLevel
import net.minecraft.world.level.levelgen.feature.Feature
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration
import thedarkcolour.hardcoredungeons.registry.block.HBlocks

class ChocolateBarFeature : Feature<NoneFeatureConfiguration>(NoneFeatureConfiguration.CODEC) {
    override fun place(pContext: FeaturePlaceContext<NoneFeatureConfiguration>): Boolean {
        return place(pContext.level(), pContext.origin())
    }

    fun place(worldIn: WorldGenLevel, pos: BlockPos): Boolean {
        if (!worldIn.getBlockState(pos.below()).canOcclude()) return false

        for (a in BlockPos.betweenClosed(pos, pos.offset(2, 6, 0))) {
            setBlock(worldIn, a, HBlocks.CHOCOLATE_BLOCK.block.defaultBlockState())
        }

        return true
    }
}