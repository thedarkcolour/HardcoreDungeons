package thedarkcolour.hardcoredungeons.client.screen

import com.mojang.blaze3d.matrix.MatrixStack
import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.gui.screen.inventory.ContainerScreen
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.util.text.ITextComponent
import thedarkcolour.hardcoredungeons.container.ExtractorContainer
import thedarkcolour.hardcoredungeons.util.modLoc

class ExtractorScreen(
    screenContainer: ExtractorContainer, inv: PlayerInventory, titleIn: ITextComponent
) : ContainerScreen<ExtractorContainer>(screenContainer, inv, titleIn) {
    override fun drawGuiContainerBackgroundLayer(matrixStack: MatrixStack, partialTicks: Float, x: Int, y: Int) {
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f)

        // bind the gui texture
        minecraft!!.textureManager.bindTexture(BACKGROUND)
        blit(matrixStack, guiLeft, guiTop, 0, 0, xSize, ySize)
    }

    // so this WAS what i needed goddammit
    // ok go ahead

    // now to try to understand this

    // signatures changed
    // idk what happened to formattedText
    // they messed with text rendering this update
    override fun drawGuiContainerForegroundLayer(matrixStack: MatrixStack, x: Int, y: Int) {
        super.drawGuiContainerForegroundLayer(matrixStack, x, y)

        val s = title
        font.func_243248_b(matrixStack, s, (xSize / 2 - font.getStringPropertyWidth(s) / 2).toFloat(), 6.0f, 4210752)
        font.func_243248_b(matrixStack, playerInventory.displayName, 8.0f, (ySize - 96 + 2).toFloat(), 4210752)
    }

    companion object {
        // gui texture location
        val BACKGROUND = modLoc("textures/gui/container/extractor.png")
    }
}