package thedarkcolour.hardcoredungeons.block.portal

import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.entity.Entity
import net.minecraft.entity.player.ServerPlayerEntity
import net.minecraft.state.StateContainer
import net.minecraft.state.properties.BlockStateProperties
import net.minecraft.util.Direction.*
import net.minecraft.util.RegistryKey
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.shapes.IBooleanFunction
import net.minecraft.util.math.shapes.ISelectionContext
import net.minecraft.util.math.shapes.VoxelShape
import net.minecraft.util.math.shapes.VoxelShapes
import net.minecraft.world.IBlockReader
import net.minecraft.world.IWorld
import net.minecraft.world.World
import thedarkcolour.hardcoredungeons.block.properties.HProperties

class PortalBlock(private val dimensionKey: () -> RegistryKey<World>, private val frameBlock: Block, properties: HProperties) : Block(properties.build()) {
    init {
        defaultState = defaultState.with(AXIS, Axis.X)
    }

    override fun onEntityCollision(state: BlockState, worldIn: World, blockPos: BlockPos, entityIn: Entity) {
        val pos = blockPos.toMutable()

        if (!worldIn.isRemote && canTeleport(state, worldIn, pos, entityIn)) {
            // picks the destination dimension
            val key = dimensionKey()

            val type = if (worldIn.dimensionKey == key) {
                World.OVERWORLD
            } else {
                key
            }

            // destination world
            val destination = worldIn.server!!.getWorld(type) ?: return

            // teleport player to the dimension
            if (entityIn is ServerPlayerEntity) {
                entityIn.teleport(destination, entityIn.position.x.toDouble(), entityIn.position.y.toDouble(), entityIn.position.z.toDouble(), entityIn.rotationYaw, entityIn.rotationPitch)
            }

            // each state has one reference
            // state does not need equals()
            if (destination.getBlockState(pos) != state) {

                // create portal that matches the one in this dimension
                //constructMatchingPortal(destination, worldIn, pos, state)
            }
        }
    }

    // if a portal block is missing then we make a new portal here
    private fun constructMatchingPortal(destination: IWorld, worldIn: IWorld, pos: BlockPos, state: BlockState) {
        val startCorner = pos.toMutable()
        val endCorner = pos.toMutable()
        val startFacing = if (state.get(AXIS) == Axis.X) {
            EAST
        } else {
            SOUTH
        }
        val endFacing = startFacing.opposite

        // find the vertices of the portal frame
        while (true) {
            if (worldIn.getBlockState(startCorner.offset(DOWN)) != state) {
                // get out of frame so we can get horizontal coordinate
                startCorner.offset(UP)
                break
            }
        }

        while (true) {
            if (worldIn.getBlockState(endCorner.offset(UP)) != state) {
                // get out of frame so we can get horizontal coordinate
                endCorner.offset(DOWN)
                break
            }
        }

        while (true) {
            if (worldIn.getBlockState(startCorner.offset(startFacing)) != state) {
                // go back into frame
                startCorner.offset(DOWN)
                break
            }
        }

        while (true) {
            if (worldIn.getBlockState(endCorner.offset(endFacing)) != state) {
                // go back into frame
                endCorner.offset(UP)
                break
            }
        }

        // store the corner
        var startingPos = startCorner.toImmutable()
        var endingPos = endCorner.toImmutable()
        val frame = frameBlock.defaultState

        // build horizontally
        if (startFacing == EAST) {
            do {
                destination.getChunk(pos)
                destination.setBlockState(startCorner, frame, 18)
                destination.setBlockState(endCorner, frame, 18)
                endCorner.offset(startFacing)
            } while (startCorner.offset(endFacing).x != endingPos.x)
        } else {
            do {
                destination.getChunk(pos)
                destination.setBlockState(startCorner, frame, 18)
                destination.setBlockState(endCorner, frame, 18)
                endCorner.offset(startFacing)
            } while (startCorner.offset(endFacing).z != endingPos.z)
        }

        // build vertically
        while (startCorner != endingPos) {
            destination.setBlockState(startCorner.offset(UP), frame, 18)
            destination.setBlockState(endCorner.offset(DOWN), frame, 18)
        }

        // get out of frame
        startingPos = startingPos.offset(endFacing)
        endingPos = endingPos.offset(startFacing)

        // fill in the frame
        for (blockPos in BlockPos.getAllInBoxMutable(startingPos.x, startingPos.y + 1, startingPos.z, endingPos.x, endingPos.y - 1, endingPos.z)) {
            destination.setBlockState(blockPos, frame, 18)
        }
    }

    private fun canTeleport(state: BlockState, worldIn: World, pos: BlockPos, entityIn: Entity): Boolean {
        return !entityIn.isPassenger && !entityIn.isBeingRidden && entityIn.isNonBoss && VoxelShapes.compare(VoxelShapes.create(entityIn.boundingBox.offset(-pos.x.toDouble(), -pos.y.toDouble(), -pos.z.toDouble())), state.getShape(worldIn, pos), IBooleanFunction.AND)
    }

    override fun fillStateContainer(builder: StateContainer.Builder<Block, BlockState>) {
        builder.add(AXIS)
    }

    override fun getShape(state: BlockState, worldIn: IBlockReader, pos: BlockPos, ctx: ISelectionContext): VoxelShape {
        return if (state.get(AXIS) == Axis.X) {
            X_SHAPE
        } else {
            Z_SHAPE
        }
    }

    companion object {
        private val AXIS = BlockStateProperties.HORIZONTAL_AXIS 
        private val X_SHAPE = makeCuboidShape(0.0, 0.0, 6.0, 16.0, 16.0, 10.0);
        private val Z_SHAPE = makeCuboidShape(6.0, 0.0, 0.0, 10.0, 16.0, 16.0);
    }
}