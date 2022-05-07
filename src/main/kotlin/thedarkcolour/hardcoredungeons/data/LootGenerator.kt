package thedarkcolour.hardcoredungeons.data

import com.google.gson.GsonBuilder
import net.minecraft.advancements.criterion.*
import net.minecraft.block.*
import net.minecraft.data.DataGenerator
import net.minecraft.data.DirectoryCache
import net.minecraft.data.IDataProvider
import net.minecraft.data.LootTableProvider
import net.minecraft.enchantment.Enchantments
import net.minecraft.entity.EntityType
import net.minecraft.item.Items
import net.minecraft.loot.*
import net.minecraft.loot.AlternativesLootEntry.alternatives
import net.minecraft.loot.BinomialRange.binomial
import net.minecraft.loot.ConstantRange.exactly
import net.minecraft.loot.LootPool.lootPool
import net.minecraft.loot.LootTable.lootTable
import net.minecraft.loot.RandomValueRange.between
import net.minecraft.loot.conditions.BlockStateProperty
import net.minecraft.loot.conditions.EntityHasProperty.hasProperties
import net.minecraft.loot.conditions.MatchTool.toolMatches
import net.minecraft.loot.conditions.RandomChance.randomChance
import net.minecraft.loot.conditions.RandomChanceWithLooting.randomChanceAndLootingBoost
import net.minecraft.loot.conditions.SurvivesExplosion
import net.minecraft.loot.conditions.TableBonus
import net.minecraft.loot.functions.ApplyBonus
import net.minecraft.loot.functions.ExplosionDecay.explosionDecay
import net.minecraft.loot.functions.LootingEnchantBonus.lootingMultiplier
import net.minecraft.loot.functions.SetCount.setCount
import net.minecraft.loot.functions.Smelt.smelted
import net.minecraft.state.Property
import net.minecraft.state.properties.BlockStateProperties
import net.minecraft.state.properties.DoubleBlockHalf
import net.minecraft.state.properties.SlabType
import net.minecraft.util.IItemProvider
import net.minecraft.util.IStringSerializable
import net.minecraft.util.ResourceLocation
import net.minecraftforge.common.Tags
import net.minecraftforge.registries.ForgeRegistries
import org.apache.logging.log4j.LogManager
import thedarkcolour.hardcoredungeons.HardcoreDungeons
import thedarkcolour.hardcoredungeons.registry.HBlocks
import thedarkcolour.hardcoredungeons.registry.HEnchantments
import thedarkcolour.hardcoredungeons.registry.HEntities
import thedarkcolour.hardcoredungeons.registry.HItems

class LootGenerator(private val generator: DataGenerator) : LootTableProvider(generator) {
    private val entityTables = hashMapOf<EntityType<*>, LootTable.Builder>()
    private val blockTables = hashMapOf<Block, LootTable.Builder>()

