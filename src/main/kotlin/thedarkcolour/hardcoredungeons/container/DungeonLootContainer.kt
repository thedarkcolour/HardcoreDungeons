package thedarkcolour.hardcoredungeons.container

import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import thedarkcolour.hardcoredungeons.registry.HContainers
import thedarkcolour.hardcoredungeons.tags.HBlockTags

class DungeonLootContainer(
    id: Int,
    playerIn: PlayerInventory,
    private val worldPos: WorldPos = WorldPos.DUMMY,
) : HContainer(HContainers.DUNGEON_LOOT, id, playerIn.player) {

    override fun canInteractWith(playerIn: PlayerEntity): Boolean {
        return isTagInRange(worldPos, playerIn, HBlockTags.DUNGEON_LOOT_CONTAINERS)
    }
}