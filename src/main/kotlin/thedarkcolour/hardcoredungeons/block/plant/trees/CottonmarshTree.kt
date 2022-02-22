package thedarkcolour.hardcoredungeons.block.plant.trees

import net.minecraft.block.trees.Tree
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig
import net.minecraft.world.gen.feature.ConfiguredFeature
import java.util.*

object CottonmarshTree : Tree() {
    override fun getConfiguredFeature(randomIn: Random, hasBeehives: Boolean): ConfiguredFeature<BaseTreeFeatureConfig, *>? {
        return null
    }
}