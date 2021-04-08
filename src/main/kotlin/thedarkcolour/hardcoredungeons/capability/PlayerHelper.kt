package thedarkcolour.hardcoredungeons.capability

import net.minecraft.entity.player.PlayerEntity
import net.minecraftforge.common.util.LazyOptional

object PlayerHelper {
    fun getPortalCooldown(player: PlayerEntity): Int {
        return getCap(player).map {
            it.portalCooldown.toInt()
        }.orElseGet { 0 }
    }

    fun setPortalCooldown(player: PlayerEntity, cooldown: Int) {
        getCap(player).ifPresent {
            it.portalCooldown = cooldown.toByte()
        }
    }

    private fun getCap(player: PlayerEntity): LazyOptional<HPlayer> {
        return player.getCapability(HPlayer.get())
    }
}