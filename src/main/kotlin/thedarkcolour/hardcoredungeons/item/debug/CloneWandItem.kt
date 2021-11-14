package thedarkcolour.hardcoredungeons.item.debug

import com.google.common.collect.ImmutableMap
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.ItemUseContext
import net.minecraft.item.UseAction
import net.minecraft.util.ActionResult
import net.minecraft.util.ActionResultType
import net.minecraft.util.Hand
import net.minecraft.util.math.BlockPos
import net.minecraft.util.text.StringTextComponent
import net.minecraft.world.level.Level
import thedarkcolour.hardcoredungeons.util.BlockPosNBTDelegate

class CloneWandItem(properties: Properties) : Item(properties) {
    private val structureMap = hashMapOf<PlayerEntity, ImmutableMap<BlockPos, BlockState>?>()
    private val undoMap = hashMapOf<PlayerEntity, ImmutableMap<BlockPos, BlockState>?>()

    override fun useOn(ctx: ItemUseContext): ActionResultType {
        val world = ctx.level


        if (!world.isClientSide) {
            val stack = ctx.itemInHand
            val pos = ctx.clickedPos
            val player = ctx.player ?: return ActionResultType.PASS

            if (player.isShiftKeyDown) {
                val startPos = stack.startPos // delegate

                if (startPos == null) {
                    stack.startPos = pos // delegate
                    player.displayClientMessage(StringTextComponent("Clone starting position: (${pos.x} ${pos.y} ${pos.z})"), true)
                } else {
                    val immutableMapBuilder = ImmutableMap.builder<BlockPos, BlockState>()

                    for (mutable in BlockPos.betweenClosed(startPos, pos)) {
                        immutableMapBuilder.put(mutable.subtract(startPos), world.getBlockState(mutable))
                    }

                    structureMap[player] = immutableMapBuilder.build()
                    player.displayClientMessage(StringTextComponent("Saved blocks from (${startPos.x} ${startPos.y} ${startPos.z}) to (${pos.x} ${pos.y} ${pos.z})"), true)
                    stack.startPos = null // delegate
                }
            } else {
                val face = ctx.clickedFace
                val offset = pos.relative(face)
                val pos2BlockState = (structureMap[player]?.entries ?: return ActionResultType.PASS)
                val immutableMapBuilder = ImmutableMap.builder<BlockPos, BlockState>()

                for (entry in pos2BlockState) {
                    val state = entry.value
                    val posOffset = entry.key.offset(offset)
                    immutableMapBuilder.put(posOffset, world.getBlockState(posOffset))
                    world.setBlockAndUpdate(posOffset, state)
                }

                undoMap[player] = immutableMapBuilder.build()
                player.displayClientMessage(StringTextComponent("Cloned structure anchored at (${offset.x} ${offset.y} ${offset.z})"), true)
            }
        }

        return ActionResultType.SUCCESS
    }

    private var ItemStack.startPos by BlockPosNBTDelegate("StartPos")

    override fun getUseAnimation(stack: ItemStack) = UseAction.BOW

    override fun getUseDuration(stack: ItemStack) = 40

    override fun finishUsingItem(stack: ItemStack, worldIn: World, entityLiving: LivingEntity): ItemStack {
        if (entityLiving is PlayerEntity) {
            undoMap[entityLiving]?.let { pos2StateMap ->
                for (entry in pos2StateMap) {
                    worldIn.setBlockAndUpdate(entry.key, entry.value)
                }
                entityLiving.displayClientMessage(StringTextComponent("Undo!"), true)
                undoMap[entityLiving] = null
            }
        }
        return stack
    }

    override fun use(worldIn: World, playerIn: PlayerEntity, handIn: Hand): ActionResult<ItemStack> {
        if (!worldIn.isClientSide) {
            if (playerIn.isCrouching) {
                playerIn.getItemInHand(handIn).removeTagKey("StartPos")
                playerIn.displayClientMessage(StringTextComponent("Cleared start position"), true)
            } else if (undoMap[playerIn] != null) {
                playerIn.displayClientMessage(StringTextComponent("Hold to undo"), true)
                playerIn.startUsingItem(handIn)
            }
        }
        return ActionResult.pass(playerIn.getItemInHand(handIn))
    }
}