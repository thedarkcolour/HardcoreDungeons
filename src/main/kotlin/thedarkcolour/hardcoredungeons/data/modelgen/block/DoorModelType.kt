package thedarkcolour.hardcoredungeons.data.modelgen.block

import net.minecraft.world.level.block.Block
import net.minecraft.block.DoorBlock
import thedarkcolour.hardcoredungeons.data.ModelGenerator

class DoorModelType : BlockModelType<DoorBlock>() {
    override fun process(block: DoorBlock, appearance: Block, gen: ModelGenerator) {
        val b = "block/" + block.registryName!!.path

        gen.doorBlock(block, gen.modLoc(b + "_bottom"), gen.modLoc(b + "_top"))
    }
}