package thedarkcolour.hardcoredungeons.client.ter

import com.mojang.blaze3d.matrix.MatrixStack
import net.minecraft.client.renderer.IRenderTypeBuffer
import net.minecraft.client.renderer.tileentity.TileEntityRenderer
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher
import net.minecraft.tileentity.TileEntity

abstract class AbstractChestTileEntityRenderer<A>(rendererDispatcherIn: TileEntityRendererDispatcher) :
    TileEntityRenderer<A>(rendererDispatcherIn) where A : HChest, A : TileEntity {

    override fun render(tile: A, partialTicks: Float, stack: MatrixStack, buffer: IRenderTypeBuffer, light: Int, overlay: Int) {
        TODO("not implemented")
    }
}