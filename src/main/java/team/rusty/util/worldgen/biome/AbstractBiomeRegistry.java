package team.rusty.util.worldgen.biome;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeAmbience;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilders;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * Class to handle {@link AbstractBiome} for a specific mod.
 *
 * @author TheDarkColour
 */
public final class AbstractBiomeRegistry {
    /** Dummy builder that gets changed later */
    private static final Biome.Builder DUMMY = new Biome.Builder().precipitation(Biome.RainType.NONE).biomeCategory(Biome.Category.PLAINS).depth(0.1f).scale(0.1f).temperature(0.1f).downfall(0.1f).specialEffects(new BiomeAmbience.Builder().fogColor(0).waterColor(0).waterFogColor(0).skyColor(0).build()).mobSpawnSettings(new MobSpawnInfo.Builder().build()).generationSettings(new BiomeGenerationSettings.Builder().surfaceBuilder(ConfiguredSurfaceBuilders.BADLANDS).build());

    /** Biome deferred register */
    private final DeferredRegister<Biome> deferredRegister;
    /** Mod id of this biome registry */
    private final String modId;
    /** Map of all biome ids to abstract biomes */
    private final BiMap<ResourceLocation, AbstractBiome> biomes = HashBiMap.create();

    /**
     * Creates a new abstract biome registry with a built-in deferred register
     */
    public AbstractBiomeRegistry(String modId) {
        this.deferredRegister = DeferredRegister.create(ForgeRegistries.BIOMES, modId);
        this.modId = modId;
    }

    /**
     * Registers this biome registry to the mod bus
     */
    public void register(IEventBus modBus) {
        deferredRegister.register(modBus);
        MinecraftForge.EVENT_BUS.addListener(this::applyBiomes);
    }

    /**
     * Register a new biome. Names have been moved out of the biome class, so you can even register duplicate biomes!
     */
    public AbstractBiome register(String name, AbstractBiome biome) {
        // Put a dummy biome in minecraft registry
        deferredRegister.register(name, DUMMY::build);
        // Put biome in biome registry
        ResourceLocation biomeId = new ResourceLocation(modId, name);
        biomes.put(biomeId, biome);

        // This is what prints out the error messages about missing mobs/features/etc.
        if (!FMLEnvironment.production) {
            BiomeChecker.checkClass(biome, biomeId);
        }

        return biome;
    }

    /**
     * Replaces all the dummy biomes with the real values set in {@link AbstractBiome#configure}
     */
    private void applyBiomes(BiomeLoadingEvent event) {
        ResourceLocation biomeId = event.getName();
        AbstractBiome biome = biomes.get(biomeId);

        if (biome != null && biomeId != null) {
            // Only obtain the key once
            if (!biome.getSpawnEntries().isEmpty()) {
                RegistryKey<Biome> key = RegistryKey.create(Registry.BIOME_REGISTRY, biomeId);

                for (AbstractBiome.SpawnEntry entry : biome.getSpawnEntries()) {
                    BiomeManager.addBiome(entry.type(), new BiomeManager.BiomeEntry(key, entry.weight()));
                }
            }

            biome.configure(event.getGeneration(), event.getSpawns());

            event.setCategory(biome.getCategory());
            event.setClimate(biome.getClimate());
            event.setDepth(biome.getDepth());
            event.setEffects(biome.getEffects());
            event.setScale(biome.getScale());
        }
    }

    public ResourceLocation getId(AbstractBiome biome) {
        return biomes.inverse().get(biome);
    }
}
