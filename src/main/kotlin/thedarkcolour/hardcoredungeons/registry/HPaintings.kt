package thedarkcolour.hardcoredungeons.registry

import net.minecraft.core.registries.Registries
import net.minecraft.world.entity.decoration.PaintingVariant
import thedarkcolour.kotlinforforge.forge.ObjectHolderDelegate

object HPaintings : HRegistry<PaintingVariant>(Registries.PAINTING_VARIANT) {
    val POST_NUCLEAR_AUBRUM_TREE by painting("post_nuclear_aubrum_tree", 32, 32)
    val RAINFOREST by painting("rainforest", 32, 32)
    // todo remove
    val NIGHT by painting("night", 32, 16)

    private fun painting(name: String, width: Int, height: Int): ObjectHolderDelegate<PaintingVariant> {
        return register(name) { PaintingVariant(width, height) }
    }
}