package thedarkcolour.hardcoredungeons.event

import net.minecraft.block.IGrowable
import net.minecraft.entity.Entity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.item.ItemUseContext
import net.minecraft.item.Items
import net.minecraft.util.Direction
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.BlockRayTraceResult
import net.minecraft.world.server.ServerWorld
import net.minecraftforge.event.AttachCapabilitiesEvent
import net.minecraftforge.event.TickEvent
import net.minecraftforge.event.entity.living.LivingDamageEvent
import net.minecraftforge.event.entity.player.PlayerInteractEvent
import net.minecraftforge.event.world.BlockEvent
import net.minecraftforge.eventbus.api.Event
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent
import thedarkcolour.hardcoredungeons.block.misc.BonusFarmlandBlock
import thedarkcolour.hardcoredungeons.block.portal.HPortalBlock
import thedarkcolour.hardcoredungeons.capability.HPlayer
import thedarkcolour.hardcoredungeons.capability.PlayerHelper
import thedarkcolour.hardcoredungeons.config.HConfig
import thedarkcolour.hardcoredungeons.feature.HConfiguredFeatures
import thedarkcolour.hardcoredungeons.registry.HBlocks
import thedarkcolour.hardcoredungeons.structure.HConfiguredStructures
import thedarkcolour.hardcoredungeons.surfacebuilder.HConfiguredSurfaceBuilders
import thedarkcolour.hardcoredungeons.tileentity.DungeonSpawnerTileEntity
import thedarkcolour.kotlinforforge.forge.FORGE_BUS
import thedarkcolour.kotlinforforge.forge.MOD_BUS

object EventHandler {
    fun registerEvents() {

        MOD_BUS.addListener(::commonSetup)

        FORGE_BUS.addListener(::overrideCropGrowthBehaviour)
        FORGE_BUS.addListener(::onBlockActivated)
        FORGE_BUS.addListener(::playerTick)
        FORGE_BUS.addGenericListener(::attachCapability)
        FORGE_BUS.addListener(::canBlockBreak)
    }

    // Hook
    private fun overrideCropGrowthBehaviour(event: BlockEvent.CropGrowEvent.Pre) {
        val world = event.world
        val pos = event.pos

        // get the BonusFarmlandBlock instance hopefully
        val farmland = world.getBlockState(pos.below()).block

        // do not override vanilla farmland for now
        if (farmland !is BonusFarmlandBlock) return

        val state = event.state
        val crop = state.block

        for (entry in farmland.boostMap.object2FloatEntrySet()) {
            if (entry.key == crop) {
                // we only have a few crops supported
                if (crop is IGrowable && world is ServerWorld) {

                    if (world.random.nextFloat() < farmland.boostMap.getFloat(crop)) {
                        // override default logic
                        event.result = Event.Result.DENY

                    }
                }
                return
            }
        }
    }

    private val GOLDEN_CARROTS_PLACER by lazy {
        BlockItem(HBlocks.GOLDEN_CARROTS, Item.Properties()).also {
            // don't put non-existent items in the block2item map
            // and add the correct middle mouse thing
            BlockItem.BY_BLOCK[HBlocks.GOLDEN_CARROTS] = Items.GOLDEN_CARROT
        }
    }

    // todo fix
    private fun onBlockActivated(event: PlayerInteractEvent.RightClickBlock) {
        if (!HConfig.goldenCarrotsCrop.value || event.face != Direction.UP) return

        val heldItem = event.itemStack

        if (heldItem.item != Items.GOLDEN_CARROT) return

        val world = event.world
        val pos = event.pos
        val up = pos.above()
        val cropState = HBlocks.GOLDEN_CARROTS.defaultBlockState()

        val player = event.player

        try {
            if (player.isCreative) {
                heldItem.grow(1)
            }
            GOLDEN_CARROTS_PLACER.useOn(ItemUseContext(player, event.hand, BlockRayTraceResult(null, event.face, pos, false)))
        } catch (e: Exception) {
            e.printStackTrace()
        }
/*
            if (world.isAirBlock(up) && cropState.isValidPosition(world, up)) {
                // cancel eating animation
                event.useItem = Event.Result.DENY

                world.setBlockState(up, HBlocks.GOLDEN_CARROTS.defaultState)

                val player = event.player

                if (player is ServerPlayerEntity) {
                    CriteriaTriggers.PLACED_BLOCK.trigger(player, up, heldItem)
                }

                heldItem.shrink(1)
                player.swingArm(event.hand)
            }
 */
    }

    private fun commonSetup(event: FMLCommonSetupEvent) {
        HPlayer.registerCapability()

        // World generation
        event.enqueueWork {
            HConfiguredFeatures.registerConfiguredFeatures()
            HConfiguredSurfaceBuilders.registerConfiguredSurfaceBuilders()
            HConfiguredStructures.registerConfiguredStructures()
        }
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

        if (player.level.hasChunksAt(start, end)) {
            loop@for (i in start.x..end.x) {
                for (j in start.y..end.y) {
                    for (k in start.z..end.z) {
                        cursor.set(i, j, k)

                        if (player.level.getBlockState(cursor).block is HPortalBlock) {
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

    fun canBlockBreak(event: BlockEvent.BreakEvent) {
        val state = event.state
        val worldIn = event.world
        val pos = event.pos
        val tile = worldIn.getBlockEntity(pos)

        if (tile is DungeonSpawnerTileEntity) {
            if (tile.remainingKills > 0) {
                // creative players bypass the kill counter
                event.isCanceled = !event.player.isCreative
            }
        }
    }

    fun calculateElementDamage(event: LivingDamageEvent) {

    }
}