package thedarkcolour.hardcoredungeons.block.base

import net.minecraft.world.level.block.*
import thedarkcolour.hardcoredungeons.block.base.properties.HProperties

// todo deprecate and eventually remove
// Door, Slab, Pressure Plate, Button, Stairs, Trapdoor, Wall
class HDoorBlock(properties: HProperties) : DoorBlock(properties.build())
class HSlabBlock(properties: HProperties) : SlabBlock(properties.build())
class HPressurePlateBlock(sensitivity: Sensitivity, properties: HProperties) : PressurePlateBlock(sensitivity, properties.build())
class HWoodButtonBlock(properties: HProperties) : WoodButtonBlock(properties.build())
class HStairsBlock(block: Block, properties: HProperties = HProperties.copy(block)) : StairBlock(block::defaultBlockState, properties.build())
class HTrapDoorBlock(properties: HProperties) : TrapDoorBlock(properties.build())
class HWallBlock(properties: HProperties) : WallBlock(properties.build())