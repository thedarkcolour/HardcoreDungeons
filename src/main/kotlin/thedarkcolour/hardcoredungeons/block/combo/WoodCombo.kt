package thedarkcolour.hardcoredungeons.block.combo

import net.minecraft.block.Block
import net.minecraft.block.PressurePlateBlock
import net.minecraft.block.WoodType
import net.minecraft.block.material.Material
import net.minecraft.block.material.MaterialColor
import net.minecraft.block.trees.Tree
import net.minecraft.data.IFinishedRecipe
import net.minecraft.item.AxeItem
import net.minecraft.item.Item
import net.minecraft.state.properties.BlockStateProperties
import net.minecraft.tags.BlockTags
import net.minecraft.tags.ITag
import net.minecraft.util.Direction
import net.minecraftforge.common.Tags
import thedarkcolour.hardcoredungeons.HardcoreDungeons
import thedarkcolour.hardcoredungeons.block.base.BlockMaker
import thedarkcolour.hardcoredungeons.block.base.ItemMaker
import thedarkcolour.hardcoredungeons.block.base.properties.HProperties
import thedarkcolour.hardcoredungeons.data.BlockTagGenerator
import thedarkcolour.hardcoredungeons.data.ItemTagGenerator
import thedarkcolour.hardcoredungeons.data.RecipeGenerator
import thedarkcolour.hardcoredungeons.data.RecipeGenerator.Companion.shaped
import thedarkcolour.hardcoredungeons.data.RecipeGenerator.Companion.shapeless
import thedarkcolour.hardcoredungeons.data.RecipeGenerator.Companion.slab
import thedarkcolour.hardcoredungeons.data.RecipeGenerator.Companion.stairs
import thedarkcolour.hardcoredungeons.data.RecipeGenerator.Companion.twoByTwo
import thedarkcolour.hardcoredungeons.data.modelgen.item.ItemModelType
import thedarkcolour.hardcoredungeons.registry.HBlocks
import thedarkcolour.hardcoredungeons.tags.HItemTags
import java.util.function.Consumer

/**
 * Combo for new wood types.
 *
 * @param wood Name for wood like "oak" or "lumlight"
 * @param applyProperties Base properties
 */
