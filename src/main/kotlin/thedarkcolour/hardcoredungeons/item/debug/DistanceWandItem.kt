package thedarkcolour.hardcoredungeons.item.debug

import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.ItemUseContext
import net.minecraft.util.ActionResultType
import net.minecraft.util.text.StringTextComponent
import thedarkcolour.hardcoredungeons.util.BlockPosNBTDelegate
import kotlin.math.abs

class DistanceWandItem(properties: Properties) : Item(properties) {
    private var ItemStack.startPos by BlockPosNBTDelegate("StartPos")

    override fun useOn(ctx: ItemUseContext): ActionResultType {
        val world = ctx.level

        if (!world.isClientSide) {
            val stack = ctx.itemInHand
            val pos = ctx.clickedPos
            val player = ctx.player ?: return ActionResultType.PASS

            val startPos = stack.startPos

            if (startPos == null) {
                stack.startPos = pos
                player.displayClientMessage(StringTextComponent("Measurement starting position: (${pos.x} ${pos.y} ${pos.z})"), true)
            } else {
                val dX = if (pos.x == startPos.x) 0 else abs(pos.x - startPos.x) + 1
                val dY = if (pos.y == startPos.y) 0 else abs(pos.y - startPos.y) + 1
                val dZ = if (pos.z == startPos.z) 0 else abs(pos.z - startPos.z) + 1
                player.displayClientMessage(StringTextComponent("Distance (XYZ): $dX, $dY, $dZ"), false)
                stack.startPos = null
            }
        }

        return ActionResultType.SUCCESS
    }
}