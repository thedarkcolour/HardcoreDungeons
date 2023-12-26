package thedarkcolour.hardcoredungeons.worldgen.feature

import com.mojang.serialization.Codec
import net.minecraft.world.level.levelgen.feature.Feature
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration

class HangingLanternFeature(codec: Codec<BlockStateConfiguration>) : Feature<BlockStateConfiguration>(codec) {
    override fun place(ctx: FeaturePlaceContext<BlockStateConfiguration>): Boolean {
        TODO("not implemented")
    }
}