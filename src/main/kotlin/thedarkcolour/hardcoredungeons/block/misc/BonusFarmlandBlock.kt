package thedarkcolour.hardcoredungeons.block.misc

import it.unimi.dsi.fastutil.floats.FloatLists
import it.unimi.dsi.fastutil.objects.Object2FloatMap
import it.unimi.dsi.fastutil.objects.ObjectSet
import it.unimi.dsi.fastutil.objects.ObjectSets
import net.minecraft.block.*
import net.minecraft.entity.Entity
import net.minecraft.item.BlockItemUseContext
import net.minecraft.pathfinding.PathType
import net.minecraft.state.IntegerProperty
import net.minecraft.state.StateContainer
import net.minecraft.state.properties.BlockStateProperties
import net.minecraft.tags.FluidTags
import net.minecraft.util.Direction
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.shapes.ISelectionContext
import net.minecraft.util.math.shapes.VoxelShape
import net.minecraft.world.IBlockReader
import net.minecraft.world.IWorld
import net.minecraft.world.IWorldReader
import net.minecraft.world.World
import net.minecraft.world.server.ServerWorld
import net.minecraftforge.common.*
import net.minecraftforge.event.world.BlockEvent
import net.minecraftforge.eventbus.api.Event
import thedarkcolour.hardcoredungeons.block.HBlock
import thedarkcolour.hardcoredungeons.block.properties.HProperties
import java.util.*
import java.util.function.Supplier

/**
 * @property boostMap crops and their chance to grow 2 stages instead of 1
 */
