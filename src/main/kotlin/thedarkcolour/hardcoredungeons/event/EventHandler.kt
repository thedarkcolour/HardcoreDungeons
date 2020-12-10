package thedarkcolour.hardcoredungeons.event

import net.minecraft.entity.Entity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.util.math.BlockPos
import net.minecraftforge.event.AttachCapabilitiesEvent
import net.minecraftforge.event.TickEvent
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent
import thedarkcolour.hardcoredungeons.block.misc.BonusFarmlandBlock
import thedarkcolour.hardcoredungeons.block.plant.misc.GoldenCarrotsBlock
import thedarkcolour.hardcoredungeons.block.portal.PortalBlock
import thedarkcolour.hardcoredungeons.capability.HPlayer
import thedarkcolour.hardcoredungeons.capability.PlayerHelper
import thedarkcolour.kotlinforforge.forge.FORGE_BUS
import thedarkcolour.kotlinforforge.forge.MOD_BUS

object EventHandler {
    fun registerEvents() {

        MOD_BUS.addListener(::commonSetup)

        // todo move methods to here
        FORGE_BUS.addListener(BonusFarmlandBlock::overrideCropGrowthBehaviour)
        FORGE_BUS.addListener(GoldenCarrotsBlock::onBlockActivated)
        FORGE_BUS.addListener(::playerTick)
        FORGE_BUS.addGenericListener(::attachCapability)
    }

    private fun commonSetup(event: FMLCommonSetupEvent) {
        HPlayer.registerCapability()
    }

    private fun attachCapability(event: AttachCapabilitiesEvent<Entity>) {
        val obj = event.getObject()

        if (obj is PlayerEntity) {
            event.addCapability(HPlayer.ID, HPlayer.Provider())
        }
    }

    private fun playerTick(event: TickEvent.PlayerTickEvent) {
        val player = event.player

        // exit fast
        if (PlayerHelper.getPortalCooldown(player) == 0) return

        val bounds = player.boundingBox
        val start = BlockPos(bounds.minX + 0.001, bounds.minY + 0.001, bounds.minZ + 0.001)
        val end = BlockPos(bounds.maxX - 0.001, bounds.maxY - 0.001, bounds.maxZ - 0.001)
        val cursor = BlockPos.Mutable()

        if (player.world.isAreaLoaded(start, end)) {
            loop@for (i in start.x..end.x) {
                for (j in start.y..end.y) {
                    for (k in start.z..end.z) {
                        cursor.setPos(i, j, k)

                        if (player.world.getBlockState(cursor).block is PortalBlock) {
                            return
                        }
                    }
                }
            }
        }

        val cooldown = PlayerHelper.getPortalCooldown(player)

        if (cooldown > 0) {
            PlayerHelper.setPortalCooldown(player, (cooldown - 1).coerceAtLeast(0))
        }
    }
}