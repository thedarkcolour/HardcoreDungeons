package thedarkcolour.hardcoredungeons.client.renderer

import com.mojang.blaze3d.matrix.MatrixStack
import com.mojang.blaze3d.vertex.IVertexBuilder
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.RenderType
import net.minecraft.nbt.CompoundNBT
import net.minecraft.nbt.NBTUtil
import net.minecraft.core.BlockPos
import net.minecraft.util.math.BlockRayTraceResult
import net.minecraft.util.math.shapes.VoxelShapes
import net.minecraftforge.client.event.DrawHighlightEvent
import net.minecraftforge.client.event.RenderWorldLastEvent
import thedarkcolour.hardcoredungeons.item.debug.GreenWandItem
import thedarkcolour.hardcoredungeons.tileentity.SootTrapControllerTileEntity
import java.awt.Color
import kotlin.math.sin

object GreenWandRender {
    val mc = Minecraft.getInstance()

    fun renderTick(event: RenderWorldLastEvent) {
        val item = mc.player?.mainHandItem ?: return

        if (item.item is GreenWandItem) {
            val pos = mc.gameRenderer.mainCamera.position
            val partialTicks = event.partialTicks
            val stack = event.matrixStack
            val builder = mc.renderBuffers().bufferSource().getBuffer(RenderType.lines())
            val x = pos.x
            val y = pos.y
            val z = pos.z

            drawRainbowOutline(partialTicks, stack, builder, x, y, z, GreenWandItem.getControlPos(item) ?: return)

            for (pos in (GreenWandItem.getPaths(item.orCreateTag) ?: return) as List<CompoundNBT>) {
                drawColoredOutline(stack, builder, x, y, z, NBTUtil.readBlockPos(pos), 20, 230, 20)
            }
        }
    }

    fun renderOutline(event: DrawHighlightEvent) {
        val item = mc.player?.mainHandItem ?: return

        if (item.item is GreenWandItem) {
            if (mc.hitResult is BlockRayTraceResult) {
                val controllerPos = (mc.hitResult as BlockRayTraceResult).blockPos
                val tile = mc.level?.getBlockEntity(controllerPos)

                if (tile is SootTrapControllerTileEntity) {
                    val pos = mc.gameRenderer.mainCamera.position
                    val partialTicks = event.partialTicks
                    val stack = event.matrix
                    val builder = mc.renderBuffers().bufferSource().getBuffer(RenderType.lines())
                    val x = pos.x
                    val y = pos.y
                    val z = pos.z

                    event.isCanceled = true

                    drawRainbowOutline(partialTicks, stack, builder, x, y, z, controllerPos)

                    for (pos in tile.paths) {
                        drawRainbowOutline(partialTicks, stack, builder, x, y, z, pos)
                    }
                }
            }
        }
    }

    fun getRainbowColor(time: Long, partialTicks: Float): Color {
        return Color.getHSBColor((180 * sin((time + partialTicks) / 16.0f) - 180) / 360.0f, 1.0f, 0.5f)
    }

    private fun drawRainbowOutline(partialTicks: Float, stack: MatrixStack, builder: IVertexBuilder, x: Double, y: Double, z: Double, pos: BlockPos) {
        val color = getRainbowColor(mc.level?.gameTime ?: return, partialTicks)
        val r = color.red
        val g = color.green
        val b = color.blue

        drawColoredOutline(stack, builder, x, y, z, pos, r, g, b)
    }

    private fun drawColoredOutline(stack: MatrixStack, builder: IVertexBuilder, x: Double, y: Double, z: Double, pos: BlockPos, r: Int, g: Int, b: Int) {
        val shape = VoxelShapes.block()
        val offsetX = pos.x - x
        val offsetY = pos.y - y
        val offsetZ = pos.z - z
        val a = 178

        val matrix = stack.last().pose()

        shape.forAllEdges { x1, y1, z1, x2, y2, z2 ->
            builder.vertex(matrix, (x1 + offsetX).toFloat(), (y1 + offsetY).toFloat(), (z1 + offsetZ).toFloat()).color(r, g, b, a).endVertex()
            builder.vertex(matrix, (x2 + offsetX).toFloat(), (y2 + offsetY).toFloat(), (z2 + offsetZ).toFloat()).color(r, g, b, a).endVertex()
        }
    }
}