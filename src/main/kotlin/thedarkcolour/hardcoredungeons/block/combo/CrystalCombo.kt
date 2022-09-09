package thedarkcolour.hardcoredungeons.block.combo

import net.minecraft.world.level.material.MaterialColor
import net.minecraft.world.level.storage.loot.LootPool
import net.minecraft.world.level.storage.loot.LootTable
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator
import thedarkcolour.hardcoredungeons.block.base.BlockMaker
import thedarkcolour.hardcoredungeons.block.base.ItemMaker
import thedarkcolour.hardcoredungeons.data.LootGenerator

class CrystalCombo(name: String, color: MaterialColor) : ICombo {
    val crystal by BlockMaker.registerCrystal(name, color)
    val item by ItemMaker.resourceItem(name)

    override fun addLoot(gen: LootGenerator) {
        gen.addLoot(crystal, LootTable.lootTable().withPool(LootPool.lootPool().add(gen.item(item).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 4.0f))))))
    }
}