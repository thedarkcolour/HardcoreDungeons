package thedarkcolour.hardcoredungeons.client.screen

import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.gui.screen.inventory.ContainerScreen
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.util.ResourceLocation
import net.minecraft.util.text.ITextComponent
import thedarkcolour.hardcoredungeons.HardcoreDungeons
import thedarkcolour.hardcoredungeons.container.ExtractorContainer

class ExtractorScreen(
    screenContainer: ExtractorContainer, inv: PlayerInventory, titleIn: ITextComponent
) : ContainerScreen<ExtractorContainer>(screenContainer, inv, titleIn) {
    override fun drawGuiContainerBackgroundLayer(partialTicks: Float, mouseX: Int, mouseY: Int) {
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f)

        // bind the gui texture
        minecraft!!.textureManager.bindTexture(BACKGROUND)

        blit(guiLeft, guiTop, 0, 0, xSize, ySize)

        // TODO Gui animation
    }

    // so this WAS what i needed goddammit
    // ok go ahead

    // now to try to understand this
    override fun drawGuiContainerForegroundLayer(mouseX: Int, mouseY: Int) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY)

        val s = title.formattedText
        font.drawString(s, (xSize / 2 - font.getStringWidth(s) / 2).toFloat(), 6.0f, 4210752)
        font.drawString(playerInventory.displayName.formattedText, 8.0f, (ySize - 96 + 2).toFloat(), 4210752)

    }

    companion object {
        // gui texture location
        val BACKGROUND = ResourceLocation(HardcoreDungeons.ID, "textures/gui/container/extractor.png")
    }
}