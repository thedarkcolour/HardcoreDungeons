
package thedarkcolour.hardcoredungeons.registry

import com.google.common.collect.ImmutableSet
import it.unimi.dsi.fastutil.objects.Object2FloatOpenHashMap
import net.minecraft.block.*
import net.minecraft.block.AbstractBlock.Properties
import net.minecraft.block.material.Material
import net.minecraft.block.material.MaterialColor
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.RenderTypeLookup
import net.minecraft.client.renderer.color.BlockColors
import net.minecraft.item.AxeItem
import net.minecraft.item.DyeColor
import net.minecraft.item.HoeItem
import net.minecraft.potion.Effects
import net.minecraft.tileentity.TileEntityType
import net.minecraft.util.Direction
import net.minecraft.util.math.shapes.VoxelShapes
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.common.ToolType
import net.minecraftforge.registries.IForgeRegistry
import thedarkcolour.hardcoredungeons.block.HBlock
import thedarkcolour.hardcoredungeons.block.decoration.DoorBlock
import thedarkcolour.hardcoredungeons.block.decoration.HorizontalBlock
import thedarkcolour.hardcoredungeons.block.decoration.PaneBlock
import thedarkcolour.hardcoredungeons.block.decoration.PressurePlateBlock
import thedarkcolour.hardcoredungeons.block.decoration.SlabBlock
import thedarkcolour.hardcoredungeons.block.decoration.StairsBlock
import thedarkcolour.hardcoredungeons.block.decoration.TrapDoorBlock
import thedarkcolour.hardcoredungeons.block.decoration.WoodButtonBlock
import thedarkcolour.hardcoredungeons.block.decoration.castleton.CastletonTorchBlock
import thedarkcolour.hardcoredungeons.block.decoration.castleton.LampBlock
import thedarkcolour.hardcoredungeons.block.decoration.castleton.LumlightCampfireBlock
import thedarkcolour.hardcoredungeons.block.misc.BonusFarmlandBlock
import thedarkcolour.hardcoredungeons.block.misc.CompressedBlock
import thedarkcolour.hardcoredungeons.block.misc.GrassBlock
import thedarkcolour.hardcoredungeons.block.plant.*
import thedarkcolour.hardcoredungeons.block.plant.FlowerBlock
import thedarkcolour.hardcoredungeons.block.plant.MushroomBlock
import thedarkcolour.hardcoredungeons.block.plant.SaplingBlock
import thedarkcolour.hardcoredungeons.block.plant.aubrum.FlameRoseBlock
import thedarkcolour.hardcoredungeons.block.plant.castleton.LumlightPodBlock
import thedarkcolour.hardcoredungeons.block.plant.misc.ChiliPepperBlock
import thedarkcolour.hardcoredungeons.block.plant.misc.GoldenCarrotsBlock
import thedarkcolour.hardcoredungeons.block.plant.trees.CottonmarshTree
import thedarkcolour.hardcoredungeons.block.plant.trees.LumlightTree
import thedarkcolour.hardcoredungeons.block.portal.CheekyTeleporterBlock
import thedarkcolour.hardcoredungeons.block.portal.PortalBlock
import thedarkcolour.hardcoredungeons.block.properties.HProperties
import thedarkcolour.hardcoredungeons.block.properties.PlantProperties
import thedarkcolour.hardcoredungeons.block.structure.DungeonMobSpawnerBlock
import thedarkcolour.hardcoredungeons.block.structure.SpawnerChestBlock
import thedarkcolour.hardcoredungeons.block.structure.castleton.FrayedSkullBlock
import thedarkcolour.hardcoredungeons.block.structure.rainbowland.RainbowFactoryFurnaceBlock
import thedarkcolour.hardcoredungeons.client.color.RainbowColor
import thedarkcolour.hardcoredungeons.data.modelgen.ModelType
import thedarkcolour.kotlinforforge.forge.runWhenOn
import java.util.*
import java.util.function.Supplier

/**
 * Handles block registry and contains references to every `Block` added by HCD.
 *
 * @author TheDarkColour, genericrandom64
 */
@Suppress("MemberVisibilityCanBePrivate", "HasPlatformType", "SpellCheckingInspection", "ReplacePutWithAssignment")
object HBlocks {
    // @formatter:off
    
    // Overworld
    val VASE = HBlock(HProperties.create(Material.MISCELLANEOUS, MaterialColor.GRAY).hardnessAndResistance(1.3f).shape(VoxelShapes.or(Block.makeCuboidShape(4.0, 0.0, 4.0, 12.0, 1.0, 12.0), Block.makeCuboidShape(3.0, 1.0, 3.0, 13.0, 9.0, 13.0), Block.makeCuboidShape(4.0, 9.0, 4.0, 12.0, 10.0, 12.0), Block.makeCuboidShape(6.0, 10.0, 6.0, 10.0, 11.0, 10.0), Block.makeCuboidShape(5.0, 11.0, 5.0, 11.0, 13.0, 11.0)))).setRegistryKey("vase")
    val SHROOMY_COBBLESTONE = Block(Properties.create(Material.ROCK).hardnessAndResistance(2.0F, 6.0F)).setRegistryKey("shroomy_cobblestone")
    val SHROOMY_COBBLESTONE_STAIRS = StairsBlock(SHROOMY_COBBLESTONE, Properties.from(SHROOMY_COBBLESTONE)).setRegistryKey("shroomy_cobblestone_stairs")
    val SHROOMY_COBBLESTONE_SLAB = SlabBlock(Properties.from(SHROOMY_COBBLESTONE)).setRegistryKey("shroomy_cobblestone_slab")
    val SHROOMY_STONE_BRICKS = Block(Properties.create(Material.ROCK).hardnessAndResistance(2.0F, 6.0F)).setRegistryKey("shroomy_stone_bricks")
    val SHROOMY_STONE_BRICK_STAIRS = StairsBlock(SHROOMY_STONE_BRICKS, Properties.from(SHROOMY_STONE_BRICKS)).setRegistryKey("shroomy_stone_brick_stairs")
    val SHROOMY_STONE_BRICK_SLAB = SlabBlock(Properties.from(SHROOMY_STONE_BRICKS)).setRegistryKey("shroomy_stone_brick_slab")
    val SHROOMY_VASE = HBlock(HProperties.from(VASE)).setRegistryKey("shroomy_vase")
    val ASHY_FARMLAND = BonusFarmlandBlock(
        boostMap = Object2FloatOpenHashMap<Supplier<Block>>().also { map ->
            map.put(Blocks::PUMPKIN_STEM, 0.6f)
            map.put(Blocks::MELON_STEM, 0.6f)
        },
        properties = HProperties.create(Material.EARTH, MaterialColor.BROWN).sound(SoundType.GROUND).hardnessAndResistance(0.6f),
    ).setRegistryKey("ashy_farmland")
    val SANDY_FARMLAND = BonusFarmlandBlock(
        getSoilState = Blocks.SAND::getDefaultState,
        properties = HProperties.create(Material.SAND, MaterialColor.SAND).sound(SoundType.SAND).hardnessAndResistance(0.6f),
    ).setRegistryKey("sandy_farmland")
    val GOLDEN_CARROTS = GoldenCarrotsBlock(Properties.from(Blocks.CARROTS)).setRegistryKey("golden_carrots")

