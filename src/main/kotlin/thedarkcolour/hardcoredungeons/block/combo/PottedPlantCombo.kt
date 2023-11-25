package thedarkcolour.hardcoredungeons.block.combo

import net.minecraft.client.renderer.ItemBlockRenderTypes
import net.minecraft.client.renderer.RenderType
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.FlowerPotBlock
import net.minecraft.world.level.block.state.BlockBehaviour
import thedarkcolour.hardcoredungeons.block.base.BlockMaker
import thedarkcolour.hardcoredungeons.data.modelgen.block.BlockModelType
import thedarkcolour.hardcoredungeons.util.modLoc

class PottedPlantCombo(name: String, pot: CustomFlowerPot? = null, plantSupplier: () -> Block) : BlockCombo() {
    val plant by BlockMaker.crossWithItem(name, plantSupplier)
    val potted by BlockMaker.registerModelled("potted_$name", BlockModelType.POTTED_PLANT, supplier = {
        pot?.makeFlowerPot(::plant) ?: FlowerPotBlock({ Blocks.FLOWER_POT as FlowerPotBlock}, ::plant, BlockBehaviour.Properties.of().instabreak().noOcclusion())
    })

    init {
        (Blocks.FLOWER_POT as FlowerPotBlock).addPlant(modLoc(name), ::potted)
    }

    override fun setRenderLayers() {
        ItemBlockRenderTypes.setRenderLayer(plant,  RenderType.cutout())
        ItemBlockRenderTypes.setRenderLayer(potted, RenderType.cutout())
    }
}