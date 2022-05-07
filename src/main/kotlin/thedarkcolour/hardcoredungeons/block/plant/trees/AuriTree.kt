package thedarkcolour.hardcoredungeons.block.plant.trees

import net.minecraft.block.trees.Tree
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig
import net.minecraft.world.gen.feature.ConfiguredFeature
import thedarkcolour.hardcoredungeons.worldgen.feature.HWorldGen
import java.util.*

object AuriTree : Tree() {
    override fun getConfiguredFeature(random: Random, hasBeehives: Boolean): ConfiguredFeature<BaseTreeFeatureConfig, *>? {
        return HWorldGen.AURI_TREE
    }
}