    // Castleton
    val CASTLETON_SOIL = Block(Properties.create(Material.EARTH, MaterialColor.GRAY).hardnessAndResistance(0.8f).sound(SoundType.GROUND)).setRegistryKey("castleton_soil")
    val CASTLETON_GRASS_BLOCK = GrassBlock(CASTLETON_SOIL, true, Properties.create(Material.ORGANIC, DyeColor.LIGHT_BLUE).tickRandomly().hardnessAndResistance(0.8f).sound(SoundType.PLANT)).setRegistryKey("castleton_grass_block")
    val CASTLETON_STONE = Block(Properties.create(Material.ROCK, MaterialColor.GRAY).hardnessAndResistance(8.0f, 400.0f)).setRegistryKey("castleton_stone")
    val CASTLETON_LOAM = Block(Properties.create(Material.EARTH, MaterialColor.GRAY).hardnessAndResistance(0.6f).sound(SoundType.GROUND)).setRegistryKey("castleton_loam")
    val CASTLETON_BRICKS = Block(Properties.create(Material.ROCK, MaterialColor.GRAY).hardnessAndResistance(30.0f, 1200.0f)).setRegistryKey("castleton_bricks")
    val CASTLETON_BRICK_STAIRS = StairsBlock(CASTLETON_BRICKS, Properties.from(CASTLETON_BRICKS)).setRegistryKey("castleton_brick_stairs")
    val CASTLETON_BRICK_SLAB = SlabBlock(Properties.from(CASTLETON_BRICKS)).setRegistryKey("castleton_brick_slab")
    val CASTLETON_BRICK_FENCE = FenceBlock(Properties.from(CASTLETON_BRICKS)).setRegistryKey("castleton_brick_fence")
    val CASTLETON_BRICK_WALL = WallBlock(Properties.from(CASTLETON_BRICKS)).setRegistryKey("castleton_brick_wall")
    val CRACKED_CASTLETON_BRICKS = Block(Properties.create(Material.ROCK, MaterialColor.GRAY).hardnessAndResistance(30.0f, 3.5f)).setRegistryKey("cracked_castleton_bricks")
    val CHARGED_CASTLETON_BRICKS = Block(Properties.create(Material.ROCK, MaterialColor.GRAY).hardnessAndResistance(35.0f, 1500.0f)).setRegistryKey("charged_castleton_bricks")
    val CHARGED_CASTLETON_BRICK_STAIRS = StairsBlock(CHARGED_CASTLETON_BRICKS, Properties.from(CHARGED_CASTLETON_BRICKS)).setRegistryKey("charged_castleton_brick_stairs")
    val CHARGED_CASTLETON_BRICK_SLAB = SlabBlock(Properties.from(CHARGED_CASTLETON_BRICKS)).setRegistryKey("charged_castleton_brick_slab")
    val CHARGED_CASTLETON_BRICK_FENCE = FenceBlock(Properties.from(CASTLETON_BRICKS)).setRegistryKey("charged_castleton_brick_fence")
    val CHARGED_CASTLETON_BRICK_WALL = WallBlock(Properties.from(CASTLETON_BRICKS)).setRegistryKey("charged_castleton_brick_wall")
    val CASTLETON_PORTAL_FRAME = Block(Properties.from(Blocks.OBSIDIAN)).setRegistryKey("castleton_portal_frame")
    val CASTLETON_PORTAL = PortalBlock(HDimensions::CASTLETON, CASTLETON_PORTAL_FRAME, HProperties.create(Material.PORTAL).doesNotBlockMovement().hardnessAndResistance(-1.0f).sound(SoundType.GLASS)/*.lightValue(12)*/.noDrops()).setRegistryKey("castleton_portal")
    val PURPLE_CASTLETON_LAMP = LampBlock(Properties.from(Blocks.REDSTONE_LAMP).hardnessAndResistance(0.7f, 400.0f)).setRegistryKey("purple_castleton_lamp")
    val LUMLIGHT_SAPLING = SaplingBlock(LumlightTree, Properties.create(Material.PLANTS).sound(SoundType.PLANT).doesNotBlockMovement()).setRegistryKey("lumlight_sapling")
    val LUMLIGHT_LOG = createLogBlock(MaterialColor.WHITE_TERRACOTTA, MaterialColor.BLACK_TERRACOTTA ){ properties -> properties.hardnessAndResistance(2.4f, 100.0f).sound(SoundType.WOOD)}.setRegistryKey("lumlight_log")
    val LUMLIGHT_WOOD = RotatedPillarBlock(Properties.from(LUMLIGHT_LOG)).setRegistryKey("lumlight_wood")
    val STRIPPED_LUMLIGHT_LOG = createLogBlock(MaterialColor.WHITE_TERRACOTTA, MaterialColor.WHITE_TERRACOTTA){ Properties.create(Material.WOOD, MaterialColor.WHITE_TERRACOTTA).hardnessAndResistance(2.4f, 100.0f).sound(SoundType.WOOD)}.setRegistryKey("stripped_lumlight_log")
    val STRIPPED_LUMLIGHT_WOOD = RotatedPillarBlock(Properties.from(STRIPPED_LUMLIGHT_LOG)).setRegistryKey("stripped_lumlight_wood")
    val LUMLIGHT_LEAVES = LeavesBlock(HProperties.create(Material.LEAVES, DyeColor.LIGHT_BLUE).hardnessAndResistance(0.2F).nonSolid().tickRandomly().sound(SoundType.PLANT).build()).setRegistryKey("lumlight_leaves")
    val LUMLIGHT_POD = LumlightPodBlock(HProperties.fromVanilla(Blocks.COCOA).light(14)).setRegistryKey("lumlight_pod")
    val LUMLIGHT_PLANKS = Block(Properties.create(Material.WOOD, MaterialColor.WHITE_TERRACOTTA).sound(SoundType.WOOD).hardnessAndResistance(2.4f, 100.0f)).setRegistryKey("lumlight_planks")
    val LUMLIGHT_STAIRS = StairsBlock(LUMLIGHT_PLANKS, Properties.from(LUMLIGHT_PLANKS)).setRegistryKey("lumlight_stairs")
    val LUMLIGHT_SLAB = SlabBlock(Properties.from(LUMLIGHT_PLANKS)).setRegistryKey("lumlight_slab")
    val LUMLIGHT_FENCE = FenceBlock(Properties.from(LUMLIGHT_PLANKS)).setRegistryKey("lumlight_fence")
    val LUMLIGHT_FENCE_GATE = FenceGateBlock(Properties.from(LUMLIGHT_PLANKS)).setRegistryKey("lumlight_fence_gate")
    val LUMLIGHT_PRESSURE_PLATE = PressurePlateBlock(net.minecraft.block.PressurePlateBlock.Sensitivity.EVERYTHING, Properties.create(Material.WOOD, MaterialColor.WHITE_TERRACOTTA).doesNotBlockMovement().hardnessAndResistance(0.5F).sound(SoundType.WOOD)).setRegistryKey("lumlight_pressure_plate")
    val LUMLIGHT_BUTTON = WoodButtonBlock(Properties.create(Material.MISCELLANEOUS).doesNotBlockMovement().hardnessAndResistance(0.5F).sound(SoundType.WOOD)).setRegistryKey("lumlight_button")
    val LUMLIGHT_DOOR = DoorBlock(Properties.create(Material.WOOD, MaterialColor.WHITE_TERRACOTTA).hardnessAndResistance(3.0F).sound(SoundType.WOOD)).setRegistryKey("lumlight_door")
    val LUMLIGHT_TRAPDOOR = TrapDoorBlock(Properties.create(Material.WOOD, MaterialColor.WHITE_TERRACOTTA).hardnessAndResistance(3.0F).sound(SoundType.WOOD)).setRegistryKey("lumlight_trapdoor")
    val LUMLIGHT_SIGN = StandingSignBlock(Properties.create(Material.WOOD, DyeColor.LIGHT_BLUE).doesNotBlockMovement().hardnessAndResistance(1.0F).sound(SoundType.WOOD), WoodTypes.LUMLIGHT).setRegistryKey("lumlight_sign")
    val LUMLIGHT_WALL_SIGN = WallSignBlock(Properties.create(Material.WOOD, DyeColor.LIGHT_BLUE).doesNotBlockMovement().hardnessAndResistance(1.0F).sound(SoundType.WOOD), WoodTypes.LUMLIGHT).setRegistryKey("lumlight_wall_sign")
    val PURPLE_LUMSHROOM = MushroomBlock(PlantProperties.create(Material.PLANTS).strict().validGround(CASTLETON_SOIL, CASTLETON_GRASS_BLOCK).sound(SoundType.PLANT).light(10).doesNotBlockMovement()).setRegistryKey("purple_lumshroom")
    val BLUE_LUMSHROOM = MushroomBlock(PlantProperties.create(Material.PLANTS).strict().validGround(CASTLETON_SOIL, CASTLETON_GRASS_BLOCK).sound(SoundType.PLANT).light(4).doesNotBlockMovement()).setRegistryKey("blue_lumshroom")
    val LOST_SKULL = HorizontalBlock(HProperties.create(Material.ROCK).nonSolid().sound(SoundType.STONE).shape(Block.makeCuboidShape(4.0, 0.0, 4.0, 12.0, 8.0, 12.0)).hardnessAndResistance(1.5f)).setRegistryKey("lost_skull")
    val CASTLETON_TORCH = CastletonTorchBlock(HProperties.create(Material.MISCELLANEOUS).sound(SoundType.WOOD).doesNotBlockMovement()).setRegistryKey("castleton_torch")
    val CASTLETON_WALL_TORCH = CastletonTorchBlock.Wall(HProperties.create(Material.MISCELLANEOUS).sound(SoundType.WOOD).doesNotBlockMovement()/*.lightValue(7)*/).setRegistryKey("castleton_wall_torch")
    val CROWN = HorizontalBlock(HProperties.create(Material.IRON).nonSolid().sound(SoundType.METAL).hardnessAndResistance(2.0f).shape(Block.makeCuboidShape(4.0, 0.0, 4.0, 12.0, 4.0, 12.0))).setRegistryKey("crown")
    val CHALICE = HBlock(HProperties.create(Material.IRON).nonSolid().sound(SoundType.METAL).hardnessAndResistance(3.0f).shape(VoxelShapes.or(Block.makeCuboidShape(6.0, 0.0, 6.0, 10.0, 1.0, 10.0), Block.makeCuboidShape(7.0, 1.0, 7.0, 9.0, 3.0, 9.0), Block.makeCuboidShape(6.0, 3.0, 6.0, 10.0, 4.0, 10.0), Block.makeCuboidShape(5.0, 4.0, 5.0, 11.0, 8.0, 11.0)))).setRegistryKey("chalice")
    val PLATE = HBlock(HProperties.create(Material.GLASS).nonSolid().sound(SoundType.GLASS).hardnessAndResistance(1.5f).shape(Block.makeCuboidShape(3.0, 0.0, 3.0, 13.0, 2.0, 13.0))).setRegistryKey("plate")
    val FRAYED_SKULL = FrayedSkullBlock(HProperties.create(Material.ROCK).nonSolid().sound(SoundType.STONE).shape(Block.makeCuboidShape(4.0, 0.0, 4.0, 12.0, 8.0, 12.0)).hardnessAndResistance(1.5f)).setRegistryKey("frayed_skull")
    val RED_WINE_BOTTLE = HBlock(HProperties.create(Material.GLASS).nonSolid().sound(SoundType.GLASS).shape(VoxelShapes.or(Block.makeCuboidShape(6.5, 0.0, 6.5, 9.5, 7.0, 9.5), Block.makeCuboidShape(7.5, 7.0, 7.5, 8.5, 11.0, 8.5)))).setRegistryKey("red_wine_bottle")
    val WHITE_WINE_BOTTLE = HBlock(HProperties.create(Material.GLASS).nonSolid().sound(SoundType.GLASS).shapeFrom(RED_WINE_BOTTLE)).setRegistryKey("white_wine_bottle")
    val WINE_BOTTLE = HBlock(HProperties.create(Material.GLASS).nonSolid().sound(SoundType.GLASS).shapeFrom(RED_WINE_BOTTLE)).setRegistryKey("wine_bottle")
    val CASTLETON_LANTERN = LanternBlock(Properties.from(Blocks.LANTERN).hardnessAndResistance(3.5f, 100.0f)).setRegistryKey("castleton_lantern")
    val DUNGEON_MOB_SPAWNER = DungeonMobSpawnerBlock(Properties.from(Blocks.BARRIER)).setRegistryKey("dungeon_mob_spawner")
    val LUMLIGHT_CAMPFIRE = LumlightCampfireBlock(HProperties.create(Material.WOOD, MaterialColor.OBSIDIAN).hardnessAndResistance(2.0F).sound(SoundType.WOOD).light(15).tickRandomly()).setRegistryKey("lumlight_campfire")
    val CASTLETON_VASE = HBlock(HProperties.from(VASE)).setRegistryKey("castleton_vase")
    val CASTLETON_TREASURE_VASE = HBlock(HProperties.from(VASE)).setRegistryKey("castleton_treasure_vase")
    val LUMLIGHT_BOOKSHELF = HBlock(HProperties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(2.4f, 100.0f).enchantmentPower(1.0f)).setRegistryKey("lumlight_bookshelf")
    val CASTLETON_DUNGEON_CHEST = SpawnerChestBlock(HProperties.create(Material.ROCK).hardnessAndResistance(9.5f, 1200.0f)).setRegistryKey("castleton_dungeon_chest")
    val CANDLE = HBlock(HProperties.create(Material.MISCELLANEOUS).hardnessAndResistance(1.1f).light(15).shape(VoxelShapes.or(Block.makeCuboidShape(6.0, 0.0, 6.0, 10.0, 1.0, 10.0), Block.makeCuboidShape(7.0, 0.75, 7.0, 9.0, 2.75, 9.0), Block.makeCuboidShape(6.0, 2.5, 6.0, 10.0, 4.5, 10.0), Block.makeCuboidShape(7.0, 3.5, 7.0, 9.0, 15.5, 9.0), Block.makeCuboidShape(7.5, 14.75, 7.5, 8.5, 15.75, 8.5)))).setRegistryKey("candle")
    val RUNED_CASTLETON_STONE = HBlock(HProperties.create(Material.ROCK, MaterialColor.LIGHT_BLUE).hardnessAndResistance(8.0f, 400.0f)).setRegistryKey("runed_castleton_stone")
    val WILD_BERROOK = HCropsBlock(HProperties.create(Material.PLANTS, MaterialColor.LIGHT_BLUE).zeroHardnessAndResistance().doesNotBlockMovement().sound(SoundType.CROP)).setRegistryKey("wild_berrook")

