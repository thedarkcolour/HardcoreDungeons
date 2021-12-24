package thedarkcolour.hardcoredungeons.block.base

import it.unimi.dsi.fastutil.objects.Object2FloatMap
import it.unimi.dsi.fastutil.objects.Object2FloatOpenHashMap
import net.minecraft.block.*
import net.minecraft.block.material.Material
import net.minecraft.block.material.MaterialColor
import net.minecraft.block.trees.Tree
import net.minecraft.potion.Effects
import net.minecraft.util.RegistryKey
import net.minecraft.util.ResourceLocation
import net.minecraft.util.Util
import net.minecraft.util.math.shapes.VoxelShape
import net.minecraft.util.math.shapes.VoxelShapes
import net.minecraft.world.World
import net.minecraftforge.common.ToolType
import thedarkcolour.hardcoredungeons.registry.HBlocks
import thedarkcolour.hardcoredungeons.block.base.properties.HProperties
import thedarkcolour.hardcoredungeons.block.combo.PottedPlantCombo
import thedarkcolour.hardcoredungeons.block.decoration.RotatableBlock
import thedarkcolour.hardcoredungeons.block.misc.BonusFarmlandBlock
import thedarkcolour.hardcoredungeons.block.plant.FlowerBlock
import thedarkcolour.hardcoredungeons.block.plant.HSaplingBlock
import thedarkcolour.hardcoredungeons.block.plant.PlantProperties
import thedarkcolour.hardcoredungeons.block.portal.HPortalBlock
import thedarkcolour.hardcoredungeons.data.modelgen.block.BlockModelType
import thedarkcolour.hardcoredungeons.data.modelgen.item.ItemModelType
import thedarkcolour.hardcoredungeons.registry.HBlocksOld
import thedarkcolour.kotlinforforge.forge.ObjectHolderDelegate

/**
 * Used in combos and anything else that's repeated
 *
 * @author TheDarkColour
 */
object BlockMaker {

    // Preset shapes
    val VASE_SHAPE: VoxelShape = VoxelShapes.or(cube(4.0, 0.0, 4.0, 12.0, 1.0, 12.0), cube(3.0, 1.0, 3.0, 13.0, 9.0, 13.0), cube(4.0, 9.0, 4.0, 12.0, 10.0, 12.0), cube(6.0, 10.0, 6.0, 10.0, 11.0, 10.0), cube(5.0, 11.0, 5.0, 11.0, 13.0, 11.0))
    val WINE_BOTTLE_SHAPE: VoxelShape = VoxelShapes.or(cube(6.5, 0.0, 6.5, 9.5, 7.0, 9.5), cube(7.5, 7.0, 7.5, 8.5, 11.0, 8.5))
    val DRUM_SHAPE: VoxelShape = cube(3.0, 0.0, 3.0, 13.0, 16.0, 13.0)
    val LOST_SKULL_SHAPE: VoxelShape = cube(4.0, 0.0, 4.0, 12.0, 8.0, 12.0)
    val PLATE_SHAPE: VoxelShape = cube(3.0, 0.0, 3.0, 13.0, 2.0, 13.0)
    val CANDLE_SHAPE: VoxelShape = VoxelShapes.or(cube(6.0, 0.0, 6.0, 10.0, 1.0, 10.0), cube(7.0, 0.75, 7.0, 9.0, 2.75, 9.0), cube(6.0, 2.5, 6.0, 10.0, 4.5, 10.0), cube(7.0, 3.5, 7.0, 9.0, 15.5, 9.0), cube(7.5, 14.75, 7.5, 8.5, 15.75, 8.5))
    val CROWN_SHAPE: VoxelShape = cube(4.0, 0.0, 4.0, 12.0, 4.0, 12.0)
    val CHALICE_SHAPE: VoxelShape = VoxelShapes.or(cube(6.0, 0.0, 6.0, 10.0, 1.0, 10.0), cube(7.0, 1.0, 7.0, 9.0, 3.0, 9.0), cube(6.0, 3.0, 6.0, 10.0, 4.0, 10.0), cube(5.0, 4.0, 5.0, 11.0, 8.0, 11.0))
    val FARMLAND_SHAPE: VoxelShape = cube(0.0, 0.0, 0.0, 16.0, 15.0, 16.0)

