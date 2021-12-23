package thedarkcolour.hardcoredungeons.feature

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider

class CrystalFeatureConfig(val crystalProvider: BlockStateProvider, val chance: Float) : FeatureConfiguration {
    constructor(state: BlockState, chance: Float): this(SimpleStateProvider(state), chance)

    companion object {
        val CODEC: Codec<CrystalFeatureConfig> = RecordCodecBuilder.create { builder ->
            builder.group(
                BlockStateProvider.CODEC.fieldOf("crystal_provider").forGetter(CrystalFeatureConfig::crystalProvider),
                Codec.FLOAT.fieldOf("chance").forGetter(CrystalFeatureConfig::chance),
            ).apply(builder, ::CrystalFeatureConfig)
        }
    }
}