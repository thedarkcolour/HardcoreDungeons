package thedarkcolour.hardcoredungeons.block.combo

import net.minecraft.core.Direction
import net.minecraft.data.recipes.FinishedRecipe
import net.minecraft.data.recipes.RecipeCategory
import net.minecraft.tags.BlockTags
import net.minecraft.tags.ItemTags
import net.minecraft.tags.TagKey
import net.minecraft.world.item.Item
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.PressurePlateBlock
import net.minecraft.world.level.block.grower.AbstractTreeGrower
import net.minecraft.world.level.block.state.properties.BlockSetType
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.block.state.properties.WoodType
import net.minecraft.world.level.material.MapColor
import net.minecraftforge.common.Tags
import thedarkcolour.hardcoredungeons.HardcoreDungeons
import thedarkcolour.hardcoredungeons.block.base.BlockMaker
import thedarkcolour.hardcoredungeons.block.base.properties.HProperties
import thedarkcolour.hardcoredungeons.data.BlockLoot
import thedarkcolour.hardcoredungeons.data.RecipeGenerator
import thedarkcolour.hardcoredungeons.data.RecipeGenerator.Companion.shaped
import thedarkcolour.hardcoredungeons.data.RecipeGenerator.Companion.shapeless
import thedarkcolour.hardcoredungeons.data.RecipeGenerator.Companion.slab
import thedarkcolour.hardcoredungeons.data.RecipeGenerator.Companion.stairs
import thedarkcolour.hardcoredungeons.data.RecipeGenerator.Companion.twoByTwo
import thedarkcolour.hardcoredungeons.data.modelgen.item.ItemModelType
import thedarkcolour.hardcoredungeons.data.slab
import thedarkcolour.hardcoredungeons.data.stairs
import thedarkcolour.hardcoredungeons.registry.block.HBlockUtil
import thedarkcolour.hardcoredungeons.registry.block.HBlocks
import thedarkcolour.hardcoredungeons.registry.items.ItemMaker
import thedarkcolour.hardcoredungeons.tags.HItemTags
import thedarkcolour.modkit.data.MKRecipeProvider
import java.util.function.Consumer

/**
 * Combo for new wood types.
 *
 * @param wood Name for wood like "oak" or "lumlight"
 * @param applyProperties Base properties
 */
