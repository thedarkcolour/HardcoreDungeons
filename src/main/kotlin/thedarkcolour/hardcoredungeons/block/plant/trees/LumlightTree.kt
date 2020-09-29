package thedarkcolour.hardcoredungeons.block.plant.trees

import net.minecraft.block.trees.Tree
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig
import net.minecraft.world.gen.feature.ConfiguredFeature
import net.minecraft.world.gen.feature.Feature
import thedarkcolour.hardcoredungeons.registry.HFeatures
import java.util.*

object LumlightTree : Tree() {
    override fun getTreeFeature(random: Random, hasBeehives: Boolean): ConfiguredFeature<BaseTreeFeatureConfig, *> {
        return Feature.TREE.withConfiguration(HFeatures.LUMLIGHT_TREE_CONFIG)
    }
}