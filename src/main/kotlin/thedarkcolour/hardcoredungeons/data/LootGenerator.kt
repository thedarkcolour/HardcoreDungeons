package thedarkcolour.hardcoredungeons.data

import com.google.gson.GsonBuilder
import net.minecraft.advancements.criterion.EnchantmentPredicate
import net.minecraft.advancements.criterion.ItemPredicate
import net.minecraft.advancements.criterion.MinMaxBounds
import net.minecraft.advancements.criterion.StatePropertiesPredicate
import net.minecraft.block.Block
import net.minecraft.block.SlabBlock
import net.minecraft.data.DataGenerator
import net.minecraft.data.DirectoryCache
import net.minecraft.data.IDataProvider
import net.minecraft.data.LootTableProvider
import net.minecraft.enchantment.Enchantments
import net.minecraft.item.Items
import net.minecraft.loot.*
import net.minecraft.loot.conditions.BlockStateProperty
import net.minecraft.loot.conditions.MatchTool
import net.minecraft.loot.conditions.SurvivesExplosion
import net.minecraft.loot.functions.SetCount
import net.minecraft.state.properties.SlabType
import net.minecraft.util.ResourceLocation
import net.minecraftforge.registries.ForgeRegistries
import org.apache.logging.log4j.LogManager
import thedarkcolour.hardcoredungeons.HardcoreDungeons
import thedarkcolour.hardcoredungeons.registry.HBlocks
import thedarkcolour.hardcoredungeons.registry.HItems

class LootGenerator(private val generator: DataGenerator) : LootTableProvider(generator) {
    private val tables = hashMapOf<Block, LootTable.Builder>()

    private fun addLootTables(loot: LootGenerator) {
        val blocks = ForgeRegistries.BLOCKS

        // By default everything has a normal loot table
        for (block in blocks) {
            if (block.registryName!!.namespace == HardcoreDungeons.ID) {
                if (block is SlabBlock) {
                    loot.dropSlabs(block)
                } else {
                    loot.dropSelf(block)
                }
            }
        }

        // Add custom loot tables here!
        loot.addLoot(
            HBlocks.CASTLETON_VASE, LootTable.builder()
                .addLootPool(
                    LootPool.builder()
                        .addEntry(
                            ItemLootEntry.builder(HItems.BULLET)
                                .acceptFunction(SetCount.builder(RandomValueRange.of(3.0f, 7.0f)))
                                .weight(5)
                        )
                        .addEntry(
                            ItemLootEntry.builder(Items.ARROW)
                                .acceptFunction(SetCount.builder(RandomValueRange.of(2.0f, 6.0f)))
                                .weight(5)
                        )
                        .addEntry(
                            ItemLootEntry.builder(Items.MUSHROOM_STEW)
                                .weight(3)
                        )
                        .addEntry(
                            ItemLootEntry.builder(HItems.CASTLE_GEM)
                                .weight(1)
                        )
                )
        )
        loot.addLoot(
            HBlocks.CASTLETON_TREASURE_VASE, LootTable.builder()
                .addLootPool(
                    LootPool.builder()
                        .addEntry(
                            ItemLootEntry.builder(HItems.CASTLE_GEM)
                                .acceptFunction(SetCount.builder(BinomialRange.of(6, 0.6f)))
                                .weight(10)
                        )
                        .addEntry(
                            ItemLootEntry.builder(HItems.CHILI_PEPPER)
                                .acceptFunction(SetCount.builder(BinomialRange.of(3, 0.7f)))
                                .weight(5)
                        )
                )
        )
        loot.addLoot(
            HBlocks.RAINBOW_GRASS_BLOCK, LootTable.builder()
                .addLootPool(
                    LootPool.builder()
                        .addEntry(
                            ItemLootEntry.builder(HItems.RAINBOW_GRASS_BLOCK)
                                .acceptCondition(SILK_TOUCH)
                                .alternatively(ItemLootEntry.builder(HItems.RAINBOW_SOIL))
                        )
                )
        )
        loot.addLoot(
            HBlocks.RAINBOW_GLASS, LootTable.builder()
                .addLootPool(
                    LootPool.builder()
                        .addEntry(
                            ItemLootEntry.builder(HItems.RAINBOW_GLASS)
                                .acceptCondition(SILK_TOUCH)
                        )
                )
        )
        loot.addLoot(
            HBlocks.RAINBOW_GLASS_PANE, LootTable.builder()
                .addLootPool(
                    LootPool.builder()
                        .addEntry(
                            ItemLootEntry.builder(HBlocks.RAINBOW_GLASS_PANE)
                                .acceptCondition(SILK_TOUCH)
                        )
                )
        )
        loot.addLoot(
            HBlocks.SCRAP_METAL, LootTable.builder()
                .addLootPool(
                    LootPool.builder()
                        .addEntry(
                            ItemLootEntry.builder(HItems.SCRAP_METAL)
                                .acceptCondition(SILK_TOUCH)
                                .alternatively(
                                    ItemLootEntry.builder(Items.IRON_NUGGET)
                                        .acceptFunction(SetCount.builder(BinomialRange.of(5, 0.45f)))
                                )
                        )
                )
        )
    }

    private fun dropSelf(block: Block) {
        val pool = LootPool.builder()
            .rolls(ConstantRange.of(1))
            .addEntry(ItemLootEntry.builder(block))
            .acceptCondition(SurvivesExplosion.builder())
        tables[block] = LootTable.builder().addLootPool(pool)
    }

    private fun dropSlabs(block: Block) {
        val pool = LootPool.builder()
            .rolls(ConstantRange.of(1))
            .addEntry(ItemLootEntry.builder(block))
            .acceptCondition(SurvivesExplosion.builder())
            .acceptFunction(
                SetCount.builder(ConstantRange.of(2))
                    .acceptCondition(
                        BlockStateProperty.builder(block)
                            .fromProperties(
                                StatePropertiesPredicate.Builder.newBuilder()
                                    .withProp(SlabBlock.TYPE, SlabType.DOUBLE)
                            )
                    )
            )
        tables[block] = LootTable.builder().addLootPool(pool)
    }

    private fun addLoot(block: Block, loot: LootTable.Builder) {
        tables[block] = loot
    }

    override fun act(cache: DirectoryCache) {
        addLootTables(this)

        val namespacedTables = hashMapOf<ResourceLocation, LootTable>()

        for (entry in tables) {
            namespacedTables[entry.key.lootTable] = entry.value.setParameterSet(LootParameterSets.BLOCK).build()
        }

        writeLootTables(namespacedTables, cache)
    }

    private fun writeLootTables(tables: Map<ResourceLocation, LootTable>, cache: DirectoryCache) {
        val output = generator.outputFolder

        tables.forEach { (key, table) ->
            val path = output.resolve("data/" + key.namespace + "/loot_tables/" + key.path + ".json")
            try {
                IDataProvider.save(GSON, cache, LootTableManager.toJson(table), path)
            } catch (e: Exception) {
                LOGGER.error("Couldn't write loot table $path", e)
            }
        }
    }

    override fun getName(): String {
        return "Hardcore Dungeons Loot Tables"
    }

    companion object {
        // templates
        private val SILK_TOUCH = MatchTool.builder(
            ItemPredicate.Builder.create()
                .enchantment(EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.IntBound.atLeast(1)))
        )
        private val NO_SILK_TOUCH = MatchTool.builder(
            ItemPredicate.Builder.create()
                .enchantment(EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.IntBound.exactly(0)))
        )

        // internal stuff
        private val GSON = GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create()
        private val LOGGER = LogManager.getLogger()
    }
}