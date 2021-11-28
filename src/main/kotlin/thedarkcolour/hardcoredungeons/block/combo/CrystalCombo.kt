package thedarkcolour.hardcoredungeons.block.combo

import net.minecraft.block.material.MaterialColor
import net.minecraft.loot.LootPool
import net.minecraft.loot.LootTable
import thedarkcolour.hardcoredungeons.block.base.HBlockMaker
import thedarkcolour.hardcoredungeons.block.base.ItemMaker
import thedarkcolour.hardcoredungeons.data.LootGenerator
import thedarkcolour.hardcoredungeons.data.LootGenerator.Companion.setCountRandom
import thedarkcolour.hardcoredungeons.registry.HItems

class CrystalCombo(name: String, color: MaterialColor) : ICombo {
    val crystal by HBlockMaker.registerCrystal(name, color)
    val item by ItemMaker.simpleItem(name)

    override fun addLoot(gen: LootGenerator) {
        gen.addLoot(crystal, LootTable.lootTable().withPool(LootPool.lootPool()
            .add(gen.item(HItems.MALACHITE_CRYSTAL).setCountRandom(1.0f, 4.0f)))
        )
    }
}