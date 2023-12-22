package thedarkcolour.hardcoredungeons.block.base

import it.unimi.dsi.fastutil.objects.Object2FloatMap
import it.unimi.dsi.fastutil.objects.Object2FloatOpenHashMap
import net.minecraft.Util
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.sounds.SoundEvent
import net.minecraft.sounds.SoundEvents
import net.minecraft.tags.TagKey
import net.minecraft.world.effect.MobEffects
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.*
import net.minecraft.world.level.block.grower.AbstractTreeGrower
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.properties.WoodType
import net.minecraft.world.level.material.MapColor
import net.minecraft.world.phys.shapes.Shapes
import net.minecraft.world.phys.shapes.VoxelShape
import net.minecraftforge.data.loading.DatagenModLoader
import thedarkcolour.hardcoredungeons.block.base.properties.HProperties
import thedarkcolour.hardcoredungeons.block.base.properties.PropertiesView
import thedarkcolour.hardcoredungeons.block.combo.PortalCombo
import thedarkcolour.hardcoredungeons.block.combo.PottedPlantCombo
import thedarkcolour.hardcoredungeons.block.decoration.RotatableBlock
import thedarkcolour.hardcoredungeons.block.misc.BonusFarmlandBlock
import thedarkcolour.hardcoredungeons.block.plant.FlowerBlock
import thedarkcolour.hardcoredungeons.block.plant.HSaplingBlock
import thedarkcolour.hardcoredungeons.block.plant.PlantProperties
import thedarkcolour.hardcoredungeons.block.portal.HPortalBlock
import thedarkcolour.hardcoredungeons.block.portal.HPortalFrameBlock
import thedarkcolour.hardcoredungeons.data.modelgen.block.BlockModelType
import thedarkcolour.hardcoredungeons.data.modelgen.item.ItemModelType
import thedarkcolour.hardcoredungeons.registry.block.HBlockUtil
import thedarkcolour.hardcoredungeons.registry.block.HBlocks
import thedarkcolour.hardcoredungeons.registry.items.ItemMaker
import thedarkcolour.kotlinforforge.forge.ObjectHolderDelegate

/**
 * Used in combos and anything else that's repeated
 *
 * Functions prefixed with `register` return a block registry object and add that block to a model type for data generation.
 * Functions not prefixed with `register` do everything a register function does as well as registering appropriate item forms.
 * Shapes are stored as constants to avoid unnecessary duplicate VoxelShape objects
 *
 * @author thedarkcolour
 */
@Suppress("MemberVisibilityCanBePrivate")
object BlockMaker {

    // Preset shapes
    val VASE_SHAPE: VoxelShape = Shapes.or(cube(4.0, 0.0, 4.0, 12.0, 1.0, 12.0), cube(3.0, 1.0, 3.0, 13.0, 9.0, 13.0), cube(4.0, 9.0, 4.0, 12.0, 10.0, 12.0), cube(6.0, 10.0, 6.0, 10.0, 11.0, 10.0), cube(5.0, 11.0, 5.0, 11.0, 13.0, 11.0))
    val WINE_BOTTLE_SHAPE: VoxelShape = Shapes.or(cube(6.5, 0.0, 6.5, 9.5, 7.0, 9.5), cube(7.5, 7.0, 7.5, 8.5, 11.0, 8.5))
    val DRUM_SHAPE: VoxelShape = cube(3.0, 0.0, 3.0, 13.0, 16.0, 13.0)
    val LOST_SKULL_SHAPE: VoxelShape = cube(4.0, 0.0, 4.0, 12.0, 8.0, 12.0)
    val PLATE_SHAPE: VoxelShape = cube(3.0, 0.0, 3.0, 13.0, 2.0, 13.0)
    val CANDLE_SHAPE: VoxelShape = Shapes.or(cube(6.0, 0.0, 6.0, 10.0, 1.0, 10.0), cube(7.0, 0.75, 7.0, 9.0, 2.75, 9.0), cube(6.0, 2.5, 6.0, 10.0, 4.5, 10.0), cube(7.0, 3.5, 7.0, 9.0, 15.5, 9.0), cube(7.5, 14.75, 7.5, 8.5, 15.75, 8.5))
    val CROWN_SHAPE: VoxelShape = cube(4.0, 0.0, 4.0, 12.0, 4.0, 12.0)
    val CHALICE_SHAPE: VoxelShape = Shapes.or(cube(6.0, 0.0, 6.0, 10.0, 1.0, 10.0), cube(7.0, 1.0, 7.0, 9.0, 3.0, 9.0), cube(6.0, 3.0, 6.0, 10.0, 4.0, 10.0), cube(5.0, 4.0, 5.0, 11.0, 8.0, 11.0))
    val FARMLAND_SHAPE: VoxelShape = cube(0.0, 0.0, 0.0, 16.0, 15.0, 16.0)
    val CRYSTAL_SHAPE: VoxelShape = cube(3.0, 0.0, 3.0, 13.0, 7.0, 13.0)

