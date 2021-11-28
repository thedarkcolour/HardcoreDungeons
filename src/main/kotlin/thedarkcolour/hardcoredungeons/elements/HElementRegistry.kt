package thedarkcolour.hardcoredungeons.elements

import net.minecraft.world.entity.EntityType
import net.minecraft.entity.LivingEntity
import net.minecraft.item.Item
import net.minecraft.item.Items
import thedarkcolour.hardcoredungeons.registry.HItems

object HElementRegistry {
    val ENTITY_ELEMENTS = hashMapOf<EntityType<out LivingEntity>, HElement>()

    fun registerArmor() {

    }

    fun registerWeapons() {
        addElement(Items.NETHERITE_AXE, HElement.FIRE)
        addElement(Items.NETHERITE_HOE, HElement.FIRE)
        addElement(Items.NETHERITE_PICKAXE, HElement.FIRE)
        addElement(Items.NETHERITE_SHOVEL, HElement.FIRE)
        addElement(Items.NETHERITE_SWORD, HElement.FIRE)
        addElement(HItems.MALACHITE_AXE, HElement.EARTH)
        addElement(HItems.MALACHITE_HOE, HElement.EARTH)
        addElement(HItems.MALACHITE_PICKAXE, HElement.EARTH)
        addElement(HItems.MALACHITE_SHOVEL, HElement.EARTH)
        addElement(HItems.MALACHITE_SWORD, HElement.EARTH)
    }

    fun addElement(type: EntityType<out LivingEntity>, element: HElement) {}

    fun addElement(item: Item, element: HElement) {}

    fun registerMobs() {
        addElement(EntityType.BAT, HElement.WIND)
        addElement(EntityType.BEE, HElement.WIND)
        addElement(EntityType.BLAZE, HElement.FIRE)
        addElement(EntityType.CAT, HElement.EARTH)
        addElement(EntityType.CAVE_SPIDER, HElement.EARTH)
        addElement(EntityType.CHICKEN, HElement.WIND)
        addElement(EntityType.COD, HElement.WATER)
        addElement(EntityType.COW, HElement.EARTH)
        addElement(EntityType.CREEPER, HElement.FIRE)
        addElement(EntityType.DOLPHIN, HElement.WATER)
        addElement(EntityType.DONKEY, HElement.EARTH)
        addElement(EntityType.DROWNED, HElement.WATER)
        addElement(EntityType.ELDER_GUARDIAN, HElement.WATER)
        addElement(EntityType.ENDER_DRAGON, HElement.WIND)
        addElement(EntityType.ENDERMAN, HElement.WIND)
        addElement(EntityType.ENDERMITE, HElement.WIND)
        addElement(EntityType.EVOKER, HElement.WIND)
        addElement(EntityType.FOX, HElement.EARTH)
        addElement(EntityType.GHAST, HElement.FIRE)
        addElement(EntityType.GIANT, HElement.EARTH)
        addElement(EntityType.GUARDIAN, HElement.WATER)
        addElement(EntityType.HOGLIN, HElement.FIRE)
        addElement(EntityType.HORSE, HElement.EARTH)
        addElement(EntityType.HUSK, HElement.FIRE)
        addElement(EntityType.ILLUSIONER, HElement.WIND)
        addElement(EntityType.IRON_GOLEM, HElement.EARTH)
        addElement(EntityType.LLAMA, HElement.EARTH)
        addElement(EntityType.MAGMA_CUBE, HElement.FIRE)
        addElement(EntityType.MULE, HElement.EARTH)
        addElement(EntityType.MOOSHROOM, HElement.EARTH)
        addElement(EntityType.OCELOT, HElement.EARTH)
        addElement(EntityType.PANDA, HElement.WATER)
        addElement(EntityType.PARROT, HElement.WIND)
        addElement(EntityType.PHANTOM, HElement.WIND)
        addElement(EntityType.PIG, HElement.EARTH)
        addElement(EntityType.PIGLIN, HElement.FIRE)
        addElement(EntityType.PIGLIN_BRUTE, HElement.FIRE)
        addElement(EntityType.PILLAGER, HElement.EARTH)
        addElement(EntityType.POLAR_BEAR, HElement.WATER)
        addElement(EntityType.PUFFERFISH, HElement.WATER)
        addElement(EntityType.RABBIT, HElement.EARTH)
        addElement(EntityType.RAVAGER, HElement.EARTH)
        addElement(EntityType.SALMON, HElement.WATER)
        addElement(EntityType.SHEEP, HElement.EARTH)
        addElement(EntityType.SHULKER, HElement.WIND)
        addElement(EntityType.SILVERFISH, HElement.EARTH)
        addElement(EntityType.SKELETON, HElement.EARTH)
        addElement(EntityType.SKELETON_HORSE, HElement.EARTH)
        addElement(EntityType.SLIME, HElement.EARTH)
        addElement(EntityType.SNOW_GOLEM, HElement.WATER)
        addElement(EntityType.SPIDER, HElement.EARTH)
        addElement(EntityType.SQUID, HElement.WATER)
        addElement(EntityType.STRAY, HElement.EARTH)
        addElement(EntityType.STRIDER, HElement.FIRE)
        addElement(EntityType.TRADER_LLAMA, HElement.EARTH)
        addElement(EntityType.TROPICAL_FISH, HElement.WATER)
        addElement(EntityType.TURTLE, HElement.WATER)
        addElement(EntityType.VEX, HElement.WIND)
        addElement(EntityType.VILLAGER, HElement.EARTH)
        addElement(EntityType.VINDICATOR, HElement.EARTH)
        addElement(EntityType.WANDERING_TRADER, HElement.EARTH)
        addElement(EntityType.WITCH, HElement.WATER)
        addElement(EntityType.WITHER, HElement.FIRE)
        addElement(EntityType.WITHER_SKELETON, HElement.FIRE)
        addElement(EntityType.WOLF, HElement.EARTH)
        addElement(EntityType.ZOGLIN, HElement.FIRE)
        addElement(EntityType.ZOMBIE, HElement.EARTH)
        addElement(EntityType.ZOMBIE_HORSE, HElement.EARTH)
        addElement(EntityType.ZOMBIE_VILLAGER, HElement.EARTH)
        addElement(EntityType.ZOMBIFIED_PIGLIN, HElement.FIRE)
        addElement(EntityType.PLAYER, HElement.EARTH)
    }
}