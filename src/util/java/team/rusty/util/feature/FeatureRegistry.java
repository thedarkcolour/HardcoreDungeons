package team.rusty.util.feature;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.carver.CarverConfiguration;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.carver.WorldCarver;
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
 * Unified registry for Feature, ConfiguredFeature, PlacedFeature, WorldCarver, and ConfiguredWorldCarver
 *
 * @author thedarkcolour
 */
public final class FeatureRegistry {
    private final DeferredRegister<Feature<?>> features;
    private final DeferredRegister<ConfiguredFeature<?, ?>> configuredFeatures;
    private final DeferredRegister<PlacedFeature> placedFeatures;
    private final DeferredRegister<WorldCarver<?>> carvers;
    private final DeferredRegister<ConfiguredWorldCarver<?>> configuredCarvers;

    public FeatureRegistry(String modid) {
        this.features = DeferredRegister.create(ForgeRegistries.FEATURES, modid);
        this.configuredFeatures = DeferredRegister.create(Registries.CONFIGURED_FEATURE, modid);
        this.placedFeatures = DeferredRegister.create(Registries.PLACED_FEATURE, modid);
        this.carvers = DeferredRegister.create(Registries.CARVER, modid);
        this.configuredCarvers = DeferredRegister.create(Registries.CONFIGURED_CARVER, modid);
    }

    public void register(IEventBus modBus) {
        features.register(modBus);
        configuredFeatures.register(modBus);
        placedFeatures.register(modBus);
        carvers.register(modBus);
        configuredCarvers.register(modBus);
    }

    public <FC extends FeatureConfiguration, F extends Feature<FC>> RegistryObject<F> feature(String name, Supplier<F> supplier) {
        return features.register(name, supplier);
    }

    public Holder<ConfiguredFeature<?, ?>> configuredFeature(String name, Supplier<ConfiguredFeature<?, ?>> supplier) {
        return configuredFeatures.register(name, supplier).getHolder().get();
    }

    public Holder<PlacedFeature> placedFeature(String name, Supplier<PlacedFeature> supplier) {
        return placedFeatures.register(name, supplier).getHolder().get();
    }

    public <C extends CarverConfiguration, WC extends WorldCarver<C>> RegistryObject<WC> carver(String name, Supplier<WC> supplier) {
        return carvers.register(name, supplier);
    }

    public Holder<ConfiguredWorldCarver<?>> configuredCarver(String name, Supplier<ConfiguredWorldCarver<?>> supplier) {
        return configuredCarvers.register(name, supplier).getHolder().get();
    }
}
