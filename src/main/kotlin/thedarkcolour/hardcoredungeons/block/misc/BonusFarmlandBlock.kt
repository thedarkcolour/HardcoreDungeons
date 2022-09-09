package thedarkcolour.hardcoredungeons.block.misc

import it.unimi.dsi.fastutil.floats.FloatLists
import it.unimi.dsi.fastutil.objects.Object2FloatMap
import it.unimi.dsi.fastutil.objects.ObjectSet
import it.unimi.dsi.fastutil.objects.ObjectSets
import net.minecraft.tags.FluidTags
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.server.level.ServerLevel
import net.minecraft.util.RandomSource
import net.minecraft.world.entity.Entity
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.LevelAccessor
import net.minecraft.world.level.LevelReader
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.piston.MovingPistonBlock
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties.MOISTURE
import net.minecraft.world.level.pathfinder.PathComputationType
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraftforge.common.*
import thedarkcolour.hardcoredungeons.block.base.BlockMaker
import thedarkcolour.hardcoredungeons.block.base.HBlock
import thedarkcolour.hardcoredungeons.block.base.properties.HProperties
import java.util.*

/**
 * @property boostMap crops and their chance to grow 2 stages instead of 1
 */
class BonusFarmlandBlock(
    val getSoilState: () -> BlockState = Blocks.DIRT::defaultBlockState,
    val boostMap: Object2FloatMap<() -> Block> = EMPTY, // default to empty map
    properties: HProperties,
) : HBlock(properties.randomTicks()) {
    init {
        registerDefaultState(stateDefinition.any().setValue(MOISTURE, 0))
    }

    override fun updateShape(
        state: BlockState,
        direction: Direction,
        fromState: BlockState,
        level: LevelAccessor,
        pos: BlockPos,
        fromPos: BlockPos,
    ): BlockState {
        if (direction == Direction.UP && !state.canSurvive(level, pos)) {
            level.scheduleTick(pos, this, 1)
        }

        return state
    }

    override fun canSurvive(state: BlockState, worldIn: LevelReader, pos: BlockPos): Boolean {
        val aboveState = worldIn.getBlockState(pos.above())
        return !aboveState.material.isSolid || aboveState.`is`(Tags.Blocks.FENCE_GATES) || aboveState.block is MovingPistonBlock
    }

    override fun getStateForPlacement(context: BlockPlaceContext): BlockState? {
        return if (!defaultBlockState().canSurvive(context.level, context.clickedPos)) {
            getSoilState()
        } else super.getStateForPlacement(context)
    }

    override fun useShapeForLightOcclusion(state: BlockState) = true

    override fun getShape(state: BlockState?, worldIn: BlockGetter?, pos: BlockPos?, ctx: CollisionContext?) = BlockMaker.FARMLAND_SHAPE

    override fun tick(state: BlockState, worldIn: ServerLevel, pos: BlockPos, rand: RandomSource) {
        if (!state.canSurvive(worldIn, pos)) {
            turnToSoil(state, worldIn, pos)
        } else {
            val i = state.getValue(MOISTURE)
            if (!hasWater(worldIn, pos) && !worldIn.isRainingAt(pos.above())) {
                if (i > 0) {
                    worldIn.setBlock(pos, state.setValue(MOISTURE, Integer.valueOf(i - 1)), 2)
                } else if (!hasCrops(worldIn, pos)) {
                    turnToSoil(state, worldIn, pos)
                }
            } else if (i < 7) {
                worldIn.setBlock(pos, state.setValue(MOISTURE, Integer.valueOf(7)), 2)
            }
        }
    }

    override fun fallOn(worldIn: Level, state: BlockState, pos: BlockPos, entityIn: Entity, fallDistance: Float) {
        if (!worldIn.isClientSide && ForgeHooks.onFarmlandTrample(worldIn, pos, getSoilState(), fallDistance, entityIn)) {
            turnToSoil(worldIn.getBlockState(pos), worldIn, pos)
        }

        super.fallOn(worldIn, state, pos, entityIn, fallDistance)
    }

    override fun isPathfindable(state: BlockState, worldIn: BlockGetter, pos: BlockPos, type: PathComputationType) = false

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
        builder.add(MOISTURE)
    }

    override fun isFertile(state: BlockState, world: BlockGetter?, pos: BlockPos?): Boolean {
        return state.getValue(MOISTURE) > 0
    }

    override fun canSustainPlant(
        state: BlockState,
        world: BlockGetter,
        pos: BlockPos,
        facing: Direction,
        plantable: IPlantable
    ): Boolean {
        val type = plantable.getPlantType(world, pos.relative(facing))

        return type == PlantType.CROP || type == PlantType.PLAINS
    }

    override fun isRandomlyTicking(state: BlockState) = true

    private fun turnToSoil(state: BlockState, worldIn: Level, pos: BlockPos) {
        worldIn.setBlockAndUpdate(pos, pushEntitiesUp(state, getSoilState(), worldIn, pos))
    }

    private fun hasWater(worldIn: LevelReader, pos: BlockPos): Boolean {
        for (pos1 in BlockPos.betweenClosed(pos.x - 4, pos.y, pos.z - 4, pos.x + 4, pos.y + 1, pos.z + 4)) {
            if (worldIn.getFluidState(pos1).`is`(FluidTags.WATER)) {
                return true
            }
        }
        return FarmlandWaterManager.hasBlockWaterTicket(worldIn, pos)
    }

    private fun hasCrops(worldIn: LevelReader, pos: BlockPos): Boolean {
        val state = worldIn.getBlockState(pos.above())
        val block = state.block

        return block is IPlantable && canSustainPlant(state, worldIn, pos, Direction.UP, block)
    }

    companion object {
        val EMPTY = object : Object2FloatMap<() -> Block> {
            override fun containsKey(key: (() -> Block)?) = false
            override fun getFloat(key: Any?) = 0.0f
            override fun defaultReturnValue(rv: Float) = Unit
            override fun defaultReturnValue() = 0.0f
            override fun putAll(from: Map<out () -> Block, Float>) = Unit
            override fun containsValue(value: Float) = false
            override fun isEmpty() = true
            override fun object2FloatEntrySet(): ObjectSet<Object2FloatMap.Entry<() -> Block>> = ObjectSets.emptySet()
            override val keys: ObjectSet<() -> Block> = ObjectSets.emptySet()
            override val values = FloatLists.EMPTY_LIST
            override val size = 0
            override fun getOrDefault(key: Any?, defaultValue: Float) = defaultValue
        }
    }
}