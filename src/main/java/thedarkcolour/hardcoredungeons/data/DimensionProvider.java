package thedarkcolour.hardcoredungeons.data;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.FixedBiomeSource;
import net.minecraft.world.level.biome.MultiNoiseBiomeSource;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.world.BiomeSpecialEffectsBuilder;
import net.minecraftforge.common.world.ClimateSettingsBuilder;
import net.minecraftforge.common.world.MobSpawnSettingsBuilder;
import net.minecraftforge.common.world.ModifiableBiomeInfo;
import thedarkcolour.hardcoredungeons.HardcoreDungeons;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

// Dimensions, Dimension Types, Biomes
public class DimensionProvider implements DataProvider {
    private final List<CompletableFuture<?>> results = new ArrayList<>();
    private final Path dataFolder;
    private final CompletableFuture<HolderLookup.Provider> lookupProvider;
    private CachedOutput cachedOutput;
    private RegistryOps<JsonElement> registryOps;
    public HolderLookup.Provider holderLookup;

    public DimensionProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        this.dataFolder = output.getOutputFolder(PackOutput.Target.DATA_PACK);
        this.lookupProvider = lookupProvider;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput output) {
        return lookupProvider.thenAccept(provider -> {
            this.holderLookup = provider;
            this.registryOps = RegistryOps.create(JsonOps.INSTANCE, provider);
            this.cachedOutput = output;
            WorldGenKt.addDimensionsAndBiomes(this);
            this.holderLookup = null;
            this.registryOps = null;
            this.cachedOutput = null;
        }).thenCompose(unused -> CompletableFuture.allOf(results.toArray(CompletableFuture[]::new)));
    }

    @Override
    public String getName() {
        return "Hardcore Dungeons Dimensions and Biomes provider";
    }

    private Path json(ResourceLocation location, String kind) {
        return this.dataFolder.resolve(location.getNamespace()).resolve(kind).resolve(location.getPath() + ".json");
    }

    public void dimensionType(ResourceLocation id, DimensionType type) {
        Path path = json(id, "dimension_type");
        this.results.add(DataProvider.saveStable(this.cachedOutput, encodeJson(DimensionType.DIRECT_CODEC, type, ResourceKey.create(Registries.DIMENSION_TYPE, id)), path));
    }

    public void dimension(ResourceLocation id, List<Pair<Climate.ParameterPoint, Holder<Biome>>> biomes) {
        dimensionWithBiomeSource(id, MultiNoiseBiomeSource.createFromList(new Climate.ParameterList<>(biomes)));
    }

    public void dimension(ResourceLocation id, ResourceKey<Biome> fixedBiome) {
        dimensionWithBiomeSource(id, new FixedBiomeSource(Holder.Reference.createStandAlone(holderLookup.lookupOrThrow(Registries.BIOME), fixedBiome)));
    }

    private void dimensionWithBiomeSource(ResourceLocation id, BiomeSource biomeSource) {
        Path path = json(id, "dimension");
        JsonObject dimension = new JsonObject();
        JsonObject generator = new JsonObject();
        dimension.addProperty("type", id.toString());
        dimension.add("generator", generator);
        generator.addProperty("type", "minecraft:noise");
        generator.addProperty("settings", id.toString());
        generator.add("biome_source", encodeJson(BiomeSource.CODEC, biomeSource, ResourceKey.create(Registries.LEVEL_STEM, id)));
        this.results.add(DataProvider.saveStable(this.cachedOutput, dimension, path));
    }

    private <T> JsonElement encodeJson(Codec<T> codec, T value, ResourceKey<?> id) {
        return codec.encodeStart(this.registryOps, value).resultOrPartial(error -> {
            HardcoreDungeons.INSTANCE.getLOGGER().error("Failed to encode JSON for {}: {}", id, error);
        }).get();
    }
}
