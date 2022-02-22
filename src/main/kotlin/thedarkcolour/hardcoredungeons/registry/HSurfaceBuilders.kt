package thedarkcolour.hardcoredungeons.registry

import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder
import net.minecraftforge.registries.IForgeRegistry
import thedarkcolour.hardcoredungeons.worldgen.surfacebuilder.ThickForestSurfaceBuilder

@Suppress("HasPlatformType", "MemberVisibilityCanBePrivate")
object HSurfaceBuilders {
    val THICK_FOREST = ThickForestSurfaceBuilder().setRegistryKey("thick_forest")

    fun registerSurfaceBuilders(surfaces: IForgeRegistry<SurfaceBuilder<*>>) {
        surfaces.register(THICK_FOREST)
    }
}