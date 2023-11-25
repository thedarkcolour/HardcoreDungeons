package thedarkcolour.hardcoredungeons.registry.block

import net.minecraft.tags.BlockTags
import net.minecraft.world.effect.MobEffects
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.SoundType
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.material.MapColor
import net.minecraft.world.level.material.PushReaction
import net.minecraftforge.registries.ForgeRegistries
import thedarkcolour.hardcoredungeons.block.base.BlockMaker
import thedarkcolour.hardcoredungeons.block.base.HBlock
import thedarkcolour.hardcoredungeons.block.base.properties.HProperties
import thedarkcolour.hardcoredungeons.block.combo.*
import thedarkcolour.hardcoredungeons.block.decoration.HorizontalBlock
import thedarkcolour.hardcoredungeons.block.decoration.castleton.LampBlock
import thedarkcolour.hardcoredungeons.block.decoration.castleton.LumlightCampfireBlock
import thedarkcolour.hardcoredungeons.block.decoration.rainbowland.RainbowGlassBlock
import thedarkcolour.hardcoredungeons.block.decoration.rainbowland.RainbowGlassPaneBlock
import thedarkcolour.hardcoredungeons.block.plant.FlowerBlock
import thedarkcolour.hardcoredungeons.block.plant.HCropsBlock
import thedarkcolour.hardcoredungeons.block.plant.MushroomBlock
import thedarkcolour.hardcoredungeons.block.plant.PlantProperties
import thedarkcolour.hardcoredungeons.block.plant.aubrum.FlameRoseBlock
import thedarkcolour.hardcoredungeons.block.plant.misc.ChiliPepperBlock
import thedarkcolour.hardcoredungeons.block.plant.trees.AuriTree
import thedarkcolour.hardcoredungeons.block.plant.trees.CottonmarshTree
import thedarkcolour.hardcoredungeons.block.plant.trees.LumlightTree
import thedarkcolour.hardcoredungeons.block.structure.LockBlock
import thedarkcolour.hardcoredungeons.block.structure.MazeBushBlock
import thedarkcolour.hardcoredungeons.block.structure.SafeSootBlock
import thedarkcolour.hardcoredungeons.block.structure.SootTrapBlock
import thedarkcolour.hardcoredungeons.block.structure.castleton.FrayedSkullBlock
import thedarkcolour.hardcoredungeons.block.structure.rainbowland.RainbowFactoryFurnaceBlock
import thedarkcolour.hardcoredungeons.data.modelgen.block.BlockModelType
import thedarkcolour.hardcoredungeons.data.modelgen.item.ItemModelType
import thedarkcolour.hardcoredungeons.registry.HDimensions
import thedarkcolour.hardcoredungeons.registry.HRegistry
import thedarkcolour.hardcoredungeons.registry.HSounds
import thedarkcolour.hardcoredungeons.registry.items.BLUE_CASTLETON_DUNGEON_KEY_ITEM
import thedarkcolour.hardcoredungeons.registry.items.ItemMaker
import thedarkcolour.hardcoredungeons.tags.HBlockTags
import thedarkcolour.hardcoredungeons.tags.HItemTags

/**
 * Simplified new version that uses deferred registers.
 *
 * @author thedarkcolour
 */
@Suppress("Unused", "MemberVisibilityCanBePrivate", "ConvertLambdaToReference")
object HBlocks : HRegistry<Block>(ForgeRegistries.Keys.BLOCKS) {
    // @formatter:off

    /** Overworld materials */
    val SHROOMY_COBBLESTONE = StoneWallCombo("shroomy_cobblestone", HProperties.of().strength(2.0F, 6.0F))
    val SHROOMY_STONE_BRICKS = StoneWallCombo("shroomy_stone_bricks", HProperties.of().strength(2.0F, 6.0F))

    /** Ore blocks */
    val RAINBOWSTONE_ORE by BlockMaker.oreWithItem("rainbowstone_ore", BlockTags.NEEDS_DIAMOND_TOOL, 3.0f, 3.0f, MapColor.COLOR_PURPLE)
    val AURIGOLD_ORE by BlockMaker.oreWithItem("aurigold_ore", BlockTags.NEEDS_DIAMOND_TOOL, 3.0f, 3.0f, MapColor.COLOR_YELLOW)