class WoodCombo(
    wood: String,
    topCol: MaterialColor,
    barkCol: MaterialColor,
    tree: Tree,
    val blockTag: ITag.INamedTag<Block>,
    val itemTag: ITag.INamedTag<Item>,
    applyProperties: (HProperties) -> Unit
) : ICombo {
    // Wood Type
    val type: WoodType = WoodType.create(HardcoreDungeons.ID + ":" + wood)

    // Planks, Slab, Stairs
    val planks by BlockMaker.cubeAllWithItem(wood + "_planks", BlockMaker.props(Material.WOOD, topCol, applyProperties))
    val slab by BlockMaker.slabWithItem(wood + "_slab", ::planks, BlockMaker.props(Material.WOOD, topCol, applyProperties))
    val stairs by BlockMaker.stairsWithItem(wood + "_stairs", ::planks, BlockMaker.props(Material.WOOD, topCol, applyProperties))

    // Log, Stripped Log, Wood, Stripped Wood, Leaves
    val log by BlockMaker.rotatedPillarWithItem(wood + "_log", HProperties.of(Material.WOOD) { state -> if (state.getValue(BlockStateProperties.AXIS) == Direction.Axis.Y) topCol else barkCol }.also(applyProperties))
    val wood by BlockMaker.rotatedPillarWithItem(wood + "_wood", BlockMaker.props(Material.WOOD, barkCol, applyProperties))
    val strippedLog by BlockMaker.rotatedPillarWithItem("stripped_" + wood + "_log", BlockMaker.props(Material.WOOD, topCol, applyProperties))
    val strippedWood by BlockMaker.rotatedPillarWithItem("stripped_" + wood + "_wood", BlockMaker.props(Material.WOOD, topCol, applyProperties))
    val leaves by BlockMaker.leavesBlock(wood + "_leaves")

    // Fence, Fence Gate, Pressure Plate, Button, Trapdoor, Door
    val fence by BlockMaker.registerFence(wood + "_fence", BlockMaker.props(Material.WOOD, topCol, applyProperties), ::planks)
    val fenceGate by BlockMaker.registerFenceGate(wood + "_fence_gate", BlockMaker.props(Material.WOOD, topCol, applyProperties), ::planks)
    val pressurePlate by BlockMaker.registerPressurePlate(wood + "_pressure_plate", PressurePlateBlock.Sensitivity.EVERYTHING, BlockMaker.props(Material.WOOD, topCol, applyProperties), ::planks)
    val button by BlockMaker.registerWoodButton(wood + "_button", BlockMaker.props(Material.WOOD, topCol, applyProperties))
    val trapdoor by BlockMaker.registerTrapdoor(wood + "_trapdoor", BlockMaker.props(Material.WOOD, topCol, applyProperties))
    val door by BlockMaker.registerDoor(wood + "_door", BlockMaker.props(Material.WOOD, topCol, applyProperties))

    val sapling = BlockMaker.saplingCombo(wood + "_sapling", tree)

    // Sign
    val sign = SignCombo(wood, type, topCol, applyProperties)

    init {
        HBlocks.onceRegistered {
            registerAxeInteraction(this.log,  this.strippedLog )
            registerAxeInteraction(this.wood, this.strippedWood)
        }

        // todo get rid of the rest of these
        ItemMaker.blockItem(wood + "_leaves", block = ::leaves)

        ItemMaker.blockItem(wood + "_fence", type = ItemModelType.WALL_FENCE_ITEM, block = ::fence)
        ItemMaker.blockItem(wood + "_fence_gate", block = ::fenceGate)
        ItemMaker.blockItem(wood + "_pressure_plate", block = ::pressurePlate)
        ItemMaker.blockItem(wood + "_button", block = ::button)
        ItemMaker.blockItem(wood + "_trapdoor", block = ::trapdoor)
        ItemMaker.simpleBlockItem(wood + "_door", block = ::door)
    }

    private fun registerAxeInteraction(target: Block, result: Block) {
        if (AxeItem.STRIPABLES !is HashMap<*, *>) {
            AxeItem.STRIPABLES = HashMap(AxeItem.STRIPABLES)
        }

        AxeItem.STRIPABLES[target] = result
    }

    override fun addTags(gen: BlockTagGenerator) {
        gen.tag(blockTag).add(log, strippedLog, wood, strippedWood)
        gen.tag(BlockTags.PLANKS).add(planks)
        gen.tag(BlockTags.WOODEN_BUTTONS).add(button)
        gen.tag(BlockTags.WOODEN_DOORS).add(door)
        gen.tag(BlockTags.WOODEN_STAIRS).add(stairs)
        gen.tag(BlockTags.WOODEN_SLABS).add(slab)
        gen.tag(BlockTags.WOODEN_FENCES).add(fence)
        gen.tag(BlockTags.WOODEN_PRESSURE_PLATES).add(pressurePlate)
        gen.tag(BlockTags.WOODEN_TRAPDOORS).add(trapdoor)
        gen.tag(BlockTags.LOGS_THAT_BURN).addTag(blockTag)
        gen.tag(BlockTags.LEAVES).add(leaves)
        gen.tag(BlockTags.FENCE_GATES).add(fenceGate)
        gen.tag(BlockTags.SAPLINGS).add(sapling.plant)

        sign.addTags(gen)
    }

    override fun addTags(gen: ItemTagGenerator) {
        gen.copy(blockTag, itemTag)
    }

    override fun addRecipes(consumer: Consumer<IFinishedRecipe>) {
        // todo more recipes
        consumer.shapeless(planks, 4) { builder ->
            builder.requires(itemTag)
            builder.group("planks")
            builder.unlockedBy("has_log", RecipeGenerator.has(HItemTags.LUMLIGHT_LOGS))
        }
        consumer.slab(slab, planks, "wooden_slab")
        consumer.stairs(stairs, planks, "wooden_stairs")
        consumer.shaped(fence, 3) { builder ->
            builder.pattern("xix")
            builder.pattern("xix")
            builder.group("wooden_fence")
            builder.define('x', planks)
            builder.define('i', Tags.Items.RODS_WOODEN)
            builder.unlockedBy("has_planks", RecipeGenerator.has(planks))
        }
        consumer.shaped(fenceGate, 3) { builder ->
            builder.pattern("ixi")
            builder.pattern("ixi")
            builder.group("wooden_fence_gate")
            builder.define('x', planks)
            builder.define('i', Tags.Items.RODS_WOODEN)
            builder.unlockedBy("has_planks", RecipeGenerator.has(planks))
        }
        consumer.shaped(sign.item, 3) { builder ->
            builder.pattern("xxx")
            builder.pattern("xxx")
            builder.pattern(" i ")
            builder.group("sign")
            builder.define('x', planks)
            builder.define('i', Tags.Items.RODS_WOODEN)
            builder.unlockedBy("has_planks", RecipeGenerator.has(planks))
        }
        consumer.twoByTwo(wood, 3, log, group = "bark", trigger = "has_log")
        consumer.twoByTwo(strippedWood, 3, strippedLog, group = "bark", trigger = "has_log")
        consumer.shaped(trapdoor, 2) { builder ->
            builder.define('x', planks)
            builder.pattern("xxx")
            builder.pattern("xxx")
            builder.group("wooden_trapdoor")
            builder.unlockedBy("has_planks", RecipeGenerator.has(planks))
        }
        consumer.shaped(door, 2) { builder ->
            builder.define('x', planks)
            builder.pattern("xx")
            builder.pattern("xx")
            builder.pattern("xx")
            builder.group("wooden_door")
            builder.unlockedBy("has_planks", RecipeGenerator.has(planks))
        }
        consumer.shaped(pressurePlate, 2) { builder ->
            builder.define('x', planks)
            builder.pattern("xx")
            builder.group("wooden_pressure_plate")
            builder.unlockedBy("has_planks", RecipeGenerator.has(planks))
        }
        consumer.shapeless(button, 1) { builder ->
            builder.group("wooden_button")
            builder.requires(planks)
            builder.unlockedBy("has_planks", RecipeGenerator.has(planks))
        }
    }

    override fun setRenderLayers() {
        sapling.setRenderLayers()
    }
}