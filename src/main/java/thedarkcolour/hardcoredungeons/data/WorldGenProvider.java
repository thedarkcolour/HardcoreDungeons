package thedarkcolour.hardcoredungeons.data;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.carver.CarverConfiguration;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.carver.WorldCarver;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraftforge.common.world.BiomeSpecialEffectsBuilder;
import net.minecraftforge.common.world.ClimateSettingsBuilder;
import net.minecraftforge.common.world.MobSpawnSettingsBuilder;
import net.minecraftforge.common.world.ModifiableBiomeInfo;
import thedarkcolour.hardcoredungeons.HardcoreDungeons;
import thedarkcolour.hardcoredungeons.worldgen.biome.ModBiome;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class WorldGenProvider implements DataProvider {
    // Necessary because biome constructor is package-private
    private static final Codec<ModifiableBiomeInfo.BiomeInfo> BIOME_INFO = RecordCodecBuilder.create(instance -> instance.group(
            Biome.ClimateSettings.CODEC.forGetter(ModifiableBiomeInfo.BiomeInfo::climateSettings),
            BiomeSpecialEffects.CODEC.fieldOf("effects").forGetter(ModifiableBiomeInfo.BiomeInfo::effects),
            BiomeGenerationSettings.CODEC.forGetter(ModifiableBiomeInfo.BiomeInfo::generationSettings),
            MobSpawnSettings.CODEC.forGetter(ModifiableBiomeInfo.BiomeInfo::mobSpawnSettings)
    ).apply(instance, ModifiableBiomeInfo.BiomeInfo::new));
    private static final Codec<List<PlacementModifier>> PLACEMENT_LIST = PlacementModifier.CODEC.listOf();

    private final List<CompletableFuture<?>> results = new ArrayList<>();
    private final PackOutput.PathProvider pathProvider;
    private final CompletableFuture<HolderLookup.Provider> lookupProvider;
    private CachedOutput cachedOutput;
    private RegistryOps<JsonElement> registryOps;
    public HolderLookup.Provider holderLookup;

    public WorldGenProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        this.pathProvider = output.createPathProvider(PackOutput.Target.DATA_PACK, "worldgen");
        this.lookupProvider = lookupProvider;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput output) {
        return lookupProvider.thenAccept(provider -> {
            this.holderLookup = provider;
            this.registryOps = RegistryOps.create(JsonOps.INSTANCE, provider);
            this.cachedOutput = output;
            WorldGenKt.addFeatures(this);
            this.holderLookup = null;
            this.registryOps = null;
            this.cachedOutput = null;
        }).thenCompose(unused -> CompletableFuture.allOf(results.toArray(CompletableFuture[]::new)));
    }

    @Override
    public String getName() {
        return "Hardcore Dungeons worldgen provider";
    }

    public <FC extends FeatureConfiguration> void configuredFeature(ResourceKey<ConfiguredFeature<?, ?>> id, Feature<FC> feature, FC config) {
        Path path = this.pathProvider.json(id.location().withPrefix("configured_feature/"));
        this.results.add(DataProvider.saveStable(this.cachedOutput, encodeJson(ConfiguredFeature.DIRECT_CODEC, new ConfiguredFeature<>(feature, config), id), path));
    }

    public void placedFeature(ResourceKey<PlacedFeature> id, ResourceKey<ConfiguredFeature<?, ?>> configuredFeature, List<PlacementModifier> placements) {
        Path path = this.pathProvider.json(id.location().withPrefix("placed_feature/"));
        JsonObject element = new JsonObject();
        element.addProperty("feature", HardcoreDungeons.ID + ":" + configuredFeature.location().getPath());
        element.add("placement", encodeJson(PLACEMENT_LIST, placements, id));

        this.results.add(DataProvider.saveStable(this.cachedOutput, element, path));
    }

    public <C extends CarverConfiguration> void configuredCarver(ResourceKey<ConfiguredWorldCarver<?>> id, WorldCarver<C> carver, C config) {
        Path path = this.pathProvider.json(id.location().withPrefix("configured_carver/"));
        this.results.add(DataProvider.saveStable(this.cachedOutput, encodeJson(ConfiguredWorldCarver.DIRECT_CODEC, new ConfiguredWorldCarver<>(carver, config), id), path));
    }

    public void biome(ResourceKey<Biome> id, ModBiome biome) {
        Path path = this.pathProvider.json(id.location().withPrefix("biome/"));
        // dummy info
        var info = ModifiableBiomeInfo.BiomeInfo.Builder.copyOf(new ModifiableBiomeInfo.BiomeInfo(
                new Biome.ClimateSettings(false, 0.0f, Biome.TemperatureModifier.NONE, 0.0f),
                new BiomeSpecialEffects.Builder().waterColor(0).skyColor(0).fogColor(0).waterFogColor(0).build(),
                new BiomeGenerationSettings.PlainBuilder().build(),
                new MobSpawnSettings.Builder().build()
        ));
        var configuration = new BiomeConfiguration(info, this.holderLookup);
        biome.configure(configuration);
        this.results.add(DataProvider.saveStable(this.cachedOutput, encodeJson(BIOME_INFO, info.build(), id), path));
    }

    private <T> JsonElement encodeJson(Codec<T> codec, T value, ResourceKey<?> id) {
        return codec.encodeStart(this.registryOps, value).resultOrPartial(error -> {
            HardcoreDungeons.INSTANCE.getLOGGER().error("Failed to encode JSON for {}: {}", id, error);
        }).get();
    }

    public static class BiomeConfiguration {
        private final ModifiableBiomeInfo.BiomeInfo.Builder builder;
        private final BiomeGenSettingsWrapper wrappedGenerationSettings;

        public BiomeConfiguration(ModifiableBiomeInfo.BiomeInfo.Builder builder, HolderLookup.Provider lookupProvider) {
            this.builder = builder;
            this.wrappedGenerationSettings = new BiomeGenSettingsWrapper(builder.getGenerationSettings(), lookupProvider);
        }

        public BiomeGenSettingsWrapper getGenerationSettings() {
            return this.wrappedGenerationSettings;
        }

        public MobSpawnSettingsBuilder getMobSpawnSettings() {
            return this.builder.getMobSpawnSettings();
        }

        public ClimateSettingsBuilder getClimateSettings() {
            return this.builder.getClimateSettings();
        }
        public BiomeSpecialEffectsBuilder getSpecialEffects() {
            return this.builder.getSpecialEffects();
        }
    }

    public static class BiomeGenSettingsWrapper extends BiomeGenerationSettings.Builder {
        private final BiomeGenerationSettings.PlainBuilder original;
        private final HolderLookup.RegistryLookup<PlacedFeature> placedFeatures;
        private final HolderLookup.RegistryLookup<ConfiguredWorldCarver<?>> configuredCarvers;

        public BiomeGenSettingsWrapper(BiomeGenerationSettings.PlainBuilder original, HolderLookup.Provider lookupProvider) {
            super(null, null);

            this.original = original;
            this.placedFeatures = lookupProvider.lookupOrThrow(Registries.PLACED_FEATURE);
            this.configuredCarvers = lookupProvider.lookupOrThrow(Registries.CONFIGURED_CARVER);
        }

        @Override
        public BiomeGenSettingsWrapper addFeature(GenerationStep.Decoration decoration, ResourceKey<PlacedFeature> feature) {
            original.addFeature(decoration.ordinal(), Holder.Reference.createStandAlone(placedFeatures, feature));
            return this;
        }

        @Override
        public BiomeGenSettingsWrapper addCarver(GenerationStep.Carving carving, ResourceKey<ConfiguredWorldCarver<?>> carver) {
            original.addCarver(carving, Holder.Reference.createStandAlone(configuredCarvers, carver));
            return this;
        }
    }
}