    /** Gem blocks */
    val MALACHITE_BLOCK by BlockMaker.cubeAllWithItem("malachite_block", HProperties.of(MapColor.COLOR_GREEN).requiresCorrectToolForDrops().strength(5.0f, 6.0f))
    val RAINBOWSTONE_BLOCK by BlockMaker.cubeAllWithItem("rainbowstone_block", HProperties.of().requiresCorrectToolForDrops().strength(5.0f, 6.0f))
    //val DIAMOND_CRYSTAL = CrystalCombo("diamond_crystal", MapColor.COLOR_LIGHT_BLUE)
    val MALACHITE_CRYSTAL = CrystalCombo("malachite_crystal", MapColor.COLOR_GREEN)

    /** Wood */
    val LUMLIGHT_WOOD = WoodCombo("lumlight", MapColor.COLOR_LIGHT_BLUE, MapColor.TERRACOTTA_BLACK, LumlightTree, HBlockTags.LUMLIGHT_LOGS, HItemTags.LUMLIGHT_LOGS) { props -> props.strength(2.4f, 100.0f).sound(SoundType.WOOD) }
    val AURI_WOOD = WoodCombo("auri", MapColor.COLOR_YELLOW, MapColor.COLOR_LIGHT_GRAY, AuriTree, HBlockTags.AURI_LOGS, HItemTags.AURI_LOGS) { props -> props.sound(SoundType.WOOD).strength(2.4f, 100.0f) }
    val COTTONMARSH_WOOD = WoodCombo("cottonmarsh", MapColor.TERRACOTTA_BROWN, MapColor.TERRACOTTA_BROWN, CottonmarshTree, HBlockTags.COTTONMARSH_LOGS, HItemTags.COTTONMARSH_LOGS) { props -> props.sound(SoundType.WOOD).strength(2.5f) }

    // todo remove
    /** Chiseled block variants */
    val OAK_PLANKS = VariantCombo(HProperties.copy(Blocks.OAK_PLANKS), "oak_planks", "crate", "braided", "short", "clean")
    val SPRUCE_PLANKS = VariantCombo(HProperties.copy(Blocks.SPRUCE_PLANKS), "spruce_planks", "large")

    /** Castleton materials */
    val CASTLETON_SOIL = GroundCombo(
        "castleton_", true, HBlockTags.CASTLETON_GRASS_PLANTABLE,
        soilProps = HProperties.of(MapColor.COLOR_GRAY).strength(0.8f).sound(SoundType.GRAVEL)/*.harvestTool(BlockTags.MINEABLE_WITH_SHOVEL)*/,
        grassProps = HProperties.of(MapColor.COLOR_LIGHT_BLUE).randomTicks().strength(0.8f).sound(SoundType.GRASS)/*.harvestTool(BlockTags.MINEABLE_WITH_SHOVEL)*/
    ) // Soil, Grass, Loam
    val CASTLETON_STONE = StonePolishedBrickCombo("castleton_stone", HProperties.of(MapColor.COLOR_GRAY).strength(30.0f, 1200.0f)) // Stairs, Slab, Rock, Polished block/stairs/slab, Wall, Fence
    val CRACKED_CASTLETON_BRICKS by BlockMaker.cubeAllWithItem("cracked_castleton_bricks", HProperties.of(MapColor.COLOR_GRAY).strength(30.0f, 3.5f))
    val CHARGED_CASTLETON_BRICKS = StoneWallFenceCombo("charged_castleton_bricks", HProperties.of(MapColor.COLOR_GRAY).strength(35.0f, 1500.0f)) // No Polished
    // maybe this becomes an ore?
    val RUNED_CASTLETON_STONE by BlockMaker.blockWithItem("runed_castleton_stone") { HBlock(HProperties.of(MapColor.COLOR_LIGHT_BLUE).strength(8.0f, 400.0f)) }
    val CASTLETON_PORTAL = PortalCombo(HDimensions.CASTLETON_ID, HDimensions::CASTLETON_KEY) // Portal frame should be a block supplier
    val PURPLE_CASTLETON_LAMP by BlockMaker.blockWithItem("purple_castleton_lamp") { LampBlock(HProperties.copy(Blocks.REDSTONE_LAMP).strength(0.7f, 400.0f)) }
    val LUMLIGHT_BOOKSHELF by BlockMaker.blockWithItem("lumlight_bookshelf") { HBlock(HProperties.of().sound(SoundType.WOOD).strength(2.4f, 100.0f).enchantmentPower(1.0f)) }

