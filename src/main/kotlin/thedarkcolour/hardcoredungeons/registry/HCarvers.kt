package thedarkcolour.hardcoredungeons.registry

import net.minecraft.world.gen.carver.WorldCarver
import net.minecraftforge.registries.ForgeRegistries
import thedarkcolour.hardcoredungeons.worldgen.carver.CastletonCanyonCarver
import thedarkcolour.hardcoredungeons.worldgen.carver.CastletonCaveCarver

object HCarvers : HRegistry<WorldCarver<*>>(ForgeRegistries.WORLD_CARVERS) {
    val CASTLETON_CAVE by registry.registerObject("castleton_cave", ::CastletonCaveCarver)
    val CASTLETON_CANYON by registry.registerObject("castleton_canyon", ::CastletonCanyonCarver)
}