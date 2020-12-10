package thedarkcolour.hardcoredungeons.villager

import com.google.common.collect.ImmutableSet
import net.minecraft.block.Block
import net.minecraft.entity.merchant.villager.VillagerProfession
import net.minecraft.item.Item
import net.minecraft.util.SoundEvent
import net.minecraft.village.PointOfInterestType
import thedarkcolour.hardcoredungeons.HardcoreDungeons
import thedarkcolour.hardcoredungeons.registry.setRegistryKey

open class HVillagerProfession(
    nameIn: String,
    pointOfInterestIn: PointOfInterestType,
    specificItemsIn: ImmutableSet<Item>,
    relatedWorldBlocksIn: ImmutableSet<Block>,
    soundIn: SoundEvent?
) : VillagerProfession(HardcoreDungeons.ID + ":" + nameIn, pointOfInterestIn, specificItemsIn, relatedWorldBlocksIn, soundIn) {
    init {
        setRegistryKey(nameIn)
    }
}