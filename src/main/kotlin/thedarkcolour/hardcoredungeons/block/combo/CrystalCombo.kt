package thedarkcolour.hardcoredungeons.block.combo

import net.minecraft.world.level.material.MapColor
import net.minecraft.world.level.storage.loot.LootPool
import net.minecraft.world.level.storage.loot.LootTable
import net.minecraft.world.level.storage.loot.entries.LootItem.lootTableItem
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator
import thedarkcolour.hardcoredungeons.block.base.BlockMaker
import thedarkcolour.hardcoredungeons.data.BlockLoot
import thedarkcolour.hardcoredungeons.registry.items.ItemMaker

class CrystalCombo(name: String, color: MapColor) : BlockCombo() {
    val crystal by BlockMaker.registerCrystal(name, color)
    val item by ItemMaker.simpleBlockItem(name, block = ::crystal)
    val shard by ItemMaker.resourceItem(name + "_shard")

    override fun addLoot(gen: BlockLoot) {
        gen.add(crystal, LootTable.lootTable().withPool(LootPool.lootPool().add(lootTableItem(shard).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 4.0f))))))
    }

    override fun addTags(tags: DataTags) {
        tags.pickaxe(crystal)
    }
}