package thedarkcolour.hardcoredungeons.data

import net.minecraftforge.data.event.GatherDataEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod

/**
 * *Isolated from main mod code.*
 *
 * Data providers for Hardcore Dungeons.
 *
 * @author thedarkcolour
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
object Data {
    @SubscribeEvent
    fun gatherData(event: GatherDataEvent) {
        val gen = event.generator
        val helper = event.existingFileHelper

        // includeClient is always true because the runData has --all,
        // but keeping just in case I decide to change it at some point.
        if (event.includeClient()) {
            gen.addProvider(true, Lang.English(gen))
            gen.addProvider(true, Lang.Español(gen))
            //gen.addProvider(BlockModelGenerator(gen, helper))
            //gen.addProvider(ItemModelGenerator(gen, helper))
            gen.addProvider(true, ModelGenerator(gen, helper))
        }
        if (event.includeServer()) {
            val blockTags = BlockTagGenerator(gen, helper)

            gen.addProvider(true, RecipeGenerator(gen))
            gen.addProvider(true, LootGenerator(gen))
            gen.addProvider(true, blockTags)
            gen.addProvider(true, ItemTagGenerator(gen, blockTags, helper))
        }
    }
}