package team.rusty.util.biome;

import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ModifiableBiomeInfo;
import net.minecraftforge.registries.RegistryObject;

/**
 * @author thedarkcolour
 */
public class ModBiomeModifier implements BiomeModifier {
    private final ModBiome biome;
    private final RegistryObject<Codec<ModBiomeModifier>> codec;

    public ModBiomeModifier(ModBiome biome, RegistryObject<Codec<ModBiomeModifier>> codec) {
        this.biome = biome;
        this.codec = codec;
    }

    @Override
    public void modify(Holder<Biome> biome, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
        // Do this at the start as if it was in the JSON file already
        if (phase == Phase.BEFORE_EVERYTHING) {
            if (biome.is(this.biome.getId())) {
                this.biome.configure(biome, builder);
            }
        }
    }

    @Override
    public Codec<? extends BiomeModifier> codec() {
        return codec.get();
    }
}
