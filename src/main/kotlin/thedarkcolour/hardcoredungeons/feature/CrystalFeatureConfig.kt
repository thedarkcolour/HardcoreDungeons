package thedarkcolour.hardcoredungeons.feature

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.gen.blockstateprovider.BlockStateProvider
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider
import net.minecraft.world.gen.feature.IFeatureConfig

class CrystalFeatureConfig(val crystalProvider: BlockStateProvider, val chance: Float) : IFeatureConfig {
    constructor(state: BlockState, chance: Float): this(SimpleBlockStateProvider(state), chance)

    companion object {
        val CODEC: Codec<CrystalFeatureConfig> = RecordCodecBuilder.create { builder ->
            builder.group(
                BlockStateProvider.CODEC.fieldOf("crystal_provider").forGetter(CrystalFeatureConfig::crystalProvider),
                Codec.FLOAT.fieldOf("chance").forGetter(CrystalFeatureConfig::chance),
            ).apply(builder, ::CrystalFeatureConfig)
        }
    }
}