package thedarkcolour.hardcoredungeons.block.combo

import net.minecraft.block.AbstractBlock
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.block.FlowerPotBlock
import net.minecraft.block.material.Material
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.RenderTypeLookup
import thedarkcolour.hardcoredungeons.block.base.BlockMaker
import thedarkcolour.hardcoredungeons.data.modelgen.block.BlockModelType
import thedarkcolour.hardcoredungeons.util.modLoc

class PottedPlantCombo(name: String, plantSupplier: () -> Block) : ICombo {
    val plant by BlockMaker.crossWithItem(name, plantSupplier)
    val potted by BlockMaker.registerModelled("potted_$name", BlockModelType.POTTED_PLANT) {
        FlowerPotBlock({ Blocks.FLOWER_POT as FlowerPotBlock}, ::plant, AbstractBlock.Properties.of(Material.DECORATION).instabreak().noOcclusion())
    }

    init {
        (Blocks.FLOWER_POT as FlowerPotBlock).addPlant(modLoc(name), ::potted)
    }

    override fun setRenderLayers() {
        RenderTypeLookup.setRenderLayer(plant,  RenderType.cutout())
        RenderTypeLookup.setRenderLayer(potted, RenderType.cutout())
    }
}