package thedarkcolour.hardcoredungeons.data.modelgen.block

import net.minecraft.world.level.block.*
import net.minecraftforge.client.model.generators.ConfiguredModel
import thedarkcolour.hardcoredungeons.data.ModelGenerator
import thedarkcolour.hardcoredungeons.util.registryName

// Six sided cube
class CubeAllModelType : BlockModelType<Block>() {
    override fun process(block: Block, appearance: Block, gen: ModelGenerator) {
        // block state and model
        val cubeAll = gen.models().cubeAll(gen.name(block), gen.blockTexture(appearance))
        gen.getVariantBuilder(block).partialState().addModels(ConfiguredModel(cubeAll))

        // item model
        gen.blockItemModel(block)
    }
}

// Cross model, like flowers and tall grass
class CrossModelType : BlockModelType<Block>() {
    override fun process(block: Block, appearance: Block, gen: ModelGenerator) {
        gen.simpleBlock(block, gen.models().cross(gen.name(block), gen.blockTexture(block)))
    }
}

// Slabs
class SlabModelType : BlockModelType<SlabBlock>() {
    override fun process(block: SlabBlock, appearance: Block, gen: ModelGenerator) {
        val loc = gen.modLoc("block/" + appearance.registryName!!.path)
        gen.slabBlock(block, loc, loc)
    }
}

// Stairs
class StairsModelType : BlockModelType<Block>() {
    override fun process(block: Block, appearance: Block, gen: ModelGenerator) {
        if (block !is StairBlock) throw IllegalArgumentException("stop")

        gen.stairsBlock(block, gen.modLoc("block/" + appearance.registryName!!.path))
        gen.blockItemModel(block)
    }
}

// Two high doors
class DoorModelType : BlockModelType<DoorBlock>() {
    override fun process(block: DoorBlock, appearance: Block, gen: ModelGenerator) {
        val b = "block/" + block.registryName!!.path

        gen.doorBlock(block, gen.modLoc(b + "_bottom"), gen.modLoc(b + "_top"))
    }
}

// Trapdoors
class TrapDoorModelType : BlockModelType<TrapDoorBlock>() {
    override fun process(block: TrapDoorBlock, appearance: Block, gen: ModelGenerator) {
        gen.trapdoorBlock(block, gen.modLoc("block/" + block.registryName!!.path), true)
    }
}

// Vases
class VaseModelType : BlockModelType<Block>() {
    override fun process(block: Block, appearance: Block, gen: ModelGenerator) {
        val texture = gen.textureLoc(block)

        gen.singleModel(block)
            .parent(gen.modModel("block/template_vase"))
            .texture("vase", texture)
    }
}

// The soot block, could be generalized to farmland perhaps?
class SootModelType : BlockModelType<Block>() {
    override fun process(block: Block, appearance: Block, gen: ModelGenerator) {
        val texture = gen.textureLoc(appearance) // use appearance

        gen.singleModel(block)
            .parent(gen.mcBlock("template_farmland"))
            .texture("particle", texture)
            .texture("dirt", texture)
            .texture("top", texture)
    }
}