    inline fun props(color: MapColor, apply: (HProperties) -> Unit): HProperties {
        return HProperties.of(color).also(apply)
    }

    private fun cube(x1: Double, y1: Double, z1: Double, x2: Double, y2: Double, z2: Double): VoxelShape {
        return Block.box(x1, y1, z1, x2, y2, z2)
    }

    // Register block only and adds to model type
    fun <T : M, M : Block> registerModelled(name: String, type: BlockModelType<M>, appearance: (() -> Block)? = null, supplier: () -> T): ObjectHolderDelegate<T> {
        val obj = HBlocks.register(name, supplier)
        if (DatagenModLoader.isRunningDataGen()) // don't bother filling lists outside data gen
            type.add(obj, appearance ?: obj)
        return obj
    }

    fun registerCubeAll(name: String, props: PropertiesView, appearance: (() -> Block)? = null): ObjectHolderDelegate<HBlock> {
        return registerModelled(name, BlockModelType.CUBE_ALL, appearance) { HBlock(props) }
    }

    fun registerSlab(name: String, full: () -> Block, props: PropertiesView): ObjectHolderDelegate<SlabBlock> {
        return registerModelled(name, BlockModelType.SLAB, full) { SlabBlock(props.build()) }
    }

    fun <T : Block> registerCross(name: String, supplier: () -> T): ObjectHolderDelegate<T> {
        return registerModelled(name, BlockModelType.CROSS, null, supplier)
    }

    fun registerStairs(name: String, full: () -> Block, props: PropertiesView): ObjectHolderDelegate<StairBlock> {
        return registerModelled(name, BlockModelType.STAIRS, full) { StairBlock(full()::defaultBlockState, props.build()) }
    }

    fun registerFence(name: String, props: PropertiesView, appearance: (() -> Block)? = null): ObjectHolderDelegate<FenceBlock> {
        return registerModelled(name, BlockModelType.FENCE, appearance) { FenceBlock(props.build()) }
    }

    /**
     * @param name        Registry name
     * @param props       Block properties
     * @param appearance  Block whose texture should be used for this fence gate's model
     * @param openSound   Opening sound event
     * @param closeSound  Closing sound event
     */
    fun registerFenceGate(name: String, props: PropertiesView, appearance: (() -> Block)? = null, openSound: () -> SoundEvent = { SoundEvents.FENCE_GATE_OPEN }, closeSound: () -> SoundEvent = { SoundEvents.FENCE_GATE_CLOSE }): ObjectHolderDelegate<FenceGateBlock> {
        return registerModelled(name, BlockModelType.FENCE_GATE, appearance) { FenceGateBlock(props.build(), openSound(), closeSound()) }
    }

    fun registerPressurePlate(name: String, sensitivity: PressurePlateBlock.Sensitivity, props: PropertiesView, appearance: (() -> Block)? = null): ObjectHolderDelegate<PressurePlateBlock> {
        return registerModelled(name, BlockModelType.PRESSURE_PLATE, appearance) { PressurePlateBlock(sensitivity, props.build().noCollission(), props.getBlockSetType()!!) }
    }

