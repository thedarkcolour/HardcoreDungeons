package thedarkcolour.hardcoredungeons.item.debug

import net.minecraft.network.chat.Component
import net.minecraft.world.InteractionResult
import net.minecraft.world.item.Item
import net.minecraft.world.item.context.UseOnContext
import thedarkcolour.hardcoredungeons.util.getStoredPos
import thedarkcolour.hardcoredungeons.util.setStoredPos
import kotlin.math.abs

class DistanceWandItem(properties: Properties) : Item(properties) {
    override fun useOn(ctx: UseOnContext): InteractionResult {
        val world = ctx.level

        if (!world.isClientSide) {
            val stack = ctx.itemInHand
            val pos = ctx.clickedPos
            val player = ctx.player ?: return InteractionResult.PASS

            val startPos = getStoredPos(stack)

            if (startPos == null) {
                setStoredPos(stack, pos)
                player.displayClientMessage(Component.literal("Measurement starting position: (${pos.x} ${pos.y} ${pos.z})"), true)
            } else {
                val dX = if (pos.x == startPos.x) 0 else abs(pos.x - startPos.x) + 1
                val dY = if (pos.y == startPos.y) 0 else abs(pos.y - startPos.y) + 1
                val dZ = if (pos.z == startPos.z) 0 else abs(pos.z - startPos.z) + 1

                player.displayClientMessage(Component.literal("Distance (XYZ): $dX, $dY, $dZ"), false)
                setStoredPos(stack, null)
            }
        }

        return InteractionResult.SUCCESS
    }
}