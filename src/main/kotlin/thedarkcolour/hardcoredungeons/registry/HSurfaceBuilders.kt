package thedarkcolour.hardcoredungeons.registry

import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import thedarkcolour.hardcoredungeons.HardcoreDungeons
import thedarkcolour.hardcoredungeons.surfacebuilder.ThickForestSurfaceBuilder
import thedarkcolour.kotlinforforge.forge.registerObject

@Suppress("HasPlatformType", "MemberVisibilityCanBePrivate")
object HSurfaceBuilders {
    val SURFACE_BUILDERS = DeferredRegister.create(ForgeRegistries.SURFACE_BUILDERS, HardcoreDungeons.ID)

    val THICK_FOREST by SURFACE_BUILDERS.registerObject("thick_forest", ::ThickForestSurfaceBuilder)
}