package thedarkcolour.hardcoredungeons.block.structure.castleton

/*
import net.minecraft.entity.EntityType
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.ItemUseContext
import net.minecraft.util.ActionResultType
import net.minecraft.util.Direction
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import thedarkcolour.hardcoredungeons.entity.castleton.clearitemframe.ClearItemFrameEntity

class ClearItemFrameItem(properties: Properties) : Item(properties) {
    override fun onItemUse(context: ItemUseContext): ActionResultType {
        val pos = context.pos
        val direction = context.face
        val offset = pos.offset(direction)
        val playerIn = context.player
        val stack = context.item
        return if (playerIn != null && !canPlace(playerIn, direction, stack, offset)) {
            ActionResultType.FAIL
        } else {
            val world = context.world
            val frame = ClearItemFrameEntity(world, offset, direction)
            val tag = stack.tag

            if (tag != null) {
                EntityType.applyItemNBT(world, playerIn, frame, tag)
            }

            if (frame.onValidSurface()) {
                if (!world.isRemote) {
                    frame.playPlaceSound()
                    world.addEntity(frame)
                }
                stack.shrink(1)
                ActionResultType.SUCCESS
            } else {
                ActionResultType.CONSUME
            }
        }
    }

    private fun canPlace(playerIn: PlayerEntity, direction: Direction, stack: ItemStack, pos: BlockPos): Boolean {
        return !World.isOutsideBuildHeight(pos) && playerIn.canPlayerEdit(pos, direction, stack)
    }
}*/