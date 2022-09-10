package thedarkcolour.hardcoredungeons.client.model

import net.minecraft.client.model.geom.ModelLayerLocation
import net.minecraftforge.client.event.EntityRenderersEvent
import thedarkcolour.hardcoredungeons.client.model.armor.MushroomArmorModel
import thedarkcolour.hardcoredungeons.client.model.armor.PendantModel
import thedarkcolour.hardcoredungeons.client.model.entity.DeerModel
import thedarkcolour.hardcoredungeons.client.model.entity.FrayedSoulModel
import thedarkcolour.hardcoredungeons.client.model.entity.KnightlyJuggernautModel
import thedarkcolour.hardcoredungeons.client.model.entity.MagicBoltModel
import thedarkcolour.hardcoredungeons.legacy.ModelConfiguration
import thedarkcolour.hardcoredungeons.util.modLoc

object HModelLayers {
    val MUSHROOM_ARMOR = mainLayer("pendant")
    val PENDANT = mainLayer("pendant")

    val MAGIC_BOLT = mainLayer("magic_bolt")
    val FRAYED_SOUL = mainLayer("frayed_soul")
    val KNIGHTLY_JUGGERNAUT = mainLayer("knightly_juggernaut")
    val DOE = mainLayer("doe")
    val STAG = mainLayer("stag")

    private fun mainLayer(name: String): ModelLayerLocation {
        return ModelLayerLocation(modLoc(name), "main")
    }

    fun registerLayers(event: EntityRenderersEvent.RegisterLayerDefinitions) {
        event.registerModel(MUSHROOM_ARMOR, MushroomArmorModel::mushroomArmorModel)
        event.registerModel(PENDANT, PendantModel::pendantModel)

        event.registerModel(MAGIC_BOLT, MagicBoltModel::magicBoltModel)
        event.registerModel(FRAYED_SOUL, FrayedSoulModel::frayedSoulModel)
        event.registerModel(KNIGHTLY_JUGGERNAUT, KnightlyJuggernautModel::knightlyJuggernautModel)
        event.registerModel(DOE, DeerModel::doeModel)
        event.registerModel(STAG, DeerModel::stagModel)
    }

    private inline fun EntityRenderersEvent.RegisterLayerDefinitions.registerModel(loc: ModelLayerLocation, crossinline configuration: () -> ModelConfiguration) {
        registerLayerDefinition(loc) { configuration().asLayerDef() }
    }
}