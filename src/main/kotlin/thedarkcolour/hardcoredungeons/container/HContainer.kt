package thedarkcolour.hardcoredungeons.container

import net.minecraft.core.BlockPos
import net.minecraft.tags.TagKey
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.MenuType
import net.minecraft.world.inventory.Slot
import net.minecraft.world.level.block.Block

/**
 * Base [Container] class that has utility functions for [WorldPos]
 *
 * It has a few other utilities and will have better support for opening screens.
 */
abstract class HContainer(type: MenuType<*>, id: Int, val playerIn: Player) : AbstractContainerMenu(type, id) {
    /**
     * Checks if the block at the position matches the [tag]
     * and if it is within the player's usable distance.
     *
     * @param worldPos used for getting the position of this block
     * @param playerIn the player whose usable distance is checked
     * @param tag the block tag to check
     */
    fun isTagInRange(worldPos: WorldPos, playerIn: Player, tag: TagKey<Block>): Boolean {
        return worldPos.invokeDefaulted(true) { level, pos ->
            level.getBlockState(pos).`is`(tag) && isUsableInRange(pos, playerIn)
        }
    }

    /**
     * Checks if the block at the position is [block]
     * and if it is within the player's usable distance.
     *
     * @param worldPos used for getting the position of this block
     * @param playerIn the player whose usable distance is checked
     * @param block the expected block
     */
    fun isBlockInRange(worldPos: WorldPos, playerIn: Player, block: Block): Boolean {
        return worldPos.invokeDefaulted(true) { worldIn, pos ->
            worldIn.getBlockState(pos).`is`(block) && isUsableInRange(pos, playerIn)
        }
    }

    /**
     * Checks if the position is within the player's usable distance.
     */
    private fun isUsableInRange(pos: BlockPos, playerIn: Player): Boolean {
        return playerIn.distanceToSqr(pos.x.toDouble() + 0.5, pos.y.toDouble() + 0.5, pos.z.toDouble() + 0.5) <= 64.0
    }

    /**
     * Make sure to add your own slots **before** adding player slots.
     */
    protected fun addPlayerSlots(playerInv: Inventory) {
        for (row in 0..2) {
            for (col in 0..8) {
                val x = col * 18 + 8
                val y = row * 18 + 84

                addSlot(Slot(playerInv, col + row * 9 + 9, x, y))
            }
        }

        for (row in 0..8) {
            val x = 9 + row * 18 - 1
            val y = 58 + 70 + 14

            addSlot(Slot(playerInv, row, x, y))
        }
    }
}