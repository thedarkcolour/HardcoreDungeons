package thedarkcolour.hardcoredungeons.data

import com.google.gson.GsonBuilder
import net.minecraft.advancements.critereon.*
import net.minecraft.data.CachedOutput
import net.minecraft.data.DataGenerator
import net.minecraft.data.DataProvider
import net.minecraft.data.loot.LootTableProvider
import net.minecraft.resources.ResourceLocation
import net.minecraft.util.StringRepresentable
import net.minecraft.world.entity.EntityType
import net.minecraft.world.item.Items
import net.minecraft.world.item.enchantment.Enchantments
import net.minecraft.world.level.ItemLike
import net.minecraft.world.level.block.*
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf
import net.minecraft.world.level.block.state.properties.Property
import net.minecraft.world.level.block.state.properties.SlabType
import net.minecraft.world.level.storage.loot.LootContext
import net.minecraft.world.level.storage.loot.LootPool
import net.minecraft.world.level.storage.loot.LootPool.lootPool
import net.minecraft.world.level.storage.loot.LootTable
import net.minecraft.world.level.storage.loot.LootTable.lootTable
import net.minecraft.world.level.storage.loot.LootTables
import net.minecraft.world.level.storage.loot.entries.LootItem
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount
import net.minecraft.world.level.storage.loot.functions.ApplyExplosionDecay.explosionDecay
import net.minecraft.world.level.storage.loot.functions.LootingEnchantFunction.lootingMultiplier
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction.setCount
import net.minecraft.world.level.storage.loot.functions.SmeltItemFunction.smelted
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets
import net.minecraft.world.level.storage.loot.predicates.BonusLevelTableCondition
import net.minecraft.world.level.storage.loot.predicates.ConditionUserBuilder
import net.minecraft.world.level.storage.loot.predicates.ExplosionCondition
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition.hasProperties
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost
import net.minecraft.world.level.storage.loot.predicates.MatchTool.toolMatches
import net.minecraft.world.level.storage.loot.providers.number.BinomialDistributionGenerator.binomial
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue.exactly
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator.between
import net.minecraftforge.common.Tags
import net.minecraftforge.registries.ForgeRegistries
import org.apache.logging.log4j.LogManager
import thedarkcolour.hardcoredungeons.HardcoreDungeons
import thedarkcolour.hardcoredungeons.registry.HEntities
import thedarkcolour.hardcoredungeons.registry.block.HBlocks
import thedarkcolour.hardcoredungeons.registry.items.*
import thedarkcolour.hardcoredungeons.util.registryName

class LootGenerator(private val generator: DataGenerator) : LootTableProvider(generator) {
    private val entityTables = hashMapOf<EntityType<*>, LootTable.Builder>()
    private val blockTables = hashMapOf<Block, LootTable.Builder>()

