package thedarkcolour.hardcoredungeons.data

import net.minecraft.data.BlockTagsProvider
import net.minecraft.data.DataGenerator
import net.minecraft.tags.BlockTags
import net.minecraft.tags.ITag
import net.minecraft.util.ResourceLocation
import net.minecraftforge.common.Tags.Blocks
import net.minecraftforge.common.data.ExistingFileHelper
import thedarkcolour.hardcoredungeons.HardcoreDungeons
import thedarkcolour.hardcoredungeons.registry.HBlocks
import thedarkcolour.hardcoredungeons.tags.HBlockTags
import java.nio.file.Path

/**
 * Generates block tags for Hardcore Dungeons blocks.
 *
 * @author TheDarkColour
 */
class BlockTagGenerator(gen: DataGenerator, helper: ExistingFileHelper) : BlockTagsProvider(gen, HardcoreDungeons.ID, helper) {
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
        filter = tagToBuilder.entries.map(Map.Entry<ResourceLocation, ITag.Builder>::key).toHashSet()

        // hcd tags
        getOrCreateBuilder(HBlockTags.LUMLIGHT_LOGS).add(HBlocks.LUMLIGHT_LOG, HBlocks.LUMLIGHT_WOOD, HBlocks.STRIPPED_LUMLIGHT_LOG, HBlocks.STRIPPED_LUMLIGHT_WOOD)
        getOrCreateBuilder(HBlockTags.GLASS_RAINBOW).add(HBlocks.RAINBOW_GLASS)
        getOrCreateBuilder(HBlockTags.GLASS_PANES_RAINBOW).add(HBlocks.RAINBOW_GLASS_PANE)
        getOrCreateBuilder(HBlockTags.FENCES_BRICK).add(HBlocks.CASTLETON_BRICK_FENCE, HBlocks.CHARGED_CASTLETON_BRICK_FENCE, HBlocks.RAINBOW_BRICK_FENCE, HBlocks.RAINBOW_FACTORY_BRICK_FENCE)
        getOrCreateBuilder(HBlockTags.COTTONMARSH_LOGS).add(HBlocks.COTTONMARSH_LOG, HBlocks.COTTONMARSH_WOOD, HBlocks.STRIPPED_COTTONMARSH_LOG, HBlocks.STRIPPED_COTTONMARSH_WOOD)
        getOrCreateBuilder(HBlockTags.CASTLETON_GRASS_PLANTABLE).add(HBlocks.LUMLIGHT_SAPLING)

        // vanilla tags
        getOrCreateBuilder(BlockTags.PLANKS).add(HBlocks.LUMLIGHT_PLANKS)
        getOrCreateBuilder(BlockTags.WOODEN_DOORS).add(HBlocks.LUMLIGHT_DOOR)
        getOrCreateBuilder(BlockTags.WOODEN_STAIRS).add(HBlocks.AURI_STAIRS, HBlocks.LUMLIGHT_STAIRS)
        getOrCreateBuilder(BlockTags.WOODEN_SLABS).add(HBlocks.AURI_SLAB, HBlocks.LUMLIGHT_SLAB)
        getOrCreateBuilder(BlockTags.WOODEN_FENCES).add(HBlocks.LUMLIGHT_FENCE)
        getOrCreateBuilder(BlockTags.WOODEN_PRESSURE_PLATES).add(HBlocks.LUMLIGHT_PRESSURE_PLATE)
        getOrCreateBuilder(BlockTags.PORTALS).add(HBlocks.AUBRUM_PORTAL, HBlocks.CASTLETON_PORTAL, HBlocks.RAINBOWLAND_PORTAL)
        getOrCreateBuilder(BlockTags.WALL_POST_OVERRIDE).add(HBlocks.LUMLIGHT_PRESSURE_PLATE, HBlocks.CASTLETON_TORCH, HBlocks.CROWN, HBlocks.CHALICE)
        getOrCreateBuilder(BlockTags.SAPLINGS).add(HBlocks.LUMLIGHT_SAPLING)
        getOrCreateBuilder(BlockTags.CROPS).add(HBlocks.CHILI_PEPPER)
        getOrCreateBuilder(BlockTags.BEE_GROWABLES).add(HBlocks.CHILI_PEPPER)
        getOrCreateBuilder(BlockTags.LEAVES).add(HBlocks.COTTONMARSH_LEAVES, HBlocks.LUMLIGHT_LEAVES)
        getOrCreateBuilder(BlockTags.LOGS).addTag(HBlockTags.LUMLIGHT_LOGS)//.addTag(HBlockTags.COTTONMARSH_LOGS)
        getOrCreateBuilder(BlockTags.IMPERMEABLE).addTag(HBlockTags.GLASS_RAINBOW)