    fun registerWoodButton(name: String, props: PropertiesView, appearance: (() -> Block)? = null): ObjectHolderDelegate<ButtonBlock> {
        return registerModelled(name, BlockModelType.BUTTON, appearance) { ButtonBlock(props.build().noCollission(), props.getBlockSetType()!!, 30, true) }
    }

    fun registerTrapdoor(name: String, props: PropertiesView): ObjectHolderDelegate<TrapDoorBlock> {
        return registerModelled(name, BlockModelType.TRAPDOOR) { TrapDoorBlock(props.build().noOcclusion(), props.getBlockSetType()!!) }
    }

    fun registerDoor(name: String, props: PropertiesView): ObjectHolderDelegate<DoorBlock> {
        return registerModelled(name, BlockModelType.DOOR) { DoorBlock(props.build().noOcclusion(), props.getBlockSetType()!!) }
    }

    fun registerStandingSign(name: String, type: WoodType, props: PropertiesView): ObjectHolderDelegate<StandingSignBlock> {
        return HBlocks.register(name) { StandingSignBlock(props.build().noCollission(), type) }
    }

    fun registerWallSign(name: String, type: WoodType, props: PropertiesView): ObjectHolderDelegate<WallSignBlock> {
        return HBlocks.register(name) { WallSignBlock(props.build().noCollission(), type) }
    }

    fun registerWineBottle(name: String): ObjectHolderDelegate<HBlock> {
        return HBlocks.register(name) { HBlock(HProperties.of().noOcclusion().shape(WINE_BOTTLE_SHAPE)) }
    }

    fun registerRotatedPillar(name: String, properties: PropertiesView): ObjectHolderDelegate<RotatedPillarBlock> {
        return registerModelled(name, BlockModelType.ROTATED_PILLAR) { RotatedPillarBlock(properties.build()) }
    }

    // todo
    fun registerRotatableBlock(name: String, props: PropertiesView): ObjectHolderDelegate<RotatableBlock> {
        return HBlocks.register(name) { RotatableBlock(props) }
    }

    fun leavesBlock(name: String): ObjectHolderDelegate<LeavesBlock> {
        return registerModelled(name, BlockModelType.CUBE_ALL) { LeavesBlock(HProperties.of(MapColor.COLOR_LIGHT_BLUE).strength(0.2F).randomTicks().sound(SoundType.GRASS).noOcclusion().build()) }
    }

    fun saplingCombo(name: String, tree: AbstractTreeGrower): PottedPlantCombo {
        return PottedPlantCombo(name) { HSaplingBlock(tree, HProperties.of().sound(SoundType.GRASS).noCollision()) }
    }

    fun gumdropCombo(name: String): PottedPlantCombo {
        return PottedPlantCombo(name) {
            FlowerBlock(PlantProperties.of().stewEffect(MobEffects.REGENERATION, 7).sound(SoundType.SLIME_BLOCK).noCollision().instabreak().lightLevel(3))
        }
    }

    fun registerWall(name: String, block: () -> Block): ObjectHolderDelegate<WallBlock> {
        return registerModelled(name, BlockModelType.WALL, block) { WallBlock(HProperties.copy(block()).build()) }
    }

    fun registerOre(name: String, miningLevel: TagKey<Block>, hardness: Float, resistance: Float, color: MapColor): ObjectHolderDelegate<HBlock> {
        // todo some mechanism for mining tools

        return registerCubeAll(name, HProperties.of(color).strength(hardness, resistance).requiresCorrectToolForDrops())
    }

    fun registerCrystal(name: String, color: MapColor): ObjectHolderDelegate<HBlock> {
        // todo some mechanism for mining tools
        return registerCross(name) { HBlock(HProperties.of(color).strength(2.3f, 40.0f).lightLevel(4).sound(SoundType.AMETHYST).requiresCorrectToolForDrops().shape(CRYSTAL_SHAPE)) }
    }

