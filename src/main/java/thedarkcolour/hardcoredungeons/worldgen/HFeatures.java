package thedarkcolour.hardcoredungeons.worldgen;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.carver.WorldCarver;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import net.minecraftforge.registries.RegistryObject;
import thedarkcolour.hardcoredungeons.HardcoreDungeons;
import thedarkcolour.hardcoredungeons.worldgen.carver.CastletonCaveCarver;
import thedarkcolour.hardcoredungeons.worldgen.feature.CandyCaneFeature;
import thedarkcolour.hardcoredungeons.worldgen.feature.ChocolateBarFeature;
import thedarkcolour.kotlinforforge.forge.ForgeKt;

public class HFeatures {
    private static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.Keys.FEATURES, HardcoreDungeons.ID);
    private static final DeferredRegister<WorldCarver<?>> CARVERS = DeferredRegister.create(ForgeRegistries.Keys.WORLD_CARVERS, HardcoreDungeons.ID);

    // Features (todo add configurations affecting height/width)
    public static final RegistryObject<CandyCaneFeature> CANDY_CANE = FEATURES.register("candy_cane", CandyCaneFeature::new);
    public static final RegistryObject<ChocolateBarFeature> CHOCOLATE_BAR = FEATURES.register("chocolate_bar", ChocolateBarFeature::new);
    // Configured Features
    public static final ResourceKey<ConfiguredFeature<?, ?>> MALACHITE_CRYSTAL = configuredFeature("malachite_crystal");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FANCY_LUMLIGHT_TREE = configuredFeature("fancy_lumlight_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CANDY_CANE_CONFIGURED = configuredFeature("candy_cane");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CHOCOLATE_BAR_CONFIGURED = configuredFeature("candy_cane");

    // Placed Features
    public static final ResourceKey<PlacedFeature> SPARSE_CANDY_CANES = placed("sparse_candy_canes");
    public static final ResourceKey<PlacedFeature> SPARSE_CHOCOLATE_BARS = placed("sparse_chocolate_bars");
    public static final ResourceKey<PlacedFeature> UNDERGROUND_MALACHITE_CRYSTALS = placed("underground_malachite_crystals");

    // Carvers
    public static final RegistryObject<CastletonCaveCarver> CASTLETON_CAVE_CARVER = CARVERS.register("castleton_cave", CastletonCaveCarver::new);
    // Configured Carvers
    public static final ResourceKey<ConfiguredWorldCarver<?>> CASTLETON_CAVES = configuredCarver("castleton_cave");

    public static void init() {
        var modBus = ForgeKt.getMOD_BUS();

        FEATURES.register(modBus);
        CARVERS.register(modBus);
        modBus.addListener(HFeatures::registerGlobalModifier);
    }

    private static void registerGlobalModifier(RegisterEvent event) {
        event.register(ForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, new ResourceLocation(HardcoreDungeons.ID, "global"), () -> GlobalBiomeModifier.CODEC);
    }

    private static ResourceKey<ConfiguredFeature<?, ?>> configuredFeature(String path) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(HardcoreDungeons.ID, path));
    }

    private static ResourceKey<ConfiguredWorldCarver<?>> configuredCarver(String path) {
        return ResourceKey.create(Registries.CONFIGURED_CARVER, new ResourceLocation(HardcoreDungeons.ID, path));
    }

    private static ResourceKey<PlacedFeature> placed(String path) {
        return ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(HardcoreDungeons.ID, path));
    }
}
