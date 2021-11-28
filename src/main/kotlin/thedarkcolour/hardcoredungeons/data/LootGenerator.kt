package thedarkcolour.hardcoredungeons.data

import com.google.gson.GsonBuilder
import com.theonlytails.loottables.*
import net.minecraft.advancements.criterion.*
import net.minecraft.block.*
import net.minecraft.data.DataGenerator
import net.minecraft.data.DirectoryCache
import net.minecraft.data.IDataProvider
import net.minecraft.data.LootTableProvider
import net.minecraft.enchantment.Enchantments
import net.minecraft.world.entity.EntityType
import net.minecraft.item.Items
import net.minecraft.loot.*
import net.minecraft.loot.conditions.BlockStateProperty
import net.minecraft.loot.conditions.EntityHasProperty
import net.minecraft.loot.conditions.MatchTool
import net.minecraft.loot.conditions.SurvivesExplosion
import net.minecraft.loot.functions.ApplyBonus
import net.minecraft.loot.functions.SetCount
import net.minecraft.state.Property
import net.minecraft.state.properties.BlockStateProperties
import net.minecraft.state.properties.DoubleBlockHalf
import net.minecraft.state.properties.SlabType
import net.minecraft.util.IItemProvider
import net.minecraft.util.IStringSerializable
import net.minecraft.resources.ResourceLocation
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
    //private val tables = hashMapOf<ResourceLocation, LootTable>()

    private fun addEntityLoot() {
        entity(HEntities.DEER) {
            pool {
                itemEntry(HItems.DEER_ANTLER)
                condition { randomChanceWithLooting(0.075f, 0.02f) }
            }
            pool {
                itemEntry(HItems.VENISON) {
                    function(
                        setRandomCount(1.0f, 3.0f),
                        furnaceSmelt { condition { EntityHasProperty.hasProperties(LootContext.EntityTarget.THIS, ON_FIRE) } },
                        looting(randomRangeValue(0.0f, 1.0f))
                    )
                }

            }
        }/*
        entityTables[HEntities.DEER] = LootTable.builder()
            .withPool(LootPool.builder().rolls(ConstantRange.of(1))
                .add(item(HItems.DEER_ANTLER)/*.acceptCondition(EntityHasProperty.builder(LootContext.EntityTarget.THIS, IS_STAG))*/)
                .acceptCondition(RandomChanceWithLooting.builder(0.075f, 0.02f))
            )
            .withPool(LootPool.builder().rolls(ConstantRange.of(1))
                .add(item(HItems.VENISON).acceptFunction(SetCount.builder(RandomValueRange.of(1.0f, 3.0f))).acceptFunction(Smelt.func_215953_b().acceptCondition(EntityHasProperty.builder(LootContext.EntityTarget.THIS, ON_FIRE))).acceptFunction(LootingEnchantBonus.builder(RandomValueRange.of(0.0F, 1.0F))))
            )*/
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

        block(HBlocks.CASTLETON_VASE) {
            pool {
                itemEntry(HItems.BULLET, weight = 5).setCountRandom(3.0f, 7.0f)//.setCountRandom(3.0f, 7.0f)
                itemEntry(Items.ARROW, weight = 5).setCountRandom(2.0f, 6.0f)
                itemEntry(Items.MUSHROOM_STEW, weight = 3)
                itemEntry(HItems.CASTLE_GEM, weight = 1)
            }
        }

        block(HBlocks.CASTLETON_TREASURE_VASE) {
            pool {
                itemEntry(HItems.CASTLE_GEM).setCountBinomial(6, 0.6f)
            }
        }

        addLoot(HBlocks.CASTLETON_TREASURE_VASE, LootTable.lootTable().withPool(LootPool.lootPool()
            .add(item(HItems.CASTLE_GEM).setCountBinomial(6, 0.6f).setWeight(10))
            .add(item(HItems.CHILI_PEPPER).setCountBinomial(3, 0.7f).setWeight(5))
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
        //dropSilk(block = HBlocksNew.RAINBOW_SOIL.grass, normal = item(HBlocksNew.RAINBOW_SOIL.soil), silk = item(HBlocksNew.RAINBOW_SOIL.grass))
        dropSilk(block = HBlocks.RAINBOW_GLASS, normal = null, silk = item(HBlocks.RAINBOW_GLASS))
        dropSilk(block = HBlocks.RAINBOW_GLASS_PANE, normal = null, silk = item(HBlocks.RAINBOW_GLASS_PANE))
        dropSilk(block = HBlocks.SCRAP_METAL, normal = item(Items.IRON_NUGGET).setCountBinomial(5, 0.45f), silk = item(HBlocks.SCRAP_METAL))

        pristineLootTable()
    }

    private fun pristineLootTable() {
        block(Blocks.DIAMOND_ORE) {
            pool {
                alternativesEntry(
                    itemEntry(Items.DIAMOND_ORE, addToPool = false) {
                        condition(hasSilkTouch)
                    },
                    itemEntry(HItems.PRISTINE_DIAMOND, addToPool = false) {
                        condition(
                            randomChance(0.4f),
                            matchTool(itemHasEnchantment(enchantAtLeast(HEnchantments.PROSPECTING, 1)))
                        )
                        function(
                            oreBonusCount(Enchantments.BLOCK_FORTUNE),
                            explosionDecay()
                        )
                    },
                    itemEntry(Items.DIAMOND, addToPool = false) {
                        function(
                            oreBonusCount(Enchantments.BLOCK_FORTUNE),
                            explosionDecay()
                        )
                    }
                )
            }
        }
    }

    fun block(block: Block, body: LootTable.Builder.() -> LootTable.Builder) {
        blockTables[block] = lootTableBuilder(LootParameterSets.BLOCK, body)
    }

    fun entity(block: EntityType<*>, body: LootTable.Builder.() -> LootTable.Builder) {
        entityTables[block] = lootTableBuilder(LootParameterSets.ENTITY, body)
    }

    private fun singlePool(block: Block, poolFunction: LootPool.Builder.() -> Unit) {
        blockTables[block] = lootTableBuilder(LootParameterSets.BLOCK) { pool(body = poolFunction) }
    }

    private fun dropSelf(block: Block) {
        // Do not redefine the empty loot table
        if (block.lootTable == LootTables.EMPTY) return

        singlePool(block) {
            itemEntry(block) { condition { com.theonlytails.loottables.survivesExplosion() } }
        }
    }

    fun dropSilk(block: Block, normal: LootEntry.Builder<*>?, silk: LootEntry.Builder<*>) {
        val entry = silk.`when`(SILK_TOUCH)
        if (normal != null) {
            entry.otherwise(normal)
        }
        singlePool(block) { add(entry) }
    }

    private fun dropSlabs(block: Block) {
        singlePool(block) { add(item(block)).survivesExplosion().setCount(2).`when`(propertyCondition(block, SlabBlock.TYPE, SlabType.DOUBLE)) }
    }

    private fun slabLoot(block: Block) {
        lootTable(LootParameterSets.BLOCK) {
            pool {
                itemEntry(block) {
                    function(::explosionDecay)
                    function {
                        setConstantCount(2) {
                            condition {
                                blockStateProperty(block) {
                                    setProperties(stateProperties {
                                        hasProperty(BlockStateProperties.SLAB_TYPE, SlabType.DOUBLE)
                                    })
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun dropDoor(block: Block) {
        singlePool(block) { add(item(block).survivesExplosion().`when`(propertyCondition(block, BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.LOWER))) }
        //blockTables[block] = LootTable.builder().withPool(LootPool.builder().rolls(ConstantRange.of(1)).add(ItemLootEntry.builder(block.asItem()).acceptCondition(BlockStateProperty.builder(block).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withProp(BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.LOWER)))).survivesExplosion())
    }

    private fun dropPottedPlant(block: FlowerPotBlock) {
        blockTables[block] = LootTable.lootTable().withPool(constPool().add(item(Items.FLOWER_POT)).survivesExplosion()).withPool(constPool().add(item(block.content)).survivesExplosion())
    }

    // rolls constant 1
    private fun constPool(): LootPool.Builder {
        return LootPool.lootPool().setRolls(ConstantRange.exactly(1))
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

    private fun LootPool.Builder.setCount(count: Int): LootPool.Builder {
        return apply(SetCount.setCount(ConstantRange.exactly(count)))
    }

    private fun StandaloneLootEntry.Builder<*>.setCountBinomial(amount: Int, chance: Float): StandaloneLootEntry.Builder<*> {
        return function { setBinomialCount(amount, chance) }//acceptFunction(SetCount.builder(BinomialRange.of(n, p)))
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

    companion object {
        // block conditions
        private val SILK_TOUCH = MatchTool.toolMatches(ItemPredicate.Builder.item().hasEnchantment(EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.IntBound.atLeast(1))))
        private val PROSPECTING = MatchTool.toolMatches(ItemPredicate.Builder.item().hasEnchantment(EnchantmentPredicate(HEnchantments.PROSPECTING, MinMaxBounds.IntBound.atLeast(1))))
        // entity conditions
        private val ON_FIRE = EntityPredicate.Builder.entity().flags(EntityFlagsPredicate.Builder.flags().setOnFire(true).build())

        private val FORTUNE = ApplyBonus.addOreBonusCount(Enchantments.BLOCK_FORTUNE)
        //private val IS_STAG = EntityPredicate.Builder.create().flags(HEntityFlagsPredicate(isStagDeer = true)) // todo make this work

        // internal stuff
        private val GSON = GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create()
        private val LOGGER = LogManager.getLogger()
        fun StandaloneLootEntry.Builder<*>.setCountRandom(min: Float, max: Float): StandaloneLootEntry.Builder<*> {
            return function { setRandomCount(min, max) }
        }
    }
}