    /** Castleton Props */
    val RED_WINE_BOTTLE by BlockMaker.wineBottleWithItem("red_wine_bottle")
    val WHITE_WINE_BOTTLE by BlockMaker.wineBottleWithItem("white_wine_bottle")
    val WINE_BOTTLE by BlockMaker.wineBottleWithItem("wine_bottle")
    val CASTLETON_TORCH = CastletonTorchCombo()
    val CASTLETON_LANTERN by BlockMaker.lanternWithItem("castleton_lantern", HProperties.copy(Blocks.LANTERN).strength(3.5f, 100.0f))
    val LUMLIGHT_CAMPFIRE by BlockMaker.blockWithItem("lumlight_campfire", ItemModelType.SIMPLE_ITEM) { LumlightCampfireBlock(HProperties.of(MapColor.PODZOL).strength(2.0F).sound(SoundType.WOOD).lightLevel(15).randomTicks().noOcclusion()) }
    val LOST_SKULL by BlockMaker.blockWithItem("lost_skull", ItemModelType.SIMPLE_ITEM) { HorizontalBlock(HProperties.of().noOcclusion().sound(SoundType.STONE).shape(BlockMaker.LOST_SKULL_SHAPE).strength(1.5f)) }
    val FRAYED_SKULL by BlockMaker.blockWithItem("frayed_skull", ItemModelType.SIMPLE_ITEM) { FrayedSkullBlock(HProperties.of().noOcclusion().sound(SoundType.STONE).shape(BlockMaker.LOST_SKULL_SHAPE).strength(1.5f)) }
    // todo rename to castleton crown
    val CROWN by BlockMaker.blockWithItem("crown", ItemModelType.SIMPLE_ITEM) { HorizontalBlock(HProperties.of().noOcclusion().sound(SoundType.METAL).strength(2.0f).shape(BlockMaker.CROWN_SHAPE)) }
    val CHALICE by BlockMaker.blockWithItem("chalice", ItemModelType.SIMPLE_ITEM) { HBlock(HProperties.of().noOcclusion().sound(SoundType.METAL).strength(3.0f).shape(BlockMaker.CHALICE_SHAPE)) }
    val PLATE by BlockMaker.blockWithItem("plate", ItemModelType.SIMPLE_ITEM) { HBlock(HProperties.of().noOcclusion().sound(SoundType.GLASS).strength(1.5f).shape(BlockMaker.PLATE_SHAPE)) }
    val CANDLE by BlockMaker.blockWithItem("candle") { HBlock(HProperties.of().strength(1.1f).lightLevel(15).shape(BlockMaker.CANDLE_SHAPE)) }

    /** Rainbowland materials */
    val RAINBOW_SOIL = GroundCombo("rainbow_", false, HBlockTags.RAINBOW_GRASS_PLANTABLE, HProperties.of(MapColor.COLOR_PURPLE).strength(0.5f).sound(SoundType.GRAVEL), HProperties.of().randomTicks().strength(0.6f).sound(SoundType.GRASS))
    val RAINBOW_ROCK = StonePolishedBrickCombo("rainbow_rock", HProperties.of(MapColor.COLOR_PURPLE).strength(1.5f, 6.0f))
    val RAINBOW_FACTORY_BRICKS = StoneWallFenceCombo("rainbow_factory_bricks", HProperties.of(MapColor.COLOR_PURPLE).strength(1.5f, 6.0f))
    val RAINBOWLAND_PORTAL = PortalCombo(HDimensions.RAINBOWLAND_ID, HDimensions::RAINBOWLAND_KEY)
    val RAINBOW_GLASS by BlockMaker.blockWithItem("rainbow_glass") { RainbowGlassBlock(HProperties.of(MapColor.COLOR_PURPLE).sound(SoundType.GLASS).strength(0.3f, 100.0f).noOcclusion()) }
    val RAINBOW_GLASS_PANE by BlockMaker.blockWithItem("rainbow_glass_pane") { RainbowGlassPaneBlock(HProperties.of(MapColor.COLOR_PURPLE).sound(SoundType.GLASS).strength(0.3f, 100.0f)) }