    // Rainbowland
    val RAINBOW_SOIL = Block(Properties.create(Material.EARTH, MaterialColor.PURPLE).hardnessAndResistance(0.5f).sound(SoundType.GROUND)).setRegistryKey("rainbow_soil")
    val RAINBOW_GRASS_BLOCK = GrassBlock(RAINBOW_SOIL, false, Properties.create(Material.ORGANIC).tickRandomly().hardnessAndResistance(0.6f).sound(SoundType.PLANT)).setRegistryKey("rainbow_grass_block")
    val RAINBOW_ROCK = Block(Properties.create(Material.ROCK, MaterialColor.PURPLE).hardnessAndResistance(1.5f, 6.0f)).setRegistryKey("rainbow_rock")
    val RAINBOW_BRICKS = Block(Properties.create(Material.ROCK, MaterialColor.PURPLE).hardnessAndResistance(4.0f)).setRegistryKey("rainbow_bricks")
    val RAINBOW_BRICK_STAIRS = StairsBlock(RAINBOW_BRICKS, Properties.from(RAINBOW_BRICKS)).setRegistryKey("rainbow_brick_stairs")
    val RAINBOW_BRICK_SLAB = SlabBlock(Properties.from(RAINBOW_BRICKS)).setRegistryKey("rainbow_brick_slab")
    val RAINBOW_BRICK_WALL = WallBlock(Properties.from(RAINBOW_BRICKS)).setRegistryKey("rainbow_brick_wall")
    val RAINBOW_BRICK_FENCE = FenceBlock(Properties.from(RAINBOW_BRICKS)).setRegistryKey("rainbow_brick_fence")
    val RAINBOWLAND_PORTAL_FRAME = Block(Properties.from(Blocks.OBSIDIAN)).setRegistryKey("rainbowland_portal_frame")
    val RAINBOWLAND_PORTAL = PortalBlock(HDimensions::RAINBOWLAND, RAINBOWLAND_PORTAL_FRAME, HProperties.create(Material.PORTAL).doesNotBlockMovement().hardnessAndResistance(-1.0f).sound(SoundType.GLASS).noDrops()).setRegistryKey("rainbowland_portal")
    val COTTONMARSH_SAPLING = SaplingBlock(CottonmarshTree, Properties.from(LUMLIGHT_SAPLING)).setRegistryKey("cottonmarsh_sapling")
    val COTTONMARSH_LEAVES = LeavesBlock(Properties.from(Blocks.OAK_LEAVES).hardnessAndResistance(0.2f).sound(SoundType.CLOTH).notSolid()).setRegistryKey("cottonmarsh_leaves")
    val COTTONMARSH_LOG = createLogBlock(MaterialColor.BROWN_TERRACOTTA, MaterialColor.BROWN_TERRACOTTA){Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(2.5f)}.setRegistryKey("cottonmarsh_log")
    val COTTONMARSH_WOOD = RotatedPillarBlock(Properties.from(COTTONMARSH_LOG)).setRegistryKey("cottonmarsh_wood")
    val STRIPPED_COTTONMARSH_LOG = createLogBlock(MaterialColor.BROWN_TERRACOTTA, MaterialColor.BROWN_TERRACOTTA){Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(2.5f)}.setRegistryKey("stripped_cottonmarsh_log")
    val STRIPPED_COTTONMARSH_WOOD = RotatedPillarBlock(Properties.from(COTTONMARSH_LOG)).setRegistryKey("stripped_cottonmarsh_wood")
    val RAINBOW_FACTORY_BRICKS = Block(Properties.create(Material.ROCK, DyeColor.PURPLE).hardnessAndResistance(35.0f, 1500.0f)).setRegistryKey("rainbow_factory_bricks")
    val RAINBOW_FACTORY_BRICK_STAIRS = StairsBlock(RAINBOW_FACTORY_BRICKS, Properties.from(RAINBOW_FACTORY_BRICKS)).setRegistryKey("rainbow_factory_brick_stairs")
    val RAINBOW_FACTORY_BRICK_SLAB = SlabBlock(Properties.from(RAINBOW_FACTORY_BRICKS)).setRegistryKey("rainbow_factory_brick_slab")
    val RAINBOW_FACTORY_BRICK_WALL = WallBlock(Properties.from(RAINBOW_FACTORY_BRICKS)).setRegistryKey("rainbow_factory_brick_wall")
    val RAINBOW_FACTORY_BRICK_FENCE = FenceBlock(Properties.from(RAINBOW_FACTORY_BRICKS)).setRegistryKey("rainbow_factory_brick_fence")
    val RAINBOW_FACTORY_FURNACE = RainbowFactoryFurnaceBlock(Properties.from(RAINBOW_FACTORY_BRICKS)).setRegistryKey("rainbow_factory_furnace")
    //val RAINBOW_FACTORY_TABLE = Block(Properties.from(RAINBOW_FACTORY_BRICKS)).setRegistryKey("rainbow_factory_table")
    val RAINBOW_GLASS = GlassBlock(Properties.create(Material.GLASS, DyeColor.PURPLE).sound(SoundType.GLASS).hardnessAndResistance(0.3f, 100.0f)).setRegistryKey("rainbow_glass")
    val RAINBOW_GLASS_PANE = PaneBlock(Properties.create(Material.GLASS, DyeColor.PURPLE).sound(SoundType.GLASS).hardnessAndResistance(0.3f, 100.0f)).setRegistryKey("rainbow_glass_pane")
    val RAINBOWSTONE_ORE = Block(Properties.from(RAINBOW_ROCK)).setRegistryKey("rainbowstone_ore")
    val RAINBOWSTONE_BLOCK = Block(Properties.create(Material.IRON, DyeColor.RED).sound(SoundType.METAL)).setRegistryKey("rainbowstone_block")
    val RAINBOW_FARMLAND = BonusFarmlandBlock(getSoilState = RAINBOW_SOIL::getDefaultState, properties = HProperties.create(Material.EARTH, MaterialColor.PURPLE).hardnessAndResistance(0.5f).sound(SoundType.GROUND)).setRegistryKey("rainbow_farmland")

