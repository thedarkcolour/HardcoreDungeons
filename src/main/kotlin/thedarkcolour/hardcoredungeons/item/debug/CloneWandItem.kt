package thedarkcolour.hardcoredungeons.item.debug

import com.google.common.collect.ImmutableMap
import net.minecraft.block.BlockState
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
import net.minecraft.world.World
import thedarkcolour.hardcoredungeons.util.BlockPosNBTDelegate

class CloneWandItem(properties: Properties) : Item(properties) {
    private val structureMap = hashMapOf<PlayerEntity, ImmutableMap<BlockPos, BlockState>?>()
    private val undoMap = hashMapOf<PlayerEntity, ImmutableMap<BlockPos, BlockState>?>()

    override fun onItemUse(ctx: ItemUseContext): ActionResultType {
        val world = ctx.world


        if (!world.isRemote) {
            val stack = ctx.item
            val pos = ctx.pos
            val player = ctx.player ?: return ActionResultType.PASS

            if (player.isSneaking) {
                val startPos = stack.startPos

                if (startPos == null) {
                    stack.startPos = pos
                    player.sendStatusMessage(StringTextComponent("Clone starting position: (${pos.x} ${pos.y} ${pos.z})"), true)
                } else {
                    val immutableMapBuilder = ImmutableMap.builder<BlockPos, BlockState>()

                    BlockPos.getAllInBoxMutable(startPos, pos).map(BlockPos::toImmutable).forEach { blockPos ->
                        immutableMapBuilder.put(blockPos.subtract(startPos), world.getBlockState(blockPos))
                    }

                    structureMap[player] = immutableMapBuilder.build()
                    player.sendStatusMessage(StringTextComponent("Saved blocks from (${startPos.x} ${startPos.y} ${startPos.z}) to (${pos.x} ${pos.y} ${pos.z})"), true)
                    stack.startPos = null
                }
            } else {
                val face = ctx.face
                val offset = pos.offset(face)
                val pos2BlockState = (structureMap[player]?.entries ?: return ActionResultType.PASS)
                val immutableMapBuilder = ImmutableMap.builder<BlockPos, BlockState>()

                for (entry in pos2BlockState) {
                    val state = entry.value
                    val posOffset = entry.key.add(offset)
                    immutableMapBuilder.put(posOffset, world.getBlockState(posOffset))
                    world.setBlockState(posOffset, state)
                }

                undoMap[player] = immutableMapBuilder.build()
                player.sendStatusMessage(StringTextComponent("Cloned structure anchored at (${offset.x} ${offset.y} ${offset.z})"), true)
            }
        }

        return ActionResultType.SUCCESS
    }

    private var ItemStack.startPos by BlockPosNBTDelegate("StartPos")

    override fun getUseAction(stack: ItemStack) = UseAction.BOW

    override fun getUseDuration(stack: ItemStack) = 40

    override fun onItemUseFinish(stack: ItemStack, worldIn: World, entityLiving: LivingEntity): ItemStack {
        if (entityLiving is PlayerEntity) {
            undoMap[entityLiving]?.let { pos2StateMap ->
                for (entry in pos2StateMap) {
                    worldIn.setBlockState(entry.key, entry.value)
                }
                entityLiving.sendStatusMessage(StringTextComponent("Undo!"), true)
                undoMap[entityLiving] = null
            }
        }
        return stack
    }

    override fun onItemRightClick(worldIn: World, playerIn: PlayerEntity, handIn: Hand): ActionResult<ItemStack> {
        if (!worldIn.isRemote) {
            if (playerIn.isCrouching) {
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