package thedarkcolour.hardcoredungeons.data

import net.minecraftforge.data.event.GatherDataEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent

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

        if (event.includeClient()) {
            gen.addProvider(Lang.English(gen))
            gen.addProvider(Lang.Español(gen))
            //gen.addProvider(BlockModelGenerator(gen, helper))
            //gen.addProvider(ItemModelGenerator(gen, helper))
            gen.addProvider(ModelGenerator(gen, helper))
        }
        if (event.includeServer()) {
            val blockTags = BlockTagGenerator(gen, helper)
            gen.addProvider(RecipeGenerator(gen))
            gen.addProvider(LootGenerator(gen))
            gen.addProvider(blockTags)
            gen.addProvider(ItemTagGenerator(gen, blockTags, helper))
        }
    }
}