class BonusFarmlandBlock(
    val getSoilState: () -> BlockState = Blocks.DIRT::getDefaultState,
    val boostMap: Object2FloatMap<Supplier<Block>> = EMPTY, // default to empty map
    properties: HProperties,
) : HBlock(properties) {

    init {
        defaultState = defaultState.with(MOISTURE, 0)
    }

    override fun updatePostPlacement(
        stateIn: BlockState,
        facing: Direction,
        facingState: BlockState,
        worldIn: IWorld,
        currentPos: BlockPos,
        facingPos: BlockPos,
    ): BlockState {
        if (facing == Direction.UP && !stateIn.isValidPosition(worldIn, currentPos)) {
            worldIn.pendingBlockTicks.scheduleTick(currentPos, this, 1)
        }

        return stateIn
    }

    override fun isValidPosition(state: BlockState, worldIn: IWorldReader, pos: BlockPos): Boolean {
        val state1 = worldIn.getBlockState(pos.up())
        return !state1.material.isSolid || state.block.isIn(Tags.Blocks.FENCE_GATES) || state.block is MovingPistonBlock
    }

    override fun getStateForPlacement(context: BlockItemUseContext): BlockState? {
        return if (!defaultState.isValidPosition(context.world, context.pos)) {
            getSoilState()
        } else super.getStateForPlacement(context)
    }

    override fun isTransparent(state: BlockState) = true

    override fun getShape(state: BlockState?, worldIn: IBlockReader?, pos: BlockPos?, ctx: ISelectionContext?) = SHAPE

    override fun tick(state: BlockState, worldIn: ServerWorld, pos: BlockPos, rand: Random) {
        if (!state.isValidPosition(worldIn, pos)) {
            turnToSoil(state, worldIn, pos)
        } else {
            val i = state.get(FarmlandBlock.MOISTURE)
            if (!hasWater(worldIn, pos) && !worldIn.isRainingAt(pos.up())) {
                if (i > 0) {
                    worldIn.setBlockState(pos, state.with(FarmlandBlock.MOISTURE, Integer.valueOf(i - 1)), 2)
                } else if (!hasCrops(worldIn, pos)) {
                    turnToSoil(state, worldIn, pos)
                }
            } else if (i < 7) {
                worldIn.setBlockState(pos, state.with(FarmlandBlock.MOISTURE, Integer.valueOf(7)), 2)
            }
        }
    }

    override fun onFallenUpon(worldIn: World, pos: BlockPos, entityIn: Entity, fallDistance: Float) {
        if (!worldIn.isRemote && ForgeHooks.onFarmlandTrample(worldIn, pos, getSoilState(), fallDistance, entityIn)) {
            turnToSoil(worldIn.getBlockState(pos), worldIn, pos)
        }

        super.onFallenUpon(worldIn, pos, entityIn, fallDistance)
    }

    override fun allowsMovement(state: BlockState, worldIn: IBlockReader, pos: BlockPos, type: PathType) = false

    override fun fillStateContainer(builder: StateContainer.Builder<Block, BlockState>) {
        builder.add(MOISTURE)
    }

    override fun isFertile(state: BlockState, world: IBlockReader?, pos: BlockPos?): Boolean {
        return state.get(MOISTURE) > 0
    }

    override fun canSustainPlant(
        state: BlockState,
        world: IBlockReader,
        pos: BlockPos,
        facing: Direction,
        plantable: IPlantable
    ): Boolean {
        val type = plantable.getPlantType(world, pos.offset(facing))

        return type == PlantType.CROP || type == PlantType.PLAINS
    }

    override fun ticksRandomly(state: BlockState) = true

    private fun turnToSoil(state: BlockState, worldIn: World, pos: BlockPos) {
        worldIn.setBlockState(pos, nudgeEntitiesWithNewState(state, getSoilState(), worldIn, pos))
    }

    private fun hasWater(worldIn: IWorldReader, pos: BlockPos): Boolean {
        for (pos1 in BlockPos.getAllInBoxMutable(pos.x - 4, pos.y, pos.z - 4, pos.x + 4, pos.y + 1, pos.z + 4)) {
            if (worldIn.getFluidState(pos1).isTagged(FluidTags.WATER)) {
                return true
            }
        }
        return FarmlandWaterManager.hasBlockWaterTicket(worldIn, pos)
    }

    private fun hasCrops(worldIn: IWorldReader, pos: BlockPos): Boolean {
        val state = worldIn.getBlockState(pos.up())
        val block = state.block

        return block is IPlantable && canSustainPlant(state, worldIn, pos, Direction.UP, block)
    }

    companion object {
        val MOISTURE: IntegerProperty = BlockStateProperties.MOISTURE_0_7
        val SHAPE: VoxelShape = makeCuboidShape(0.0, 0.0, 0.0, 16.0, 15.0, 16.0)

        val EMPTY = object : Object2FloatMap<Supplier<Block>> {
            override fun containsKey(key: Supplier<Block>?) = false
            override fun getFloat(key: Any?) = 0.0f
            override fun defaultReturnValue(rv: Float) = Unit
            override fun defaultReturnValue() = 0.0f
            override fun putAll(from: Map<out Supplier<Block>, Float>) = Unit
            override fun containsValue(value: Float) = false
            override fun isEmpty() = true
            override fun object2FloatEntrySet(): ObjectSet<Object2FloatMap.Entry<Supplier<Block>>> = ObjectSets.emptySet()
            override val keys: ObjectSet<Supplier<Block>> = ObjectSets.emptySet()
            override val values = FloatLists.EMPTY_LIST
            override val size = 0
            override fun getOrDefault(key: Any?, defaultValue: Float) = defaultValue
        }

        // Hook
        fun overrideCropGrowthBehaviour(event: BlockEvent.CropGrowEvent.Pre) {
            val world = event.world
            val pos = event.pos

            // get the BonusFarmlandBlock instance hopefully
            val farmland = world.getBlockState(pos.down()).block

            // do not override vanilla farmland for now
            if (farmland !is BonusFarmlandBlock) return

            val state = event.state
            val crop = state.block

            for (entry in farmland.boostMap.object2FloatEntrySet()) {
                if (entry.key.get() == crop) {
                    // we only have a few crops supported
                    if (crop is IGrowable && world is ServerWorld) {

                        if (world.random.nextFloat() < farmland.boostMap.getFloat(crop)) {
                            // override default logic
                            event.result = Event.Result.DENY

                        }
                    }
                    return
                }
            }
        }
    }
}