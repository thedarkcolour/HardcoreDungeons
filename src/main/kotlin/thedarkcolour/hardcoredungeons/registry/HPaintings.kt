package thedarkcolour.hardcoredungeons.registry

import net.minecraft.entity.item.PaintingType
import net.minecraftforge.registries.ForgeRegistries
import thedarkcolour.kotlinforforge.forge.ObjectHolderDelegate

object HPaintings : HRegistry<PaintingType>(ForgeRegistries.PAINTING_TYPES) {
    val POST_NUCLEAR_AUBRUM_TREE by painting("post_nuclear_aubrum_tree", 32, 32)
    val RAINFOREST by painting("rainforest", 32, 32)
    // todo remove
    val NIGHT by painting("night", 32, 16)

    private fun painting(name: String, width: Int, height: Int): ObjectHolderDelegate<PaintingType> {
        return register(name) { PaintingType(width, height) }
    }
}