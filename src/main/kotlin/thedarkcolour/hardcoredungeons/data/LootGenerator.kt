package thedarkcolour.hardcoredungeons.data

import com.google.gson.GsonBuilder
import net.minecraft.advancements.criterion.*
import net.minecraft.block.Block
import net.minecraft.block.FlowerPotBlock
import net.minecraft.block.SlabBlock
import net.minecraft.data.DataGenerator
import net.minecraft.data.DirectoryCache
import net.minecraft.data.IDataProvider
import net.minecraft.data.LootTableProvider
import net.minecraft.enchantment.Enchantments
import net.minecraft.entity.EntityType
import net.minecraft.item.Items
import net.minecraft.loot.*
import net.minecraft.loot.conditions.*
import net.minecraft.loot.functions.*
import net.minecraft.state.Property
import net.minecraft.state.properties.BlockStateProperties
import net.minecraft.state.properties.DoubleBlockHalf
import net.minecraft.state.properties.SlabType
import net.minecraft.util.IItemProvider
import net.minecraft.util.IStringSerializable
import net.minecraft.util.ResourceLocation
import net.minecraftforge.registries.ForgeRegistries
import org.apache.logging.log4j.LogManager
import thedarkcolour.hardcoredungeons.HardcoreDungeons
import thedarkcolour.hardcoredungeons.block.decoration.DoorBlock
import thedarkcolour.hardcoredungeons.registry.HBlocks
import thedarkcolour.hardcoredungeons.registry.HEnchantments
import thedarkcolour.hardcoredungeons.registry.HEntities
import thedarkcolour.hardcoredungeons.registry.HItems

class LootGenerator(private val generator: DataGenerator) : LootTableProvider(generator) {
    private val entityTables = hashMapOf<EntityType<*>, LootTable.Builder>()
    private val blockTables = hashMapOf<Block, LootTable.Builder>()

    private fun addEntityLoot() {
        entityTables[HEntities.DEER] = LootTable.builder()
            .addLootPool(LootPool.builder().rolls(ConstantRange.of(1))
                .addEntry(item(HItems.DEER_ANTLER)/*.acceptCondition(EntityHasProperty.builder(LootContext.EntityTarget.THIS, IS_STAG))*/)
                .acceptCondition(RandomChanceWithLooting.builder(0.075f, 0.02f))
            )
            .addLootPool(LootPool.builder().rolls(ConstantRange.of(1))
                .addEntry(item(HItems.VENISON).acceptFunction(SetCount.builder(RandomValueRange.of(1.0f, 3.0f))).acceptFunction(Smelt.func_215953_b().acceptCondition(EntityHasProperty.builder(LootContext.EntityTarget.THIS, ON_FIRE))).acceptFunction(LootingEnchantBonus.builder(RandomValueRange.of(0.0F, 1.0F))))
            )
    }

    private fun addBlockLoot() {
        val blocks = ForgeRegistries.BLOCKS

        // By default everything has a loot table that drops itself
        for (block in blocks) {
            if (block.registryName!!.namespace == HardcoreDungeons.ID) {
                when (block) {
                    is SlabBlock -> dropSlabs(block)
                    is FlowerPotBlock -> dropPottedPlant(block)
                    is DoorBlock -> dropDoor(block)

                    else -> dropSelf(block)
                }
            }
        }

        //
        // Add custom loot tables here!
        //

        addLoot(HBlocks.CASTLETON_VASE, LootTable.builder().addLootPool(LootPool.builder()
            .addEntry(item(HItems.BULLET).setCountRandom(3.0f, 7.0f).weight(5))
            .addEntry(item(Items.ARROW).setCountRandom(2.0f, 6.0f).weight(5))
            .addEntry(item(Items.MUSHROOM_STEW).weight(3))
            .addEntry(item(HItems.CASTLE_GEM).weight(1))
        ))
        addLoot(HBlocks.CASTLETON_TREASURE_VASE, LootTable.builder().addLootPool(LootPool.builder()
            .addEntry(item(HItems.CASTLE_GEM).setCountBinomial(6, 0.6f).weight(10))
            .addEntry(item(HItems.CHILI_PEPPER).setCountBinomial(3, 0.7f).weight(5))
        ))
        addLoot(HBlocks.MALACHITE_CRYSTAL, LootTable.builder().addLootPool(LootPool.builder()
            .addEntry(item(HItems.MALACHITE_CRYSTAL).setCountRandom(1.0f, 4.0f))
        ))
        /*addLoot(Blocks.DIAMOND_ORE, LootTable.builder().addLootPool(LootPool.builder()
            .addEntry(
                .alternatively(item(Items.DIAMOND).explosionDecay().acceptFunction(FORTUNE))
            )
        ))
        dropSilk(
            block = Blocks.DIAMOND_ORE,
            normal = item(HItems.PRISTINE_DIAMOND).acceptCondition(PROSPECTING).acceptCondition(RandomChance.builder(0.3f)).alternatively(item(Items.DIAMOND).acceptFunction(FORTUNE).explosionDecay()),
            silk = item(Items.DIAMOND_ORE)
        )*/
        dropSilk(block = HBlocks.RAINBOW_GRASS_BLOCK, normal = item(HBlocks.RAINBOW_SOIL), silk = item(HItems.RAINBOW_GRASS_BLOCK))
        dropSilk(block = HBlocks.RAINBOW_GLASS, normal = null, silk = item(HItems.RAINBOW_GLASS))
        dropSilk(block = HBlocks.RAINBOW_GLASS_PANE, normal = null, silk = item(HItems.RAINBOW_GLASS_PANE))
        dropSilk(block = HBlocks.SCRAP_METAL, normal = item(Items.IRON_NUGGET).setCountBinomial(5, 0.45f), silk = item(HItems.SCRAP_METAL))
    }

