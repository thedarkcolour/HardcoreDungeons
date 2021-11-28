package thedarkcolour.hardcoredungeons.data.advancements

import net.minecraft.advancements.Advancement
import net.minecraft.advancements.FrameType
import net.minecraft.advancements.criterion.InventoryChangeTrigger
import net.minecraft.world.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.resources.ResourceLocation
import net.minecraft.util.text.TranslationTextComponent
import thedarkcolour.hardcoredungeons.HardcoreDungeons
import thedarkcolour.hardcoredungeons.util.modLoc

class OverworldAdvancements : AdvancementGroup("overworld") {
    override fun invoke(p1: (Advancement) -> Unit) {
        val root = advancement(
            name = "root",
            icon = ItemStack(Items.IRON_SWORD),
            background = modLoc("textures/gui/advancements/backgrounds/overworld"),
            frameType = FrameType.TASK,
            showToast = false,
            announceToChat = true,
            hidden = false
        )/*AdvancementBuilder.builder()
            .withDisplay(
                Items.IRON_SWORD,
                TranslationTextComponent("advancements.hardcoredungeons.overworld.root.title"),
                TranslationTextComponent("advancements.hardcoredungeons.overworld.root.description"),
                modLoc("textures/gui/advancements/backgrounds/overworld"),
                FrameType.TASK,
                false,
                true,
                false
            )*/
            .addCriterion("crafted_item", InventoryChangeTrigger.Instance.hasItems(Items.IRON_SWORD))
            .save(p1, "hardcoredungeons:overworld/root")

        //AdvancementBuilder.builder().withParent(root).withDisplay(
        //    HItems.
        //)
    }

    private fun advancement(
        name: String,
        icon: ItemStack,
        background: ResourceLocation?,
        frameType: FrameType,
        showToast: Boolean,
        announceToChat: Boolean,
        hidden: Boolean,
        parent: Advancement? = null,
    ): AdvancementBuilder {
        val prefix = "advancements.${HardcoreDungeons.ID}.${group}.${name}."
        val b = AdvancementBuilder.advancement().display(icon, TranslationTextComponent(prefix + "title"), TranslationTextComponent(prefix + "description"), background, frameType, showToast, announceToChat, hidden)
        if (parent != null) b.parent(parent)
        return b
    }
}