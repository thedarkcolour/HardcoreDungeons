package thedarkcolour.hardcoredungeons.worldgen.biome

import net.minecraft.util.Mth
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.MobCategory
import net.minecraft.world.level.biome.MobSpawnSettings
import net.minecraftforge.common.world.BiomeSpecialEffectsBuilder

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

val DEFAULT_CASTLETON_EFFECTS: BiomeSpecialEffectsBuilder.() -> Unit = {
    waterColor(0x6fa3bf)
    waterFogColor(0x1f37f2)
    skyColor(0x113d51)
    fogColor(0x1d5a75)
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