class WoodCombo(
    wood: String,
    topCol: MapColor,
    barkCol: MapColor,
    tree: AbstractTreeGrower,
    private val logBlockTag: TagKey<Block>,
    private val logItemTag: TagKey<Item>,
    applyProperties: (HProperties) -> Unit
) : BlockCombo() {
    // Wood Type
    val blockSetType: BlockSetType
    val woodType: WoodType

    init {
        val name = HardcoreDungeons.ID + ":" + wood

        this.blockSetType = BlockSetType(name)
        this.woodType = WoodType(HardcoreDungeons.ID + ":" + wood, blockSetType).also(WoodType::register)

        HBlocks.onceRegistered {
            HBlockUtil.registerAxeStripInteraction(this.log, this.strippedLog)
            HBlockUtil.registerAxeStripInteraction(this.wood, this.strippedWood)
        }

        // todo get rid of the rest of these
        ItemMaker.blockItem(wood + "_leaves", block = ::leaves)

        ItemMaker.blockItem(wood + "_fence", type = ItemModelType.WALL_FENCE_ITEM, block = ::fence)
        ItemMaker.blockItem(wood + "_fence_gate", block = ::fenceGate)
        ItemMaker.blockItem(wood + "_pressure_plate", block = ::pressurePlate)
        ItemMaker.blockItem(wood + "_button", block = ::button)
        ItemMaker.blockItem(wood + "_trapdoor", type = ItemModelType.TRAPDOOR_ITEM, block = ::trapdoor)
        ItemMaker.simpleBlockItem(wood + "_door", block = ::door)
    }

    // Planks, Slab, Stairs
    val planks by BlockMaker.cubeAllWithItem(wood + "_planks", BlockMaker.props(topCol, applyProperties))
    val slab by BlockMaker.slabWithItem(wood + "_slab", ::planks, BlockMaker.props(topCol, applyProperties))
    val stairs by BlockMaker.stairsWithItem(wood + "_stairs", ::planks, BlockMaker.props(topCol, applyProperties))

    // Log, Stripped Log, Wood, Stripped Wood, Leaves
    val log by BlockMaker.rotatedPillarWithItem(wood + "_log", HProperties.of { state -> if (state.getValue(BlockStateProperties.AXIS) == Direction.Axis.Y) topCol else barkCol }.also(applyProperties))
    val wood by BlockMaker.rotatedPillarWithItem(wood + "_wood", BlockMaker.props(barkCol, applyProperties))
    val strippedLog by BlockMaker.rotatedPillarWithItem("stripped_" + wood + "_log", BlockMaker.props(
        topCol,
        applyProperties
    ))
    val strippedWood by BlockMaker.rotatedPillarWithItem("stripped_" + wood + "_wood", BlockMaker.props(
        topCol,
        applyProperties
    ))
    val leaves by BlockMaker.leavesBlock(wood + "_leaves")

    // Fence, Fence Gate, Pressure Plate, Button, Trapdoor, Door
    val fence by BlockMaker.registerFence(wood + "_fence", BlockMaker.props(topCol, applyProperties), ::planks)
    val fenceGate by BlockMaker.registerFenceGate(wood + "_fence_gate", BlockMaker.props(topCol, applyProperties), ::planks)
    val pressurePlate by BlockMaker.registerPressurePlate(wood + "_pressure_plate", PressurePlateBlock.Sensitivity.EVERYTHING, BlockMaker.props(
        topCol,
        applyProperties
    ), ::planks)
    val button by BlockMaker.registerWoodButton(wood + "_button", BlockMaker.props(topCol, applyProperties))
    val trapdoor by BlockMaker.registerTrapdoor(wood + "_trapdoor", BlockMaker.props(topCol, applyProperties))
    val door by BlockMaker.registerDoor(wood + "_door", BlockMaker.props(topCol, applyProperties))

    val sapling = BlockMaker.saplingCombo(wood + "_sapling", tree)

    // Sign
    val sign = SignCombo(wood, woodType, topCol, applyProperties)

    override fun addTags(tags: DataTags) {
        tags.block(logBlockTag, log, strippedLog, wood, strippedWood)
        tags.item(logItemTag, log, strippedLog, wood, strippedWood)

        tags.block(BlockTags.PLANKS, ItemTags.PLANKS, planks)
        tags.block(BlockTags.WOODEN_BUTTONS, ItemTags.WOODEN_BUTTONS, button)
        tags.block(BlockTags.WOODEN_DOORS, ItemTags.WOODEN_DOORS, door)
        tags.block(BlockTags.WOODEN_STAIRS, ItemTags.WOODEN_STAIRS, stairs)
        tags.block(BlockTags.WOODEN_SLABS, ItemTags.WOODEN_SLABS, slab)
        tags.block(BlockTags.WOODEN_FENCES, ItemTags.WOODEN_FENCES, fence)
        tags.block(BlockTags.WOODEN_PRESSURE_PLATES, ItemTags.WOODEN_PRESSURE_PLATES, pressurePlate)
        tags.block(BlockTags.WOODEN_TRAPDOORS, ItemTags.WOODEN_TRAPDOORS, trapdoor)
        tags.tag(BlockTags.LOGS_THAT_BURN, ItemTags.LOGS_THAT_BURN, logBlockTag, logItemTag)
        tags.block(BlockTags.LEAVES, ItemTags.LEAVES, leaves)
        tags.block(BlockTags.FENCES, Tags.Items.FENCES, fence)
        tags.block(BlockTags.FENCE_GATES, Tags.Items.FENCE_GATES, fenceGate)
        tags.block(BlockTags.SAPLINGS, ItemTags.SAPLINGS, sapling.plant)

        tags.axe(planks)
        tags.axe(button)
        tags.axe(door)
        tags.axe(stairs)
        tags.axe(slab)
        tags.axe(fence)
        tags.axe(pressurePlate)
        tags.axe(trapdoor)
        tags.axe(leaves)
        tags.axe(fence)
        tags.axe(fenceGate)
        tags.axe(sapling.plant)

        sign.addTags(tags)
    }

    override fun addLoot(gen: BlockLoot) {
        gen.addLeaves(leaves, sapling.plant)
    }

    override fun addRecipes(writer: Consumer<FinishedRecipe>, recipes: MKRecipeProvider) {
        // todo more recipes
        recipes.shapelessCrafting(RecipeCategory.BUILDING_BLOCKS, planks, 4, logItemTag)
        recipes.slab(slab, planks, "wooden_slab")
        recipes.stairs(stairs, planks, "wooden_stairs")
        recipes.shapedCrafting(RecipeCategory.MISC, fence, 3) { builder ->
            builder.pattern("xix")
            builder.pattern("xix")
            builder.group("wooden_fence")
            builder.define('x', planks)
            builder.define('i', Tags.Items.RODS_WOODEN)
        }
        recipes.shapedCrafting(RecipeCategory.REDSTONE, fenceGate, 3) { builder ->
            builder.pattern("ixi")
            builder.pattern("ixi")
            builder.group("wooden_fence_gate")
            builder.define('x', planks)
            builder.define('i', Tags.Items.RODS_WOODEN)
        }
        recipes.shapedCrafting(RecipeCategory.MISC, sign.item, 3) { builder ->
            builder.pattern("xxx")
            builder.pattern("xxx")
            builder.pattern(" i ")
            builder.group("sign")
            builder.define('x', planks)
            builder.define('i', Tags.Items.RODS_WOODEN)
        }
        recipes.grid2x2(RecipeCategory.BUILDING_BLOCKS, wood, 3, Ingredient.of(log))
        recipes.grid2x2(RecipeCategory.BUILDING_BLOCKS, strippedWood, 3, Ingredient.of(strippedLog))
        recipes.shapedCrafting(trapdoor, 2) { builder ->
            builder.define('x', planks)
            builder.pattern("xxx")
            builder.pattern("xxx")
            builder.group("wooden_trapdoor")
        }
        recipes.shapedCrafting(door, 3) { builder ->
            builder.define('x', planks)
            builder.pattern("xx")
            builder.pattern("xx")
            builder.pattern("xx")
            builder.group("wooden_door")
        }
        recipes.shapedCrafting(RecipeCategory.REDSTONE, pressurePlate, 2) { builder ->
            builder.define('x', planks)
            builder.pattern("xx")
            builder.group("wooden_pressure_plate")
        }
        recipes.shapelessCrafting(button, 1) { builder ->
            builder.group("wooden_button")
            builder.requires(planks)
        }
    }

    override fun setRenderLayers() {
        sapling.setRenderLayers()
    }
}