    // Aubrum
    val FLAME_ROSE = FlameRoseBlock(PlantProperties.create(Material.PLANTS, DyeColor.ORANGE).effect(Effects.FIRE_RESISTANCE).duration(19).sound(SoundType.PLANT).doesNotBlockMovement().hardnessAndResistance(0.0f).light(13)).setRegistryKey("flame_rose")
    val GOLDEN_TULIP = FlowerBlock(PlantProperties.create(Material.PLANTS, DyeColor.YELLOW).effect(Effects.ABSORPTION).duration(5).sound(SoundType.PLANT).doesNotBlockMovement().hardnessAndResistance(0.0f).light(4)).setRegistryKey("golden_tulip")
    val AURISOIL = Block(Properties.create(Material.EARTH, MaterialColor.GRAY).hardnessAndResistance(0.4f).sound(SoundType.SAND)).setRegistryKey("aurisoil")
    val AURIGRASS_BLOCK = GrassBlock(AURISOIL, true, Properties.create(Material.ORGANIC, DyeColor.YELLOW).tickRandomly().hardnessAndResistance(0.5f).sound(SoundType.SAND)).setRegistryKey("aurigrass_block")
    val AURILOAM = Block(Properties.create(Material.ROCK).hardnessAndResistance(1.0f).sound(SoundType.SAND)).setRegistryKey("auriloam")
    //val GOLDEN_AURISOIL = Block(Properties.create(Material.EARTH, MaterialColor.GOLD).hardnessAndResistance(0.4f).sound(SoundType.SAND)).setRegistryKey("golden_aurisoil")
    //val GOLDEN_AURIGRASS_BLOCK = GrassBlock(GOLDEN_AURISOIL, true, Properties.create(Material.ORGANIC, MaterialColor.GOLD).tickRandomly().hardnessAndResistance(0.5f).sound(SoundType.SAND)).setRegistryKey("golden_aurigrass_block")
    val AUBRUM_ROCK = Block(Properties.create(Material.ROCK, MaterialColor.BLACK).hardnessAndResistance(1.5f).sound(SoundType.STONE)).setRegistryKey("aubrum_rock")
    val AUBRUM_PORTAL = PortalBlock(HDimensions::AUBRUM, CASTLETON_PORTAL_FRAME, HProperties.create(Material.PORTAL).doesNotBlockMovement().hardnessAndResistance(-1.0f).sound(SoundType.GLASS).light(12).noDrops()).setRegistryKey("aubrum_portal")
    val DRUM = HBlock(HProperties.create(Material.IRON).hardnessAndResistance(2.5f).sound(SoundType.METAL).shape(Block.makeCuboidShape(3.0, 0.0, 3.0, 13.0, 16.0, 13.0))).setRegistryKey("drum")
    val AURI_LOG = createLogBlock(MaterialColor.WHITE_TERRACOTTA, MaterialColor.WHITE_TERRACOTTA){Properties.create(Material.WOOD, MaterialColor.BLACK_TERRACOTTA).hardnessAndResistance(2.4f, 100.0f).sound(SoundType.WOOD)}.setRegistryKey("auri_log")
    val AURI_WOOD = RotatedPillarBlock(Properties.from(AURI_LOG)).setRegistryKey("auri_wood")
    val STRIPPED_AURI_LOG = createLogBlock(MaterialColor.WHITE_TERRACOTTA, MaterialColor.WHITE_TERRACOTTA){Properties.create(Material.WOOD, MaterialColor.WHITE_TERRACOTTA).hardnessAndResistance(2.4f, 100.0f).sound(SoundType.WOOD)}.setRegistryKey("stripped_auri_log")
    val STRIPPED_AURI_WOOD = RotatedPillarBlock(Properties.from(STRIPPED_AURI_LOG)).setRegistryKey("stripped_auri_wood")
    val AURI_PLANKS = Block(Properties.create(Material.WOOD, MaterialColor.WHITE_TERRACOTTA).sound(SoundType.WOOD).hardnessAndResistance(2.4f, 100.0f)).setRegistryKey("auri_planks")
    val AURI_STAIRS = StairsBlock(AURI_PLANKS, Properties.from(AURI_PLANKS)).setRegistryKey("auri_stairs")
    val AURI_SLAB = SlabBlock(Properties.from(AURI_PLANKS)).setRegistryKey("auri_slab")
    val SCRAP_METAL = HBlock(HProperties.create(Material.IRON, MaterialColor.BROWN).hardnessAndResistance(2.5f).harvestLevel(1).harvestTool(ToolType.PICKAXE).sound(HSounds.SCRAP_METAL)).setRegistryKey("scrap_metal")
    val AURIGOLD_ORE = HBlock(HProperties.create(Material.ROCK, MaterialColor.BLACK).hardnessAndResistance(3.3f).harvestLevel(3).harvestTool(ToolType.PICKAXE)).setRegistryKey("aurigold_ore")