    inline fun props(material: Material, color: MaterialColor, apply: (HProperties) -> Unit): HProperties {
        return HProperties.of(material, color).also(apply)
    }

    private fun cube(x1: Double, y1: Double, z1: Double, x2: Double, y2: Double, z2: Double): VoxelShape {
        return Block.box(x1, y1, z1, x2, y2, z2)
    }

    fun <T : M, M : Block> registerModelled(name: String, type: BlockModelType<M>, appearance: (() -> Block)? = null, supplier: () -> T): ObjectHolderDelegate<T> {
        val obj = HBlocks.register(name, supplier)
        HBlocks.onceRegistered { type.add(obj, appearance ?: obj) }
        return obj
    }

    fun registerCubeAll(name: String, props: HProperties, appearance: (() -> Block)? = null): ObjectHolderDelegate<HBlock> {
        return registerModelled(name, BlockModelType.CUBE_ALL, appearance) { HBlock(props) }
    }

    fun registerSlab(name: String, full: () -> Block, props: HProperties): ObjectHolderDelegate<SlabBlock> {
        return registerModelled(name, BlockModelType.SLAB, full) { SlabBlock(props.build()) }
    }

    fun <T : Block> registerCross(name: String, supplier: () -> T): ObjectHolderDelegate<T> {
        return registerModelled(name, BlockModelType.CROSS, null, supplier)
    }

    fun registerStairs(name: String, full: () -> Block, props: HProperties): ObjectHolderDelegate<StairsBlock> {
        return registerModelled(name, BlockModelType.STAIRS, full) { StairsBlock(full()::defaultBlockState, props.build()) }
    }

    fun registerFence(name: String, props: HProperties): ObjectHolderDelegate<FenceBlock> {
        return registerModelled(name, BlockModelType.FENCE) { FenceBlock(props.build()) }
    }

    fun registerFenceGate(name: String, props: HProperties): ObjectHolderDelegate<FenceGateBlock> {
        return HBlocks.register(name) { FenceGateBlock(props.build()) }
    }

    fun registerPressurePlate(name: String, sensitivity: PressurePlateBlock.Sensitivity, props: HProperties): ObjectHolderDelegate<PressurePlateBlock> {
        return registerModelled(name, BlockModelType.PRESSURE_PLATE) { PressurePlateBlock(sensitivity, props.build().noCollission()) }
    }

    fun registerWoodButton(name: String, props: HProperties): ObjectHolderDelegate<WoodButtonBlock> {
        return registerModelled(name, BlockModelType.BUTTON) { WoodButtonBlock(props.build().noCollission()) }
    }

    fun registerTrapdoor(name: String, props: HProperties): ObjectHolderDelegate<TrapDoorBlock> {
        return registerModelled(name, BlockModelType.TRAPDOOR) { TrapDoorBlock(props.build().noOcclusion()) }
    }

    fun registerDoor(name: String, props: HProperties): ObjectHolderDelegate<DoorBlock> {
        return registerModelled(name, BlockModelType.DOOR) { DoorBlock(props.build().noOcclusion()) }
    }

    fun registerStandingSign(name: String, type: WoodType, props: HProperties): ObjectHolderDelegate<StandingSignBlock> {
        return HBlocks.register(name) { StandingSignBlock(props.build().noCollission(), type) }
    }

    fun registerWallSign(name: String, type: WoodType, props: HProperties): ObjectHolderDelegate<WallSignBlock> {
        return HBlocks.register(name) { WallSignBlock(props.build().noCollission(), type) }
    }

    fun registerWineBottle(name: String): ObjectHolderDelegate<HBlock> {
        return HBlocks.register(name) { HBlock(HProperties.of(Material.GLASS).noOcclusion().shape(WINE_BOTTLE_SHAPE)) }
    }

