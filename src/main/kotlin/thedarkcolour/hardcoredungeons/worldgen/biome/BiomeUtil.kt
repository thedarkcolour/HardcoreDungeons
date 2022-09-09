package thedarkcolour.hardcoredungeons.worldgen.biome

import net.minecraft.entity.EntityClassification
import net.minecraft.entity.EntityType
import net.minecraft.util.math.MathHelper
import net.minecraft.world.biome.BiomeAmbience
import net.minecraft.world.biome.MobSpawnInfo
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.MobCategory
import net.minecraft.world.level.biome.MobSpawnSettings

/**
 * Biome ambience with default parameters and enforced the required ones.
 * Should prevent slip ups on my part :)
 */
fun biomeFx(
    waterColor: Int,
    waterFogColor: Int,
    skyColor: Int,
    skyFogColor: Int,
): BiomeAmbience.Builder {
    return BiomeAmbience.Builder()
        .waterColor(waterColor)
        .waterFogColor(waterFogColor)
        .skyColor(skyColor)
        .fogColor(skyFogColor)
}

fun getSkyForTemp(temperature: Float): Int {
    val a = MathHelper.clamp(temperature / 3.0f, -1.0f, 1.0f)
    return MathHelper.hsvToRgb(0.62222224f - a * 0.05f, 0.5f + a * 0.1f, 1.0f)
}

/** Shortcut function */
fun MobSpawnSettings.Builder.addSpawn(
    classification: MobCategory,
    entityType: EntityType<*>,
    weight: Int,
    min: Int,
    max: Int
): MobSpawnSettings.Builder {
    return addSpawn(classification, MobSpawnInfo.Spawners(entityType, weight, min, max))
}