    // Misc
    val CHILI_PEPPER = ChiliPepperBlock(Properties.create(Material.PLANTS).sound(SoundType.PLANT).hardnessAndResistance(1.0f, 0.0f).doesNotBlockMovement().tickRandomly()).setRegistryKey("chili_pepper")
    //val EXTRACTOR = ExtractorBlock(HProperties.create(Material.WOOD, MaterialColor.OBSIDIAN).hardnessAndResistance(2.0F).sound(SoundType.STONE).light(0)).setRegistryKey("extractor")
    //val CORN = CornBlock(Properties.create(Material.PLANTS).sound(SoundType.PLANT).hardnessAndResistance(1.0f, 0.0f).doesNotBlockMovement().tickRandomly()).setRegistryKey("corn")
    //val TABLE = HorizontalBlock(HProperties.create(Material.ROCK).sound(SoundType.STONE).shape(Block.makeCuboidShape(0.0, 0.0, 0.0, 16.0, 16.0, 16.0)).hardnessAndResistance(1.5f)).setRegistryKey("table")
    val COMPRESSED_COBBLESTONE = CompressedBlock(Blocks::COBBLESTONE, null)
    val CHEEKY_TELEPORTER_TOP = HBlock(HProperties.fromVanilla(Blocks.BEDROCK).shape(CheekyTeleporterBlock.TOP_SHAPE).light(15)).setRegistryKey("cheeky_teleporter_top")
    val CHEEKY_TELEPORTER = CheekyTeleporterBlock(HProperties.fromVanilla(Blocks.BEDROCK).light(15)).setRegistryKey("cheeky_teleporter")
    val STONE_TEXTURE = HBlock(HProperties.fromVanilla(Blocks.BEDROCK)).setRegistryKey("stone_texture")
    val STONE_TEXTURE_STAIRS = StairsBlock(STONE_TEXTURE, Properties.from(Blocks.BEDROCK)).setRegistryKey("stone_texture_stairs")
    val STONE_TEXTURE_SLAB = SlabBlock(Properties.from(Blocks.BEDROCK)).setRegistryKey("stone_texture_slab")

