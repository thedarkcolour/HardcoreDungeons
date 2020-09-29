package thedarkcolour.hardcoredungeons.block.plant.misc

import net.minecraft.block.CarrotBlock
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.item.ItemUseContext
import net.minecraft.item.Items
import net.minecraft.util.Direction
import net.minecraft.util.IItemProvider
import net.minecraft.util.math.BlockRayTraceResult
import net.minecraftforge.event.entity.player.PlayerInteractEvent
import thedarkcolour.hardcoredungeons.registry.HBlocks

class GoldenCarrotsBlock(properties: Properties) : CarrotBlock(properties) {
    init {
        BlockItem.BLOCK_TO_ITEM[HBlocks.GOLDEN_CARROTS] = Items.GOLDEN_CARROT
    }

    override fun getSeedsItem(): IItemProvider {
        return Items.GOLDEN_CARROT
    }

    companion object {
        private val PLACER by lazy {
            BlockItem(HBlocks.GOLDEN_CARROTS, Item.Properties()).also {
                // don't put non-existent items in the block2item map
                // and add the correct middle mouse thing
                BlockItem.BLOCK_TO_ITEM[HBlocks.GOLDEN_CARROTS] = Items.GOLDEN_CARROT
            }
        }

        fun onBlockActivated(event: PlayerInteractEvent.RightClickBlock) {
            if (event.face != Direction.UP) return

            val heldItem = event.itemStack

            if (heldItem.item != Items.GOLDEN_CARROT) return

            val world = event.world
            val pos = event.pos
            val up = pos.up()
            val cropState = HBlocks.GOLDEN_CARROTS.defaultState

            val player = event.player

            try {
                if (player.isCreative) {
                    heldItem.grow(1)
                }
                PLACER.onItemUse(ItemUseContext(player, event.hand, BlockRayTraceResult(null, event.face, pos, false)))
            } catch (e: Exception) {
                e.printStackTrace()
            }
/*
            if (world.isAirBlock(up) && cropState.isValidPosition(world, up)) {
                // cancel eating animation
                event.useItem = Event.Result.DENY

                world.setBlockState(up, HBlocks.GOLDEN_CARROTS.defaultState)

                val player = event.player

                if (player is ServerPlayerEntity) {
                    CriteriaTriggers.PLACED_BLOCK.trigger(player, up, heldItem)
                }

                heldItem.shrink(1)
                player.swingArm(event.hand)
            }
 */
        }
    }
}