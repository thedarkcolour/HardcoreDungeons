package team.rusty.util.biome;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

/**
 * Class to handle {@link ModBiome} for a specific mod.
 *
 * @author thedarkcolour
 */
public final class ModBiomeRegistry {
    /** Dummy builder that gets changed later */
    private static final Biome.BiomeBuilder DUMMY = new Biome.BiomeBuilder().precipitation(Biome.Precipitation.NONE).temperature(0.1f).downfall(0.1f).specialEffects(new BiomeSpecialEffects.Builder().fogColor(0).waterColor(0).waterFogColor(0).skyColor(0).build()).mobSpawnSettings(new MobSpawnSettings.Builder().build()).generationSettings(new BiomeGenerationSettings.Builder().build());

    /** Biome deferred register */
    private final DeferredRegister<Biome> biomesRegistry;
    private final DeferredRegister<Codec<? extends BiomeModifier>> modifierSerializerRegistry;
    private final DeferredRegister<BiomeModifier> modifierRegistry;
    /** Mod id of this biome registry */
    private final String modId;
    /** Map of all biome ids to mod biomes */
    private final BiMap<ResourceLocation, ModBiome> biomes = HashBiMap.create();

    /**
     * Creates a new mod biome registry with a built-in deferred register
     */
    public ModBiomeRegistry(String modId) {
        this.biomesRegistry = DeferredRegister.create(ForgeRegistries.Keys.BIOMES, modId);
        this.modifierSerializerRegistry = DeferredRegister.create(ForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, modId);
        this.modifierRegistry = DeferredRegister.create(ForgeRegistries.Keys.BIOME_MODIFIERS, modId);
        this.modId = modId;
    }

    /**
     * Registers this biome registry to the mod bus. Only call this once
     */
    public void register(IEventBus modBus) {
        biomesRegistry.register(modBus);
        modifierSerializerRegistry.register(modBus);
        modifierRegistry.register(modBus);
    }

    /**
     * Register a new biome. Names have been moved out of the biome class, so you can even register duplicate biomes!
     * Each biome gets its own corresponding BiomeModifier and BiomeModifier codec, which are singletons.
     * The ModBiomeModifier applies all the properties declared in the ModBiome when all modifiers are applied.
     */
    public ModBiome register(String name, ModBiome biome) {
        // Put a dummy biome in minecraft registry
        biomesRegistry.register(name, DUMMY::build);

        // Modifier needs a reference to its serializer, serializer needs the instance of the modifier
        final MovedValue<ModBiomeModifier> modifier = new MovedValue<>(null);
        RegistryObject<Codec<ModBiomeModifier>> serializer = modifierSerializerRegistry.register(name, () -> Codec.unit(modifier));
        modifier.value = new ModBiomeModifier(biome, serializer);
        modifierRegistry.register(name, modifier);

        // Assign the biome an ID
        var biomeId = new ResourceLocation(modId, name);
        biome.id = ResourceKey.create(Registry.BIOME_REGISTRY, biomeId);
        // Track biome in the registry
        biomes.put(biomeId, biome);

        return biome;
    }

    private static final class MovedValue<T> implements Supplier<T> {
        public T value;

        public MovedValue(T initial) {
            this.value = initial;
        }

        @Override
        public T get() {
            return this.value;
        }
    }
}
