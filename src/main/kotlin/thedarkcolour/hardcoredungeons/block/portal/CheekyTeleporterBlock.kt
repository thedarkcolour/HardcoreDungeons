package thedarkcolour.hardcoredungeons.block.portal

import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.screen.ConfirmOpenLinkScreen
import net.minecraft.entity.Entity
import net.minecraft.util.Util
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.shapes.IBooleanFunction
import net.minecraft.util.math.shapes.ISelectionContext
import net.minecraft.util.math.shapes.VoxelShape
import net.minecraft.util.math.shapes.VoxelShapes
import net.minecraft.world.IBlockReader
import net.minecraft.world.World
import thedarkcolour.hardcoredungeons.block.properties.HProperties
import thedarkcolour.kotlinforforge.forge.lazySidedDelegate
import java.util.concurrent.Executors

class CheekyTeleporterBlock(properties: HProperties) : Block(properties.build()) {
    private fun canTeleport(pos: BlockPos, entityIn: Entity): Boolean {
        return !entityIn.isPassenger && !entityIn.isBeingRidden && entityIn.isNonBoss && VoxelShapes.compare(
            VoxelShapes.create(entityIn.boundingBox.offset(-pos.x.toDouble(), -pos.y.toDouble(), -pos.z.toDouble())), TP_CHECK, IBooleanFunction.AND)
    }

    override fun onEntityCollision(state: BlockState, worldIn: World, pos: BlockPos, entityIn: Entity) {
        if (canTeleport(pos, entityIn)) {
            if (worldIn.isRemote) {
                val previousScreen = Minecraft.getInstance().currentScreen

                APPLY_FORCE?.execute {
                    Thread.sleep(100)

                    if (canTeleport(pos, entityIn)) {
                        Minecraft.getInstance().displayGuiScreen(ConfirmOpenLinkScreen({ open ->
                            if (open) {
                                Util.getOSType().openURI("https://www.roblox.com/games/2572204670/MUGEN")
                            }
                            synchronized(Minecraft.getInstance()) {
                                Minecraft.getInstance().displayGuiScreen(previousScreen)
                            }
                        }, "https://www.roblox.com/games/2572204670/MUGEN", false))
                    }
                }
            } else {
                if (entityIn.posZ > pos.z + 0.5) {
                    entityIn.addVelocity(0.0, 0.0, 0.03)
                } else {
                    entityIn.addVelocity(0.0, 0.0, -0.03)
                }
                entityIn.velocityChanged = true
            }
        }
    }

    override fun getShape(
        state: BlockState,
        worldIn: IBlockReader,
        pos: BlockPos,
        context: ISelectionContext
    ): VoxelShape {
        return SHAPE
    }

    companion object {
        // portal, side, side
        private val SHAPE = VoxelShapes.or(
            makeCuboidShape(2.0, 0.0, 1.5, 14.0, 16.0, 14.5),
            makeCuboidShape(14.0, 0.0, 0.0, 16.0, 16.0, 16.0),
            makeCuboidShape(0.0, 0.0, 0.0, 2.0, 16.0, 16.0),
        )
        private val TP_CHECK = makeCuboidShape(1.0, 0.0, 1.4, 15.0, 16.0, 14.6)
        // from bottom to top
        val TOP_SHAPE = VoxelShapes.or(
            makeCuboidShape(0.0, 0.0, 0.0, 16.0, 2.0, 16.0),
            makeCuboidShape(2.0, 2.0, 0.0, 14.0, 4.0, 16.0),
            makeCuboidShape(4.0, 4.0, 0.0, 12.0, 6.0, 16.0),
            makeCuboidShape(6.0, 6.0, 0.0, 10.0, 8.0, 16.0),
        )
        val APPLY_FORCE by lazySidedDelegate(clientValue = { Executors.newFixedThreadPool(1) }, serverValue = { null })
    }
}