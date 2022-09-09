package thedarkcolour.hardcoredungeons.capability

import net.minecraft.world.entity.player.Player
import net.minecraftforge.common.util.LazyOptional

object PlayerHelper {
    fun getPortalCooldown(player: Player): Int {
        return getCap(player).map {
            it.portalCooldown.toInt()
        }.orElseGet { 0 }
    }

    fun setPortalCooldown(player: Player, cooldown: Int) {
        getCap(player).ifPresent {
            it.portalCooldown = cooldown.toByte()
        }
    }

    private fun getCap(player: Player): LazyOptional<HPlayerData> {
        return player.getCapability(HPlayerData.PLAYER_DATA)
    }
}