package thedarkcolour.hardcoredungeons.block.combo

import net.minecraft.core.Direction
import net.minecraft.data.recipes.FinishedRecipe
import net.minecraft.tags.BlockTags
import net.minecraft.tags.ItemTags
import net.minecraft.tags.TagKey
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.PressurePlateBlock
import net.minecraft.world.level.block.grower.AbstractTreeGrower
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.block.state.properties.WoodType
import net.minecraft.world.level.material.Material
import net.minecraft.world.level.material.MaterialColor
import net.minecraftforge.common.Tags
import thedarkcolour.hardcoredungeons.HardcoreDungeons
import thedarkcolour.hardcoredungeons.block.base.BlockMaker
import thedarkcolour.hardcoredungeons.block.base.properties.HProperties
import thedarkcolour.hardcoredungeons.data.LootGenerator
import thedarkcolour.hardcoredungeons.data.RecipeGenerator
import thedarkcolour.hardcoredungeons.data.RecipeGenerator.Companion.shaped
import thedarkcolour.hardcoredungeons.data.RecipeGenerator.Companion.shapeless
import thedarkcolour.hardcoredungeons.data.RecipeGenerator.Companion.slab
import thedarkcolour.hardcoredungeons.data.RecipeGenerator.Companion.stairs
import thedarkcolour.hardcoredungeons.data.RecipeGenerator.Companion.twoByTwo
import thedarkcolour.hardcoredungeons.data.modelgen.item.ItemModelType
import thedarkcolour.hardcoredungeons.registry.block.HBlockUtil
import thedarkcolour.hardcoredungeons.registry.block.HBlocks
import thedarkcolour.hardcoredungeons.registry.items.ItemMaker
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
    tree: AbstractTreeGrower,
    private val blockTag: TagKey<Block>,
    private val itemTag: TagKey<Item>,
    applyProperties: (HProperties) -> Unit
) : ICombo {
    // Wood Type
    val type: WoodType = WoodType.create(HardcoreDungeons.ID + ":" + wood).also(WoodType::register)

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

    override fun addTags(tags: DataTags) {
        tags.block(blockTag, log, strippedLog, wood, strippedWood)
        tags.item(itemTag, log, strippedLog, wood, strippedWood)

        tags.block(BlockTags.PLANKS, ItemTags.PLANKS, planks)
        tags.block(BlockTags.WOODEN_BUTTONS, ItemTags.WOODEN_BUTTONS, button)
        tags.block(BlockTags.WOODEN_DOORS, ItemTags.WOODEN_DOORS, door)
        tags.block(BlockTags.WOODEN_STAIRS, ItemTags.WOODEN_STAIRS, stairs)
        tags.block(BlockTags.WOODEN_SLABS, ItemTags.WOODEN_SLABS, slab)
        tags.block(BlockTags.WOODEN_FENCES, ItemTags.WOODEN_FENCES, fence)
        tags.block(BlockTags.WOODEN_PRESSURE_PLATES, ItemTags.WOODEN_PRESSURE_PLATES, pressurePlate)
        tags.block(BlockTags.WOODEN_TRAPDOORS, ItemTags.WOODEN_TRAPDOORS, trapdoor)
        tags.tag(BlockTags.LOGS_THAT_BURN, ItemTags.LOGS_THAT_BURN, blockTag, itemTag)
        tags.block(BlockTags.LEAVES, ItemTags.LEAVES, leaves)
        tags.block(BlockTags.FENCES, Tags.Items.FENCES, fence)
        tags.block(BlockTags.FENCE_GATES, Tags.Items.FENCE_GATES, fenceGate)
        tags.block(BlockTags.SAPLINGS, ItemTags.SAPLINGS, sapling.plant)

        sign.addTags(tags)
    }

    override fun addLoot(gen: LootGenerator) {
        gen.addLeaves(leaves, sapling.plant)
    }

    override fun addRecipes(consumer: Consumer<FinishedRecipe>) {
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