package thedarkcolour.hardcoredungeons.tileentity

import net.minecraft.entity.player.PlayerEntity
import net.minecraft.state.properties.BlockStateProperties.HORIZONTAL_FACING
import net.minecraft.tileentity.ITickableTileEntity
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.Direction
import net.minecraft.util.math.AxisAlignedBB
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import thedarkcolour.hardcoredungeons.HardcoreDungeons
import thedarkcolour.hardcoredungeons.entity.overworld.mazeboss.MazeBossEntity
import thedarkcolour.hardcoredungeons.registry.HBlocks
import thedarkcolour.hardcoredungeons.registry.HTileEntities
import thedarkcolour.hardcoredungeons.util.toRadians

class MazeBossSpawnerTileEntity : TileEntity(HTileEntities.MAZE_BOSS_SPAWNER), ITickableTileEntity {
    private lateinit var bounds: AxisAlignedBB
    private var ticks = 0

    override fun validate() {
        removed = false

        val state = blockState

        if (!state.properties.contains(HORIZONTAL_FACING)) {
            HardcoreDungeons.LOGGER.error("Failed to initialize block data")
            return
        }

        bounds = createBounds(state.get(HORIZONTAL_FACING), pos)
    }

    private fun createBounds(direction: Direction, pos: BlockPos): AxisAlignedBB {
        val right = direction.rotateY()
        val mut = pos.toMutable()

        val start = mut.move(direction, 3).move(right, 7).move(0, -1, 0).toImmutable()
        mut.setPos(pos)
        val end = mut.move(direction, 17).move(right, -7).move(0, 3, 0).toImmutable()

        return AxisAlignedBB(start, end)
    }

    // Check if player is in spawn range and spawn the boss if they are
    override fun tick() {
        // don't check every tick
        if (++ticks < 20) {
            return
        }

        ticks = 0

        val worldIn = world!!
        val pos = pos
        val players = worldIn.getEntitiesWithinAABB(PlayerEntity::class.java, bounds)

        if (players.isNotEmpty()) {
            spawnBoss(worldIn, pos)
            closeArena(worldIn, pos)

            worldIn.destroyBlock(pos, false)
        }
    }

    private fun spawnBoss(worldIn: World, pos: BlockPos) {
        val boss = MazeBossEntity(worldIn)
        val direction = blockState.get(HORIZONTAL_FACING)

        boss.setSpawnLocation(pos, bounds, direction)
        boss.setLocationAndAngles(pos.x + 0.5, pos.y.toDouble(), pos.z + 0.5, toRadians(direction.horizontalAngle), 0.0f)
        worldIn.addEntity(boss)
    }

    // fill in the door with bushes
    private fun closeArena(worldIn: World, pos: BlockPos) {
        val direction = blockState.get(HORIZONTAL_FACING)
        val right = direction.rotateY()
        val mut = pos.toMutable()
        val start = mut.move(direction, 9).move(right, 8).down(2)
        val end = mut.move(direction, 2).move(right, 1).up(2)

        BlockPos.getAllInBoxMutable(start, end).forEach { p ->
            worldIn.setBlockState(p, HBlocks.MAZE_BUSH.defaultState)
        }
    }
}