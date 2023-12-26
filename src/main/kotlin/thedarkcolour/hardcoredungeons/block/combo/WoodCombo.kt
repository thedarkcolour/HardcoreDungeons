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
import thedarkcolour.hardcoredungeons.data.modelgen.item.ItemModelType
import thedarkcolour.hardcoredungeons.registry.items.ItemMaker
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
    private val defaultProps: HProperties

    init {
        val name = HardcoreDungeons.ID + ":" + wood

        this.blockSetType = BlockSetType(name)
        this.woodType = WoodType(HardcoreDungeons.ID + ":" + wood, blockSetType).also(WoodType::register)
        this.defaultProps = BlockMaker.props(topCol, applyProperties).blockSetType(blockSetType)

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
    val planks by BlockMaker.cubeAllWithItem(wood + "_planks", defaultProps)
    val slab by BlockMaker.slabWithItem(wood + "_slab", ::planks, defaultProps)
    val stairs by BlockMaker.stairsWithItem(wood + "_stairs", ::planks, defaultProps)

    // Log, Stripped Log, Wood, Stripped Wood, Leaves
    val strippedLog by BlockMaker.rotatedPillarWithItem("stripped_" + wood + "_log", defaultProps)
    val strippedWood by BlockMaker.rotatedPillarWithItem("stripped_" + wood + "_wood", defaultProps)
    val log by BlockMaker.logWithItem(wood + "_log", { strippedLog }, HProperties.of { state -> if (state.getValue(BlockStateProperties.AXIS) == Direction.Axis.Y) topCol else barkCol }.also(applyProperties))
    val wood by BlockMaker.logWithItem(wood + "_wood", { strippedWood }, defaultProps)
    val leaves by BlockMaker.leavesBlock(wood + "_leaves")

    // Fence, Fence Gate, Pressure Plate, Button, Trapdoor, Door
    val fence by BlockMaker.registerFence(wood + "_fence", defaultProps, ::planks)
    val fenceGate by BlockMaker.registerFenceGate(wood + "_fence_gate", defaultProps, ::planks)
    val pressurePlate by BlockMaker.registerPressurePlate(wood + "_pressure_plate", PressurePlateBlock.Sensitivity.EVERYTHING, defaultProps, ::planks)
    val button by BlockMaker.registerWoodButton(wood + "_button", defaultProps, ::planks)
    val trapdoor by BlockMaker.registerTrapdoor(wood + "_trapdoor", defaultProps)
    val door by BlockMaker.registerDoor(wood + "_door", defaultProps)

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

    override fun addRecipes(writer: Consumer<FinishedRecipe>, recipes: RecipesHolder) {
        recipes.apply {
            // todo more recipes
            shapelessCrafting(RecipeCategory.BUILDING_BLOCKS, planks, 4, logItemTag)
            slab(slab, planks, "wooden_slab")
            stairs(stairs, planks, "wooden_stairs")
            shapedCrafting(RecipeCategory.MISC, fence, 3) { builder ->
                builder.pattern("xix")
                builder.pattern("xix")
                builder.group("wooden_fence")
                builder.define('x', planks)
                builder.define('i', Tags.Items.RODS_WOODEN)
            }
            shapedCrafting(RecipeCategory.REDSTONE, fenceGate, 3) { builder ->
                builder.pattern("ixi")
                builder.pattern("ixi")
                builder.group("wooden_fence_gate")
                builder.define('x', planks)
                builder.define('i', Tags.Items.RODS_WOODEN)
            }
            shapedCrafting(RecipeCategory.MISC, sign.item, 3) { builder ->
                builder.pattern("xxx")
                builder.pattern("xxx")
                builder.pattern(" i ")
                builder.group("sign")
                builder.define('x', planks)
                builder.define('i', Tags.Items.RODS_WOODEN)
            }
            grid2x2(RecipeCategory.BUILDING_BLOCKS, wood, 3, Ingredient.of(log))
            grid2x2(RecipeCategory.BUILDING_BLOCKS, strippedWood, 3, Ingredient.of(strippedLog))
            shapedCrafting(RecipeCategory.REDSTONE, trapdoor, 2) { builder ->
                builder.define('x', planks)
                builder.pattern("xxx")
                builder.pattern("xxx")
                builder.group("wooden_trapdoor")
            }
            shapedCrafting(RecipeCategory.REDSTONE, door, 3) { builder ->
                builder.define('x', planks)
                builder.pattern("xx")
                builder.pattern("xx")
                builder.pattern("xx")
                builder.group("wooden_door")
            }
            shapedCrafting(RecipeCategory.REDSTONE, pressurePlate, 2) { builder ->
                builder.define('x', planks)
                builder.pattern("xx")
                builder.group("wooden_pressure_plate")
            }
            // todo add group parameter to MKRecipeProvider
            shapelessCrafting(RecipeCategory.REDSTONE, button, 1, planks)
        }
    }

    override fun setRenderLayers() {
        sapling.setRenderLayers()
    }
}