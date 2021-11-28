package team.rusty.util.worldgen.structure;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.StructureFeatureConfiguration;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author TheDarkColour
 */
public final class SimpleStructureRegistry {
    /** Biome deferred register */
    private final DeferredRegister<StructureFeature<?>> deferredRegister;
    /** Map of structure information */
    private final Map<ResourceLocation, StructureInfo<StructureFeature<?>>> structureInfo;

    public SimpleStructureRegistry(String modId) {
        this.deferredRegister = DeferredRegister.create(ForgeRegistries.STRUCTURE_FEATURES, modId);
        this.structureInfo = new HashMap<>();
    }

    /**
     * Registers this biome registry to the mod bus
     */
    public void register(IEventBus modBus) {
        deferredRegister.register(modBus);
        modBus.addListener(this::commonSetup);
        MinecraftForge.EVENT_BUS.addListener(this::worldLoad);
    }

    public <T extends StructureFeature<?>> RegistryObject<T> register(String name, Supplier<T> structure, Function<T, ConfiguredStructureFeature<?, ?>> configured, boolean adjustGround, int spacing, int separation, int structureSeed) {
        var object = deferredRegister.register(name, structure);
        structureInfo.put(object.getId(), new StructureInfo(configured, adjustGround, new StructureFeatureConfiguration(spacing, separation, structureSeed)));
        return object;
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            for (var entry : deferredRegister.getEntries()) {
                var value = entry.get();
                var id = entry.getId();
                var info = structureInfo.get(id);

                // register to vanilla
                Registry.register(BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE, id, info.configured().apply(value));
                // register to vanilla again
                StructureFeature.STRUCTURES_REGISTRY.put(id.toString(), value);

                // add bearding to the structure ground
                if (info.adjustGround()) {
                    StructureFeature.NOISE_AFFECTING_FEATURES = ImmutableList.<StructureFeature<?>>builder().addAll(StructureFeature.NOISE_AFFECTING_FEATURES).add(value).build();
                }
            }
        });
    }

    private void worldLoad(WorldEvent.Load event) {
        if (event.getWorld() instanceof ServerLevel level) {
            var configurations = level.getChunkSource().getGenerator().getSettings().structureConfig();

            // Add structure placement settings to world instead of to the immutable default map
            for (var entry : deferredRegister.getEntries()) {
                // Fix in case world type has immutable configurations (like flat world)
                if (!(configurations instanceof ImmutableMap)) {
                    configurations.put(entry.get(), structureInfo.get(entry.getId()).config());
                }
            }
        }
    }

    private record StructureInfo<T extends StructureFeature<?>>(
            Function<T, ConfiguredStructureFeature<?, ?>> configured,
            boolean adjustGround,
            StructureFeatureConfiguration config
    ) {}
}
