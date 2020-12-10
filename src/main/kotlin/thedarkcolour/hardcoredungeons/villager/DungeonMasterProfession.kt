package thedarkcolour.hardcoredungeons.villager

import com.google.common.collect.ImmutableSet
import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.util.SoundEvent
import net.minecraft.village.PointOfInterestType

class DungeonMasterProfession(
    nameIn: String,
    pointOfInterestIn: PointOfInterestType,
    specificItemsIn: ImmutableSet<Item>,
    relatedWorldBlocksIn: ImmutableSet<Block>,
    soundIn: SoundEvent?
) : HVillagerProfession(nameIn, pointOfInterestIn, specificItemsIn, relatedWorldBlocksIn, soundIn) {

}