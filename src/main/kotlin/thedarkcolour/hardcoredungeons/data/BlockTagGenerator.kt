package thedarkcolour.hardcoredungeons.data

import net.minecraft.block.Block
import net.minecraft.data.BlockTagsProvider
import net.minecraft.data.DataGenerator
import net.minecraft.tags.BlockTags
import net.minecraft.tags.ITag
import net.minecraftforge.common.Tags.Blocks
import net.minecraftforge.common.data.ExistingFileHelper
import thedarkcolour.hardcoredungeons.HardcoreDungeons
import thedarkcolour.hardcoredungeons.registry.HBlocks
import thedarkcolour.hardcoredungeons.tags.HBlockTags

/**
 * Generates block tags for Hardcore Dungeons blocks.
 *
 * @author TheDarkColour
 */
class BlockTagGenerator(gen: DataGenerator, helper: ExistingFileHelper) : BlockTagsProvider(gen, HardcoreDungeons.ID, helper) {
    /**
     * Register tags for each block.
     */
    override fun addTags() {

        tags()

        // hcd tags
        HBlocks.LUMLIGHT_WOOD.addTags(this)
        HBlocks.COTTONMARSH_WOOD.addTags(this)
        HBlocks.AURI_WOOD.addTags(this)

        tag(HBlockTags.GLASS_RAINBOW).add(HBlocks.RAINBOW_GLASS)
        tag(HBlockTags.GLASS_PANES_RAINBOW).add(HBlocks.RAINBOW_GLASS_PANE)
        HBlocks.CASTLETON_STONE.addTags(this)
        HBlocks.CHARGED_CASTLETON_BRICKS.addTags(this)
        HBlocks.RAINBOW_ROCK.addTags(this)
        HBlocks.RAINBOW_FACTORY_BRICKS.addTags(this)
        //tag(HBlockTags.FENCES_BRICK).add(, HBlocksNew.CHARGED_CASTLETON_BRICK_FENCE, HBlocksNew.RAINBOW_BRICK_FENCE, HBlocksNew.RAINBOW_FACTORY_BRICK_FENCE)
        tag(HBlockTags.CASTLETON_GRASS_PLANTABLE).add(HBlocks.LUMLIGHT_WOOD.sapling.plant, HBlocks.BLUE_LUMSHROOM.plant, HBlocks.PURPLE_LUMSHROOM.plant)
        tag(HBlockTags.RAINBOW_GRASS_PLANTABLE).add(HBlocks.LUMLIGHT_WOOD.sapling.plant)
        tag(HBlockTags.AURIGRASS_PLANTABLE).add(HBlocks.FLAME_ROSE.plant, HBlocks.GOLDEN_TULIP.plant)
        tag(HBlockTags.SUGARY_GRASS_PLANTABLE).add(HBlocks.GREEN_GUMDROP.plant, HBlocks.MINI_GREEN_GUMDROP.plant, HBlocks.PINK_GUMDROP.plant, HBlocks.MINI_PINK_GUMDROP.plant, HBlocks.BLUE_GUMDROP.plant, HBlocks.MINI_BLUE_GUMDROP.plant, HBlocks.PURPLE_GUMDROP.plant, HBlocks.MINI_PURPLE_GUMDROP.plant, HBlocks.RED_GUMDROP.plant, HBlocks.MINI_RED_GUMDROP.plant, HBlocks.YELLOW_GUMDROP.plant, HBlocks.MINI_YELLOW_GUMDROP.plant)

        HBlocks.CHILI_PEPPER.addTags(this)
        HBlocks.WILD_BERROOK.addTags(this)

        HBlocks.AUBRUM_PORTAL.addTags(this)
        HBlocks.CANDYLAND_PORTAL.addTags(this)
        HBlocks.CASTLETON_PORTAL.addTags(this)
        HBlocks.RAINBOWLAND_PORTAL.addTags(this)

        // vanilla tags
        /*tag(BlockTags.WALLS).add(HBlocksNew.SHROOMY_COBBLESTONE_WALL, HBlocksNew.SHROOMY_STONE_BRICK_WALL, HBlocksNew.CASTLETON_BRICK_WALL, HBlocksNew.CHARGED_CASTLETON_BRICK_WALL, HBlocksNew.RAINBOW_BRICK_WALL, HBlocksNew.RAINBOW_FACTORY_BRICK_WALL)
        tag(BlockTags.FENCES).add(HBlocksNew.CASTLETON_BRICK_FENCE, HBlocksNew.CHARGED_CASTLETON_BRICK_FENCE, HBlocksNew.LUMLIGHT_FENCE, HBlocksNew.RAINBOW_BRICK_FENCE, HBlocksNew.RAINBOW_FACTORY_BRICK_FENCE)
        tag(BlockTags.LEAVES).add(HBlocksNew.COTTONMARSH_LEAVES, HBlocksNew.LUMLIGHT_LEAVES)
        tag(BlockTags.IMPERMEABLE).add(HBlocksNew.RAINBOW_GLASS)
        tag(BlockTags.STANDING_SIGNS).add(HBlocksNew.LUMLIGHT_SIGN)
        tag(BlockTags.WALL_SIGNS).add(HBlocksNew.LUMLIGHT_WALL_SIGN)
        tag(BlockTags.SIGNS).add(HBlocksNew.LUMLIGHT_SIGN, HBlocksNew.LUMLIGHT_WALL_SIGN)
        tag(BlockTags.WITHER_IMMUNE).add(HBlocksNew.DUNGEON_CASTLETON_BRICKS)
        tag(BlockTags.CROPS).add(HBlocksNew.CHILI_PEPPER, HBlocksNew.WILD_BERROOK)
        tag(BlockTags.PORTALS).add(HBlocksNew.AUBRUM_PORTAL, HBlocksNew.CASTLETON_PORTAL, HBlocksNew.RAINBOWLAND_PORTAL, HBlocksNew.CANDYLAND_PORTAL)
        tag(BlockTags.BEACON_BASE_BLOCKS).add(HBlocksNew.RAINBOWSTONE_BLOCK, HBlocksNew.MALACHITE_BLOCK)
        tag(BlockTags.PLANKS).add(HBlocksNew.LUMLIGHT_PLANKS, *HBlocksNew.OAK_PLANKS.variants, *HBlocksNew.SPRUCE_PLANKS.variants)
        tag(BlockTags.WOODEN_DOORS).add(HBlocksNew.LUMLIGHT_DOOR)
        tag(BlockTags.WOODEN_STAIRS).add(HBlocksNew.AURI_STAIRS, HBlocksNew.LUMLIGHT_STAIRS)
        tag(BlockTags.WOODEN_SLABS).add(HBlocksNew.AURI_SLAB, HBlocksNew.LUMLIGHT_SLAB)
        tag(BlockTags.WOODEN_FENCES).add(HBlocksNew.LUMLIGHT_FENCE)
        tag(BlockTags.WOODEN_PRESSURE_PLATES).add(HBlocksNew.LUMLIGHT_PRESSURE_PLATE)
        tag(BlockTags.PORTALS).add(HBlocksNew.AUBRUM_PORTAL, HBlocksNew.CASTLETON_PORTAL, HBlocksNew.RAINBOWLAND_PORTAL)
        tag(BlockTags.WALL_POST_OVERRIDE).add(HBlocksNew.LUMLIGHT_PRESSURE_PLATE, HBlocksNew.CASTLETON_TORCH, HBlocksNew.CROWN, HBlocksNew.CHALICE)
        tag(BlockTags.SAPLINGS).add(HBlocksNew.LUMLIGHT_SAPLING)
        tag(BlockTags.CROPS).add(HBlocksNew.CHILI_PEPPER)
        tag(BlockTags.BEE_GROWABLES).add(HBlocksNew.CHILI_PEPPER)
        tag(BlockTags.LOGS).addTag(HBlockTags.LUMLIGHT_LOGS)//.addTag(HBlockTags.COTTONMARSH_LOGS)
        tag(BlockTags.IMPERMEABLE).addTag(HBlockTags.GLASS_RAINBOW)

        // forge tags
        tag(Blocks.DIRT).add(HBlocksNew.CASTLETON_SOIL, HBlocksNew.CASTLETON_GRASS_BLOCK, HBlocksNew.RAINBOW_SOIL, HBlocksNew.RAINBOW_GRASS_BLOCK, HBlocksNew.AURIGRASS_BLOCK, HBlocksNew.AURISOIL, HBlocksNew.SUGARY_GRASS_BLOCK, HBlocksNew.SUGARY_SOIL)
        tag(Blocks.FENCES).add(HBlocksNew.CASTLETON_BRICK_FENCE, HBlocksNew.CHARGED_CASTLETON_BRICK_FENCE, HBlocksNew.RAINBOW_BRICK_FENCE, HBlocksNew.RAINBOW_FACTORY_BRICK_FENCE)
        tag(Blocks.FENCES_WOODEN).add(HBlocksNew.LUMLIGHT_FENCE)
        tag(Blocks.FENCE_GATES).add(HBlocksNew.LUMLIGHT_FENCE_GATE)
        tag(Blocks.FENCE_GATES_WOODEN).add(HBlocksNew.LUMLIGHT_FENCE_GATE)*/

        tag(Blocks.GLASS).addTag(HBlockTags.GLASS_RAINBOW)
        tag(Blocks.GLASS_PANES).addTag(HBlockTags.GLASS_PANES_RAINBOW) // the chaotic glass
        tag(Blocks.GLASS_BLACK).addTag(HBlockTags.GLASS_RAINBOW)
        tag(Blocks.GLASS_BLUE).addTag(HBlockTags.GLASS_RAINBOW)
        tag(Blocks.GLASS_BROWN).addTag(HBlockTags.GLASS_RAINBOW)
        tag(Blocks.GLASS_CYAN).addTag(HBlockTags.GLASS_RAINBOW)
        tag(Blocks.GLASS_GRAY).addTag(HBlockTags.GLASS_RAINBOW)
        tag(Blocks.GLASS_GREEN).addTag(HBlockTags.GLASS_RAINBOW)
        tag(Blocks.GLASS_LIGHT_BLUE).addTag(HBlockTags.GLASS_RAINBOW)
        tag(Blocks.GLASS_LIGHT_GRAY).addTag(HBlockTags.GLASS_RAINBOW)
        tag(Blocks.GLASS_LIME).addTag(HBlockTags.GLASS_RAINBOW)
        tag(Blocks.GLASS_MAGENTA).addTag(HBlockTags.GLASS_RAINBOW)
        tag(Blocks.GLASS_ORANGE).addTag(HBlockTags.GLASS_RAINBOW)
        tag(Blocks.GLASS_PINK).addTag(HBlockTags.GLASS_RAINBOW)
        tag(Blocks.GLASS_PURPLE).addTag(HBlockTags.GLASS_RAINBOW)
        tag(Blocks.GLASS_RED).addTag(HBlockTags.GLASS_RAINBOW)
        tag(Blocks.GLASS_WHITE).addTag(HBlockTags.GLASS_RAINBOW)
        tag(Blocks.GLASS_YELLOW).addTag(HBlockTags.GLASS_RAINBOW)
        tag(Blocks.GLASS_PANES).addTag(HBlockTags.GLASS_PANES_RAINBOW)
        tag(Blocks.GLASS_PANES_BLACK).addTag(HBlockTags.GLASS_PANES_RAINBOW)
        tag(Blocks.GLASS_PANES_BLUE).addTag(HBlockTags.GLASS_PANES_RAINBOW)
        tag(Blocks.GLASS_PANES_BROWN).addTag(HBlockTags.GLASS_PANES_RAINBOW)
        tag(Blocks.GLASS_PANES_COLORLESS).addTag(HBlockTags.GLASS_PANES_RAINBOW)
        tag(Blocks.GLASS_PANES_CYAN).addTag(HBlockTags.GLASS_PANES_RAINBOW)
        tag(Blocks.GLASS_PANES_GRAY).addTag(HBlockTags.GLASS_PANES_RAINBOW)
        tag(Blocks.GLASS_PANES_GREEN).addTag(HBlockTags.GLASS_PANES_RAINBOW)
        tag(Blocks.GLASS_PANES_LIGHT_BLUE).addTag(HBlockTags.GLASS_PANES_RAINBOW)
        tag(Blocks.GLASS_PANES_LIGHT_GRAY).addTag(HBlockTags.GLASS_PANES_RAINBOW)
        tag(Blocks.GLASS_PANES_LIME).addTag(HBlockTags.GLASS_PANES_RAINBOW)
        tag(Blocks.GLASS_PANES_MAGENTA).addTag(HBlockTags.GLASS_PANES_RAINBOW)
        tag(Blocks.GLASS_PANES_ORANGE).addTag(HBlockTags.GLASS_PANES_RAINBOW)
        tag(Blocks.GLASS_PANES_PINK).addTag(HBlockTags.GLASS_PANES_RAINBOW)
        tag(Blocks.GLASS_PANES_PURPLE).addTag(HBlockTags.GLASS_PANES_RAINBOW)
        tag(Blocks.GLASS_PANES_RED).addTag(HBlockTags.GLASS_PANES_RAINBOW)
    }

