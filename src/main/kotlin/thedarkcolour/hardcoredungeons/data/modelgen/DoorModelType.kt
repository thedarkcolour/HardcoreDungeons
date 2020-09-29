package thedarkcolour.hardcoredungeons.data.modelgen

import thedarkcolour.hardcoredungeons.block.decoration.DoorBlock
import thedarkcolour.hardcoredungeons.data.ModelGenerator

class DoorModelType : BlockModelType<DoorBlock>() {
    override fun process(block: DoorBlock, gen: ModelGenerator) {
        val b = "block/" + block.registryName!!.path

        gen.doorBlock(block, gen.modLoc(b + "_bottom"), gen.modLoc(b + "_top"))
    }
}