    // todo add model type
    fun registerPortal(id: ResourceLocation, key: () -> ResourceKey<Level>, combo: PortalCombo): ObjectHolderDelegate<HPortalBlock> {
        return registerModelled(id.path + "_portal", BlockModelType.PORTAL) {
            HPortalBlock(key, combo, HProperties.of().noCollision().strength(-1.0f).sound(SoundType.GLASS).noDrops())
        }
    }

    fun portalFrameWithItem(id: ResourceLocation, props: PropertiesView, combo: PortalCombo): ObjectHolderDelegate<HPortalFrameBlock> {
        val name = id.path + "_portal_frame"
        return withItem(name, ItemModelType.BLOCK_ITEM, registerModelled(name, BlockModelType.CUBE_ALL, null) { HPortalFrameBlock(combo, props) })
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
    fun cubeAllWithItem(name: String, props: PropertiesView, type: ItemModelType = ItemModelType.BLOCK_ITEM, appearance: (() -> Block)? = null): ObjectHolderDelegate<HBlock> {
        return withItem(name, type, registerCubeAll(name, props, appearance))
    }

    fun <T : Block> cubeAllWithItem(name: String, type: ItemModelType = ItemModelType.BLOCK_ITEM, supplier: () -> T): ObjectHolderDelegate<T> {
        return withItem(name, type, registerModelled(name, BlockModelType.CUBE_ALL, null, supplier))
    }

    fun slabWithItem(name: String, full: () -> Block, props: PropertiesView, type: ItemModelType = ItemModelType.BLOCK_ITEM): ObjectHolderDelegate<SlabBlock> {
        return withItem(name, type, registerSlab(name, full, props))
    }

    fun stairsWithItem(name: String, full: () -> Block, props: PropertiesView, type: ItemModelType = ItemModelType.BLOCK_ITEM): ObjectHolderDelegate<StairBlock> {
        return withItem(name, type, registerStairs(name, full, props))
    }

    fun wallWithItem(name: String, block: () -> Block, type: ItemModelType = ItemModelType.WALL_FENCE_ITEM): ObjectHolderDelegate<WallBlock> {
        return withItem(name, type, registerWall(name, block))
    }

    fun rotatedPillarWithItem(name: String, props: PropertiesView): ObjectHolderDelegate<RotatedPillarBlock> {
        return withItem(name, ItemModelType.BLOCK_ITEM, registerRotatedPillar(name, props))
    }

    fun rotatableBlockWithItem(name: String, props: PropertiesView): ObjectHolderDelegate<RotatableBlock> {
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

    fun oreWithItem(name: String, miningLevel: TagKey<Block>, hardness: Float, resistance: Float, color: MapColor): ObjectHolderDelegate<HBlock> {
        return withItem(name, ItemModelType.BLOCK_ITEM, registerOre(name, miningLevel, hardness, resistance, color))
    }

    // Handles hoe interaction
    // Warning: Mutates the properties parameter
    fun farmlandWithItem(
        name: String,
        from: () -> Block,
        soil: () -> BlockState,
        props: HProperties,
        boostMap: (Object2FloatMap<() -> Block>) -> Unit = { }
    ): ObjectHolderDelegate<BonusFarmlandBlock> {
        val farmland = withItem(name, ItemModelType.BLOCK_ITEM, registerModelled(name, BlockModelType.FARMLAND) { BonusFarmlandBlock(soil, Util.make(Object2FloatOpenHashMap(), boostMap), props) })
        HBlocks.onceRegistered {
            HBlockUtil.registerHoeInteraction(from(), farmland().defaultBlockState())
        }
        return farmland
    }

    fun lanternWithItem(name: String, props: PropertiesView): ObjectHolderDelegate<LanternBlock> {
        return withItem(name, ItemModelType.SIMPLE_ITEM, registerModelled(name, BlockModelType.LANTERN) { LanternBlock(props.build()) })
    }

    fun vaseWithItem(name: String, color: MapColor): ObjectHolderDelegate<HBlock> {
        return withItem(name, ItemModelType.BLOCK_ITEM, registerModelled(name, BlockModelType.VASE_BLOCK) { HBlock(HProperties.of(
            color
        ).strength(1.3f).shape(VASE_SHAPE)) })
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