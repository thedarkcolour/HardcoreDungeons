package thedarkcolour.hardcoredungeons.data

import net.minecraft.loot.BinomialRange.binomial
import net.minecraft.loot.ConstantRange
import net.minecraft.loot.ILootFunctionConsumer
import net.minecraft.loot.RandomValueRange.between
import net.minecraft.loot.functions.SetCount.setCount

fun <T : ILootFunctionConsumer<out T>> T.setCountBinomial(n: Int, p: Float) = apply(setCount(binomial(n, p)))

fun <T : ILootFunctionConsumer<out T>> T.setCountRandom(n: Float, p: Float) = apply(setCount(between(n, p)))

fun <T : ILootFunctionConsumer<out T>> T.setCount(count: Int) = apply(setCount(ConstantRange.exactly(count)))