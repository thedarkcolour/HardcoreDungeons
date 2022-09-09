package team.rusty.util.feature;

import net.minecraft.data.BuiltinRegistries;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

/**
 * Unified registry for Feature, ConfiguredFeature, and PlacedFeature
 *
 * @author TheDarkColour
 */
public final class FeatureRegistry {
    private final DeferredRegister<Feature<?>> features;
    private final DeferredRegister<ConfiguredFeature<?, ?>> configuredFeatures;
    private final DeferredRegister<PlacedFeature> placedFeatures;
    public FeatureRegistry(String modid) {
        this.features = DeferredRegister.create(ForgeRegistries.FEATURES, modid);
        this.configuredFeatures = DeferredRegister.create(BuiltinRegistries.CONFIGURED_FEATURE.key(), modid);
        this.placedFeatures = DeferredRegister.create(BuiltinRegistries.PLACED_FEATURE.key(), modid);
    }

    public void register(IEventBus modBus) {
        features.register(modBus);
        configuredFeatures.register(modBus);
        placedFeatures.register(modBus);
    }

    public <FC extends FeatureConfiguration, F extends Feature<FC>> RegistryObject<F> feature(String name, Supplier<F> supplier) {
        return features.register(name, supplier);
    }

    public <FC extends FeatureConfiguration, F extends Feature<FC>> RegistryObject<ConfiguredFeature<FC, F>> configuredFeature(String name, Supplier<ConfiguredFeature<FC, F>> supplier) {
        return configuredFeatures.register(name, supplier);
    }

    public RegistryObject<PlacedFeature> placedFeature(String name, Supplier<PlacedFeature> supplier) {
        return placedFeatures.register(name, supplier);
    }
}
