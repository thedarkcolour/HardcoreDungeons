package thedarkcolour.hardcoredungeons.item.debug

import com.google.common.collect.ImmutableMap
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.item.ItemStack
import net.minecraft.core.BlockPos
import net.minecraft.nbt.NbtUtils
import net.minecraft.network.chat.Component
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.UseAnim
import net.minecraft.world.item.context.UseOnContext
import net.minecraft.world.level.Level

/**
 * Base class for wands that have a fill-style function.
 * Includes the undo function.
 *
 * @param properties the item properties
 * @property undoMap the map of undo actions per player
 * @property fillMessage the message this wand should display when filling
 *
 * @author thedarkcolour
 */
abstract class AbstractFillingWandItem(properties: Properties) : Item(properties) {
    private val undoMap = hashMapOf<Player, Map<BlockPos, BlockState>>()
    abstract val fillMessage: String

    abstract override fun useOn(context: UseOnContext): InteractionResult

    /**
     * Places blocks from the saved StartPos tag to the specified [pos].
     *
     * @param stack the wand to check for saved start position
     * @param state the [BlockState] to fill in the bounds
     * @param pos the [BlockPos] that will specify the other bound of the area.
     * @param worldIn the world
     * @param playerIn the player that may or may not exist
     */
    protected fun place(stack: ItemStack, state: BlockState, pos: BlockPos, worldIn: Level, playerIn: Player?) {
        val startPos = NbtUtils.readBlockPos(stack.getTagElement("StartPos")!!)
        val builder = ImmutableMap.builder<BlockPos, BlockState>()

        for (blockPos in BlockPos.betweenClosed(startPos, pos)) {
            val immutablePos = blockPos.immutable()

            builder.put(immutablePos, worldIn.getBlockState(immutablePos))
            worldIn.setBlock(immutablePos, state, 2)
        }

        playerIn?.let { p ->
            undoMap[p] = builder.build()
        }
        playerIn?.displayClientMessage(
            Component.translatable(fillMessage)
                .append(" (${startPos.x} ${startPos.y} ${startPos.z}) ")
                .append(Component.translatable("lang.hardcoredungeons.to"))
                .append(" (${pos.x} ${pos.y} ${pos.z})"),
            true
        )
        stack.removeTagKey("StartPos")
    }

    /**
     * Called when the player finishes using this Item (E.g. finishes eating.). Not called when the player stops using
     * the Item before the action is complete.
     */
    override fun finishUsingItem(stack: ItemStack?, worldIn: Level, entityLiving: LivingEntity): ItemStack? {
        if (entityLiving is Player) {
            undoMap[entityLiving]?.let { pos2StateMap ->
                for (entry in pos2StateMap) {
                    worldIn.setBlock(entry.key, entry.value, 2)
                }
                entityLiving.displayClientMessage(Component.literal("Undo!"), true)
                undoMap.remove(entityLiving)
            }
        }
        return stack
    }

    /**
     * Determines if the wand should complete a fill action.
     *
     * @param stack the wand
     *
     * @return if the wand has a starting position selected
     */
    protected fun hasStartPos(stack: ItemStack): Boolean {
        return stack.getTagElement("StartPos") != null
    }

    /**
     * Serializes the starting position of the current
     * fill action to the NBT of the wand.
     *
     * @param stack the wand
     * @param pos the starting position
     * @param playerIn the player to send a selection message
     */
    protected fun saveStartPosition(stack: ItemStack, pos: BlockPos, playerIn: Player?) {
        stack.addTagElement("StartPos", NbtUtils.writeBlockPos(pos))
        playerIn?.displayClientMessage(Component.literal("Starting position: (${pos.x} ${pos.y} ${pos.z})"), true)
    }

    /**
     * How long it takes to use or consume an item
     */
    override fun getUseDuration(stack: ItemStack?) = 40

    /**
     * Returns the action that specifies what animation to play when the items is being used
     */
    override fun getUseAnimation(stack: ItemStack?) = UseAnim.BOW

    /**
     * Called to trigger the item's "innate" right click behavior.
     * To handle when this item is used on a Block, see [onItemUse].
     */
    override fun use(worldIn: Level, playerIn: Player, handIn: InteractionHand): InteractionResultHolder<ItemStack> {
        if (!worldIn.isClientSide) {
            if (playerIn.isShiftKeyDown) {
                playerIn.getItemInHand(handIn).removeTagKey("StartPos")
                playerIn.displayClientMessage(Component.literal("Cleared start position"), true)
            } else if (undoMap[playerIn] != null) {
                playerIn.displayClientMessage(Component.literal("Hold to undo"), true)
                playerIn.startUsingItem(handIn)
            }
        }
        return InteractionResultHolder.pass(playerIn.getItemInHand(handIn))
    }
}