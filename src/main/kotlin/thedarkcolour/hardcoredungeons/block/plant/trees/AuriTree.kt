package thedarkcolour.hardcoredungeons.block.plant.trees

import net.minecraft.block.trees.Tree
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig
import net.minecraft.world.gen.feature.ConfiguredFeature
import thedarkcolour.hardcoredungeons.worldgen.feature.HConfiguredFeatures
import java.util.*

object AuriTree : Tree() {
    override fun getConfiguredFeature(random: Random, hasBeehives: Boolean): ConfiguredFeature<BaseTreeFeatureConfig, *>? {
        return HConfiguredFeatures.AURI_TREE
    }
}