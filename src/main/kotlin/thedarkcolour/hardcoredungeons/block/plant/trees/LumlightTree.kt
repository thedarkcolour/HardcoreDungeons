package thedarkcolour.hardcoredungeons.block.plant.trees

import net.minecraft.block.trees.Tree
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig
import net.minecraft.world.gen.feature.ConfiguredFeature
import thedarkcolour.hardcoredungeons.feature.HConfiguredFeatures
import java.util.*

object LumlightTree : Tree() {
    override fun getConfiguredFeature(random: Random, hasBeehives: Boolean): ConfiguredFeature<BaseTreeFeatureConfig, *> {
        // todo fix tree being straight
        return HConfiguredFeatures.LUMLIGHT_TREE
    }
}