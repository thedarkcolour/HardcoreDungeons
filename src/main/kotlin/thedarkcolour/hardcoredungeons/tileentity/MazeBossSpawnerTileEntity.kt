package thedarkcolour.hardcoredungeons.tileentity

import net.minecraft.entity.player.PlayerEntity
import net.minecraft.state.properties.BlockStateProperties.HORIZONTAL_FACING
import net.minecraft.tileentity.ITickableTileEntity
import net.minecraft.tileentity.TileEntity
import net.minecraft.core.Direction
import net.minecraft.util.math.AxisAlignedBB
import net.minecraft.core.BlockPos
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import thedarkcolour.hardcoredungeons.HardcoreDungeons
import thedarkcolour.hardcoredungeons.entity.overworld.mazeboss.MazeBossEntity
import thedarkcolour.hardcoredungeons.registry.HBlocks
import thedarkcolour.hardcoredungeons.registry.HTileEntities
import thedarkcolour.hardcoredungeons.util.toRadians

class MazeBossSpawnerTileEntity : BlockEntity(HTileEntities.MAZE_BOSS_SPAWNER) {
    private lateinit var bounds: AxisAlignedBB
    private var ticks = 0

    override fun clearRemoved() {
        remove = false

        val state = blockState

        if (!state.properties.contains(HORIZONTAL_FACING)) {
            HardcoreDungeons.LOGGER.error("Failed to initialize block data")
            return
        }

        bounds = createBounds(state.getValue(HORIZONTAL_FACING), blockPos)
    }

    private fun createBounds(direction: Direction, pos: BlockPos): AxisAlignedBB {
        val right = direction.clockWise
        val mut = pos.mutable()

        val start = mut.move(direction, 3).move(right, 7).move(0, -1, 0).immutable()
        mut.set(pos)
        val end = mut.move(direction, 17).move(right, -7).move(0, 3, 0).immutable()

        return AxisAlignedBB(start, end)
    }

    // Check if player is in spawn range and spawn the boss if they are
    override fun tick() {
        // don't check every tick
        if (++ticks < 20) {
            return
        }

        ticks = 0

        val worldIn = level!!
        val pos = blockPos
        val players = worldIn.getEntitiesOfClass(PlayerEntity::class.java, bounds)

        if (players.isNotEmpty()) {
            spawnBoss(worldIn, pos)
            closeArena(worldIn, pos)

            worldIn.destroyBlock(pos, false)
        }
    }

    private fun spawnBoss(worldIn: World, pos: BlockPos) {
        val boss = MazeBossEntity(worldIn)
        val direction = blockState.getValue(HORIZONTAL_FACING)

        boss.setSpawnLocation(pos, bounds, direction)
        boss.absMoveTo(pos.x + 0.5, pos.y.toDouble(), pos.z + 0.5, toRadians(direction.toYRot()), 0.0f)
        worldIn.addFreshEntity(boss)
    }

    // fill in the door with bushes
    private fun closeArena(worldIn: World, pos: BlockPos) {
        val direction = blockState.getValue(HORIZONTAL_FACING)
        val right = direction.clockWise
        val mut = pos.mutable()
        val start = mut.move(direction, 9).move(right, 8).below(2)
        val end = mut.move(direction, 2).move(right, 1).above(2)

        BlockPos.betweenClosed(start, end).forEach { p ->
            worldIn.setBlockAndUpdate(p, HBlocks.MAZE_BUSH.defaultBlockState())
        }
    }
}