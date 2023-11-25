package thedarkcolour.hardcoredungeons.data

import net.minecraft.advancements.critereon.EnchantmentPredicate
import net.minecraft.advancements.critereon.ItemPredicate
import net.minecraft.advancements.critereon.MinMaxBounds
import net.minecraft.advancements.critereon.StatePropertiesPredicate
import net.minecraft.core.registries.Registries
import net.minecraft.data.loot.BlockLootSubProvider
import net.minecraft.util.StringRepresentable
import net.minecraft.world.flag.FeatureFlags
import net.minecraft.world.item.Items
import net.minecraft.world.item.enchantment.Enchantments
import net.minecraft.world.level.ItemLike
import net.minecraft.world.level.block.*
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf
import net.minecraft.world.level.block.state.properties.Property
import net.minecraft.world.level.block.state.properties.SlabType
import net.minecraft.world.level.storage.loot.BuiltInLootTables
import net.minecraft.world.level.storage.loot.LootPool
import net.minecraft.world.level.storage.loot.LootPool.lootPool
import net.minecraft.world.level.storage.loot.LootTable
import net.minecraft.world.level.storage.loot.LootTable.lootTable
import net.minecraft.world.level.storage.loot.entries.LootItem
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount
import net.minecraft.world.level.storage.loot.functions.ApplyExplosionDecay
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction.setCount
import net.minecraft.world.level.storage.loot.predicates.*
import net.minecraft.world.level.storage.loot.providers.number.BinomialDistributionGenerator.binomial
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator.between
import net.minecraftforge.common.Tags
import org.apache.logging.log4j.LogManager
import thedarkcolour.hardcoredungeons.HardcoreDungeons
import thedarkcolour.hardcoredungeons.registry.block.HBlocks
import thedarkcolour.hardcoredungeons.registry.items.BULLET_ITEM
import thedarkcolour.hardcoredungeons.registry.items.CASTLE_GEM_ITEM
import thedarkcolour.modkit.MKUtils

class BlockLoot : BlockLootSubProvider(emptySet(), FeatureFlags.DEFAULT_FLAGS) {
    private val added = arrayListOf<Block>()

    override fun getKnownBlocks(): MutableIterable<Block> {
        return added
    }

    public override fun add(block: Block, builder: LootTable.Builder) {
        super.add(block, builder)
        added.add(block)
    }

    override fun generate() {
        MKUtils.forModRegistry(Registries.BLOCK, HardcoreDungeons.ID) { id, block ->
            if (block.lootTable != BuiltInLootTables.EMPTY) {
                when (block) {
                    is SlabBlock -> dropSlabs(block)
                    is FlowerPotBlock -> dropPottedPlant(block)
                    is DoorBlock -> dropDoor(block)

                    else -> dropSelf(block)
                }
            }
        }

        addCustomLootTables()
    }

    private fun addCustomLootTables() {
        singlePool(HBlocks.CASTLETON_VASE) {
            add(item(BULLET_ITEM).setWeight(5).apply(setCount(between(3.0f, 7.0f))))
            add(item(Items.ARROW).setWeight(5).apply(setCount(between(2.0f, 6.0f))))
            add(item(Items.MUSHROOM_STEW).setWeight(3))
            add(item(CASTLE_GEM_ITEM).setWeight(1))
        }
        singlePool(HBlocks.CASTLETON_TREASURE_VASE) {
            add(item(CASTLE_GEM_ITEM).apply(setCount(binomial(6, 0.6f))).setWeight(10))
            add(item(HBlocks.CHILI_PEPPER.item).apply(setCount(binomial(3, 0.7f))).setWeight(5))
        }

        HBlocks.MALACHITE_CRYSTAL.addLoot(this)
        HBlocks.RAINBOW_SOIL.addLoot(this)
        HBlocks.CASTLETON_SOIL.addLoot(this)
        HBlocks.AURISOIL.addLoot(this)
        HBlocks.SUGARY_SOIL.addLoot(this)
        HBlocks.LUMLIGHT_WOOD.addLoot(this)
        HBlocks.AURI_WOOD.addLoot(this)
        HBlocks.COTTONMARSH_WOOD.addLoot(this)
        dropSilk(block = HBlocks.RAINBOW_GLASS, normal = null, silk = item(HBlocks.RAINBOW_GLASS))
        dropSilk(block = HBlocks.RAINBOW_GLASS_PANE, normal = null, silk = item(HBlocks.RAINBOW_GLASS_PANE))
    }