    fun registerRotatedPillar(name: String, properties: HProperties): ObjectHolderDelegate<RotatedPillarBlock> {
        return registerModelled(name, BlockModelType.ROTATED_PILLAR) { RotatedPillarBlock(properties.build()) }
    }

    // todo
    fun registerRotatableBlock(name: String, props: HProperties): ObjectHolderDelegate<RotatableBlock> {
        return HBlocks.register(name) { RotatableBlock(props) }
    }

    fun leavesBlock(name: String): ObjectHolderDelegate<LeavesBlock> {
        return registerModelled(name, BlockModelType.CUBE_ALL) { LeavesBlock(HProperties.of(Material.LEAVES, MaterialColor.COLOR_LIGHT_BLUE).strength(0.2F).randomTicks().sound(SoundType.GRASS).noOcclusion().build()) }
    }

    fun saplingCombo(name: String, tree: Tree): PottedPlantCombo {
        return PottedPlantCombo(name) { HSaplingBlock(tree, HProperties.of(Material.PLANT).sound(SoundType.GRASS).noCollission()) }
    }

    fun registerWall(name: String, block: () -> Block): ObjectHolderDelegate<WallBlock> {
        return registerModelled(name, BlockModelType.WALL, block) { WallBlock(HProperties.copy(block()).build()) }
    }

