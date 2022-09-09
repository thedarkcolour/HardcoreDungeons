package team.rusty.util.biome;

import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.biome.AmbientMoodSettings;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ModifiableBiomeInfo;
import net.minecraftforge.registries.RegistryObject;

import java.util.Collections;
import java.util.List;

/**
 * Base biome class for creating a biome.
 *
 * @author TheDarkColour
 */
public abstract class ModBiome {
    /** Default biome effects for new biomes */
    public static final BiomeSpecialEffects DEFAULT_EFFECTS = new BiomeSpecialEffects.Builder().waterColor(4159204).waterFogColor(329011).fogColor(12638463).skyColor(calculateSkyColor(0.7F)).ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS).build();

    /** Package-protected resource key */
    ResourceKey<Biome> id;

    /** Copy + paste from VanillaBiomes */
    public static int calculateSkyColor(float temperature) {
        float f = temperature / 3.0F;
        f = Mth.clamp(f, -1.0F, 1.0F);
        return Mth.hsvToRgb(0.62222224F - f * 0.05F, 0.5F + f * 0.1F, 1.0F);
    }

    /**
     * Add features/structures/carvers, add mob spawns, etc.
     */
    public abstract void configure(Holder<Biome> biome, ModifiableBiomeInfo.BiomeInfo.Builder info);

    /** todo work with Terrablender
     * @return The spawn configuration of this biome, or empty if the biome should not spawn naturally.
     */
    //public List<SpawnEntry> getSpawnEntries() {
    //    return Collections.emptyList();
    //}

    /**
     * Used to reference the biome for spawning structures
     */
    public ResourceKey<Biome> getResourceKey() {
        return id;
    }

    public ResourceLocation getId() {
        return id.location();
    }

    public record SpawnEntry(BiomeManager.BiomeType type, int weight) {
        public static SpawnEntry of(BiomeManager.BiomeType type, int weight) {
            return new SpawnEntry(type, weight);
        }
    }
}