    private fun addEntityLoot() {
        entityTables[HEntities.DEER] = lootTable()
            .withPool(lootPool().setRolls(exactly(1))
                .add(item(HItems.DEER_ANTLER))
                .`when`(randomChanceAndLootingBoost(0.075f, 0.02f))
            )
            .withPool(lootPool().setRolls(exactly(1))
                .add(item(HItems.VENISON)
                    .apply(setCount(between(1.0f, 3.0f)))
                    .apply(smelted().`when`(hasProperties(LootContext.EntityTarget.THIS, ON_FIRE))).apply(lootingMultiplier(between(0.0F, 1.0F))))
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

        addLoot(HBlocks.CASTLETON_VASE, lootTable().withPool(lootPool()
            .add(item(HItems.BULLET).setWeight(5).apply(setCount(between(3.0f, 7.0f))))
            .add(item(Items.ARROW).setWeight(5).apply(setCount(between(2.0f, 6.0f))))
            .add(item(Items.MUSHROOM_STEW).setWeight(3))
            .add(item(HItems.CASTLE_GEM).setWeight(1))
        ))

        addLoot(HBlocks.CASTLETON_TREASURE_VASE, lootTable().withPool(
            lootPool()
            .add(item(HItems.CASTLE_GEM).apply(setCount(binomial(6, 0.6f))).setWeight(10))
            .add(item(HBlocks.CHILI_PEPPER.item).apply(setCount(binomial(3, 0.7f))).setWeight(5))
        ))
        HBlocks.MALACHITE_CRYSTAL.addLoot(this)
        HBlocks.DIAMOND_CRYSTAL.addLoot(this)
        //addLoot(HBlocksNew.MALACHITE_CRYSTAL.crystal, LootTable.lootTable().withPool(LootPool.lootPool()
        //    .add(item(HItems.MALACHITE_CRYSTAL).setCountRandom(1.0f, 4.0f))
        //))
        /*addLoot(Blocks.DIAMOND_ORE, LootTable.builder().withPool(LootPool.builder()
            .add(
                .alternatively(item(Items.DIAMOND).explosionDecay().acceptFunction(FORTUNE))
            )
        ))
        dropSilk(
            block = Blocks.DIAMOND_ORE,
            normal = item(HItems.PRISTINE_DIAMOND).acceptCondition(PROSPECTING).acceptCondition(RandomChance.builder(0.3f)).alternatively(item(Items.DIAMOND).acceptFunction(FORTUNE).explosionDecay()),
            silk = item(Items.DIAMOND_ORE)
        )*/
        HBlocks.RAINBOW_SOIL.addLoot(this)
        HBlocks.CASTLETON_SOIL.addLoot(this)
        HBlocks.AURISOIL.addLoot(this)
        HBlocks.SUGARY_SOIL.addLoot(this)
        HBlocks.LUMLIGHT_WOOD.addLoot(this)
        HBlocks.AURI_WOOD.addLoot(this)
        HBlocks.COTTONMARSH_WOOD.addLoot(this)
        //dropSilk(block = HBlocksNew.RAINBOW_SOIL.grass, normal = item(HBlocksNew.RAINBOW_SOIL.soil), silk = item(HBlocksNew.RAINBOW_SOIL.grass))
        dropSilk(block = HBlocks.RAINBOW_GLASS, normal = null, silk = item(HBlocks.RAINBOW_GLASS))
        dropSilk(block = HBlocks.RAINBOW_GLASS_PANE, normal = null, silk = item(HBlocks.RAINBOW_GLASS_PANE))
        dropSilk(block = HBlocks.SCRAP_METAL, normal = item(Items.IRON_NUGGET).apply(setCount(binomial(5, 0.45f))), silk = item(HBlocks.SCRAP_METAL))

        pristineLootTable()
    }

    private fun pristineLootTable() {
        // todo make Prospecting into global loot modifier
        addLoot(Blocks.DIAMOND_ORE, lootTable()
            .withPool(lootPool().add(alternatives(
                item(Items.DIAMOND_ORE).`when`(SILK_TOUCH),
                item(HItems.PRISTINE_DIAMOND).`when`(randomChance(0.4f)).`when`(PROSPECTING),
                item(Items.DIAMOND).apply(FORTUNE).apply(explosionDecay())
            ))))
    }

    private fun singlePool(block: Block, poolFunction: LootPool.Builder.() -> Unit) {
        val pool = lootPool()
        poolFunction(pool)

        addLoot(block, lootTable().withPool(pool))
    }

    private fun dropSelf(block: Block) {
        singlePool(block) {
            add(item(block).`when`(SurvivesExplosion.survivesExplosion()))
        }
    }

    fun dropSilk(block: Block, normal: LootEntry.Builder<*>?, silk: LootEntry.Builder<*>) {
        var entry = silk.`when`(SILK_TOUCH)

        if (normal != null) {
            entry = entry.otherwise(normal.`when`(SurvivesExplosion.survivesExplosion()))
        }

        singlePool(block) { add(entry) }
    }

    private fun dropSlabs(block: Block) {
        singlePool(block) {
            add(item(block).apply(setCount(exactly(2)).`when`(propertyCondition(block, SlabBlock.TYPE, SlabType.DOUBLE))).apply(explosionDecay()))
        }
    }

    private fun dropDoor(block: Block) {
        singlePool(block) { add(item(block).survivesExplosion().`when`(propertyCondition(block, BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.LOWER))) }
        //blockTables[block] = LootTable.builder().withPool(LootPool.builder().rolls(ConstantRange.of(1)).add(ItemLootEntry.builder(block.asItem()).acceptCondition(BlockStateProperty.builder(block).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withProp(BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.LOWER)))).survivesExplosion())
    }

    private fun dropPottedPlant(block: FlowerPotBlock) {
        blockTables[block] = lootTable().withPool(constPool().add(item(Items.FLOWER_POT)).survivesExplosion()).withPool(constPool().add(item(block.content)).survivesExplosion())
    }

    // rolls constant 1
    private fun constPool(): LootPool.Builder {
        return lootPool().setRolls(exactly(1))
    }

    private fun <T> propertyCondition(block: Block, property: Property<T>, value: T): BlockStateProperty.Builder where T : IStringSerializable, T : Comparable<T> {
        return BlockStateProperty.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(property, value))
    }