    /** Rainbowland props */
    val RAINBOW_FACTORY_FURNACE by BlockMaker.blockWithItem("rainbow_factory_furnace") { RainbowFactoryFurnaceBlock(HProperties.copy(
        RAINBOW_FACTORY_BRICKS.block).lightLevel { if (it.getValue(RainbowFactoryFurnaceBlock.LIT)) 15 else 0 }) } // Standalone

    /** Aubrum materials */
    val AURISOIL = GroundCombo("auri", true, HBlockTags.AURIGRASS_PLANTABLE, HProperties.of(MapColor.COLOR_GRAY).strength(0.4f).sound(SoundType.SAND), HProperties.of(MapColor.COLOR_YELLOW).randomTicks().strength(0.5f).sound(SoundType.SAND))
    val AUBRUM_ROCK by BlockMaker.cubeAllWithItem("aubrum_rock", HProperties.of(MapColor.COLOR_BLACK).strength(1.5f).sound(SoundType.STONE))
    val AUBRUM_PORTAL = PortalCombo(HDimensions.AUBRUM_ID, HDimensions::AUBRUM_KEY)

    /** Aubrum props */
    val DRUM by BlockMaker.blockWithItem("drum") { HBlock(HProperties.of().strength(2.5f).sound(SoundType.METAL).shape(BlockMaker.DRUM_SHAPE)) }
    val SCRAP_METAL = StairsSlabCombo("scrap_metal", HProperties.of(MapColor.COLOR_BROWN).strength(2.5f)/*.harvestLevel(1).harvestTool(ToolType.PICKAXE)*/.sound(HSounds.SCRAP_METAL))

    /** Candyland materials */
    val SUGARY_SOIL = GroundCombo("sugary_", false, HBlockTags.SUGARY_GRASS_PLANTABLE, HProperties.of(MapColor.SAND).sound(SoundType.GRAVEL), HProperties.of(MapColor.COLOR_PINK).sound(SoundType.GRASS), soilDelegate = { name, props -> BlockMaker.blockWithItem(name) {HBlock((props))} })
    val SUGAR_BLOCK by BlockMaker.cubeAllWithItem("sugar_block", HProperties.of(MapColor.SNOW).sound(SoundType.SAND)) // Standalone
    val CANDY_CANE_BLOCK by BlockMaker.rotatedPillarWithItem("candy_cane_block", HProperties.of().sound(SoundType.BASALT))
    val BENT_CANDY_CANE_BLOCK by BlockMaker.rotatableBlockWithItem("bent_candy_cane_block", HProperties.of().sound(SoundType.BASALT))
    val CHOCOLATE_BLOCK = StoneWallCombo("chocolate_block", HProperties.of(MapColor.COLOR_BROWN).strength(2.2f).sound(SoundType.BASALT))
    // todo custom material
    val GUMDROP_BLOCK = VariantCombo(HProperties.of().sound(SoundType.SLIME_BLOCK).strength(0.6f)/*.harvestTool(ToolType.HOE)*/, "gumdrop_block", "green", "pink", "blue", "purple", "red", "yellow")