    // @formatter:on

    /**
     * Registers each block during the block registry event.
     *
     * @param blocks the registry provided by `RegistryEvent.Register<Block>`
     */
    @Suppress("DuplicatedCode")
    fun registerBlocks(blocks: IForgeRegistry<Block>) {
        // todo single texture template function
        blocks.registerVase(VASE)
        blocks.registerSimpleBlock(SHROOMY_COBBLESTONE)
        blocks.registerStairsBlock(SHROOMY_COBBLESTONE_STAIRS, fullBlock = SHROOMY_COBBLESTONE)
        blocks.registerSlabBlock(SHROOMY_COBBLESTONE_SLAB, fullBlock = SHROOMY_COBBLESTONE)
        blocks.registerSimpleBlock(SHROOMY_STONE_BRICKS)
        blocks.registerStairsBlock(SHROOMY_STONE_BRICK_STAIRS, fullBlock = SHROOMY_COBBLESTONE)
        blocks.registerSlabBlock(SHROOMY_STONE_BRICK_SLAB, fullBlock = SHROOMY_COBBLESTONE)
        blocks.registerVase(SHROOMY_VASE)
        blocks.registerFarmlandBlock(ASHY_FARMLAND)
        blocks.registerFarmlandBlock(SANDY_FARMLAND)
        blocks.register(GOLDEN_CARROTS)

        blocks.registerSimpleBlock(CASTLETON_SOIL)
        blocks.registerBlock(CASTLETON_GRASS_BLOCK)
        blocks.registerSimpleBlock(CASTLETON_LOAM)
        blocks.registerSimpleBlock(CASTLETON_STONE)
        blocks.registerSimpleBlock(CASTLETON_BRICKS)
        blocks.registerStairsBlock(CASTLETON_BRICK_STAIRS, CASTLETON_BRICKS)
        blocks.registerSlabBlock(CASTLETON_BRICK_SLAB, CASTLETON_BRICKS)
        blocks.registerFenceBlock(CASTLETON_BRICK_FENCE)
        blocks.registerWallBlock(CASTLETON_BRICK_WALL)
        blocks.registerSimpleBlock(CRACKED_CASTLETON_BRICKS)
        blocks.registerSimpleBlock(CHARGED_CASTLETON_BRICKS)
        blocks.registerStairsBlock(CHARGED_CASTLETON_BRICK_STAIRS, CHARGED_CASTLETON_BRICKS)
        blocks.registerSlabBlock(CHARGED_CASTLETON_BRICK_SLAB, CHARGED_CASTLETON_BRICKS)
        blocks.registerBlock(CHARGED_CASTLETON_BRICK_FENCE) //
        blocks.registerWallBlock(CHARGED_CASTLETON_BRICK_WALL)
        blocks.registerSimpleBlock(CASTLETON_PORTAL_FRAME)
        blocks.registerBlockAndSimpleItem(CASTLETON_PORTAL) // todo portal model gen
        blocks.registerSimpleBlock(PURPLE_CASTLETON_LAMP)
        blocks.registerBlockAndSimpleItem(LUMLIGHT_SAPLING)
        blocks.registerPillarBlock(LUMLIGHT_LOG, end = "lumlight_log_top", side = "lumlight_log")
        blocks.registerPillarBlock(LUMLIGHT_WOOD, end = "lumlight_log", side = "lumlight_log")
        blocks.registerPillarBlock(STRIPPED_LUMLIGHT_LOG, end = "stripped_lumlight_log_top", side = "stripped_lumlight_log")
        blocks.registerPillarBlock(STRIPPED_LUMLIGHT_WOOD, end = "stripped_lumlight_log", side = "stripped_lumlight_log")
        blocks.registerSimpleBlock(LUMLIGHT_LEAVES)
        blocks.register(LUMLIGHT_POD)
        blocks.registerBlock(LUMLIGHT_PLANKS)
        blocks.registerStairsBlock(LUMLIGHT_STAIRS, LUMLIGHT_PLANKS)
        blocks.registerSlabBlock(LUMLIGHT_SLAB, LUMLIGHT_PLANKS)
        blocks.registerFenceBlock(LUMLIGHT_FENCE) // todo fix
        blocks.registerBlock(LUMLIGHT_FENCE_GATE) // todo fix
        blocks.registerBlock(LUMLIGHT_PRESSURE_PLATE) // todo fix
        blocks.registerBlock(LUMLIGHT_BUTTON) // todo fix
        blocks.registerDoorBlock(LUMLIGHT_DOOR)
        blocks.registerBlock(LUMLIGHT_TRAPDOOR)
        blocks.register(LUMLIGHT_SIGN)
        blocks.register(LUMLIGHT_WALL_SIGN)
        blocks.registerBlockAndSimpleItem(PURPLE_LUMSHROOM)
        blocks.registerBlockAndSimpleItem(BLUE_LUMSHROOM)
        blocks.registerBlockAndSimpleItem(LOST_SKULL)
        blocks.register(CASTLETON_TORCH)
        blocks.register(CASTLETON_WALL_TORCH)
        blocks.registerBlockAndSimpleItem(CROWN)
        blocks.registerBlockAndSimpleItem(CHALICE)
        blocks.registerBlockAndSimpleItem(PLATE)
        blocks.registerBlockAndSimpleItem(FRAYED_SKULL)
        blocks.registerBlockAndSimpleItem(RED_WINE_BOTTLE)
        blocks.registerBlockAndSimpleItem(WHITE_WINE_BOTTLE)
        blocks.registerBlockAndSimpleItem(WINE_BOTTLE)
        blocks.registerBlockAndSimpleItem(CASTLETON_LANTERN)
        blocks.registerBlock(DUNGEON_MOB_SPAWNER)
        blocks.registerBlockAndSimpleItem(LUMLIGHT_CAMPFIRE)
        blocks.registerVase(CASTLETON_VASE)
        blocks.registerVase(CASTLETON_TREASURE_VASE)
        blocks.registerColumn(LUMLIGHT_BOOKSHELF, LUMLIGHT_PLANKS)
        //blocks.register(CASTLETON_DUNGEON_CHEST)
        blocks.registerBlock(CANDLE)
        blocks.registerBlockAndSimpleItem(RUNED_CASTLETON_STONE)
        blocks.register(WILD_BERROOK)

        blocks.registerSimpleBlock(RAINBOW_SOIL)
        blocks.registerBlock(RAINBOW_GRASS_BLOCK)
        blocks.registerSimpleBlock(RAINBOW_ROCK)
        blocks.registerSimpleBlock(RAINBOW_BRICKS)
        blocks.registerStairsBlock(RAINBOW_BRICK_STAIRS, RAINBOW_BRICKS)
        blocks.registerSlabBlock(RAINBOW_BRICK_SLAB, RAINBOW_BRICKS)
        blocks.registerWallBlock(RAINBOW_BRICK_WALL)
        blocks.registerFenceBlock(RAINBOW_BRICK_FENCE)
        blocks.registerSimpleBlock(RAINBOWLAND_PORTAL_FRAME)
        blocks.registerBlockAndSimpleItem(RAINBOWLAND_PORTAL)
        blocks.registerBlockAndSimpleItem(COTTONMARSH_SAPLING)
        blocks.registerSimpleBlock(COTTONMARSH_LEAVES)
        blocks.registerBlock(COTTONMARSH_LOG)
        blocks.registerBlock(COTTONMARSH_WOOD)
        blocks.registerBlock(STRIPPED_COTTONMARSH_LOG)
        blocks.registerBlock(STRIPPED_COTTONMARSH_WOOD)
        blocks.registerSimpleBlock(RAINBOW_FACTORY_BRICKS)
        blocks.registerStairsBlock(RAINBOW_FACTORY_BRICK_STAIRS, RAINBOW_FACTORY_BRICKS)
        blocks.registerSlabBlock(RAINBOW_FACTORY_BRICK_SLAB, RAINBOW_FACTORY_BRICKS)
        blocks.registerWallBlock(RAINBOW_FACTORY_BRICK_WALL)
        blocks.registerBlock(RAINBOW_FACTORY_BRICK_FENCE)
        blocks.registerBlock(RAINBOW_FACTORY_FURNACE)
        //blocks.registerBlock(RAINBOW_FACTORY_TABLE)
        blocks.registerSimpleBlock(RAINBOW_GLASS)
        blocks.registerBlockAndSimpleItem(RAINBOW_GLASS_PANE) // todo
        blocks.registerSimpleBlock(RAINBOWSTONE_ORE)
        blocks.registerSimpleBlock(RAINBOWSTONE_BLOCK)
        blocks.registerFarmlandBlock(RAINBOW_FARMLAND)

        blocks.registerBlockAndSimpleItem(FLAME_ROSE)
        blocks.registerBlockAndSimpleItem(GOLDEN_TULIP)
        blocks.registerBlockAndSimpleItem(AUBRUM_PORTAL)
        blocks.registerSimpleBlock(AURISOIL)
        blocks.registerBlock(AURIGRASS_BLOCK)
        blocks.registerSimpleBlock(AURILOAM)
        blocks.registerSimpleBlock(AUBRUM_ROCK)
        blocks.registerBlock(DRUM)
        blocks.registerBlock(AURI_LOG)
        blocks.registerBlock(AURI_WOOD)
        blocks.registerBlock(STRIPPED_AURI_LOG)
        blocks.registerBlock(STRIPPED_AURI_WOOD)
        blocks.registerSimpleBlock(AURI_PLANKS)
        blocks.registerStairsBlock(AURI_STAIRS, AURI_PLANKS)
        blocks.registerSlabBlock(AURI_SLAB, AURI_PLANKS)
        blocks.registerSimpleBlock(SCRAP_METAL)
        blocks.registerSimpleBlock(AURIGOLD_ORE)

        blocks.register(CHILI_PEPPER)
        //blocks.registerBlock(EXTRACTOR)
        //blocks.register(CORN)
        //blocks.registerBlock(TABLE)

        COMPRESSED_COBBLESTONE.registerBlocks(blocks)
        blocks.registerBlock(CHEEKY_TELEPORTER_TOP)
        blocks.registerBlock(CHEEKY_TELEPORTER)
        blocks.registerSimpleBlock(STONE_TEXTURE)
        blocks.registerStairsBlock(STONE_TEXTURE_STAIRS, STONE_TEXTURE)
        blocks.registerSlabBlock(STONE_TEXTURE_SLAB, STONE_TEXTURE)

        blockSetup()
    }

