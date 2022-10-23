package thedarkcolour.hardcoredungeons.worldgen

import com.mojang.serialization.Codec
import net.minecraft.core.Holder
import net.minecraft.tags.BiomeTags
import net.minecraft.world.level.biome.Biome
import net.minecraft.world.level.levelgen.GenerationStep
import net.minecraftforge.common.world.BiomeModifier
import net.minecraftforge.common.world.ModifiableBiomeInfo

// the biome modifier system is so annoying for people who don't need anything to be extensible
object GlobalBiomeModifier : BiomeModifier {
    val codec: Codec<GlobalBiomeModifier> = Codec.unit(this)

    override fun modify(biome: Holder<Biome>, phase: BiomeModifier.Phase, builder: ModifiableBiomeInfo.BiomeInfo.Builder) {
        if (phase == BiomeModifier.Phase.ADD) {
            if (biome.`is`(BiomeTags.IS_OVERWORLD)) {
                builder.generationSettings.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, HFeatures.UNDERGROUND_MALACHITE_CRYSTALS)
            }
        }
    }

    override fun codec(): Codec<out BiomeModifier> {
        return codec
    }
}