    fun item(item: IItemProvider): StandaloneLootEntry.Builder<*> {
        return ItemLootEntry.lootTableItem(item)
    }

    private fun <T> ILootConditionConsumer<T>.survivesExplosion(): T {
        return `when`(SurvivesExplosion.survivesExplosion())
    }

    fun addLoot(block: Block, loot: LootTable.Builder) {
        blockTables[block] = loot
    }

    override fun run(cache: DirectoryCache) {
        addBlockLoot()
        addEntityLoot()

        val namespacedTables = hashMapOf<ResourceLocation, LootTable>()

        for ((key, value) in blockTables) {
            namespacedTables[key.lootTable] = value.setParamSet(LootParameterSets.BLOCK).build()
        }

        for (entry in entityTables) {
            namespacedTables[entry.key.defaultLootTable] = entry.value.setParamSet(LootParameterSets.ENTITY).build()
        }

        // Do not redefine the empty loot table
        namespacedTables.remove(ResourceLocation("minecraft:empty"))

        writeLootTables(namespacedTables, cache)
    }

    private fun writeLootTables(tables: Map<ResourceLocation, LootTable>, cache: DirectoryCache) {
        val output = generator.outputFolder

        tables.forEach { (key, table) ->
            val path = output.resolve("data/" + key.namespace + "/loot_tables/" + key.path + ".json")
            try {
                IDataProvider.save(GSON, cache, LootTableManager.serialize(table), path)
            } catch (e: Exception) {
                LOGGER.error("Couldn't write loot table $path", e)
            }
        }
    }

    override fun getName(): String {
        return "Hardcore Dungeons Loot Tables"
    }

    fun addLeaves(leaves: LeavesBlock, sapling: Block) {
        addLoot(leaves, lootTable()
            .withPool(lootPool().add(item(leaves).`when`(SILK_TOUCH.or(SHEARS)).otherwise(item(sapling).`when`(SurvivesExplosion.survivesExplosion()).`when`(NORMAL_LEAVES_SAPLING_CHANCES))))
            .withPool(lootPool().add(item(Items.STICK).`when`(NORMAL_LEAVES_STICK_CHANCES).apply(setCount(between(1f, 2f)))).`when`(SILK_TOUCH.or(SHEARS).invert())))
    }

    companion object {
        // block conditions
        private val SILK_TOUCH = toolMatches(ItemPredicate.Builder.item().hasEnchantment(EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.IntBound.atLeast(1))))
        private val SHEARS = toolMatches(ItemPredicate.Builder.item().of(Tags.Items.SHEARS))
        private val PROSPECTING = toolMatches(ItemPredicate.Builder.item().hasEnchantment(EnchantmentPredicate(HEnchantments.PROSPECTING, MinMaxBounds.IntBound.atLeast(1))))
        // entity conditions
        private val ON_FIRE = EntityPredicate.Builder.entity().flags(EntityFlagsPredicate.Builder.flags().setOnFire(true).build())

        private val FORTUNE = ApplyBonus.addOreBonusCount(Enchantments.BLOCK_FORTUNE)
        private val NORMAL_LEAVES_SAPLING_CHANCES = TableBonus.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, 0.05f, 0.0625f, 0.083333336f, 0.1f)
        private val NORMAL_LEAVES_STICK_CHANCES = TableBonus.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, 0.02f, 0.022222223f, 0.025f, 0.033333335f, 0.1f)
        //private val IS_STAG = EntityPredicate.Builder.create().flags(HEntityFlagsPredicate(isStagDeer = true)) // todo make this work

        // internal stuff
        private val GSON = GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create()
        private val LOGGER = LogManager.getLogger()
    }
}