    /**
     * Sets the `RenderType` for each block that does not use the default.
     */
    fun setRenderTypes() {
        RenderTypeLookup.setRenderLayer(FLAME_ROSE, RenderType.getCutout())
        RenderTypeLookup.setRenderLayer(GOLDEN_TULIP, RenderType.getCutout())
        RenderTypeLookup.setRenderLayer(PURPLE_LUMSHROOM, RenderType.getCutout())
        RenderTypeLookup.setRenderLayer(BLUE_LUMSHROOM, RenderType.getCutout())
        RenderTypeLookup.setRenderLayer(CHILI_PEPPER, RenderType.getCutout())
        //RenderTypeLookup.setRenderLayer(CORN, RenderType.getCutout())
        RenderTypeLookup.setRenderLayer(COTTONMARSH_LEAVES, RenderType.getCutout())
        RenderTypeLookup.setRenderLayer(RAINBOW_GRASS_BLOCK, RenderType.getCutout())
        RenderTypeLookup.setRenderLayer(RAINBOW_GLASS, RenderType.getTranslucent())
        RenderTypeLookup.setRenderLayer(RAINBOW_GLASS_PANE, RenderType.getTranslucent())
        RenderTypeLookup.setRenderLayer(CASTLETON_TORCH, RenderType.getCutout())
        RenderTypeLookup.setRenderLayer(CASTLETON_WALL_TORCH, RenderType.getCutout())
        RenderTypeLookup.setRenderLayer(CROWN, RenderType.getCutout())
        RenderTypeLookup.setRenderLayer(LUMLIGHT_SAPLING, RenderType.getCutout())
        RenderTypeLookup.setRenderLayer(LUMLIGHT_SAPLING, RenderType.getCutout())
        RenderTypeLookup.setRenderLayer(RED_WINE_BOTTLE, RenderType.getTranslucent())
        RenderTypeLookup.setRenderLayer(WHITE_WINE_BOTTLE, RenderType.getTranslucent())
        RenderTypeLookup.setRenderLayer(WINE_BOTTLE, RenderType.getTranslucent())
        RenderTypeLookup.setRenderLayer(CASTLETON_LANTERN, RenderType.getCutout())
        RenderTypeLookup.setRenderLayer(DUNGEON_MOB_SPAWNER, RenderType.getCutout())
        RenderTypeLookup.setRenderLayer(LUMLIGHT_CAMPFIRE, RenderType.getCutout())
        RenderTypeLookup.setRenderLayer(COTTONMARSH_LEAVES, RenderType.getCutout())
        RenderTypeLookup.setRenderLayer(RUNED_CASTLETON_STONE, RenderType.getCutout())
        RenderTypeLookup.setRenderLayer(WILD_BERROOK, RenderType.getCutout())
        RenderTypeLookup.setRenderLayer(GOLDEN_CARROTS, RenderType.getCutout())
    }

