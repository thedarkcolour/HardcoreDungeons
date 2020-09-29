package thedarkcolour.hardcoredungeons.registry

import net.minecraft.item.crafting.IRecipeSerializer
import net.minecraft.item.crafting.IRecipeType
import net.minecraftforge.registries.IForgeRegistry
import thedarkcolour.hardcoredungeons.recipe.ExtractorRecipe

@Suppress("HasPlatformType")
object HRecipes {
    val EXTRACTOR_SERIALIZER = ExtractorRecipe.Serializer().setRegistryKey("extraction")

    val EXTRACTION = IRecipeType.register<ExtractorRecipe>("hardcoredungeons:extraction")

    fun registerRecipeSerializers(serializers: IForgeRegistry<IRecipeSerializer<*>>) {
        serializers.register(EXTRACTOR_SERIALIZER)
    }
}