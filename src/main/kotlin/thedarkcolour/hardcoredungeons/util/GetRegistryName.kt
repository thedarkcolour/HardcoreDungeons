package thedarkcolour.hardcoredungeons.util

import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.minecraftforge.registries.ForgeRegistries

val Block.registryName: ResourceLocation?
    get() = ForgeRegistries.BLOCKS.getKey(this)
val Item.registryName: ResourceLocation?
    get() = ForgeRegistries.ITEMS.getKey(this)
