package thedarkcolour.hardcoredungeons.asm

import net.minecraft.world.gen.INoiseRandom
import net.minecraft.world.gen.layer.traits.IBishopTransformer

object MushroomCliffsBiomeLayer : IBishopTransformer {
    init {
        //LayerUtil.CATEGORIES[DynamicRegistries.builtin().registryOrThrow(Registry.BIOME_REGISTRY).getId(HBiomes.MUSHROOM_CLIFFS)] = 15
    }

    override fun apply(p0: INoiseRandom, p1: Int, p2: Int, p3: Int, p4: Int, p5: Int): Int {
        TODO()/*
        return if (LayerUtil.isShallowOcean(p5) && LayerUtil.isShallowOcean(p4) && LayerUtil.isShallowOcean(p1)
            && LayerUtil.isShallowOcean(p3) && LayerUtil.isShallowOcean(p2) && p0.nextRandom(20) == 0
        ) DynamicRegistries.builtin().registryOrThrow(Registry.BIOME_REGISTRY).getId(HBiomes.MUSHROOM_CLIFFS) else p5
    */}
}