    private fun tags() {
        /** Overworld materials */
        HBlocks.SHROOMY_COBBLESTONE.addTags(this)
        HBlocks.SHROOMY_STONE_BRICKS.addTags(this)

        /** Ore blocks */
        tag(Blocks.ORES).add(HBlocks.RAINBOWSTONE_ORE, HBlocks.AURIGOLD_ORE)

        /** Gem blocks */
        tag(Blocks.STORAGE_BLOCKS).add(HBlocks.MALACHITE_BLOCK, HBlocks.RAINBOWSTONE_BLOCK)
        tag(BlockTags.BEACON_BASE_BLOCKS).add(HBlocks.MALACHITE_BLOCK, HBlocks.RAINBOWSTONE_BLOCK)

        /** Wood */
        HBlocks.LUMLIGHT_WOOD.addTags(this)
        HBlocks.AURI_WOOD.addTags(this)
        HBlocks.COTTONMARSH_WOOD.addTags(this)

        /** Chiseled block variants */
        // idk what to do for chiseled diamond block
        HBlocks.OAK_PLANKS.addTag(this, BlockTags.PLANKS)
        HBlocks.SPRUCE_PLANKS.addTag(this, BlockTags.PLANKS)

        /** Castleton materials */
        HBlocks.CASTLETON_SOIL.addTags(this)
        HBlocks.CASTLETON_STONE.addTags(this)
        HBlocks.CHARGED_CASTLETON_BRICKS.addTags(this)
        HBlocks.CASTLETON_PORTAL.addTags(this)

        /** Castleton Props */
        tag(BlockTags.CAMPFIRES).add(HBlocks.LUMLIGHT_CAMPFIRE)
        HBlocks.CASTLETON_TORCH.addTags(this)

        /** Rainbowland materials */
        HBlocks.RAINBOW_SOIL.addTags(this)
        HBlocks.RAINBOW_ROCK.addTags(this)
        HBlocks.RAINBOW_FACTORY_BRICKS.addTags(this)
        HBlocks.RAINBOWLAND_PORTAL.addTags(this)

        /** Aubrum materials */
        HBlocks.AURISOIL.addTags(this)
        HBlocks.AUBRUM_PORTAL.addTags(this)

        /** Candyland materials */
        HBlocks.SUGARY_SOIL.addTags(this)
        tag(Blocks.STORAGE_BLOCKS).add(HBlocks.SUGAR_BLOCK, HBlocks.CHOCOLATE_BLOCK.block)
        HBlocks.CHOCOLATE_BLOCK.addTags(this)
        HBlocks.CANDYLAND_PORTAL.addTags(this)

        /** Crops */
        HBlocks.CHILI_PEPPER.addTags(this)
        tag(BlockTags.CROPS).add(HBlocks.GOLDEN_CARROTS)
        HBlocks.WILD_BERROOK.addTags(this)
    }

    public override fun tag(tag: ITag.INamedTag<Block>): Builder<Block> {
        return super.tag(tag)
    }

    override fun getName(): String {
        return "Hardcore Dungeons Block Tags"
    }
}