package thedarkcolour.hardcoredungeons.compat.jei

import mezz.jei.api.IModPlugin
import mezz.jei.api.JeiPlugin
import mezz.jei.api.constants.RecipeTypes
import mezz.jei.api.constants.VanillaTypes
import mezz.jei.api.registration.IRecipeCatalystRegistration
import mezz.jei.api.registration.IRecipeRegistration
import mezz.jei.api.registration.ISubtypeRegistration
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import thedarkcolour.hardcoredungeons.registry.HBlocks
import thedarkcolour.hardcoredungeons.registry.items.HItemsNew
import thedarkcolour.hardcoredungeons.util.modLoc

@JeiPlugin
class JeiPlugin : IModPlugin {
    override fun getPluginUid(): ResourceLocation {
        return modLoc("jei_plugin")
    }

    override fun registerRecipeCatalysts(registration: IRecipeCatalystRegistration) {
        registration.addRecipeCatalyst(ItemStack(HBlocks.LUMLIGHT_CAMPFIRE), RecipeTypes.CAMPFIRE_COOKING)
    }

    override fun registerItemSubtypes(registration: ISubtypeRegistration) {
        registration.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, HItemsNew.POTION_SYRINGE, SyringeSubtypeInterpreter)
    }

    override fun registerRecipes(registration: IRecipeRegistration) {
        //addDescription(registration, "mod.hardcoredungeons.help.desc")
    }

    private fun addDescription(registration: IRecipeRegistration, langKey: String, vararg items: Item) {
        if (items.isEmpty()) return

        val stacks = items.map(::ItemStack)
        val ingredientType = registration.ingredientManager.getIngredientType(ItemStack::class.java)
        registration.addIngredientInfo(stacks, ingredientType, Component.translatable(langKey))
    }
}