    private fun singlePool(block: Block, poolFunction: (LootPool.Builder) -> Unit) {
        val pool = LootPool.builder().rolls(ConstantRange.of(1))
        poolFunction(pool)
        blockTables[block] = LootTable.builder().addLootPool(pool)
    }

    private fun dropSelf(block: Block) {
        singlePool(block) { pool -> pool.addEntry(item(block)).acceptCondition(SurvivesExplosion.builder()) }
    }

    private fun dropSilk(block: Block, normal: LootEntry.Builder<*>?, silk: LootEntry.Builder<*>) {
        val entry = silk.acceptCondition(SILK_TOUCH)
        if (normal != null) {
            entry.alternatively(normal)
        }
        singlePool(block) { pool -> pool.addEntry(entry) }
        //blockTables[block] = LootTable.builder().addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(entry))
    }

    private fun dropSlabs(block: Block) {
        singlePool(block) { pool -> pool.addEntry(item(block)).survivesExplosion().setCount(2).acceptCondition(propertyCondition(block, SlabBlock.TYPE, SlabType.DOUBLE)) }
        //blockTables[block] = LootTable.builder().addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(item(block)).survivesExplosion().setCount(2).acceptCondition(BlockStateProperty.builder(block).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withProp(SlabBlock.TYPE, SlabType.DOUBLE))))
    }

    private fun dropDoor(block: Block) {
        singlePool(block) { pool -> pool.addEntry(item(block).survivesExplosion().acceptCondition(propertyCondition(block, BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.LOWER))) }
        //blockTables[block] = LootTable.builder().addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(block.asItem()).acceptCondition(BlockStateProperty.builder(block).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withProp(BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.LOWER)))).survivesExplosion())
    }

    private fun dropPottedPlant(block: FlowerPotBlock) {
        blockTables[block] = LootTable.builder().addLootPool(constPool().addEntry(item(Items.FLOWER_POT)).survivesExplosion()).addLootPool(constPool().addEntry(item(block.flower)).survivesExplosion())
    }

    // rolls constant 1
    private fun constPool(): LootPool.Builder {
        return LootPool.builder().rolls(ConstantRange.of(1))
    }

    private fun <T> propertyCondition(block: Block, property: Property<T>, value: T): BlockStateProperty.Builder where T : IStringSerializable, T : Comparable<T> {
        return BlockStateProperty.builder(block).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withProp(property, value))
    }

    private fun item(item: IItemProvider): StandaloneLootEntry.Builder<*> {
        return ItemLootEntry.builder(item)
    }

    private fun <T> ILootConditionConsumer<T>.survivesExplosion(): T {
        return acceptCondition(SurvivesExplosion.builder())
    }

    private fun <T> ILootFunctionConsumer<T>.explosionDecay(): T {
        return acceptFunction(ExplosionDecay.builder())
    }

    private fun LootPool.Builder.setCount(count: Int): LootPool.Builder {
        return acceptFunction(SetCount.builder(ConstantRange.of(count)))
    }

    private fun StandaloneLootEntry.Builder<*>.setCountBinomial(n: Int, p: Float): StandaloneLootEntry.Builder<*> {
        return acceptFunction(SetCount.builder(BinomialRange.of(n, p)))
    }

    private fun StandaloneLootEntry.Builder<*>.setCountRandom(min: Float, max: Float): StandaloneLootEntry.Builder<*> {
        return acceptFunction(SetCount.builder(RandomValueRange.of(min, max)))
    }

    private fun addLoot(block: Block, loot: LootTable.Builder) {
        blockTables[block] = loot
    }

    override fun act(cache: DirectoryCache) {
        addBlockLoot()
        addEntityLoot()

        val namespacedTables = hashMapOf<ResourceLocation, LootTable>()

        for (entry in blockTables) {
            namespacedTables[entry.key.lootTable] = entry.value.setParameterSet(LootParameterSets.BLOCK).build()
        }

        for (entry in entityTables) {
            namespacedTables[entry.key.lootTable] = entry.value.setParameterSet(LootParameterSets.ENTITY).build()
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
        // block conditions
        private val SILK_TOUCH = MatchTool.builder(ItemPredicate.Builder.create().enchantment(EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.IntBound.atLeast(1))))
        private val PROSPECTING = MatchTool.builder(ItemPredicate.Builder.create().enchantment(EnchantmentPredicate(HEnchantments.PROSPECTING, MinMaxBounds.IntBound.atLeast(1))))
        // entity conditions
        private val ON_FIRE = EntityPredicate.Builder.create().flags(EntityFlagsPredicate.Builder.create().onFire(true).build())

        private val FORTUNE = ApplyBonus.oreDrops(Enchantments.FORTUNE)
        //private val IS_STAG = EntityPredicate.Builder.create().flags(HEntityFlagsPredicate(isStagDeer = true)) // todo make this work

        // internal stuff
        private val GSON = GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create()
        private val LOGGER = LogManager.getLogger()
    }
}