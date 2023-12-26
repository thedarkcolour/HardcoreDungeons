package thedarkcolour.hardcoredungeons.worldgen.biome

import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.MobCategory
import net.minecraft.world.level.biome.MobSpawnSettings
import thedarkcolour.hardcoredungeons.data.WorldGenProvider

abstract class ModBiome {
    abstract fun configure(info: WorldGenProvider.BiomeConfiguration)

    companion object {
        @JvmStatic
        protected fun MobSpawnSettings.Builder.addSpawn(classification: MobCategory, entityType: EntityType<*>, weight: Int, min: Int, max: Int): MobSpawnSettings.Builder {
            return addSpawn(classification, MobSpawnSettings.SpawnerData(entityType, weight, min, max))
        }
    }
}