package thedarkcolour.hardcoredungeons.data.recipe

import com.google.gson.JsonObject
import net.minecraft.data.IFinishedRecipe
import net.minecraft.item.Item
import net.minecraft.item.crafting.IRecipeSerializer
import net.minecraft.tags.Tag
import net.minecraft.util.IItemProvider
import net.minecraft.util.ResourceLocation
import net.minecraftforge.registries.ForgeRegistries
import org.apache.logging.log4j.LogManager
import java.util.function.Consumer

class StonecuttingRecipeBuilder(private val input: Item, private val count: Int = 1) {

    constructor(result: IItemProvider, count: Int = 1) : this(result.asItem(), count)

    fun addResult(tag: Tag<Item>) {

    }

    fun addResult(tag: IItemProvider) {

    }

    fun build(consumer: Consumer<IFinishedRecipe>) {
        build(consumer, ForgeRegistries.ITEMS.getKey(input)!!)
    }

    private fun build(consumer: Consumer<IFinishedRecipe>, key: ResourceLocation) {
        TODO("not implemented")
    }

    companion object {
        private val LOGGER = LogManager.getLogger()
    }

    class Result : IFinishedRecipe {
        override fun getSerializer(): IRecipeSerializer<*> {
            return IRecipeSerializer.STONECUTTING
        }

        /**
         * Gets the ID for the advancement associated with this recipe. Should not be null if [.getAdvancementJson] is
         * non-null.
         */
        override fun getAdvancementID(): ResourceLocation? {
            TODO("not implemented")
        }

        /**
         * Gets the ID for the recipe.
         */
        override fun getID(): ResourceLocation {
            TODO("not implemented")
        }

        override fun serialize(json: JsonObject) {
            TODO("not implemented")
        }

        /**
         * Gets the JSON for the advancement that unlocks this recipe. Null if there is no advancement.
         */
        override fun getAdvancementJson(): JsonObject? {
            TODO("not implemented")
        }
    }
}