package thedarkcolour.hardcoredungeons.client.model

import net.minecraft.client.model.geom.ModelLayerLocation
import net.minecraftforge.client.event.EntityRenderersEvent
import thedarkcolour.hardcoredungeons.client.model.entity.DoeModel
import thedarkcolour.hardcoredungeons.client.model.entity.MagicBoltModel
import thedarkcolour.hardcoredungeons.legacy.ModelConfiguration
import thedarkcolour.hardcoredungeons.util.modLoc

object HModelLayers {
    val PENDANT = mainLayer("pendant")
    val MAGIC_BOLT = mainLayer("magic_bolt")
    val DOE = mainLayer("doe")

    private fun mainLayer(name: String): ModelLayerLocation {
        return ModelLayerLocation(modLoc(name), "main")
    }

    fun registerLayers(event: EntityRenderersEvent.RegisterLayerDefinitions) {
        event.registerModel(MAGIC_BOLT, MagicBoltModel::magicBoltModel)
        event.registerModel(DOE, DoeModel::doeModel)
    }

    private inline fun EntityRenderersEvent.RegisterLayerDefinitions.registerModel(loc: ModelLayerLocation, crossinline configuration: () -> ModelConfiguration) {
        registerLayerDefinition(loc) { configuration().asLayerDef() }
    }
}