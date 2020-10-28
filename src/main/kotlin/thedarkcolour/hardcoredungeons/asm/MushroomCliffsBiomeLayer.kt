package thedarkcolour.hardcoredungeons.asm

import net.minecraft.util.registry.DynamicRegistries
import net.minecraft.util.registry.Registry
import net.minecraft.world.gen.INoiseRandom
import net.minecraft.world.gen.layer.LayerUtil
import net.minecraft.world.gen.layer.traits.IBishopTransformer
import thedarkcolour.hardcoredungeons.registry.HBiomes

object MushroomCliffsBiomeLayer : IBishopTransformer {
    private val MUSHROOM_CLIFFS = DynamicRegistries.Impl.func_239770_b_().getRegistry(Registry.BIOME_KEY).getId(HBiomes.MUSHROOM_CLIFFS)

    init {
        println("HERE !!!")

        LayerUtil.field_242937_a[MUSHROOM_CLIFFS] = 15
    }

    override fun apply(p0: INoiseRandom, p1: Int, p2: Int, p3: Int, p4: Int, p5: Int): Int {
        return if (LayerUtil.isShallowOcean(p5) && LayerUtil.isShallowOcean(p4) && LayerUtil.isShallowOcean(p1)
            && LayerUtil.isShallowOcean(p3) && LayerUtil.isShallowOcean(p2) && p0.random(20) == 0
        ) MUSHROOM_CLIFFS else p5
    }
}