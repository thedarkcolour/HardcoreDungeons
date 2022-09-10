package thedarkcolour.hardcoredungeons.block.structure

import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.core.Direction
import net.minecraft.world.level.ItemLike
import net.minecraft.core.BlockPos
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.phys.BlockHitResult
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
        level: Level,
        pos: BlockPos,
        player: Player,
        handIn: InteractionHand,
        hit: BlockHitResult,
    ): InteractionResult {
        val key = key.asItem()
        if (key == player.getItemInHand(InteractionHand.MAIN_HAND).item || key == player.getItemInHand(InteractionHand.OFF_HAND).item) {
            if (level.isClientSide) {
                return InteractionResult.SUCCESS
            } else {
                val keyspace = keyspace()
                val positions = arrayListOf(pos)

                // This is the fastest way I've tested so far
                collectAdjacentBlocks(level, pos, keyspace, positions, 144)

                for (position in positions) {
                    level.destroyBlock(position, false)
                }
            }
        }

        return InteractionResult.PASS
    }

    companion object {
        fun collectAdjacentBlocks(level: BlockGetter, from: BlockPos, target: Block, list: MutableList<BlockPos>, limit: Int) {
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