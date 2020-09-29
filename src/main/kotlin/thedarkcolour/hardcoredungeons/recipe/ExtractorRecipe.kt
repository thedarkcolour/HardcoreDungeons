package thedarkcolour.hardcoredungeons.recipe

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonSyntaxException
import net.minecraft.item.ItemStack
import net.minecraft.item.crafting.AbstractCookingRecipe
import net.minecraft.item.crafting.IRecipeSerializer
import net.minecraft.item.crafting.Ingredient
import net.minecraft.item.crafting.ShapedRecipe
import net.minecraft.network.PacketBuffer
import net.minecraft.util.JSONUtils
import net.minecraft.util.ResourceLocation
import net.minecraftforge.registries.ForgeRegistries
import net.minecraftforge.registries.ForgeRegistryEntry
import thedarkcolour.hardcoredungeons.registry.HRecipes

class ExtractorRecipe(
    idIn: ResourceLocation, groupIn: String, ingredientIn: Ingredient,
    resultIn: ItemStack, experienceIn: Float, cookTimeIn: Int
) : AbstractCookingRecipe(HRecipes.EXTRACTION, idIn, groupIn, ingredientIn, resultIn, experienceIn, cookTimeIn) {
    override fun getSerializer(): IRecipeSerializer<*> {
        return HRecipes.EXTRACTOR_SERIALIZER
    }

    class Serializer : ForgeRegistryEntry<IRecipeSerializer<*>>(), IRecipeSerializer<ExtractorRecipe> {
        override fun read(recipeId: ResourceLocation, json: JsonObject): ExtractorRecipe {
            val s = JSONUtils.getString(json, "group", "")
            val element = (if (JSONUtils.isJsonArray(json, "ingredient")) {
                JSONUtils.getJsonArray(json, "ingredient")
            } else {
                JSONUtils.getJsonObject(json, "ingredient")
            }) as JsonElement
            val ingredient = Ingredient.deserialize(element)
            if (!json.has("result")) throw JsonSyntaxException("Missing result, expected to find a string or object")
            val result = if (json.get("result").isJsonObject) {
                ShapedRecipe.deserializeItem(JSONUtils.getJsonObject(json, "result"))
            } else {
                val s1 = JSONUtils.getString(json, "result")
                val location = ResourceLocation(s1)
                ItemStack(ForgeRegistries.ITEMS.getValue(location)!!)
            }
            val f = JSONUtils.getFloat(json, "experience", 0.0f)
            val i = JSONUtils.getInt(json, "cookingtime", 600)
            return ExtractorRecipe(recipeId, s, ingredient, result, f, i)
        }

        override fun read(recipeId: ResourceLocation, buffer: PacketBuffer): ExtractorRecipe? {
            val s = buffer.readString(32767)
            val ingredient = Ingredient.read(buffer)
            val stack = buffer.readItemStack()
            val f = buffer.readFloat()
            val i = buffer.readVarInt()
            return ExtractorRecipe(recipeId, s, ingredient, stack, f, i)
        }

        override fun write(buffer: PacketBuffer, recipe: ExtractorRecipe) {
            buffer.writeString(recipe.group)
            recipe.ingredient.write(buffer)
            buffer.writeItemStack(recipe.result)
            buffer.writeFloat(recipe.experience)
            buffer.writeVarInt(recipe.cookTime)
        }
    }
}