    private fun addEntityLoot() {
        entityTables[HEntities.DEER] = lootTable()
            .withPool(lootPool().setRolls(exactly(1f))
                .add(item(DEER_ANTLER_ITEM))
                .`when`(randomChanceAndLootingBoost(0.075f, 0.02f))
            )
            .withPool(lootPool().setRolls(exactly(1f))
                .add(item(VENISON_ITEM)
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

        addLoot(
            HBlocks.CASTLETON_VASE, lootTable().withPool(lootPool()
            .add(item(BULLET_ITEM).setWeight(5).apply(setCount(between(3.0f, 7.0f))))
            .add(item(Items.ARROW).setWeight(5).apply(setCount(between(2.0f, 6.0f))))
            .add(item(Items.MUSHROOM_STEW).setWeight(3))
            .add(item(CASTLE_GEM_ITEM).setWeight(1))
        ))

        addLoot(
            HBlocks.CASTLETON_TREASURE_VASE, lootTable().withPool(
            lootPool()
            .add(item(CASTLE_GEM_ITEM).apply(setCount(binomial(6, 0.6f))).setWeight(10))
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
        dropSilk(block = HBlocks.SCRAP_METAL, normal = item(Items.IRON_NUGGET).apply(setCount(binomial(5, 0.45f))), silk = item(
            HBlocks.SCRAP_METAL))
    }

    private fun singlePool(block: Block, poolFunction: LootPool.Builder.() -> Unit) {
        val pool = lootPool()
        poolFunction(pool)

        addLoot(block, lootTable().withPool(pool))
    }

    private fun dropSelf(block: Block) {
        singlePool(block) {
            add(item(block).whenSurvivesExplosion())
        }
    }

    fun dropSilk(block: Block, normal: LootPoolEntryContainer.Builder<*>?, silk: LootPoolEntryContainer.Builder<*>) {
        var entry = silk.`when`(SILK_TOUCH)

        if (normal != null) {
            entry = entry.otherwise(normal.whenSurvivesExplosion())
        }

        singlePool(block) { add(entry) }
    }

    private fun dropSlabs(block: Block) {
        singlePool(block) {
            add(item(block).apply(setCount(exactly(2f)).`when`(propertyCondition(block, SlabBlock.TYPE, SlabType.DOUBLE))).apply(explosionDecay()))
        }
    }

    private fun dropDoor(block: Block) {
        singlePool(block) { add(item(block).whenSurvivesExplosion().`when`(propertyCondition(block, BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.LOWER))) }
        //blockTables[block] = LootTable.builder().withPool(LootPool.builder().rolls(ConstantRange.of(1)).add(ItemLootEntry.builder(block.asItem()).acceptCondition(BlockStateProperty.builder(block).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withProp(BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.LOWER)))).survivesExplosion())
    }

    private fun dropPottedPlant(block: FlowerPotBlock) {
        blockTables[block] = lootTable().withPool(constPool().add(item(Items.FLOWER_POT)).whenSurvivesExplosion()).withPool(constPool().add(item(block.content)).whenSurvivesExplosion())
    }

    // rolls constant 1
    private fun constPool(): LootPool.Builder {
        return lootPool().setRolls(exactly(1f))
    }

    private fun <T> propertyCondition(block: Block, property: Property<T>, value: T): LootItemBlockStatePropertyCondition.Builder where T : StringRepresentable, T : Comparable<T> {
        return LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(property, value))
    }

    fun item(item: ItemLike): LootPoolSingletonContainer.Builder<*> {
        return LootItem.lootTableItem(item)
    }

    fun addLoot(block: Block, loot: LootTable.Builder) {
        blockTables[block] = loot
    }

    override fun run(cache: CachedOutput) {
        addBlockLoot()
        addEntityLoot()

        val namespacedTables = hashMapOf<ResourceLocation, LootTable>()

        for ((key, value) in blockTables) {
            namespacedTables[key.lootTable] = value.setParamSet(LootContextParamSets.BLOCK).build()
        }

        for (entry in entityTables) {
            namespacedTables[entry.key.defaultLootTable] = entry.value.setParamSet(LootContextParamSets.ENTITY).build()
        }

        // Do not redefine the empty loot table
        namespacedTables.remove(ResourceLocation("minecraft:empty"))

        writeLootTables(namespacedTables, cache)
    }

    private fun writeLootTables(tables: Map<ResourceLocation, LootTable>, cache: CachedOutput) {
        val output = generator.outputFolder

        for ((key, table) in tables) {
            val path = output.resolve("data/" + key.namespace + "/loot_tables/" + key.path + ".json")

            try {
                DataProvider.saveStable(cache, LootTables.serialize(table), path)
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
            .withPool(lootPool().add(item(leaves).`when`(SILK_TOUCH.or(SHEARS)).otherwise(item(sapling).whenSurvivesExplosion().`when`(NORMAL_LEAVES_SAPLING_CHANCES))))
            .withPool(lootPool().add(item(Items.STICK).`when`(NORMAL_LEAVES_STICK_CHANCES).apply(setCount(between(1f, 2f)))).`when`(SILK_TOUCH.or(SHEARS).invert())))
    }

    companion object {
        // block conditions
        private val SILK_TOUCH = toolMatches(ItemPredicate.Builder.item().hasEnchantment(EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.Ints.atLeast(1))))
        private val SHEARS = toolMatches(ItemPredicate.Builder.item().of(Tags.Items.SHEARS))
        // entity conditions
        private val ON_FIRE = EntityPredicate.Builder.entity().flags(EntityFlagsPredicate.Builder.flags().setOnFire(true).build())

        private val FORTUNE = ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE)
        private val NORMAL_LEAVES_SAPLING_CHANCES = BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, 0.05f, 0.0625f, 0.083333336f, 0.1f)
        private val NORMAL_LEAVES_STICK_CHANCES = BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, 0.02f, 0.022222223f, 0.025f, 0.033333335f, 0.1f)
        //private val IS_STAG = EntityPredicate.Builder.create().flags(HEntityFlagsPredicate(isStagDeer = true)) // todo make this work

        // internal stuff
        private val LOGGER = LogManager.getLogger()

        private fun <T : ConditionUserBuilder<T>> ConditionUserBuilder<T>.whenSurvivesExplosion(): T {
            return `when`(ExplosionCondition.survivesExplosion())
        }
    }
}