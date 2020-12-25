package thedarkcolour.hardcoredungeons.block.decoration

import net.minecraft.block.*
import net.minecraft.block.DoorBlock
import net.minecraft.block.PaneBlock
import net.minecraft.block.PressurePlateBlock
import net.minecraft.block.SlabBlock
import net.minecraft.block.StairsBlock
import net.minecraft.block.TrapDoorBlock
import net.minecraft.block.WoodButtonBlock

// for all the classes with protected constructors
class DoorBlock(properties: Properties) : DoorBlock(properties)
class SlabBlock(properties: Properties) : SlabBlock(properties)
class PressurePlateBlock(sensitivity: Sensitivity, properties: Properties) : PressurePlateBlock(sensitivity, properties)
class WoodButtonBlock(properties: Properties) : WoodButtonBlock(properties)
class StairsBlock(block: Block, properties: Properties = Properties.from(block)) : StairsBlock(block::getDefaultState, properties)
class TrapDoorBlock(properties: Properties) : TrapDoorBlock(properties)
class PaneBlock(properties: Properties) : PaneBlock(properties)