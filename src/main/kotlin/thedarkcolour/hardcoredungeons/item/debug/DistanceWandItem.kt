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

    override fun onItemUse(ctx: ItemUseContext): ActionResultType {
        val world = ctx.world

        if (!world.isRemote) {
            val stack = ctx.item
            val pos = ctx.pos
            val player = ctx.player ?: return ActionResultType.PASS

            val startPos = stack.startPos

            if (startPos == null) {
                stack.startPos = pos
                player.sendStatusMessage(StringTextComponent("Measurement starting position: (${pos.x} ${pos.y} ${pos.z})"), true)
            } else {
                val dX = if (pos.x == startPos.x) 0 else abs(pos.x - startPos.x) + 1
                val dY = if (pos.y == startPos.y) 0 else abs(pos.y - startPos.y) + 1
                val dZ = if (pos.z == startPos.z) 0 else abs(pos.z - startPos.z) + 1
                player.sendStatusMessage(
                    StringTextComponent("Distance (XYZ): $dX, $dY, $dZ"),
                    false
                )
                stack.startPos = null
            }
        }

        return ActionResultType.SUCCESS
    }
}