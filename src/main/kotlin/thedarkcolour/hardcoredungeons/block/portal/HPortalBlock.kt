package thedarkcolour.hardcoredungeons.block.portal

import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.entity.Entity
import net.minecraft.entity.player.ServerPlayerEntity
import net.minecraft.item.BlockItemUseContext
import net.minecraft.state.StateContainer
import net.minecraft.state.properties.BlockStateProperties
import net.minecraft.core.Direction
import net.minecraft.core.Direction.Axis
import net.minecraft.util.RegistryKey
import net.minecraft.core.BlockPos
import net.minecraft.core.BlockPos.MutableBlockPos
import net.minecraft.core.Direction
import net.minecraft.resources.ResourceKey
import net.minecraft.server.level.ServerPlayer
import net.minecraft.util.math.shapes.IBooleanFunction
import net.minecraft.util.math.shapes.ISelectionContext
import net.minecraft.util.math.shapes.VoxelShape
import net.minecraft.util.math.shapes.VoxelShapes
import net.minecraft.world.IBlockReader
import net.minecraft.world.IWorld
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level
import net.minecraft.world.level.LevelReader
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import thedarkcolour.hardcoredungeons.block.base.properties.HProperties
import thedarkcolour.hardcoredungeons.block.combo.PortalCombo
import thedarkcolour.hardcoredungeons.block.structure.LockBlock
import thedarkcolour.hardcoredungeons.capability.PlayerHelper

