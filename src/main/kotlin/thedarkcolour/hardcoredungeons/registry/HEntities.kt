package thedarkcolour.hardcoredungeons.registry

import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.entity.SheepRenderer
import net.minecraft.client.renderer.entity.SpriteRenderer
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityClassification
import net.minecraft.entity.EntitySize
import net.minecraft.entity.EntityType
import net.minecraft.entity.EntityType.Builder
import net.minecraft.entity.ai.attributes.AttributeModifierMap
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes
import net.minecraft.util.ResourceLocation
import net.minecraftforge.fml.client.registry.ClientRegistry
import net.minecraftforge.fml.client.registry.RenderingRegistry
import net.minecraftforge.registries.IForgeRegistry
import thedarkcolour.hardcoredungeons.entity.HEntityType
import thedarkcolour.hardcoredungeons.entity.castleton.deer.CastletonDeerEntity
import thedarkcolour.hardcoredungeons.entity.castleton.deer.DeerRenderer
import thedarkcolour.hardcoredungeons.entity.castleton.frayedsoul.FrayedSoulEntity
import thedarkcolour.hardcoredungeons.entity.castleton.frayedsoul.FrayedSoulRenderer
import thedarkcolour.hardcoredungeons.entity.castleton.infinity.BlackStarEntity
import thedarkcolour.hardcoredungeons.entity.castleton.infinity.InfinityEntity
import thedarkcolour.hardcoredungeons.entity.castleton.knightlyjuggernaut.KnightlyJuggernautEntity
import thedarkcolour.hardcoredungeons.entity.castleton.knightlyjuggernaut.KnightlyJuggernautRenderer
import thedarkcolour.hardcoredungeons.entity.castleton.voidrunner.VoidRunnerEntity
import thedarkcolour.hardcoredungeons.entity.castleton.voidrunner.VoidRunnerRenderer
import thedarkcolour.hardcoredungeons.entity.misc.bullet.SmallBulletEntity
import thedarkcolour.hardcoredungeons.entity.misc.cheeky.CheekyEntity
import thedarkcolour.hardcoredungeons.entity.misc.cheeky.CheekyRenderer
import thedarkcolour.hardcoredungeons.entity.misc.magic.MagicBoltEntity
import thedarkcolour.hardcoredungeons.entity.rainbowland.sheep.RainbowlandSheepEntity
import thedarkcolour.hardcoredungeons.util.modLoc

@Suppress("MemberVisibilityCanBePrivate")
object HEntities {
    // put the potato
    // in the sack
    val CHEEKY = Builder
        .create(::CheekyEntity, EntityClassification.AMBIENT)
        .build(modLoc("cheeky"))
    val INFINITY = Builder
        .create(::InfinityEntity, EntityClassification.MISC)
        .immuneToFire()
        .build(modLoc("infinity"))

    // you ever walk into a bush and burn to death? well this guy walked into a bush and burned to death.
    val FRAYED_SOUL = Builder
        .create(::FrayedSoulEntity, EntityClassification.MONSTER)
        .build(modLoc("frayed_soul"))
    val VOID_RUNNER = Builder
        .create(::VoidRunnerEntity, EntityClassification.MONSTER)
        .build(modLoc("void_runner"))
    val CASTLETON_DEER = Builder
        .create(::CastletonDeerEntity, EntityClassification.CREATURE)
        .build(modLoc("castleton_deer"))
    val KNIGHTLY_JUGGERNAUT = Builder
        .create(::KnightlyJuggernautEntity, EntityClassification.CREATURE)
        .build(modLoc("knightly_juggernaut"))

    val RAINBOWLAND_SHEEP = Builder
        .create(::RainbowlandSheepEntity, EntityClassification.CREATURE)
        .build(modLoc("rainbowland_sheep"))

    // projectile
    val SMALL_BULLET = HEntityType(
        factory = ::SmallBulletEntity,
        classification = EntityClassification.MISC,
        velocityUpdateSpecial = { true },
        size = EntitySize.flexible(0.2f, 0.2f),
        id = modLoc("small_bullet")
    )
    val MAGIC_BOLT = HEntityType(
        factory = ::MagicBoltEntity,
        classification = EntityClassification.MISC,
        velocityUpdateSpecial = { true },
        id = modLoc("magic_bolt")
    )
    val BLACK_STAR = HEntityType(
        factory = ::BlackStarEntity,
        classification = EntityClassification.MISC,
        velocityUpdateSpecial = { true },
        id = modLoc("black_star")
    )

    val BLUE_EYES_SHADER = modLoc("shaders/post/blue_eyes.json")

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

        // todo attributes
        GlobalEntityTypeAttributes.put(CHEEKY, CheekyEntity.ATTRIBUTES.create())
        GlobalEntityTypeAttributes.put(FRAYED_SOUL, AttributeModifierMap(hashMapOf()))
        GlobalEntityTypeAttributes.put(VOID_RUNNER, AttributeModifierMap(hashMapOf()))
        GlobalEntityTypeAttributes.put(CASTLETON_DEER, AttributeModifierMap(hashMapOf()))
        GlobalEntityTypeAttributes.put(KNIGHTLY_JUGGERNAUT, AttributeModifierMap(hashMapOf()))
        GlobalEntityTypeAttributes.put(RAINBOWLAND_SHEEP, AttributeModifierMap(hashMapOf()))
        GlobalEntityTypeAttributes.put(INFINITY, AttributeModifierMap(hashMapOf()))
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