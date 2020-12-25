package thedarkcolour.hardcoredungeons.block.portal

import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.entity.Entity
import net.minecraft.entity.player.ServerPlayerEntity
import net.minecraft.state.StateContainer
import net.minecraft.state.properties.BlockStateProperties
import net.minecraft.util.Direction.Axis
import net.minecraft.util.RegistryKey
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.BlockPos.getAllInBoxMutable
import net.minecraft.util.math.shapes.IBooleanFunction
import net.minecraft.util.math.shapes.ISelectionContext
import net.minecraft.util.math.shapes.VoxelShape
import net.minecraft.util.math.shapes.VoxelShapes
import net.minecraft.world.IBlockReader
import net.minecraft.world.IWorld
import net.minecraft.world.World
import thedarkcolour.hardcoredungeons.block.properties.HProperties
import thedarkcolour.hardcoredungeons.capability.PlayerHelper

class PortalBlock(private val dimensionKey: () -> RegistryKey<World>, private val frameState: () -> BlockState, properties: HProperties) : Block(properties.build()) {
    init {
        defaultState = defaultState.with(AXIS, Axis.X)
    }

    override fun onEntityCollision(state: BlockState, worldIn: World, blockPos: BlockPos, entityIn: Entity) {
        val pos = blockPos.toMutable()

        @Suppress("ControlFlowWithEmptyBody")
        if (!worldIn.isRemote && canTeleport(state, worldIn, pos, entityIn)) {
            // exit early before reading the world
            if (entityIn !is ServerPlayerEntity || PlayerHelper.getPortalCooldown(entityIn) > 0) {
                return
            }
            // picks the destination dimension
            val key = dimensionKey()

            val type = if (worldIn.dimensionKey == key) {
                World.OVERWORLD
            } else {
                key
            }

            // destination world
            val destination = worldIn.server!!.getWorld(type) ?: return

            // height to spawn the portal
            var portalOffset = 0.0
            val x = entityIn.posX
            val z = entityIn.posZ
            val mutable = BlockPos.Mutable(x, portalOffset, z)

            // first check for existing portal
            while (++portalOffset < 255 && destination.getBlockState(mutable.setPos(x, portalOffset, z)).block != this);

            // then check for vacant portal position if no portal exists
            if (destination.getBlockState(mutable).block != this) {
                portalOffset = 0.0
                mutable.setPos(x, 0.0, z)
                while (++portalOffset < 255 && !destination.isAirBlock(mutable.setPos(x, portalOffset, z)));
            }

            // offset by one for the portal to spawn
            ++portalOffset

            // teleport player to the dimension
            PlayerHelper.setPortalCooldown(entityIn, 30)
            // cache effects because teleport clears them for some reason
            val effects = ArrayList(entityIn.activePotionEffects)
            entityIn.teleport(destination, entityIn.position.x.toDouble(), portalOffset, entityIn.position.z.toDouble(), entityIn.rotationYaw, entityIn.rotationPitch)
            // add all the effects back
            for (effect in effects) {
                entityIn.addPotionEffect(effect)
            }

            // each state has one reference
            // state does not need equals()
            if (destination.getBlockState(BlockPos(entityIn.position.x.toDouble(), portalOffset, entityIn.position.z.toDouble())) != state) {

                // create portal that matches the one in this dimension
                constructMatchingPortal(destination, worldIn, pos, entityIn.position, state)
            }
        }
    }

    // if a portal block is missing then we make a new portal here
    private fun constructMatchingPortal(destination: IWorld, origin: IWorld, pos: BlockPos, tpLocation: BlockPos, state: BlockState) {
        val axis = state.get(AXIS)
        // cursor for measuring portal
        val testCursor = pos.toMutable()
        var endY = pos.y
        var startY = pos.y

        // only get the state of the portal frame once
        val frame = frameState()

        // find the top and bottom of the portal
        while (origin.getBlockState(testCursor.move(0, -1, 0)).block == this) --startY
        testCursor.setPos(pos)
        while (origin.getBlockState(testCursor.move(0, 1, 0)).block == this) ++endY
        testCursor.setPos(pos)

        // top and bottom coordinates of the destination portal
        val minY = tpLocation.y - 1
        val maxY = tpLocation.y + endY - pos.y + 1

        // axis specific code
        if (axis == Axis.X) {
            var startX = pos.x
            var endX = pos.x

            // find sides of portal
            while (origin.getBlockState(testCursor.move(-1, 0, 0)).block == this) --startX
            testCursor.setPos(pos)
            while (origin.getBlockState(testCursor.move(1, 0, 0)).block == this) ++endX

            // don't make an impossible portal
            if (endX - startX < 1 || endY - startY < 2) {
                return
            }

            // edge coordinates of portal
            val minX = startX - 1
            val maxX = endX + 1

            for (cursor in getAllInBoxMutable(minX, minY, pos.z, maxX, maxY, pos.z)) {
                // if we're on the edge place a frame
                if (cursor.x == minX || cursor.x == maxX || cursor.y == minY || cursor.y == maxY) {
                    destination.setBlockState(cursor, frame, 18)
                } else {
                    destination.setBlockState(cursor, state, 18)
                }
            }
        } else {
            var startZ = pos.z
            var endZ = pos.z

            // find one side of the portal
            while (origin.getBlockState(testCursor.move(0, 0, -1)).block == this) --startZ
            // reset cursor
            testCursor.setPos(pos)
            // find the other side
            while (origin.getBlockState(testCursor.move(0, 0, 1)).block == this) ++endZ

            // don't make an impossible portal
            if (endZ - startZ < 1 || endY - startY < 2) {
                return
            }

            // edge coordinates of portal
            val minZ = startZ - 1
            val maxZ = endZ + 1

            for (cursor in getAllInBoxMutable(pos.x, minY, minZ, pos.x, maxY, maxZ)) {
                // if we're on the edge place a frame
                if (cursor.z == minZ || cursor.z == maxZ || cursor.y == minY || cursor.y == maxY) {
                    destination.setBlockState(cursor, frame, 18)
                } else {
                    destination.setBlockState(cursor, state, 18)
                }
            }
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