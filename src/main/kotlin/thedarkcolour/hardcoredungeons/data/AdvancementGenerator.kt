package thedarkcolour.hardcoredungeons.data

import com.google.common.collect.Sets
import com.google.gson.GsonBuilder
import net.minecraft.advancements.Advancement
import net.minecraft.data.AdvancementProvider
import net.minecraft.data.DataGenerator
import net.minecraft.data.DirectoryCache
import net.minecraft.data.IDataProvider
import net.minecraft.resources.ResourceLocation
import org.apache.logging.log4j.LogManager
import thedarkcolour.hardcoredungeons.data.advancements.AdvancementGroup
import thedarkcolour.hardcoredungeons.data.advancements.OverworldAdvancements
import java.io.IOException
import java.nio.file.Path

// todo
class AdvancementGenerator(private val gen: DataGenerator) : AdvancementProvider(gen) {
    private val advancementGroups = listOf<AdvancementGroup>(OverworldAdvancements())

    /**
     * Performs this provider's action.
     */
    override fun run(cache: DirectoryCache) {
        val path = gen.outputFolder
        val set: MutableSet<ResourceLocation> = Sets.newHashSet()
        val consumer = { advancement: Advancement ->
            check(set.add(advancement.id)) { "Duplicate advancement " + advancement.id }
            val path1 = getPath(path, advancement)

            try {
                IDataProvider.save(GSON, cache, advancement.deconstruct().serializeToJson(), path1)
            } catch (io: IOException) {
                LOGGER.error("Couldn't save advancement {}", path1, io)
            }
        }

        for (group in advancementGroups) {
            group.invoke(consumer)
        }
    }

    companion object {
        private val LOGGER = LogManager.getLogger()
        private val GSON = GsonBuilder().setPrettyPrinting().create()

        private fun getPath(pathIn: Path, advancementIn: Advancement): Path {
            return pathIn.resolve("data/" + advancementIn.id.namespace + "/advancements/" + advancementIn.id.path + ".json")
        }
    }
}