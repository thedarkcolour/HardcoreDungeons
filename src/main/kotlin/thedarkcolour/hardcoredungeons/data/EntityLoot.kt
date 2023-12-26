package thedarkcolour.hardcoredungeons.data

import net.minecraft.advancements.critereon.EntityFlagsPredicate
import net.minecraft.advancements.critereon.EntityPredicate
import net.minecraft.data.loot.EntityLootSubProvider
import net.minecraft.world.entity.EntityType
import net.minecraft.world.flag.FeatureFlags
import net.minecraft.world.level.storage.loot.LootContext
import net.minecraft.world.level.storage.loot.LootPool
import net.minecraft.world.level.storage.loot.LootTable
import net.minecraft.world.level.storage.loot.entries.LootItem.lootTableItem
import net.minecraft.world.level.storage.loot.functions.LootingEnchantFunction
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction
import net.minecraft.world.level.storage.loot.functions.SmeltItemFunction
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition.hasProperties
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceWithLootingCondition
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator
import thedarkcolour.hardcoredungeons.registry.HEntities
import thedarkcolour.hardcoredungeons.registry.items.DEER_ANTLER_ITEM
import thedarkcolour.hardcoredungeons.registry.items.VENISON_ITEM
import java.util.stream.Stream

class EntityLoot : EntityLootSubProvider(FeatureFlags.DEFAULT_FLAGS) {
    private val added = arrayListOf<EntityType<*>>()

    override fun getKnownEntityTypes(): Stream<EntityType<*>> {
        return added.stream()
    }

    override fun add(entityType: EntityType<*>, builder: LootTable.Builder) {
        super.add(entityType, builder)
        this.added.add(entityType)
    }

    override fun generate() {
        add(HEntities.DEER, LootTable.lootTable()
            .withPool(
                LootPool.lootPool().setRolls(ConstantValue.exactly(1f))
                .add(lootTableItem(DEER_ANTLER_ITEM))
                .`when`(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.075f, 0.02f))
            )
            .withPool(
                LootPool.lootPool().setRolls(ConstantValue.exactly(1f))
                .add(lootTableItem(VENISON_ITEM)
                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 3.0f)))
                    .apply(SmeltItemFunction.smelted().`when`(hasProperties(LootContext.EntityTarget.THIS, ON_FIRE))).apply(
                        LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F))
                    ))
            ))
    }

    companion object {
        // entity conditions
        private val ON_FIRE = EntityPredicate.Builder.entity().flags(EntityFlagsPredicate.Builder.flags().setOnFire(true).build())
        //private val IS_STAG = EntityPredicate.Builder.create().flags(HEntityFlagsPredicate(isStagDeer = true)) // todo make this work
    }
}