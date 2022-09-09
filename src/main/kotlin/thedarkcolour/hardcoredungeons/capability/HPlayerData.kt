package thedarkcolour.hardcoredungeons.capability

import net.minecraft.world.entity.player.Player
import net.minecraftforge.common.capabilities.CapabilityManager
import net.minecraftforge.common.capabilities.CapabilityToken
import thedarkcolour.hardcoredungeons.util.modLoc

class HPlayerData(val owner: Player) {
    var portalCooldown: Byte = 0

    fun copyFrom(oldData: HPlayerData) {
        this.portalCooldown = oldData.portalCooldown
    }

    companion object {
        val PLAYER_DATA = CapabilityManager.get(object : CapabilityToken<HPlayerData>() {})
        val ID = modLoc("player_data")
    }
}