class HPortalBlock(
    private val dimensionKey: () -> ResourceKey<Level>,
    private val combo: PortalCombo, // todo use a tag here
    properties: HProperties
) : Block(properties.build()) {
    init {
        registerDefaultState(stateDefinition.any().setValue(AXIS, Direction.Axis.X))
    }

    override fun entityInside(state: BlockState, worldIn: Level, blockPos: BlockPos, entityIn: Entity) {
        val pos = blockPos.mutable()

        @Suppress("ControlFlowWithEmptyBody")
        if (!worldIn.isClientSide && canTeleport(state, worldIn, pos, entityIn)) {
            // exit early before reading the world
            if (entityIn !is ServerPlayer || PlayerHelper.getPortalCooldown(entityIn) > 0) {
                return
            }
            // picks the destination dimension
            val key = dimensionKey()

            val type = if (worldIn.dimension() == key) {
                Level.OVERWORLD
            } else {
                key
            }

            // destination world
            val destination = worldIn.server!!.getLevel(type) ?: return

            // height to spawn the portal
            var portalOffset = 0.0
            val x = entityIn.x
            val z = entityIn.z
            val mutable = MutableBlockPos(x, portalOffset, z)

            // first check for existing portal
            while (++portalOffset < 255 && destination.getBlockState(mutable.set(x, portalOffset, z)).block != this);

            // then check for vacant portal position if no portal exists
            if (destination.getBlockState(mutable).block != this) {
                portalOffset = 0.0
                mutable.set(x, 0.0, z)
                while (++portalOffset < 255 && !destination.isEmptyBlock(mutable.set(x, portalOffset, z)));
            }

            // offset by one for the portal to spawn
            ++portalOffset

            // teleport player to the dimension
            PlayerHelper.setPortalCooldown(entityIn, 30)
            // cache effects because teleport clears them for some reason
            val effects = ArrayList(entityIn.activeEffects)
            entityIn.teleportTo(destination, entityIn.position().x, portalOffset, entityIn.position().z, entityIn.yRot, entityIn.xRot)
            // add all the effects back
            for (effect in effects) {
                entityIn.addEffect(effect)
            }

            // each state has one reference
            if (destination.getBlockState(BlockPos(entityIn.blockPosition().x.toDouble(), portalOffset, entityIn.blockPosition().z.toDouble())) != state) {
                // create portal that matches the one in this dimension
                constructMatchingPortal(destination, worldIn, pos, entityIn.blockPosition(), state)
            }
        }
    }

    // if a portal block is missing then we make a new portal here
    private fun constructMatchingPortal(destination: LevelReader, origin: LevelReader, pos: BlockPos, tpLocation: BlockPos, state: BlockState) {
        val axis = state.getValue(AXIS)
        // cursor for measuring portal
        val testCursor = pos.mutable()
        var endY = pos.y
        var startY = pos.y

        // only get the state of the portal frame once
        val frame = combo.frame.defaultBlockState()

        // find the top and bottom of the portal
        while (origin.getBlockState(testCursor.move(0, -1, 0)).block == this) --startY
        testCursor.set(pos)
        while (origin.getBlockState(testCursor.move(0, 1, 0)).block == this) ++endY
        testCursor.set(pos)

        // top and bottom coordinates of the destination portal
        val minY = tpLocation.y - 1
        val maxY = tpLocation.y + endY - pos.y + 1

        // axis specific code
        if (axis == Axis.X) {
            var startX = pos.x
            var endX = pos.x

            // find sides of portal
            while (origin.getBlockState(testCursor.move(-1, 0, 0)).block == this) --startX
            testCursor.set(pos)
            while (origin.getBlockState(testCursor.move(1, 0, 0)).block == this) ++endX

            // don't make an impossible portal
            if (endX - startX < 1 || endY - startY < 2) {
                return
            }

            // edge coordinates of portal
            val minX = startX - 1
            val maxX = endX + 1

            for (cursor in BlockPos.betweenClosed(minX, minY, pos.z, maxX, maxY, pos.z)) {
                // if we're on the edge place a frame
                if (cursor.x == minX || cursor.x == maxX || cursor.y == minY || cursor.y == maxY) {
                    destination.setBlock(cursor, frame, 18)
                } else {
                    destination.setBlock(cursor, state, 18)
                }
            }
        } else {
            var startZ = pos.z
            var endZ = pos.z

            // find one side of the portal
            while (origin.getBlockState(testCursor.move(0, 0, -1)).block == this) --startZ
            // reset cursor
            testCursor.set(pos)
            // find the other side
            while (origin.getBlockState(testCursor.move(0, 0, 1)).block == this) ++endZ

            // don't make an impossible portal
            if (endZ - startZ < 1 || endY - startY < 2) {
                return
            }

            // edge coordinates of portal
            val minZ = startZ - 1
            val maxZ = endZ + 1

            for (cursor in BlockPos.betweenClosed(pos.x, minY, minZ, pos.x, maxY, maxZ)) {
                // if we're on the edge place a frame
                if (cursor.z == minZ || cursor.z == maxZ || cursor.y == minY || cursor.y == maxY) {
                    destination.setBlock(cursor, frame, 18)
                } else {
                    destination.setBlock(cursor, state, 18)
                }
            }
        }
    }

    private fun canTeleport(state: BlockState, worldIn: World, pos: BlockPos, entityIn: Entity): Boolean {
        return !entityIn.isPassenger && !entityIn.isVehicle && entityIn.canChangeDimensions() && VoxelShapes.joinIsNotEmpty(VoxelShapes.create(entityIn.boundingBox.move(-pos.x.toDouble(), -pos.y.toDouble(), -pos.z.toDouble())), state.getShape(worldIn, pos), IBooleanFunction.AND)
    }

    override fun createBlockStateDefinition(builder: StateContainer.Builder<Block, BlockState>) {
        builder.add(AXIS)
    }

    override fun getStateForPlacement(context: BlockItemUseContext): BlockState? {
        return defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_AXIS, context.horizontalDirection.clockWise.axis)
    }

    override fun getShape(state: BlockState, worldIn: IBlockReader, pos: BlockPos, ctx: ISelectionContext): VoxelShape {
        return if (state.getValue(AXIS) == Axis.X) {
            X_SHAPE
        } else {
            Z_SHAPE
        }
    }

    override fun updateShape(
        state: BlockState,
        direction: Direction,
        fromState: BlockState,
        level: IWorld,
        pos: BlockPos,
        fromPos: BlockPos
    ): BlockState {
        if (!level.isClientSide) {
            if (direction.axis == Axis.Y || state.getValue(AXIS) == direction.axis) {
                if (fromState.block != this && fromState.block != combo.frame) {
                    val toDestroy = arrayListOf(pos)

                    // not sure what this limit should be
                    LockBlock.collectAdjacentBlocks(level, pos, this, toDestroy, 21 * 21)

                    // since state has HORIZONTAL_AXIS there are only two axes
                    if (direction.axis == Axis.X) {
                        for (position in toDestroy) {
                            if (position.z == pos.z) {
                                level.destroyBlock(position, false) // doDrops: false
                            }
                        }
                    } else {
                        for (position in toDestroy) {
                            if (position.x == pos.x) {
                                level.destroyBlock(position, false)
                            }
                        }
                    }
                }
            }
        }

        return state
    }

    companion object {
        private val AXIS = BlockStateProperties.HORIZONTAL_AXIS
        private val X_SHAPE = box(0.0, 0.0, 6.0, 16.0, 16.0, 10.0)
        private val Z_SHAPE = box(6.0, 0.0, 0.0, 10.0, 16.0, 16.0)
    }
}