    fun registerOre(name: String, level: Int, hardness: Float, resistance: Float, color: MaterialColor): ObjectHolderDelegate<HBlock> {
        return registerCubeAll(name, HProperties.of(Material.STONE, color).strength(hardness, resistance).harvestLevel(level).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE))
    }

    fun gumdrop(name: String): PottedPlantCombo {
        return PottedPlantCombo(name) {
            FlowerBlock(PlantProperties.of(Material.PLANT).stewEffect(Effects.REGENERATION, 7).sound(SoundType.SLIME_BLOCK).noCollission().instabreak().lightLevel(3))
        }
    }

    fun registerCrystal(name: String, color: MaterialColor): ObjectHolderDelegate<HBlock> {
        return registerCross(name) { HBlock(HProperties.of(Material.STONE, color).harvestLevel(2).strength(2.3f, 40.0f).lightLevel(4).shape(Block.box(6.0, 0.0, 6.0, 10.0, 4.0, 10.0))) }
    }

    // todo add model type
    fun registerPortal(id: ResourceLocation, key: () -> RegistryKey<World>, frame: () -> BlockState): ObjectHolderDelegate<HPortalBlock> {
        return registerModelled(id.path + "_portal", BlockModelType.PORTAL) {
            HPortalBlock(key, frame, HProperties.of(Material.PORTAL).noCollission().strength(-1.0f).sound(SoundType.GLASS).noDrops())
        }
    }

    fun getComboName(name: String): String {
        return when {
            name.endsWith("_planks") -> name.removeSuffix("_planks")
            name.endsWith("_bricks") -> name.removeSuffix("s")
            name.endsWith("_block") -> name.removeSuffix("_block")
            else -> name
        }
    }

    /////////////////////////////////////////////
    // Special blocks that have their own data //
    /////////////////////////////////////////////

    // Cube all block + 3d item
    fun cubeAllWithItem(name: String, props: HProperties, type: ItemModelType = ItemModelType.BLOCK_ITEM, appearance: (() -> Block)? = null): ObjectHolderDelegate<HBlock> {
        return withItem(name, type, registerCubeAll(name, props, appearance))
    }

    fun <T : Block> cubeAllWithItem(name: String, type: ItemModelType = ItemModelType.BLOCK_ITEM, supplier: () -> T): ObjectHolderDelegate<T> {
        return withItem(name, type, registerModelled(name, BlockModelType.CUBE_ALL, null, supplier))
    }

    fun slabWithItem(name: String, full: () -> Block, props: HProperties, type: ItemModelType = ItemModelType.BLOCK_ITEM): ObjectHolderDelegate<SlabBlock> {
        return withItem(name, type, registerSlab(name, full, props))
    }

    fun stairsWithItem(name: String, full: () -> Block, props: HProperties, type: ItemModelType = ItemModelType.BLOCK_ITEM): ObjectHolderDelegate<StairsBlock> {
        return withItem(name, type, registerStairs(name, full, props))
    }

    fun wallWithItem(name: String, block: () -> Block, type: ItemModelType = ItemModelType.WALL_FENCE_ITEM): ObjectHolderDelegate<WallBlock> {
        return withItem(name, type, registerWall(name, block))
    }

    fun rotatedPillarWithItem(name: String, props: HProperties): ObjectHolderDelegate<RotatedPillarBlock> {
        return withItem(name, ItemModelType.BLOCK_ITEM, registerRotatedPillar(name, props))
    }

    fun rotatableBlockWithItem(name: String, props: HProperties): ObjectHolderDelegate<RotatableBlock> {
        return withItem(name, ItemModelType.BLOCK_ITEM, registerRotatableBlock(name, props))
    }

    // Purely decorative
    fun unbreakableWithItem(name: String, type: BlockModelType<Block>, block: () -> Block): ObjectHolderDelegate<HBlock> {
        return withItem(name, ItemModelType.BLOCK_ITEM, registerModelled(name, type, appearance = block) { HBlock(HProperties.copy(block())) })
    }

    fun wineBottleWithItem(name: String): ObjectHolderDelegate<HBlock> {
        return withItem(name, ItemModelType.SIMPLE_ITEM, registerWineBottle(name))
    }

    fun <T : Block> crossWithItem(name: String, plantSupplier: () -> T): ObjectHolderDelegate<T> {
        return withItem(name, ItemModelType.SIMPLE_ITEM, registerCross(name, plantSupplier))
    }

    fun oreWithItem(name: String, level: Int, hardness: Float, resistance: Float, color: MaterialColor): ObjectHolderDelegate<HBlock> {
        return withItem(name, ItemModelType.BLOCK_ITEM, registerOre(name, level, hardness, resistance, color))
    }

    // Handles hoe interaction
    fun farmlandWithItem(
        name: String,
        from: () -> Block,
        soil: () -> BlockState = Blocks.DIRT::defaultBlockState,
        props: HProperties,
        boostMap: (Object2FloatMap<() -> Block>) -> Unit = { }
    ): ObjectHolderDelegate<BonusFarmlandBlock> {
        val farmland = withItem(name, ItemModelType.BLOCK_ITEM, registerModelled(name, BlockModelType.FARMLAND) { BonusFarmlandBlock(soil, Util.make(
            Object2FloatOpenHashMap(), boostMap), props) })
        HBlocks.onceRegistered { HBlocksOld.registerHoeInteraction(from(), farmland().defaultBlockState()) }
        return farmland
    }

    fun lanternWithItem(name: String, props: HProperties): ObjectHolderDelegate<LanternBlock> {
        return withItem(name, ItemModelType.SIMPLE_ITEM, registerModelled(name, BlockModelType.LANTERN) { LanternBlock(props.build()) })
    }

    fun vaseWithItem(name: String, color: MaterialColor): ObjectHolderDelegate<HBlock> {
        return withItem(name, ItemModelType.BLOCK_ITEM, registerModelled(name, BlockModelType.VASE_BLOCK) { HBlock(HProperties.of(Material.DECORATION, color).strength(1.3f).shape(VASE_SHAPE)) })
    }

    // Only registers the additional item
    fun <T : Block> withItem(name: String, type: ItemModelType = ItemModelType.BLOCK_ITEM, supplier: ObjectHolderDelegate<T>): ObjectHolderDelegate<T> {
        ItemMaker.blockItem(name, type, supplier)
        return supplier
    }

    // Registers the block as well
    fun <T : Block> blockWithItem(name: String, type: ItemModelType = ItemModelType.BLOCK_ITEM, blockSupplier: () -> T): ObjectHolderDelegate<T> {
        val block = HBlocks.register(name, blockSupplier)
        ItemMaker.blockItem(name, type, block)
        return block
    }
}