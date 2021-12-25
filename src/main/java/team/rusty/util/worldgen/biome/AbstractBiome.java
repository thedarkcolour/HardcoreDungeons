package team.rusty.util.worldgen.biome;

import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeAmbience;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.biome.MoodSoundAmbience;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;

import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.concurrent.Immutable;
import java.util.Collections;
import java.util.List;

/**
 * Base biome class for creating a biome.
 *
 * @author TheDarkColour
 */
public abstract class AbstractBiome {
    /** Default biome effects for new biomes */
    public static final BiomeAmbience DEFAULT_EFFECTS = new BiomeAmbience.Builder().waterColor(4159204).waterFogColor(329011).fogColor(12638463).skyColor(calculateSkyColor(0.7F)).ambientMoodSound(MoodSoundAmbience.LEGACY_CAVE_SETTINGS).build();

    /** Vanilla biome category */
    protected Biome.Category category = Biome.Category.PLAINS;
    /** Negative depth counts as ocean */
    protected float depth = 0.1f;
    /** Vertical stretch */
    protected float scale = 0.1f;
    /** Biome water/sky/fog colors, particles, and music. Basically anything clientside */
    protected BiomeAmbience effects = DEFAULT_EFFECTS;
    /** Which type of weather effects are displayed when it is raining. */
    protected Biome.RainType precipitation = Biome.RainType.RAIN;
    protected Biome.TemperatureModifier tempMod = Biome.TemperatureModifier.NONE;
    /** Affects melting of ice and snow */
    protected float temperature = 0.0f;
    /** Affects grass/foliage color. If greater than {@literal 0.85f}, the biome is considered humid. */
    protected float downfall = 0.0f;

    /** Copy + paste from VanillaBiomes */
    public static int calculateSkyColor(float temperature) {
        float f = temperature / 3.0F;
        f = MathHelper.clamp(f, -1.0F, 1.0F);
        return MathHelper.hsvToRgb(0.62222224F - f * 0.05F, 0.5F + f * 0.1F, 1.0F);
    }

    /**
     * Configure biome properties, add features/structures/carvers, add mob spawns, etc.
     */
    @ParametersAreNonnullByDefault
    public abstract void configure(BiomeGenerationSettingsBuilder generation, MobSpawnInfo.Builder spawns);

    /**
     * @return The spawn configuration of this biome, or empty if the biome should not spawn naturally.
     */
    public List<SpawnEntry> getSpawnEntries() {
        return Collections.emptyList();
    }

    public Biome.Climate getClimate() {
        return new Biome.Climate(precipitation, temperature, tempMod, downfall);
    }

    public Biome.Category getCategory() {
        return category;
    }

    public float getDepth() {
        return depth;
    }

    public float getScale() {
        return scale;
    }

    public BiomeAmbience getEffects() {
        return effects;
    }

    public static final class SpawnEntry {
        private final BiomeManager.BiomeType type;
        private final int weight;

        public SpawnEntry(BiomeManager.BiomeType type, int weight) {
            this.type = type;
            this.weight = weight;
        }

        public static SpawnEntry of(BiomeManager.BiomeType type, int weight) {
            return new SpawnEntry(type, weight);
        }

        public BiomeManager.BiomeType type() {
            return type;
        }

        public int weight() {
            return weight;
        }
    }
}