    /**
     * Registers custom `IBlockColor` handlers for certain blocks.
     */
    fun setBlockColors(colors: Any) {
        runWhenOn(Dist.CLIENT) {
            colors as BlockColors // smart cast to client only class

            colors.register(RainbowColor, RAINBOW_GRASS_BLOCK)
            colors.register(RainbowColor, RAINBOW_GLASS)
            colors.register(RainbowColor, RAINBOW_GLASS_PANE)
        }
    }

    /**
     * Registers custom `WoodType` enums, allows certain `TileEntityType`
     * instances to work with HCD blocks, and registers axe interactions for
     * wood blocks added by HCD.
     */
    private fun blockSetup() {
        WoodType.register(WoodTypes.LUMLIGHT)

        TileEntityType.SIGN.addValidBlocks(LUMLIGHT_SIGN, LUMLIGHT_WALL_SIGN)
        TileEntityType.CAMPFIRE.addValidBlocks(LUMLIGHT_CAMPFIRE)

        registerAxeInteraction(LUMLIGHT_LOG, STRIPPED_LUMLIGHT_LOG)
        //registerAxeInteraction(COTTONMARSH_LOG, STRIPPED_COTTONMARSH_LOG)

        registerHoeInteraction(RAINBOW_GRASS_BLOCK, RAINBOW_FARMLAND)
        registerHoeInteraction(Blocks.PODZOL, ASHY_FARMLAND)
        registerHoeInteraction(Blocks.SAND, SANDY_FARMLAND)
    }

    /**
     * Adds the [blocks] as valid blocks for this `TileEntityType`.
     *
     * @param blocks The valid blocks to be added
     */
    private fun TileEntityType<*>.addValidBlocks(vararg blocks: Block) {
        validBlocks = ImmutableSet.builder<Block>().addAll(validBlocks).addAll(blocks.iterator()).build()
    }

    private fun registerAxeInteraction(target: Block, result: Block) {
        if (AxeItem.BLOCK_STRIPPING_MAP !is HashMap<*, *>) {
            AxeItem.BLOCK_STRIPPING_MAP = HashMap(AxeItem.BLOCK_STRIPPING_MAP)
        }

        AxeItem.BLOCK_STRIPPING_MAP[target] = result
    }

    private fun registerHoeInteraction(original: Block, new: Block) =
        registerHoeInteraction(original, new.defaultState)

    private fun registerHoeInteraction(original: Block, new: BlockState) {
        HoeItem.HOE_LOOKUP[original] = new
    }

    private fun createLogBlock(topColor: MaterialColor, barkColor: MaterialColor, applyProperties: (Properties) -> Unit): RotatedPillarBlock {
        val p = Properties.create(Material.WOOD) { state ->
            if (state.get(RotatedPillarBlock.AXIS) == Direction.Axis.Y) topColor else barkColor
        }
        applyProperties(p)

        return RotatedPillarBlock(p)
    }

    /**
     * `cube_all` plus item block
     */
    fun IForgeRegistry<Block>.registerSimpleBlock(block: Block) {
        ModelType.SIMPLE_BLOCK.add(block)
        this.registerBlock(block)
    }

    /**
     * Register a rotatable pillar block
     */
    fun IForgeRegistry<Block>.registerPillarBlock(block: Block, end: String? = null, side: String? = null) {
        ModelType.PILLAR_BLOCK.add(block, end, side)
        this.registerBlock(block)
    }

    /** Non-rotatable pillar */
    fun IForgeRegistry<Block>.registerColumn(block: Block, end: Block) {
        ModelType.COLUMN_BLOCK.add(block to end)
        this.registerBlock(block)
    }

    // TODO
    fun IForgeRegistry<Block>.registerDoorBlock(block: DoorBlock) {
        registerBlockAndSimpleItem(block)
    }

    /**
     * slab w model + item block
     */
    fun IForgeRegistry<Block>.registerSlabBlock(block: SlabBlock, fullBlock: Block) {
        ModelType.SLAB_BLOCK.add(block, fullBlock)
        this.registerBlock(block)
    }

    /**
     * stairs w model + item block
     */
    fun IForgeRegistry<Block>.registerStairsBlock(stairs: StairsBlock, fullBlock: Block) {
        ModelType.STAIRS_BLOCK.add(stairs, fullBlock)
        this.registerBlock(stairs)
    }

    fun IForgeRegistry<Block>.registerWallBlock(wall: WallBlock) {
        ModelType.WALL_BLOCK.add(wall)
        registerBlock(wall)
    }

    fun IForgeRegistry<Block>.registerFenceBlock(fence: FenceBlock) {
        ModelType.FENCE_BLOCK.add(fence)
        registerBlock(fence)
    }

    fun IForgeRegistry<Block>.registerButtonBlock(button: AbstractButtonBlock) {
        ModelType.BUTTON_BLOCK.add(button)
        this.registerBlock(button)
    }

    fun IForgeRegistry<Block>.registerFarmlandBlock(farmland: BonusFarmlandBlock) {
        ModelType.FARMLAND_BLOCK.add(farmland)
        this.registerBlock(farmland)
    }

    fun IForgeRegistry<Block>.registerVase(farmland: Block) {
        ModelType.VASE_BLOCK.add(farmland)
        this.registerBlock(farmland)
    }

    /** SIMPLE (2D) LIKE A HOPPER/LANTERN
     *
     * block (no block model) + 2d item model
     */
    fun IForgeRegistry<Block>.registerBlockAndSimpleItem(block: Block) {
        this.register(block)
        BLOCKS_W_ITEMS.add(block)
    }

    /**
     * block (no block model) + 3d item model
     */
    fun IForgeRegistry<Block>.registerBlock(block: Block) {
        this.register(block)
        BLOCKS_W_ITEMS.add(block)
    }

    fun props(material: Material, materialColor: MaterialColor = material.color): HProperties {
        return HProperties.create(material, materialColor)
    }

    val BLOCKS_W_ITEMS = ArrayList<Block>()
    val BLOCKS_W_SIMPLE_ITEMS = ArrayList<Block>()
}