package thedarkcolour.hardcoredungeons.worldgen.biome

import net.minecraft.util.Mth
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.MobCategory
import net.minecraft.world.level.biome.MobSpawnSettings
import net.minecraftforge.common.world.BiomeSpecialEffectsBuilder

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
