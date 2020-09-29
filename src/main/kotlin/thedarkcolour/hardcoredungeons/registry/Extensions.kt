package thedarkcolour.hardcoredungeons.registry

import net.minecraftforge.registries.ForgeRegistryEntry
import thedarkcolour.hardcoredungeons.HardcoreDungeons

fun <T : ForgeRegistryEntry<*>> T.setRegistryKey(key: String): T {
    setRegistryName(HardcoreDungeons.ID + ":" + key)
    return this
}