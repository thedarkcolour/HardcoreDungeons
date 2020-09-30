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
import org.apache.logging.log4j.LogManager
import thedarkcolour.hardcoredungeons.registry.HBlocks
import thedarkcolour.hardcoredungeons.registry.HItems

class LootGenerator(private val generator: DataGenerator) : LootTableProvider(generator) {
    private val tables = hashMapOf<Block, LootTable.Builder>()

    private fun addLootTables(loot: LootGenerator) {
        loot.dropSelf(HBlocks.CASTLETON_SOIL)
        // loot.dropSelf(HBlocks.CASTLETON_GRASS_BLOCK)
        loot.dropSelf(HBlocks.CASTLETON_STONE)
        loot.dropSelf(HBlocks.CASTLETON_LOAM)
        loot.dropSelf(HBlocks.CASTLETON_BRICKS)
        loot.dropSelf(HBlocks.CASTLETON_BRICK_STAIRS)
        loot.dropSlabs(HBlocks.CASTLETON_BRICK_SLAB)
        loot.dropSelf(HBlocks.CASTLETON_BRICK_FENCE)
        loot.dropSelf(HBlocks.CASTLETON_BRICK_WALL)
        loot.dropSelf(HBlocks.CRACKED_CASTLETON_BRICKS)
        loot.dropSelf(HBlocks.CHARGED_CASTLETON_BRICKS)
        loot.dropSelf(HBlocks.CHARGED_CASTLETON_BRICK_STAIRS)
        loot.dropSlabs(HBlocks.CHARGED_CASTLETON_BRICK_SLAB)
        loot.dropSelf(HBlocks.CHARGED_CASTLETON_BRICK_FENCE)
        loot.dropSelf(HBlocks.CHARGED_CASTLETON_BRICK_WALL)
        loot.dropSelf(HBlocks.CASTLETON_PORTAL_FRAME)
        loot.dropSelf(HBlocks.PURPLE_CASTLETON_LAMP)
        loot.dropSelf(HBlocks.LUMLIGHT_SAPLING)
        loot.dropSelf(HBlocks.LUMLIGHT_LOG)
        loot.dropSelf(HBlocks.LUMLIGHT_WOOD)
        loot.dropSelf(HBlocks.STRIPPED_LUMLIGHT_LOG)
        loot.dropSelf(HBlocks.STRIPPED_LUMLIGHT_WOOD)
        // loot.dropSelf(HBlocks.LUMLIGHT_LEAVES)
        loot.dropSelf(HBlocks.LUMLIGHT_POD)
        loot.dropSelf(HBlocks.LUMLIGHT_PLANKS)
        loot.dropSelf(HBlocks.LUMLIGHT_STAIRS)
        loot.dropSlabs(HBlocks.LUMLIGHT_SLAB)
        loot.dropSelf(HBlocks.LUMLIGHT_FENCE)
        loot.dropSelf(HBlocks.LUMLIGHT_FENCE_GATE)
        // loot.dropSelf(HBlocks.LUMLIGHT_DOOR)
        loot.dropSelf(HBlocks.LUMLIGHT_TRAPDOOR)
        loot.dropSelf(HBlocks.LUMLIGHT_SIGN)
        loot.dropSelf(HBlocks.LUMLIGHT_WALL_SIGN)
        loot.dropSelf(HBlocks.PURPLE_LUMSHROOM)
        loot.dropSelf(HBlocks.BLUE_LUMSHROOM)
        loot.dropSelf(HBlocks.LOST_SKULL)
        // loot.dropSelf(HBlocks.CASTLETON_TORCH)
        // loot.dropSelf(HBlocks.CASTLETON_WALL_TORCH)
        loot.dropSelf(HBlocks.CROWN)
        loot.dropSelf(HBlocks.CHALICE)
        loot.dropSelf(HBlocks.PLATE)
        loot.dropSelf(HBlocks.FRAYED_SKULL)
        loot.dropSelf(HBlocks.RED_WINE_BOTTLE)
        loot.dropSelf(HBlocks.WHITE_WINE_BOTTLE)
        loot.dropSelf(HBlocks.WINE_BOTTLE)
        loot.dropSelf(HBlocks.CASTLETON_LANTERN)
        // loot.dropSelf(HBlocks.DUNGEON_MOB_SPAWNER)
        // loot.dropSelf(HBlocks.LUMLIGHT_CAMPFIRE)
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
        loot.dropSelf(HBlocks.RAINBOW_SOIL)
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
        loot.dropSelf(HBlocks.RAINBOW_ROCK)
        loot.dropSelf(HBlocks.RAINBOW_BRICKS)
        loot.dropSelf(HBlocks.RAINBOW_BRICK_STAIRS)
        loot.dropSlabs(HBlocks.RAINBOW_BRICK_SLAB)
        loot.dropSelf(HBlocks.RAINBOW_BRICK_WALL)
        loot.dropSelf(HBlocks.RAINBOW_BRICK_FENCE)
        loot.dropSelf(HBlocks.RAINBOWLAND_PORTAL_FRAME)
        loot.dropSelf(HBlocks.COTTONMARSH_SAPLING)
        loot.dropSelf(HBlocks.COTTONMARSH_LEAVES)
        loot.dropSelf(HBlocks.COTTONMARSH_LOG)
        loot.dropSelf(HBlocks.COTTONMARSH_WOOD)
        loot.dropSelf(HBlocks.STRIPPED_COTTONMARSH_LOG)
        loot.dropSelf(HBlocks.STRIPPED_COTTONMARSH_WOOD)
        loot.dropSelf(HBlocks.RAINBOW_FACTORY_BRICKS)
        loot.dropSelf(HBlocks.RAINBOW_FACTORY_BRICK_STAIRS)
        loot.dropSlabs(HBlocks.RAINBOW_FACTORY_BRICK_SLAB)
        loot.dropSelf(HBlocks.RAINBOW_FACTORY_BRICK_WALL)
        loot.dropSelf(HBlocks.RAINBOW_FACTORY_BRICK_FENCE)
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
        loot.dropSelf(HBlocks.RAINBOWSTONE_ORE)
        loot.dropSelf(HBlocks.RAINBOWSTONE_BLOCK)
        loot.dropSelf(HBlocks.FLAME_ROSE)
        loot.dropSelf(HBlocks.GOLDEN_TULIP)
        loot.dropSelf(HBlocks.AURISOIL)
        // loot.dropSelf(HBlocks.AURIGRASS_BLOCK)
        loot.dropSelf(HBlocks.AURILOAM)
        loot.dropSelf(HBlocks.GOLDEN_AURISOIL)
        // loot.dropSelf(HBlocks.GOLDEN_AURIGRASS_BLOCK)
        loot.dropSelf(HBlocks.AUBRUM_ROCK)
        // loot.dropSelf(HBlocks.DRUM)
        loot.dropSelf(HBlocks.AURI_LOG)
        loot.dropSelf(HBlocks.AURI_WOOD)
        loot.dropSelf(HBlocks.STRIPPED_AURI_LOG)
        loot.dropSelf(HBlocks.STRIPPED_AURI_WOOD)
        loot.dropSelf(HBlocks.AURI_PLANKS)
        loot.dropSelf(HBlocks.AURI_STAIRS)
        loot.dropSlabs(HBlocks.AURI_SLAB)
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
        loot.dropSelf(HBlocks.EXTRACTOR)
        loot.dropSelf(HBlocks.TABLE)
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