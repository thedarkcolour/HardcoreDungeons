package thedarkcolour.hardcoredungeons.item.debug

import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.item.ItemUseContext
import net.minecraft.nbt.CompoundNBT
import net.minecraft.nbt.ListNBT
import net.minecraft.nbt.NBTUtil
import net.minecraft.util.ActionResultType
import net.minecraft.util.Hand
import net.minecraft.core.BlockPos
import net.minecraft.network.chat.Component
import net.minecraft.util.text.ITextComponent
import net.minecraft.util.text.StringTextComponent
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraftforge.common.util.Constants
import thedarkcolour.hardcoredungeons.block.structure.SootTrapControllerBlock
import thedarkcolour.hardcoredungeons.entity.overworld.deer.DeerEntity
import thedarkcolour.hardcoredungeons.tileentity.SootTrapControllerTileEntity

class GreenWandItem(properties: Properties) : Item(properties) {
    override fun interactLivingEntity(
        stack: ItemStack,
        player: Player,
        target: LivingEntity,
        hand: InteractionHand
    ): InteractionResult {
        if (!player.level.isClientSide) {
            if (target is DeerEntity) {
                player.displayClientMessage(Component.literal("DeerType: " + target.deerType), true)

                return InteractionResult.SUCCESS
            }
        }

        return InteractionResult.PASS
    }

    override fun useOn(ctx: ItemUseContext): ActionResultType {
        val level = ctx.level
        val pos = ctx.clickedPos
        val state = level.getBlockState(pos)
        val stack = ctx.itemInHand

        if (getMode(stack) == NONE) {
            if (state.block is SootTrapControllerBlock) {
                getPaths(stack.orCreateTag)?.clear()
                setMode(stack, SOOT_TRAP)
                setControlPos(stack, pos)

                ctx.player?.displayClientMessage(StringTextComponent("Create a path"), true)
            }
        } else if (getMode(stack) == SOOT_TRAP) {
            var paths = getPaths(stack.orCreateTag)

            if (pos == getControlPos(stack)) {
                val tile = level.getBlockEntity(pos)

                if (tile is SootTrapControllerTileEntity) {
                    if (paths != null) {
                        tile.paths.clear()

                        for (posNbt in paths) {
                            tile.paths.add(NBTUtil.readBlockPos(posNbt as CompoundNBT))
                        }

                        ctx.player?.displayClientMessage(StringTextComponent("Saved path"), true)

                        setMode(stack, NONE)
                    }
                }
            } else {
                val posNbt = NBTUtil.writeBlockPos(pos)

                if (paths == null) {
                    paths = ListNBT()
                    paths.add(posNbt)
                    stack.addTagElement("Paths", paths)
                } else if (paths.contains(posNbt)) {
                    paths.remove(posNbt)
                } else {
                    paths.add(posNbt)
                }
            }
        }

        return ActionResultType.PASS
    }

    // todo display wand mode in tooltip
    override fun getDescription(): ITextComponent {
        return super.getDescription()
    }

    companion object {
        const val NONE: Byte = 0
        const val SOOT_TRAP: Byte = 1

        fun getMode(stack: ItemStack): Byte {
            return stack.tag?.getByte("Mode") ?: 0
        }

        fun setMode(stack: ItemStack, mode: Byte) {
            stack.orCreateTag.putByte("Mode", mode)
        }

        // returns the position of the trap block currently being configured
        fun getControlPos(stack: ItemStack): BlockPos? {
            return NBTUtil.readBlockPos(stack.getTagElement("ControlPos") ?: return null)
        }

        fun setControlPos(stack: ItemStack, pos: BlockPos) {
            stack.addTagElement("ControlPos", NBTUtil.writeBlockPos(pos))
        }

        // Unmodifiable for rendering
        fun getPaths(tag: CompoundNBT): ListNBT? {
            if (tag.contains("Paths")) {
                val list = tag.getList("Paths", Constants.NBT.TAG_COMPOUND)
                if (list.isEmpty()) return null

                return list
            }

            return null
        }
        
        fun addPath(tag: CompoundNBT, pos: BlockPos) {
            getPaths(tag)?.add(NBTUtil.writeBlockPos(pos))
        }
    }
}