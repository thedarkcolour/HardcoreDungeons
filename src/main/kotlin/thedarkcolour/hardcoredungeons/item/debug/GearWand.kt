package thedarkcolour.hardcoredungeons.item.debug

import net.minecraft.entity.player.ServerPlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemUseContext
import net.minecraft.util.ActionResultType
import thedarkcolour.hardcoredungeons.network.NetworkHandler
import thedarkcolour.hardcoredungeons.registry.HBlocks

/**
 * Used to configure certain blocks for debug purposes.
 *
 * @author TheDarkColour
 */
class GearWand(properties: Properties) : Item(properties) {
    override fun onItemUse(ctx: ItemUseContext): ActionResultType {
        val playerIn = ctx.player ?: return ActionResultType.PASS
        val worldIn = ctx.world
        val pos = ctx.pos
        val state = worldIn.getBlockState(pos)
        val block = state.block

        if (block == HBlocks.DUNGEON_SPAWNER) {
            if (playerIn is ServerPlayerEntity) {
                NetworkHandler.sendGearWandMessage(Type.DUNGEON_SPAWNER, playerIn)
            }
        }

        return super.onItemUse(ctx)
    }

    enum class Type {
        DUNGEON_SPAWNER
    }
}