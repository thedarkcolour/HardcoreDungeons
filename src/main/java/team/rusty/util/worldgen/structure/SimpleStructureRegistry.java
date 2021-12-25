package team.rusty.util.worldgen.structure;

import com.google.common.collect.ImmutableList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author TheDarkColour
 */
public final class SimpleStructureRegistry {
    /** Biome deferred register */
    private final DeferredRegister<Structure<?>> deferredRegister;
    /** Map of structure information */
    private final Map<ResourceLocation, StructureInfo<Structure<?>>> structureInfo;

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

    @SuppressWarnings("rawtypes")
    public <T extends Structure<?>> RegistryObject<T> register(String name, Supplier<T> structure, Function<T, StructureFeature<?, ?>> configured, boolean adjustGround, int spacing, int separation, int structureSeed) {
        RegistryObject<T> object = deferredRegister.register(name, structure);
        structureInfo.put(object.getId(), new StructureInfo(configured, adjustGround, new StructureSeparationSettings(spacing, separation, structureSeed)));
        return object;
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            for (RegistryObject<Structure<?>> entry : deferredRegister.getEntries()) {
                Structure<?> value = entry.get();
                ResourceLocation id = entry.getId();
                StructureInfo<Structure<?>> info = structureInfo.get(id);

                // register to vanilla
                Registry.register(WorldGenRegistries.CONFIGURED_STRUCTURE_FEATURE, id, info.configured().apply(value));
                // register to vanilla again
                Structure.STRUCTURES_REGISTRY.put(id.toString(), value);

                // add bearding to the structure ground
                if (info.adjustGround()) {
                    Structure.NOISE_AFFECTING_FEATURES = ImmutableList.<Structure<?>>builder().addAll(Structure.NOISE_AFFECTING_FEATURES).add(value).build();
                }
            }
        });
    }

    private void worldLoad(WorldEvent.Load event) {
        IWorld world = event.getWorld();

        if (world instanceof ServerWorld) {
            Map<Structure<?>, StructureSeparationSettings> configurations = ((ServerWorld) world).getChunkSource().getGenerator().getSettings().structureConfig();

            // Add structure placement settings to world instead of to the immutable default map
            for (RegistryObject<Structure<?>> entry : deferredRegister.getEntries()) {
                // Fix in case world type has immutable configurations (like flat world)
                try {
                    configurations.put(entry.get(), structureInfo.get(entry.getId()).config());
                } catch (UnsupportedOperationException ignored) { }
            }
        }
    }

    private static final class StructureInfo<T extends Structure<?>> {
        private final Function<T, StructureFeature<?, ?>> configured;
        private final boolean adjustGround;
        private final StructureSeparationSettings config;

        private StructureInfo(
                Function<T, StructureFeature<?, ?>> configured,
                boolean adjustGround,
                StructureSeparationSettings config
        ) {
            this.configured = configured;
            this.adjustGround = adjustGround;
            this.config = config;
        }

        public Function<T, StructureFeature<?, ?>> configured() {
            return configured;
        }

        public boolean adjustGround() {
            return adjustGround;
        }

        public StructureSeparationSettings config() {
            return config;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) return true;
            if (obj == null || obj.getClass() != this.getClass()) return false;
            StructureInfo<?> that = (StructureInfo<?>) obj;
            return Objects.equals(this.configured, that.configured) &&
                    this.adjustGround == that.adjustGround &&
                    Objects.equals(this.config, that.config);
        }

        @Override
        public int hashCode() {
            return Objects.hash(configured, adjustGround, config);
        }

        @Override
        public String toString() {
            return "StructureInfo[" +
                    "configured=" + configured + ", " +
                    "adjustGround=" + adjustGround + ", " +
                    "config=" + config + ']';
        }
    }
}
