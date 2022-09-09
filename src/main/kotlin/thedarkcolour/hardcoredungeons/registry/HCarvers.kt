package thedarkcolour.hardcoredungeons.registry

import net.minecraft.world.gen.carver.WorldCarver
import net.minecraft.world.level.levelgen.carver.WorldCarver
import net.minecraftforge.registries.ForgeRegistries
import thedarkcolour.hardcoredungeons.worldgen.carver.CastletonCanyonCarver
import thedarkcolour.hardcoredungeons.worldgen.carver.CastletonCaveCarver

object HCarvers : HRegistry<WorldCarver<*>>(ForgeRegistries.Keys.WORLD_CARVERS) {
    val CASTLETON_CAVE by register("castleton_cave", ::CastletonCaveCarver)
    val CASTLETON_CANYON by register("castleton_canyon", ::CastletonCanyonCarver)
}