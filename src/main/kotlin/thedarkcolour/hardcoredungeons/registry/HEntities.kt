package thedarkcolour.hardcoredungeons.registry

import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.entity.SheepRenderer
import net.minecraft.client.renderer.entity.SpriteRenderer
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityClassification
import net.minecraft.entity.EntityType
import net.minecraft.entity.EntityType.Builder
import net.minecraft.util.ResourceLocation
import net.minecraftforge.fml.client.registry.ClientRegistry
import net.minecraftforge.fml.client.registry.RenderingRegistry
import net.minecraftforge.registries.IForgeRegistry
import thedarkcolour.hardcoredungeons.HardcoreDungeons
import thedarkcolour.hardcoredungeons.entity.castleton.deer.CastletonDeerEntity
import thedarkcolour.hardcoredungeons.entity.castleton.deer.DeerRenderer
import thedarkcolour.hardcoredungeons.entity.castleton.frayedsoul.FrayedSoulEntity
import thedarkcolour.hardcoredungeons.entity.castleton.frayedsoul.FrayedSoulRenderer
import thedarkcolour.hardcoredungeons.entity.castleton.knightlyjuggernaut.KnightlyJuggernautEntity
import thedarkcolour.hardcoredungeons.entity.castleton.knightlyjuggernaut.KnightlyJuggernautRenderer
import thedarkcolour.hardcoredungeons.entity.castleton.voidrunner.VoidRunnerEntity
import thedarkcolour.hardcoredungeons.entity.castleton.voidrunner.VoidRunnerRenderer
import thedarkcolour.hardcoredungeons.entity.misc.bullet.SmallBulletEntity
import thedarkcolour.hardcoredungeons.entity.misc.cheeky.CheekyEntity
import thedarkcolour.hardcoredungeons.entity.misc.cheeky.CheekyRenderer
import thedarkcolour.hardcoredungeons.entity.misc.magic.MagicBoltEntity
import thedarkcolour.hardcoredungeons.entity.rainbowland.sheep.RainbowlandSheepEntity

@Suppress("MemberVisibilityCanBePrivate")
object HEntities {
    // put the potato
    // in the sack
    val CHEEKY = Builder
        .create(::CheekyEntity, EntityClassification.AMBIENT)
        .build(HardcoreDungeons.loc("cheeky"))

    // you ever walk into a bush and burn to death? well this guy walked into a bush and burned to death.
    val FRAYED_SOUL = Builder
        .create(::FrayedSoulEntity, EntityClassification.MONSTER)
        .build(HardcoreDungeons.loc("frayed_soul"))
    val VOID_RUNNER = Builder
        .create(::VoidRunnerEntity, EntityClassification.MONSTER)
        .build(HardcoreDungeons.loc("void_runner"))
    val CASTLETON_DEER = Builder
        .create(::CastletonDeerEntity, EntityClassification.CREATURE)
        .build(HardcoreDungeons.loc("castleton_deer"))
    val KNIGHTLY_JUGGERNAUT = Builder
        .create(::KnightlyJuggernautEntity, EntityClassification.CREATURE)
        .build(HardcoreDungeons.loc("knightly_juggernaut"))

    val RAINBOWLAND_SHEEP = Builder
        .create(::RainbowlandSheepEntity, EntityClassification.CREATURE)
        .build(HardcoreDungeons.loc("rainbowland_sheep"))

    val SMALL_BULLET = Builder
        .create(::SmallBulletEntity, EntityClassification.MISC)
        .setShouldReceiveVelocityUpdates(true)
        .size(0.2f, 0.2f)
        .build(HardcoreDungeons.loc("small_bullet"))
    val MAGIC_BOLT = Builder
        .create(::MagicBoltEntity, EntityClassification.MISC)
        //.setCustomClientFactory(::MagicBoltEntity)
        .setShouldReceiveVelocityUpdates(true)
        .build(HardcoreDungeons.loc("magic_bolt"))

    val BLUE_EYES_SHADER = HardcoreDungeons.loc("shaders/post/blue_eyes.json")

    // preserves generic type and bundles build + setRegistryName into one call
    private fun <T : Entity> Builder<T>.build(name: ResourceLocation): EntityType<T> {
        val type = build(name.toString())
        type.registryName = name
        return type
    }

    fun registerEntities(entities: IForgeRegistry<EntityType<*>>) {
        entities.register(CHEEKY)

        entities.register(FRAYED_SOUL)
        entities.register(VOID_RUNNER)
        entities.register(CASTLETON_DEER)
        entities.register(KNIGHTLY_JUGGERNAUT)

        entities.register(RAINBOWLAND_SHEEP)

        entities.register(SMALL_BULLET)
        entities.register(MAGIC_BOLT)
    }

    fun registerEntityRenderers() {
        RenderingRegistry.registerEntityRenderingHandler(CHEEKY, ::CheekyRenderer)
        RenderingRegistry.registerEntityRenderingHandler(FRAYED_SOUL, ::FrayedSoulRenderer)
        RenderingRegistry.registerEntityRenderingHandler(VOID_RUNNER, ::VoidRunnerRenderer)
        RenderingRegistry.registerEntityRenderingHandler(RAINBOWLAND_SHEEP, ::SheepRenderer)
        RenderingRegistry.registerEntityRenderingHandler(CASTLETON_DEER, ::DeerRenderer)
        RenderingRegistry.registerEntityRenderingHandler(KNIGHTLY_JUGGERNAUT, ::KnightlyJuggernautRenderer)
        RenderingRegistry.registerEntityRenderingHandler(SMALL_BULLET) { SpriteRenderer(it, Minecraft.getInstance().itemRenderer) }
        RenderingRegistry.registerEntityRenderingHandler(MAGIC_BOLT) { SpriteRenderer(it, Minecraft.getInstance().itemRenderer) }
    }

    fun registerEntityShaders() {
        ClientRegistry.registerEntityShader(FrayedSoulEntity::class.java, BLUE_EYES_SHADER)
        ClientRegistry.registerEntityShader(VoidRunnerEntity::class.java, BLUE_EYES_SHADER)
        ClientRegistry.registerEntityShader(CastletonDeerEntity::class.java, BLUE_EYES_SHADER)
        ClientRegistry.registerEntityShader(KnightlyJuggernautEntity::class.java, BLUE_EYES_SHADER)
    }
}