    private fun singlePool(block: Block, poolFunction: LootPool.Builder.() -> Unit) {
        val pool = lootPool()
        poolFunction(pool)

        add(block, lootTable().withPool(pool))
    }

    fun dropSilk(block: Block, normal: LootPoolEntryContainer.Builder<*>?, silk: LootPoolEntryContainer.Builder<*>) {
        var entry = silk.`when`(SILK_TOUCH)

        if (normal != null) {
            entry = entry.otherwise(normal.whenSurvivesExplosion())
        }

        singlePool(block) {
            add(entry)
        }
    }

    private fun dropSlabs(block: Block) {
        singlePool(block) {
            add(item(block)
                .apply(setCount(ConstantValue.exactly(2f)).`when`(propertyCondition(block, SlabBlock.TYPE, SlabType.DOUBLE)))
                .apply(ApplyExplosionDecay.explosionDecay())
            )
        }
    }

    private fun dropDoor(block: Block) {
        singlePool(block) {
            add(item(block).whenSurvivesExplosion().`when`(propertyCondition(block, BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.LOWER)))
        }
    }

    private fun dropPottedPlant(block: FlowerPotBlock) {
        add(block, lootTable().withPool(constPool().add(item(Items.FLOWER_POT)).whenSurvivesExplosion()).withPool(constPool().add(item(block.content)).whenSurvivesExplosion()))
    }

    fun addLeaves(leaves: LeavesBlock, sapling: Block) {
        add(leaves, lootTable()
            .withPool(lootPool().add(item(leaves).`when`(SILK_TOUCH.or(SHEARS)).otherwise(item(sapling).whenSurvivesExplosion().`when`(NORMAL_LEAVES_SAPLING_CHANCES))))
            .withPool(lootPool().add(item(Items.STICK).`when`(NORMAL_LEAVES_STICK_CHANCES).apply(setCount(between(1f, 2f)))).`when`(SILK_TOUCH.or(SHEARS).invert())))
    }

    // rolls constant 1
    private fun constPool(): LootPool.Builder {
        return lootPool().setRolls(ConstantValue.exactly(1f))
    }

    private fun <T> propertyCondition(block: Block, property: Property<T>, value: T): LootItemBlockStatePropertyCondition.Builder where T : StringRepresentable, T : Comparable<T> {
        return LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(property, value))
    }

    fun item(item: ItemLike): LootPoolSingletonContainer.Builder<*> {
        return LootItem.lootTableItem(item)
    }

    companion object {
        // block conditions
        private val SILK_TOUCH = MatchTool.toolMatches(ItemPredicate.Builder.item().hasEnchantment(EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.Ints.atLeast(1))))
        private val SHEARS = MatchTool.toolMatches(ItemPredicate.Builder.item().of(Tags.Items.SHEARS))

        private val FORTUNE = ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE)
        private val NORMAL_LEAVES_SAPLING_CHANCES = BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, 0.05f, 0.0625f, 0.083333336f, 0.1f)
        private val NORMAL_LEAVES_STICK_CHANCES = BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, 0.02f, 0.022222223f, 0.025f, 0.033333335f, 0.1f)

        // internal stuff
        private val LOGGER = LogManager.getLogger()

        private fun <T : ConditionUserBuilder<T>> ConditionUserBuilder<T>.whenSurvivesExplosion(): T {
            return `when`(ExplosionCondition.survivesExplosion())
        }
    }
}