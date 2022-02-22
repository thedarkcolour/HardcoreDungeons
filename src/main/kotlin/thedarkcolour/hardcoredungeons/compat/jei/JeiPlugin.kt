package thedarkcolour.hardcoredungeons.compat.jei

import mezz.jei.api.IModPlugin
import mezz.jei.api.JeiPlugin
import mezz.jei.api.constants.VanillaRecipeCategoryUid
import mezz.jei.api.registration.IRecipeCatalystRegistration
import mezz.jei.api.registration.IRecipeRegistration
import mezz.jei.api.registration.ISubtypeRegistration
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.ResourceLocation
import net.minecraft.util.text.TranslationTextComponent
import thedarkcolour.hardcoredungeons.registry.HBlocks
import thedarkcolour.hardcoredungeons.registry.HItems
import thedarkcolour.hardcoredungeons.util.modLoc

@JeiPlugin
class JeiPlugin : IModPlugin {
    override fun getPluginUid(): ResourceLocation {
        return modLoc("jei_plugin")
    }

    override fun registerRecipeCatalysts(registration: IRecipeCatalystRegistration) {
        registration.addRecipeCatalyst(ItemStack(HBlocks.LUMLIGHT_CAMPFIRE), VanillaRecipeCategoryUid.CAMPFIRE)
    }

    override fun registerItemSubtypes(registration: ISubtypeRegistration) {
        registration.registerSubtypeInterpreter(HItems.POTION_SYRINGE, SyringeSubtypeInterpreter)
    }

    override fun registerRecipes(registration: IRecipeRegistration) {
        //addDescription(registration, "mod.hardcoredungeons.help.desc")
    }

    private fun addDescription(registration: IRecipeRegistration, langKey: String, vararg items: Item) {
        if (items.isEmpty()) return

        val stacks = items.map(::ItemStack)
        val ingredientType = registration.ingredientManager.getIngredientType(ItemStack::class.java)
        registration.addIngredientInfo(stacks, ingredientType, TranslationTextComponent(langKey))
    }
}