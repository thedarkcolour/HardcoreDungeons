package thedarkcolour.hardcoredungeons.block.combo

import net.minecraft.block.material.MaterialColor
import net.minecraft.loot.LootPool
import net.minecraft.loot.LootTable
import net.minecraft.loot.RandomValueRange
import net.minecraft.loot.functions.SetCount
import thedarkcolour.hardcoredungeons.block.base.BlockMaker
import thedarkcolour.hardcoredungeons.block.base.ItemMaker
import thedarkcolour.hardcoredungeons.data.LootGenerator

class CrystalCombo(name: String, color: MaterialColor) : ICombo {
    val crystal by BlockMaker.registerCrystal(name, color)
    val item by ItemMaker.simpleItem(name)

    override fun addLoot(gen: LootGenerator) {
        gen.addLoot(crystal, LootTable.lootTable().withPool(LootPool.lootPool().add(gen.item(item).apply(SetCount.setCount(RandomValueRange.between(1.0f, 4.0f))))))
    }
}