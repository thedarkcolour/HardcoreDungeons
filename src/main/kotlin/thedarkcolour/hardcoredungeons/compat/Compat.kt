package thedarkcolour.hardcoredungeons.compat

import net.minecraftforge.fml.ModList
import thedarkcolour.hardcoredungeons.compat.biomesoplenty.BiomesOPlentyCompat

private const val BIOMES_O_PLENTY = "biomesoplenty"

fun getBiomesOPlentyCompat(): BiomesOPlentyCompat? {
    return if (isLoaded(BIOMES_O_PLENTY)) BiomesOPlentyCompat else null
}

fun isLoaded(id: String): Boolean {
    return ModList.get().isLoaded(id)
}

