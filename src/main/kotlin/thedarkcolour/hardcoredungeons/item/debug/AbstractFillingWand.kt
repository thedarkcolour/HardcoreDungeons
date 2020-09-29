package thedarkcolour.hardcoredungeons.item.debug

import com.google.common.collect.ImmutableMap
import net.minecraft.block.BlockState
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.ItemUseContext
import net.minecraft.item.UseAction
import net.minecraft.nbt.NBTUtil
import net.minecraft.util.ActionResult
import net.minecraft.util.ActionResultType
import net.minecraft.util.Hand
import net.minecraft.util.math.BlockPos
import net.minecraft.util.text.StringTextComponent
import net.minecraft.util.text.TranslationTextComponent
import net.minecraft.world.World

/**
 * Base class for wands that have a fill-style function.
 * Includes the undo function.
 *
 * @property properties the item properties
 * @property undoMap the map of undo actions per player
 * @property fillMessage the message this wand should display when filling
 *
 * @author TheDarkColour
 */
abstract class AbstractFillingWand(properties: Properties) : Item(properties) {
    private val undoMap = hashMapOf<PlayerEntity, Map<BlockPos, BlockState>>()
    abstract val fillMessage: String

    abstract override fun onItemUse(context: ItemUseContext): ActionResultType

    /**
     * Places blocks from the saved StartPos tag to the specified [pos].
     *
     * @param stack the wand to check for saved start position
     * @param state the [BlockState] to fill in the bounds
     * @param pos the [BlockPos] that will specify the other bound of the area.
     * @param worldIn the world
     * @param playerIn the player that may or may not exist
     */
    protected fun place(stack: ItemStack, state: BlockState, pos: BlockPos, worldIn: World, playerIn: PlayerEntity?) {
        val startPos = NBTUtil.readBlockPos(stack.getChildTag("StartPos")!!)
        val builder = ImmutableMap.builder<BlockPos, BlockState>()

        for (blockPos in BlockPos.getAllInBoxMutable(startPos, pos)) {
            val immutablePos = blockPos.toImmutable()

            builder.put(immutablePos, worldIn.getBlockState(immutablePos))
            worldIn.setBlockState(immutablePos, state, 2)
        }

        playerIn?.let { p ->
            undoMap[p] = builder.build()
        }
        playerIn?.sendStatusMessage(
            TranslationTextComponent(fillMessage)
                .appendSibling(StringTextComponent(" (${startPos.x} ${startPos.y} ${startPos.z}) "))
                .appendSibling(TranslationTextComponent("lang.hardcoredungeons.to"))
                .appendSibling(StringTextComponent(" (${pos.x} ${pos.y} ${pos.z})")),
            true
        )
        stack.removeChildTag("StartPos")
    }

    /**
     * Called when the player finishes using this Item (E.g. finishes eating.). Not called when the player stops using
     * the Item before the action is complete.
     */
    override fun onItemUseFinish(stack: ItemStack?, worldIn: World, entityLiving: LivingEntity): ItemStack? {
        if (entityLiving is PlayerEntity) {
            undoMap[entityLiving]?.let { pos2StateMap ->
                for (entry in pos2StateMap) {
                    worldIn.setBlockState(entry.key, entry.value, 2)
                }
                entityLiving.sendStatusMessage(StringTextComponent("Undo!"), true)
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
        return stack.getChildTag("StartPos") != null
    }

    /**
     * Serializes the starting position of the current
     * fill action to the NBT of the wand.
     *
     * @param stack the wand
     * @param pos the starting position
     * @param playerIn the player to send a selection message
     */
    protected fun saveStartPosition(stack: ItemStack, pos: BlockPos, playerIn: PlayerEntity?) {
        stack.setTagInfo("StartPos", NBTUtil.writeBlockPos(pos))
        playerIn?.sendStatusMessage(StringTextComponent("Starting position: (${pos.x} ${pos.y} ${pos.z})"), true)
    }

    /**
     * How long it takes to use or consume an item
     */
    override fun getUseDuration(stack: ItemStack?) = 40

    /**
     * Returns the action that specifies what animation to play when the items is being used
     */
    override fun getUseAction(stack: ItemStack?) = UseAction.BOW

    /**
     * Called to trigger the item's "innate" right click behavior.
     * To handle when this item is used on a Block, see [onItemUse].
     */
    override fun onItemRightClick(worldIn: World, playerIn: PlayerEntity, handIn: Hand): ActionResult<ItemStack> {
        if (!worldIn.isRemote) {
            if (playerIn.isSneaking) {
                playerIn.getHeldItem(handIn).removeChildTag("StartPos")
                playerIn.sendStatusMessage(StringTextComponent("Cleared start position"), true)
            } else if (undoMap[playerIn] != null) {
                playerIn.sendStatusMessage(StringTextComponent("Hold to undo"), true)
                playerIn.activeHand = handIn
            }
        }
        return ActionResult.resultPass(playerIn.getHeldItem(handIn))
    }
}