    /** Dungeon features */
    val DUNGEON_CASTLETON_BRICKS by BlockMaker.unbreakableWithItem("dungeon_castleton_bricks", BlockModelType.CUBE_ALL, CASTLETON_STONE.brick::block) // Standalone? Adjust with needs later on
    //val DUNGEON_SPAWNER by BlockMaker.blockWithItem("dungeon_spawner") { DungeonSpawnerBlock(HProperties.copy(Blocks.BARRIER)) } // Standalone? Adjust with needs later on
    val BLUE_CASTLETON_DUNGEON_LOCK by BlockMaker.blockWithItem("blue_castleton_dungeon_lock") { LockBlock({ BLUE_CASTLETON_DUNGEON_KEY_ITEM }, { Blocks.OBSIDIAN }, HProperties.of(MapColor.COLOR_BLACK)) } // Door lock combo to handle key
    val SOOT by BlockMaker.withItem("soot", ItemModelType.BLOCK_ITEM, BlockMaker.registerModelled("soot", BlockModelType.SOOT) {
        SafeSootBlock(HProperties.of()
            .shape(BlockMaker.FARMLAND_SHAPE)
            .speedFactor(0.4f)
            .strength(40.0f, 3000.0f)
            .harvestTool(BlockTags.MINEABLE_WITH_SHOVEL))
    })
    val SOOT_TRAP = BlockMaker.withItem("soot_trap", ItemModelType.BLOCK_ITEM, BlockMaker.registerModelled("soot_trap", BlockModelType.SOOT, appearance = { SOOT }) { SootTrapBlock(HProperties.copy(
        SOOT
    ).lootFrom { SOOT }) })
    //val SOOT_TRAP_CONTROLLER by BlockMaker.blockWithItem("soot_trap_controller") { SootTrapControllerBlock(HProperties.of(Material.DECORATION).strength(50.0f, 1200.0f).noDrops()) }

    /** Vases */
    val VASE by BlockMaker.vaseWithItem("vase", MapColor.COLOR_GRAY)
    val SHROOMY_VASE by BlockMaker.vaseWithItem("shroomy_vase", MapColor.COLOR_GRAY)
    val CASTLETON_VASE by BlockMaker.vaseWithItem("castleton_vase", MapColor.TERRACOTTA_BLUE)
    val CASTLETON_TREASURE_VASE by BlockMaker.vaseWithItem("castleton_treasure_vase", MapColor.TERRACOTTA_BLUE)

    /** Maze Boss */
    val MAZE_BUSH by BlockMaker.cubeAllWithItem("maze_bush") { MazeBushBlock(HProperties.of(MapColor.PLANT).pushReaction(PushReaction.BLOCK).strength(50.0F, 1200.0F).sound(SoundType.GRASS)) }
    //val MAZE_BOSS_SPAWNER by BlockMaker.blockWithItem("maze_boss_spawner") { MazeBossSpawnerBlock(HProperties.of(Material.METAL).indestructible()) }

    /** Misc */
    //val COMPRESSED_COBBLESTONE = CompressedCombo(Blocks::COBBLESTONE)

    /** Farmlands */
    val ASHY_FARMLAND by BlockMaker.farmlandWithItem("ashy_farmland", Blocks::PODZOL, { Blocks.DIRT.defaultBlockState() }, HProperties.of(MapColor.COLOR_BROWN).sound(SoundType.GRAVEL).harvestTool(BlockTags.MINEABLE_WITH_SHOVEL).strength(0.6f)) { map ->
        map.put(Blocks::PUMPKIN_STEM, 0.6f)
        map.put(Blocks::MELON_STEM, 0.6f)
    }
    val SANDY_FARMLAND by BlockMaker.farmlandWithItem("sandy_farmland", Blocks::SAND, { Blocks.SAND.defaultBlockState() }, HProperties.of(MapColor.SAND).harvestTool(BlockTags.MINEABLE_WITH_SHOVEL).sound(SoundType.SAND).strength(0.6f))
    //val RED_SANDY_FARMLAND by BlockMaker.farmlandWithItem("red_sandy_farmland", Blocks::RED_SAND, { Blocks.RED_SAND.defaultBlockState() }, HProperties.of(Material.SAND, MapColor.COLOR_ORANGE).harvestTool(BlockTags.MINEABLE_WITH_SHOVEL).sound(SoundType.SAND).strength(0.6f))
    val RAINBOW_FARMLAND by BlockMaker.farmlandWithItem("rainbow_farmland", RAINBOW_SOIL::soil, { RAINBOW_SOIL.soil.defaultBlockState() }, HProperties.of(MapColor.COLOR_PURPLE).strength(0.5f).harvestTool(BlockTags.MINEABLE_WITH_SHOVEL).sound(SoundType.GRAVEL))
    val CASTLETON_FARLMAND by BlockMaker.farmlandWithItem("castleton_farmland", CASTLETON_SOIL::soil, { CASTLETON_SOIL.soil.defaultBlockState() }, HProperties.of(MapColor.COLOR_BLACK).strength(0.8f).harvestTool(BlockTags.MINEABLE_WITH_SHOVEL).sound(SoundType.GRAVEL))

