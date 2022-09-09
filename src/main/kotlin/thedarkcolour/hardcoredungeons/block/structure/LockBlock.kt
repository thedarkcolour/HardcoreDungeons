package thedarkcolour.hardcoredungeons.block.structure

import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.util.ActionResultType
import net.minecraft.core.Direction
import net.minecraft.util.Hand
import net.minecraft.world.level.ItemLike
import net.minecraft.core.BlockPos
import net.minecraft.util.math.BlockRayTraceResult
import net.minecraft.world.IBlockReader
import net.minecraft.world.level.Level
import thedarkcolour.hardcoredungeons.block.base.HBlock
import thedarkcolour.hardcoredungeons.block.base.properties.HProperties

/**
 * @param key The item used to unlock this door
 * @param keyspace The block that is "unlocked" by this key
 * @param properties Block properties
 */
class LockBlock(private val key: ItemLike, private val keyspace: () -> Block, properties: HProperties) : HBlock(properties) {
    override fun use(
        state: BlockState,
        level: World,
        pos: BlockPos,
        player: PlayerEntity,
        handIn: Hand,
        hit: BlockRayTraceResult,
    ): ActionResultType {
        val key = key.asItem()
        if (key == player.getItemInHand(Hand.MAIN_HAND).item || key == player.getItemInHand(Hand.OFF_HAND).item) {
            if (level.isClientSide) {
                return ActionResultType.SUCCESS
            } else {
                val keyspace = keyspace()
                val positions = arrayListOf(pos)

                // This is the fastest way I've tested so far
                Companion.collectAdjacentBlocks(level, pos, keyspace, positions, 144)

                for (position in positions) {
                    level.destroyBlock(position, false)
                }
            }
        }

        return ActionResultType.PASS
    }

    companion object {
        fun collectAdjacentBlocks(level: IBlockReader, from: BlockPos, target: Block, list: MutableList<BlockPos>, limit: Int) {
            val toCheck = arrayListOf(from)
            val listIter = toCheck.listIterator(1)

            // Iterate backwards so that added positions will not be skipped over
            while (listIter.hasPrevious()) {
                val pos = listIter.previous()
                listIter.remove()

                for (direction in Direction.values()) {
                    val p = pos.relative(direction)

                    if (list.size >= limit) {
                        return
                    }

                    if (level.getBlockState(p).block == target && !list.contains(p)) {
                        list.add(p)
                        listIter.add(p)
                    }
                }
            }
        }
    }
}