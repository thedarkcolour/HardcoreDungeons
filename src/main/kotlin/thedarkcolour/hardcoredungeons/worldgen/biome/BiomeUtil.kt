package thedarkcolour.hardcoredungeons.worldgen.biome

import net.minecraft.core.HolderLookup
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.util.Mth
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.MobCategory
import net.minecraft.world.level.biome.BiomeGenerationSettings
import net.minecraft.world.level.biome.BiomeGenerationSettings.PlainBuilder
import net.minecraft.world.level.biome.MobSpawnSettings
import net.minecraft.world.level.levelgen.GenerationStep
import net.minecraft.world.level.levelgen.GenerationStep.Carving
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver
import net.minecraft.world.level.levelgen.placement.PlacedFeature
import net.minecraftforge.common.world.BiomeSpecialEffectsBuilder
import net.minecraftforge.server.ServerLifecycleHooks


//
///**
// * Biome ambience with default parameters and enforced the required ones.
// * Should prevent slip ups on my part :)
// */
//fun biomeFx(
//    waterColor: Int,
//    waterFogColor: Int,
//    skyColor: Int,
//    skyFogColor: Int,
//): BiomeAmbience.Builder {
//    return BiomeAmbience.Builder()
//        .waterColor(waterColor)
//        .waterFogColor(waterFogColor)
//        .skyColor(skyColor)
//        .fogColor(skyFogColor)
//}

fun defaultAubrumEffects(builder: BiomeSpecialEffectsBuilder) {
    builder.waterColor(0x999966)
    builder.waterFogColor(0x1f37f2)
    builder.skyColor(0xab9560)
    builder.fogColor(0xc4af7c)
}

fun defaultCandylandEffects(builder: BiomeSpecialEffectsBuilder) {
    builder.waterColor(0xd19bdc)
    builder.waterFogColor(0x50533)
    builder.skyColor(getSkyForTemp(0.9f))
    builder.fogColor(12638463)
}

fun defaultCastletonEffects(builder: BiomeSpecialEffectsBuilder) {
    builder.waterColor(0x6fa3bf)
    builder.waterFogColor(0x1f37f2)
    builder.skyColor(0x113d51)
    builder.fogColor(0x1d5a75)
}

fun getSkyForTemp(temperature: Float): Int {
    val a = Mth.clamp(temperature / 3.0f, -1.0f, 1.0f)
    return Mth.hsvToRgb(0.62222224f - a * 0.05f, 0.5f + a * 0.1f, 1.0f)
}

/** Shortcut function */
fun MobSpawnSettings.Builder.addSpawn(
    classification: MobCategory,
    entityType: EntityType<*>,
    weight: Int,
    min: Int,
    max: Int
): MobSpawnSettings.Builder {
    return addSpawn(classification, MobSpawnSettings.SpawnerData(entityType, weight, min, max))
}

fun wrapped(builder: PlainBuilder): BiomeGenerationSettings.Builder {
    return BiomeGenSettingsWrapper(builder)
}

private class BiomeGenSettingsWrapper(private val original: PlainBuilder) : BiomeGenerationSettings.Builder(null, null) {
    private val placedFeatures: HolderLookup<PlacedFeature>
    private val worldCarvers: HolderLookup<ConfiguredWorldCarver<*>>

    init {
        val access = ServerLifecycleHooks.getCurrentServer().registryAccess()
        placedFeatures = access.lookupOrThrow(Registries.PLACED_FEATURE)
        worldCarvers = access.lookupOrThrow(Registries.CONFIGURED_CARVER)
    }

    override fun addFeature(decoration: GenerationStep.Decoration, feature: ResourceKey<PlacedFeature>): BiomeGenerationSettings.Builder {
        original.addFeature(decoration.ordinal, placedFeatures.getOrThrow(feature))
        return this
    }

    override fun addCarver(carving: Carving, carver: ResourceKey<ConfiguredWorldCarver<*>>): BiomeGenerationSettings.Builder {
        original.addCarver(carving, worldCarvers.getOrThrow(carver))
        return this
    }
}