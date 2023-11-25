package thedarkcolour.hardcoredungeons.data

import net.minecraft.core.registries.Registries
import net.minecraft.data.loot.LootTableProvider
import net.minecraft.world.item.Item
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets
import net.minecraftforge.data.event.GatherDataEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import thedarkcolour.hardcoredungeons.HardcoreDungeons
import thedarkcolour.modkit.data.MKEnglishProvider
import thedarkcolour.modkit.data.MKTagsProvider

/**
 * Isolated from main mod code.
 *
 * Data providers for Hardcore Dungeons.
 *
 * @author thedarkcolour
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
object Data {
    @SubscribeEvent
    fun gatherData(event: GatherDataEvent) {
        val helper = thedarkcolour.modkit.data.DataHelper(HardcoreDungeons.ID, event)

        // the function references are EXTENSION FUNCTIONS found in the BlockTags.kt, English.kt, ItemModels.kt, etc. files
        helper.createEnglish(true, MKEnglishProvider::addTranslations)
        helper.createItemModels(true, true, true, ::addItemModels)
        helper.createTags(Registries.ITEM, MKTagsProvider<Item>::addItemTags)
        // I'm an idiot who didn't set the correct order of arguments!
        helper.createRecipes { writer, recipes -> recipes.addRecipes(writer) }

        event.generator.addProvider(true, LootTableProvider(event.generator.packOutput, emptySet(), listOf(
            LootTableProvider.SubProviderEntry(::BlockLoot, LootContextParamSets.BLOCK),
            LootTableProvider.SubProviderEntry(::EntityLoot, LootContextParamSets.ENTITY),
        )))
    }
}