    /** Crops */
    val CHILI_PEPPER = CropsCombo("chili_pepper", ItemMaker.food(2, 0.2f, fastEat = true, alwaysEdible = true)) { ChiliPepperBlock(HProperties.of().sound(SoundType.GRASS).instabreak().noCollision().randomTicks()) } //BlockMaker.createBlock("chili_pepper", ItemModelType.SIMPLE_ITEM) { ChiliPepperBlock(AbstractBlock.Properties.of(Material.PLANT).sound(SoundType.GRASS).strength(1.0f, 0.0f).noCollission().randomTicks()) }
    val WILD_BERROOK = CropsCombo("wild_berrook", ItemMaker.food(2, 0.4f, fastEat = true)) { HCropsBlock(HProperties.of(MapColor.COLOR_LIGHT_BLUE).instabreak().noCollision().sound(SoundType.CROP)) }

    /** Potted plants */
    val FLAME_ROSE = PottedPlantCombo("flame_rose", pot = FlameRoseBlock) { FlameRoseBlock(PlantProperties.of(MapColor.COLOR_ORANGE).stewEffect(MobEffects.FIRE_RESISTANCE, 19).sound(SoundType.GRASS).noCollision().instabreak().lightLevel(13)) }
    val GOLDEN_TULIP = PottedPlantCombo("golden_tulip") { FlowerBlock(PlantProperties.of(MapColor.COLOR_YELLOW).stewEffect(MobEffects.ABSORPTION, 5).sound(SoundType.GRASS).noCollision().instabreak().lightLevel(4)) }
    val PURPLE_LUMSHROOM = PottedPlantCombo("purple_lumshroom") { MushroomBlock(PlantProperties.of().validGround(
        CASTLETON_SOIL.soil, CASTLETON_SOIL.grass).sound(SoundType.GRASS).lightLevel(10).noCollision()) }
    val BLUE_LUMSHROOM = PottedPlantCombo("blue_lumshroom") { MushroomBlock(PlantProperties.of().validGround(
        CASTLETON_SOIL.soil, CASTLETON_SOIL.grass).sound(SoundType.GRASS).lightLevel(4).noCollision()) }
    val GREEN_GUMDROP = BlockMaker.gumdropCombo("green_gumdrop")
    val MINI_GREEN_GUMDROP = BlockMaker.gumdropCombo("mini_green_gumdrop")
    val PINK_GUMDROP = BlockMaker.gumdropCombo("pink_gumdrop")
    val MINI_PINK_GUMDROP = BlockMaker.gumdropCombo("mini_pink_gumdrop")
    val BLUE_GUMDROP = BlockMaker.gumdropCombo("blue_gumdrop")
    val MINI_BLUE_GUMDROP = BlockMaker.gumdropCombo("mini_blue_gumdrop")
    val PURPLE_GUMDROP = BlockMaker.gumdropCombo("purple_gumdrop")
    val MINI_PURPLE_GUMDROP = BlockMaker.gumdropCombo("mini_purple_gumdrop")
    val RED_GUMDROP = BlockMaker.gumdropCombo("red_gumdrop")
    val MINI_RED_GUMDROP = BlockMaker.gumdropCombo("mini_red_gumdrop")
    val YELLOW_GUMDROP = BlockMaker.gumdropCombo("yellow_gumdrop")
    val MINI_YELLOW_GUMDROP = BlockMaker.gumdropCombo("mini_yellow_gumdrop")

    init {
        onceRegistered {
            HBlockUtil.addValidBlocks(BlockEntityType.CAMPFIRE, LUMLIGHT_CAMPFIRE)
            HBlockUtil.registerHoeInteraction(CASTLETON_SOIL.grass, CASTLETON_FARLMAND.defaultBlockState())
            HBlockUtil.registerHoeInteraction(RAINBOW_SOIL.grass, RAINBOW_FARMLAND.defaultBlockState())
        }
    }
}