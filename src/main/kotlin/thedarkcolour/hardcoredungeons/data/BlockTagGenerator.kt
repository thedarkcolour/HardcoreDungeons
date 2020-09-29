package thedarkcolour.hardcoredungeons.data

import net.minecraft.data.BlockTagsProvider
import net.minecraft.data.DataGenerator
import net.minecraft.tags.BlockTags
import net.minecraft.util.ResourceLocation
import net.minecraftforge.common.Tags.Blocks
import thedarkcolour.hardcoredungeons.registry.HBlocks
import thedarkcolour.hardcoredungeons.tags.HBlockTags
import java.nio.file.Path

/**
 * Generates block tags for Hardcore Dungeons blocks.
 *
 * @author TheDarkColour
 */
class BlockTagGenerator(gen: DataGenerator) : BlockTagsProvider(gen) {
    /**
     * A set of all non Hardcore Dungeons blocks and tags
     * that is used to avoid generating unused tags.
     */
    private var filter: Set<ResourceLocation>? = null

    /**
     * Register tags for each block.
     *
     * todo split into separate method after filter is created
     */
    override fun registerTags() {
        super.registerTags()

        // Collect all tags before this point (vanilla tags are already registered)
        // These tags will not be saved by Hardcore Dungeons data provider
        filter = tagToBuilder.entries.map { e -> e.key.id }.toHashSet()

        // hcd tags
        getBuilder(HBlockTags.LUMLIGHT_LOGS).add(HBlocks.LUMLIGHT_LOG, HBlocks.LUMLIGHT_WOOD, HBlocks.STRIPPED_LUMLIGHT_LOG, HBlocks.STRIPPED_LUMLIGHT_WOOD)
        getBuilder(HBlockTags.GLASS_RAINBOW).add(HBlocks.RAINBOW_GLASS)
        getBuilder(HBlockTags.GLASS_PANES_RAINBOW).add(HBlocks.RAINBOW_GLASS_PANE)
        getBuilder(HBlockTags.FENCES_BRICK).add(HBlocks.CASTLETON_BRICK_FENCE, HBlocks.CHARGED_CASTLETON_BRICK_FENCE, HBlocks.RAINBOW_BRICK_FENCE, HBlocks.RAINBOW_FACTORY_BRICK_FENCE)
        getBuilder(HBlockTags.COTTONMARSH_LOGS).add(HBlocks.COTTONMARSH_LOG, HBlocks.COTTONMARSH_WOOD, HBlocks.STRIPPED_COTTONMARSH_LOG, HBlocks.STRIPPED_COTTONMARSH_WOOD)
        getBuilder(HBlockTags.CASTLETON_GRASS_PLANTABLE).add(HBlocks.LUMLIGHT_SAPLING)

        // vanilla tags
        getBuilder(BlockTags.PLANKS).add(HBlocks.LUMLIGHT_PLANKS)
        getBuilder(BlockTags.WOODEN_DOORS).add(HBlocks.LUMLIGHT_DOOR)
        getBuilder(BlockTags.WOODEN_STAIRS).add(HBlocks.AURI_STAIRS, HBlocks.LUMLIGHT_STAIRS)
        getBuilder(BlockTags.WOODEN_SLABS).add(HBlocks.AURI_SLAB, HBlocks.LUMLIGHT_SLAB)
        getBuilder(BlockTags.WOODEN_FENCES).add(HBlocks.LUMLIGHT_FENCE)
        getBuilder(BlockTags.WOODEN_PRESSURE_PLATES).add(HBlocks.LUMLIGHT_PRESSURE_PLATE)
        getBuilder(BlockTags.PORTALS).add(HBlocks.AUBRUM_PORTAL, HBlocks.CASTLETON_PORTAL, HBlocks.RAINBOWLAND_PORTAL)
        getBuilder(BlockTags.SAPLINGS).add(HBlocks.LUMLIGHT_SAPLING)
        getBuilder(BlockTags.LOGS).add(HBlockTags.LUMLIGHT_LOGS)
        getBuilder(BlockTags.CROPS).add(HBlocks.CHILI_PEPPER)
        getBuilder(BlockTags.BEE_GROWABLES).add(HBlocks.CHILI_PEPPER)
        getBuilder(BlockTags.LEAVES).add(HBlocks.COTTONMARSH_LEAVES, HBlocks.LUMLIGHT_LEAVES)
        getBuilder(BlockTags.LOGS).add(HBlockTags.COTTONMARSH_LOGS)
        getBuilder(BlockTags.IMPERMEABLE).add(HBlockTags.GLASS_RAINBOW)

        // forge tags
        getBuilder(Blocks.DIRT).add(HBlocks.CASTLETON_SOIL, HBlocks.RAINBOW_SOIL)
        getBuilder(Blocks.FENCES).add(HBlocks.LUMLIGHT_FENCE, HBlocks.CHARGED_CASTLETON_BRICK_FENCE)
        getBuilder(Blocks.FENCE_GATES_WOODEN).add(HBlocks.LUMLIGHT_FENCE_GATE)
        getBuilder(Blocks.GLASS).add(HBlockTags.GLASS_RAINBOW)
        getBuilder(Blocks.GLASS_PANES).add(HBlockTags.GLASS_PANES_RAINBOW) // the chaotic glass
        getBuilder(Blocks.GLASS_BLACK).add(HBlockTags.GLASS_RAINBOW)
        getBuilder(Blocks.GLASS_BLUE).add(HBlockTags.GLASS_RAINBOW)
        getBuilder(Blocks.GLASS_BROWN).add(HBlockTags.GLASS_RAINBOW)
        getBuilder(Blocks.GLASS_CYAN).add(HBlockTags.GLASS_RAINBOW)
        getBuilder(Blocks.GLASS_GRAY).add(HBlockTags.GLASS_RAINBOW)
        getBuilder(Blocks.GLASS_GREEN).add(HBlockTags.GLASS_RAINBOW)
        getBuilder(Blocks.GLASS_LIGHT_BLUE).add(HBlockTags.GLASS_RAINBOW)
        getBuilder(Blocks.GLASS_LIGHT_GRAY).add(HBlockTags.GLASS_RAINBOW)
        getBuilder(Blocks.GLASS_LIME).add(HBlockTags.GLASS_RAINBOW)
        getBuilder(Blocks.GLASS_MAGENTA).add(HBlockTags.GLASS_RAINBOW)
        getBuilder(Blocks.GLASS_ORANGE).add(HBlockTags.GLASS_RAINBOW)
        getBuilder(Blocks.GLASS_PINK).add(HBlockTags.GLASS_RAINBOW)
        getBuilder(Blocks.GLASS_PURPLE).add(HBlockTags.GLASS_RAINBOW)
        getBuilder(Blocks.GLASS_RED).add(HBlockTags.GLASS_RAINBOW)
        getBuilder(Blocks.GLASS_WHITE).add(HBlockTags.GLASS_RAINBOW)
        getBuilder(Blocks.GLASS_YELLOW).add(HBlockTags.GLASS_RAINBOW)
        getBuilder(Blocks.GLASS_PANES).add(HBlockTags.GLASS_PANES_RAINBOW)
        getBuilder(Blocks.GLASS_PANES_BLACK).add(HBlockTags.GLASS_PANES_RAINBOW)
        getBuilder(Blocks.GLASS_PANES_BLUE).add(HBlockTags.GLASS_PANES_RAINBOW)
        getBuilder(Blocks.GLASS_PANES_BROWN).add(HBlockTags.GLASS_PANES_RAINBOW)
        getBuilder(Blocks.GLASS_PANES_COLORLESS).add(HBlockTags.GLASS_PANES_RAINBOW)
        getBuilder(Blocks.GLASS_PANES_CYAN).add(HBlockTags.GLASS_PANES_RAINBOW)
        getBuilder(Blocks.GLASS_PANES_GRAY).add(HBlockTags.GLASS_PANES_RAINBOW)
        getBuilder(Blocks.GLASS_PANES_GREEN).add(HBlockTags.GLASS_PANES_RAINBOW)
        getBuilder(Blocks.GLASS_PANES_LIGHT_BLUE).add(HBlockTags.GLASS_PANES_RAINBOW)
        getBuilder(Blocks.GLASS_PANES_LIGHT_GRAY).add(HBlockTags.GLASS_PANES_RAINBOW)
        getBuilder(Blocks.GLASS_PANES_LIME).add(HBlockTags.GLASS_PANES_RAINBOW)
        getBuilder(Blocks.GLASS_PANES_MAGENTA).add(HBlockTags.GLASS_PANES_RAINBOW)
        getBuilder(Blocks.GLASS_PANES_ORANGE).add(HBlockTags.GLASS_PANES_RAINBOW)
        getBuilder(Blocks.GLASS_PANES_PINK).add(HBlockTags.GLASS_PANES_RAINBOW)
        getBuilder(Blocks.GLASS_PANES_PURPLE).add(HBlockTags.GLASS_PANES_RAINBOW)
        getBuilder(Blocks.GLASS_PANES_RED).add(HBlockTags.GLASS_PANES_RAINBOW)
    }

    /**
     * Resolves a Path for the location to save the given tag.
     */
    override fun makePath(id: ResourceLocation): Path? {
        return if (filter?.contains(id) == true) null else super.makePath(id)
    }

    /**
     * Gets a name for this provider, to use in logging.
     */
    override fun getName(): String {
        return "Hardcore Dungeons Block Tags"
    }
}