package thedarkcolour.hardcoredungeons.data.advancements

import net.minecraft.advancements.Advancement
import net.minecraft.advancements.FrameType
import net.minecraft.advancements.criterion.InventoryChangeTrigger
import net.minecraft.item.Items
import net.minecraft.util.text.TranslationTextComponent
import thedarkcolour.hardcoredungeons.util.modLoc

class OverworldAdvancements : AdvancementGroup {
    override fun invoke(p1: (Advancement) -> Unit) {
        val root = AdvancementBuilder.builder()
            .withDisplay(
                Items.IRON_SWORD,
                TranslationTextComponent("advancements.hardcoredungeons.overworld.forge_gear.title"),
                TranslationTextComponent("advancements.hardcoredungeons.overworld.forge_gear.description"),
                modLoc("textures/gui/advancements/backgrounds/overworld"),
                FrameType.TASK,
                false,
                true,
                false
            )
            .withCriterion("crafted_item", InventoryChangeTrigger.Instance.forItems(Items.IRON_SWORD))
            .register(p1, "hardcoredungeons:overworld/root")
    }
}