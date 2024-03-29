package thedarkcolour.hardcoredungeons.event

import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.block.BonemealableBlock
import net.minecraftforge.common.brewing.BrewingRecipeRegistry
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent
import net.minecraftforge.event.AttachCapabilitiesEvent
import net.minecraftforge.event.TickEvent
import net.minecraftforge.event.entity.player.PlayerEvent
import net.minecraftforge.event.level.BlockEvent
import net.minecraftforge.eventbus.api.Event
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent
import thedarkcolour.hardcoredungeons.block.misc.BonusFarmlandBlock
import thedarkcolour.hardcoredungeons.block.portal.HPortalBlock
import thedarkcolour.hardcoredungeons.capability.HPlayerData
import thedarkcolour.hardcoredungeons.capability.HPlayerDataProvider
import thedarkcolour.hardcoredungeons.capability.PlayerHelper
import thedarkcolour.hardcoredungeons.recipe.SyringeRecipe
import thedarkcolour.hardcoredungeons.registry.block.HBlockUtil
import thedarkcolour.kotlinforforge.forge.FORGE_BUS
import thedarkcolour.kotlinforforge.forge.MOD_BUS
import thedarkcolour.kotlinforforge.forge.addGenericListener

/**
 * Event handler class for common events. Client-only events are handled in [thedarkcolour.hardcoredungeons.client.ClientHandler]
 */
object EventHandler {
    fun registerEvents() {
        // Mod setup
        MOD_BUS.addListener(::commonSetup)
        MOD_BUS.addListener(::registerCapabilities)

        // Capability
        FORGE_BUS.addListener(::playerTick)
        FORGE_BUS.addListener(::playerCloned)
        // World events
        FORGE_BUS.addListener(::overrideCropGrowthBehaviour)
        FORGE_BUS.addGenericListener(::attachCapability)
        FORGE_BUS.addListener(HBlockUtil::blockModificationEvent)
    }

    private fun commonSetup(event: FMLCommonSetupEvent) {
        event.enqueueWork {
            BrewingRecipeRegistry.addRecipe(SyringeRecipe)
        }
    }

    private fun registerCapabilities(event: RegisterCapabilitiesEvent) {
        event.register(HPlayerData::class.java)
    }

    private fun attachCapability(event: AttachCapabilitiesEvent<Entity>) {
        val player = event.getObject()

        if (player is Player) {
            if (!player.getCapability(HPlayerData.PLAYER_DATA).isPresent) {
                event.addCapability(HPlayerData.ID, HPlayerDataProvider())
            }
        }
    }

    private fun playerCloned(event: PlayerEvent.Clone) {
        if (event.isWasDeath) {
            event.original.getCapability(HPlayerData.PLAYER_DATA).ifPresent { oldData ->
                event.entity.getCapability(HPlayerData.PLAYER_DATA).ifPresent { newData ->
                    newData.copyFrom(oldData)
                }
            }
        }
    }

    // Hook
    private fun overrideCropGrowthBehaviour(event: BlockEvent.CropGrowEvent.Pre) {
        val world = event.level
        val pos = event.pos

        // get the BonusFarmlandBlock instance
        val farmland = world.getBlockState(pos.below()).block
        if (farmland !is BonusFarmlandBlock) return

        val state = event.state
        val crop = state.block

        for (entry in farmland.boostMap.object2FloatEntrySet()) {
            if (entry.key == crop) {
                if (crop is BonemealableBlock && world is ServerLevel) {
                    if (world.random.nextFloat() < farmland.boostMap.getFloat(crop)) {
                        // override default logic
                        event.result = Event.Result.DENY
                    }
                }
                return
            }
        }
    }

    private fun playerTick(event: TickEvent.PlayerTickEvent) {
        val player = event.player

        // don't do anything during regular ticks
        if (PlayerHelper.getPortalCooldown(player) == 0)
            return

        val bounds = player.boundingBox
        val start = BlockPos.containing(bounds.minX + 0.001, bounds.minY + 0.001, bounds.minZ + 0.001)
        val end = BlockPos.containing(bounds.maxX - 0.001, bounds.maxY - 0.001, bounds.maxZ - 0.001)
        val cursor = BlockPos.MutableBlockPos()
        val level = player.level()

        if (level.hasChunksAt(start, end)) {
            loop@for (i in start.x..end.x) {
                for (j in start.y..end.y) {
                    for (k in start.z..end.z) {
                        cursor.set(i, j, k)

                        if (level.getBlockState(cursor).block is HPortalBlock) {
                            // set cooldown back to 30
                            PlayerHelper.setPortalCooldown(player, 30)
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