package thedarkcolour.hardcoredungeons.item.debug

import com.google.common.collect.ImmutableMap
import net.minecraft.core.BlockPos
import net.minecraft.network.chat.Component
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.UseAnim
import net.minecraft.world.item.context.UseOnContext
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState
import thedarkcolour.hardcoredungeons.util.getStoredPos
import thedarkcolour.hardcoredungeons.util.setStoredPos

class CloneWandItem(properties: Properties) : Item(properties) {
    private val structureMap = hashMapOf<Player, ImmutableMap<BlockPos, BlockState>?>()
    private val undoMap = hashMapOf<Player, ImmutableMap<BlockPos, BlockState>?>()

    override fun useOn(ctx: UseOnContext): InteractionResult {
        val world = ctx.level

        if (!world.isClientSide) {
            val stack = ctx.itemInHand
            val pos = ctx.clickedPos
            val player = ctx.player ?: return InteractionResult.PASS

            if (player.isShiftKeyDown) {
                val startPos = getStoredPos(stack) // delegate

                if (startPos == null) {
                    setStoredPos(stack, pos) // delegate
                    player.displayClientMessage(Component.literal("Clone starting position: (${pos.x} ${pos.y} ${pos.z})"), true)
                } else {
                    val immutableMapBuilder = ImmutableMap.builder<BlockPos, BlockState>()

                    for (mutable in BlockPos.betweenClosed(startPos, pos)) {
                        immutableMapBuilder.put(mutable.subtract(startPos), world.getBlockState(mutable))
                    }

                    structureMap[player] = immutableMapBuilder.build()
                    player.displayClientMessage(Component.literal("Saved blocks from (${startPos.x} ${startPos.y} ${startPos.z}) to (${pos.x} ${pos.y} ${pos.z})"), true)
                    setStoredPos(stack, null) // delegate
                }
            } else {
                val face = ctx.clickedFace
                val offset = pos.relative(face)
                val pos2BlockState = (structureMap[player]?.entries ?: return InteractionResult.PASS)
                val immutableMapBuilder = ImmutableMap.builder<BlockPos, BlockState>()

                for (entry in pos2BlockState) {
                    val state = entry.value
                    val posOffset = entry.key.offset(offset)
                    immutableMapBuilder.put(posOffset, world.getBlockState(posOffset))
                    world.setBlockAndUpdate(posOffset, state)
                }

                undoMap[player] = immutableMapBuilder.build()
                player.displayClientMessage(Component.literal("Cloned structure anchored at (${offset.x} ${offset.y} ${offset.z})"), true)
            }
        }

        return InteractionResult.SUCCESS
    }

    override fun getUseAnimation(stack: ItemStack) = UseAnim.BOW

    override fun getUseDuration(stack: ItemStack) = 40

    override fun finishUsingItem(stack: ItemStack, worldIn: Level, entityLiving: LivingEntity): ItemStack {
        if (entityLiving is Player) {
            undoMap[entityLiving]?.let { pos2StateMap ->
                for (entry in pos2StateMap) {
                    worldIn.setBlockAndUpdate(entry.key, entry.value)
                }
                entityLiving.displayClientMessage(Component.literal("Undo!"), true)
                undoMap[entityLiving] = null
            }
        }
        return stack
    }

    override fun use(worldIn: Level, playerIn: Player, handIn: InteractionHand): InteractionResultHolder<ItemStack> {
        if (!worldIn.isClientSide) {
            if (playerIn.isCrouching) {
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