        // forge tags
        getOrCreateBuilder(Blocks.DIRT).add(HBlocks.CASTLETON_SOIL, HBlocks.CASTLETON_GRASS_BLOCK, HBlocks.RAINBOW_SOIL, HBlocks.RAINBOW_GRASS_BLOCK, HBlocks.AURIGRASS_BLOCK, HBlocks.AURISOIL)
        getOrCreateBuilder(Blocks.FENCES).add(HBlocks.LUMLIGHT_FENCE, HBlocks.CHARGED_CASTLETON_BRICK_FENCE)
        getOrCreateBuilder(Blocks.FENCE_GATES_WOODEN).add(HBlocks.LUMLIGHT_FENCE_GATE)
        getOrCreateBuilder(Blocks.GLASS).addTag(HBlockTags.GLASS_RAINBOW)
        getOrCreateBuilder(Blocks.GLASS_PANES).addTag(HBlockTags.GLASS_PANES_RAINBOW) // the chaotic glass
        getOrCreateBuilder(Blocks.GLASS_BLACK).addTag(HBlockTags.GLASS_RAINBOW)
        getOrCreateBuilder(Blocks.GLASS_BLUE).addTag(HBlockTags.GLASS_RAINBOW)
        getOrCreateBuilder(Blocks.GLASS_BROWN).addTag(HBlockTags.GLASS_RAINBOW)
        getOrCreateBuilder(Blocks.GLASS_CYAN).addTag(HBlockTags.GLASS_RAINBOW)
        getOrCreateBuilder(Blocks.GLASS_GRAY).addTag(HBlockTags.GLASS_RAINBOW)
        getOrCreateBuilder(Blocks.GLASS_GREEN).addTag(HBlockTags.GLASS_RAINBOW)
        getOrCreateBuilder(Blocks.GLASS_LIGHT_BLUE).addTag(HBlockTags.GLASS_RAINBOW)
        getOrCreateBuilder(Blocks.GLASS_LIGHT_GRAY).addTag(HBlockTags.GLASS_RAINBOW)
        getOrCreateBuilder(Blocks.GLASS_LIME).addTag(HBlockTags.GLASS_RAINBOW)
        getOrCreateBuilder(Blocks.GLASS_MAGENTA).addTag(HBlockTags.GLASS_RAINBOW)
        getOrCreateBuilder(Blocks.GLASS_ORANGE).addTag(HBlockTags.GLASS_RAINBOW)
        getOrCreateBuilder(Blocks.GLASS_PINK).addTag(HBlockTags.GLASS_RAINBOW)
        getOrCreateBuilder(Blocks.GLASS_PURPLE).addTag(HBlockTags.GLASS_RAINBOW)
        getOrCreateBuilder(Blocks.GLASS_RED).addTag(HBlockTags.GLASS_RAINBOW)
        getOrCreateBuilder(Blocks.GLASS_WHITE).addTag(HBlockTags.GLASS_RAINBOW)
        getOrCreateBuilder(Blocks.GLASS_YELLOW).addTag(HBlockTags.GLASS_RAINBOW)
        getOrCreateBuilder(Blocks.GLASS_PANES).addTag(HBlockTags.GLASS_PANES_RAINBOW)
        getOrCreateBuilder(Blocks.GLASS_PANES_BLACK).addTag(HBlockTags.GLASS_PANES_RAINBOW)
        getOrCreateBuilder(Blocks.GLASS_PANES_BLUE).addTag(HBlockTags.GLASS_PANES_RAINBOW)
        getOrCreateBuilder(Blocks.GLASS_PANES_BROWN).addTag(HBlockTags.GLASS_PANES_RAINBOW)
        getOrCreateBuilder(Blocks.GLASS_PANES_COLORLESS).addTag(HBlockTags.GLASS_PANES_RAINBOW)
        getOrCreateBuilder(Blocks.GLASS_PANES_CYAN).addTag(HBlockTags.GLASS_PANES_RAINBOW)
        getOrCreateBuilder(Blocks.GLASS_PANES_GRAY).addTag(HBlockTags.GLASS_PANES_RAINBOW)
        getOrCreateBuilder(Blocks.GLASS_PANES_GREEN).addTag(HBlockTags.GLASS_PANES_RAINBOW)
        getOrCreateBuilder(Blocks.GLASS_PANES_LIGHT_BLUE).addTag(HBlockTags.GLASS_PANES_RAINBOW)
        getOrCreateBuilder(Blocks.GLASS_PANES_LIGHT_GRAY).addTag(HBlockTags.GLASS_PANES_RAINBOW)
        getOrCreateBuilder(Blocks.GLASS_PANES_LIME).addTag(HBlockTags.GLASS_PANES_RAINBOW)
        getOrCreateBuilder(Blocks.GLASS_PANES_MAGENTA).addTag(HBlockTags.GLASS_PANES_RAINBOW)
        getOrCreateBuilder(Blocks.GLASS_PANES_ORANGE).addTag(HBlockTags.GLASS_PANES_RAINBOW)
        getOrCreateBuilder(Blocks.GLASS_PANES_PINK).addTag(HBlockTags.GLASS_PANES_RAINBOW)
        getOrCreateBuilder(Blocks.GLASS_PANES_PURPLE).addTag(HBlockTags.GLASS_PANES_RAINBOW)
        getOrCreateBuilder(Blocks.GLASS_PANES_RED).addTag(HBlockTags.GLASS_PANES_RAINBOW)
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