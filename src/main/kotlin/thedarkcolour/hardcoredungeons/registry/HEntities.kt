package thedarkcolour.hardcoredungeons.registry

import net.minecraft.client.renderer.entity.ThrownItemRenderer
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntityDimensions
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.EntityType.Builder
import net.minecraft.world.entity.MobCategory
import net.minecraftforge.client.event.EntityRenderersEvent
import net.minecraftforge.event.entity.EntityAttributeCreationEvent
import net.minecraftforge.fmlclient.registry.ClientRegistry
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import thedarkcolour.hardcoredungeons.client.renderer.entity.DeerRenderer
import thedarkcolour.hardcoredungeons.client.renderer.entity.FrayedSoulRenderer
import thedarkcolour.hardcoredungeons.client.renderer.entity.KnightlyJuggernautRenderer
import thedarkcolour.hardcoredungeons.client.renderer.entity.VoidRunnerRenderer
import thedarkcolour.hardcoredungeons.entity.HEntityType
import thedarkcolour.hardcoredungeons.entity.castleton.frayedsoul.FrayedSoulEntity
import thedarkcolour.hardcoredungeons.entity.castleton.knightlyjuggernaut.KnightlyJuggernautEntity
import thedarkcolour.hardcoredungeons.entity.castleton.voidrunner.VoidRunnerEntity
import thedarkcolour.hardcoredungeons.entity.overworld.deer.CastletonDeerEntity
import thedarkcolour.hardcoredungeons.entity.overworld.deer.DeerEntity
import thedarkcolour.hardcoredungeons.entity.overworld.mazeboss.MazeBossEntity
import thedarkcolour.hardcoredungeons.entity.overworld.mazeboss.MazeBossRenderer
import thedarkcolour.hardcoredungeons.entity.overworld.mushroomarcher.MushroomArcherEntity
import thedarkcolour.hardcoredungeons.entity.overworld.mushroomarcher.MushroomArcherRenderer
import thedarkcolour.hardcoredungeons.entity.projectile.bullet.SmallBulletEntity
import thedarkcolour.hardcoredungeons.entity.projectile.magic.MagicBoltEntity
import thedarkcolour.hardcoredungeons.util.modLoc
import thedarkcolour.kotlinforforge.forge.registerObject

@Suppress("MemberVisibilityCanBePrivate")
object HEntities {
    val ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, "entity_types")

    val FRAYED_SOUL = Builder
        .of(::FrayedSoulEntity, MobCategory.MONSTER)
        .build(modLoc("frayed_soul"))
    val VOID_RUNNER = Builder
        .of(::VoidRunnerEntity, MobCategory.MONSTER)
        .build(modLoc("void_runner"))
    val CASTLETON_DEER = Builder
        .of(::CastletonDeerEntity, MobCategory.CREATURE)
        .build(modLoc("castleton_deer"))
    val DEER = Builder
        .of(::DeerEntity, MobCategory.CREATURE)
        .build(modLoc("deer"))
    val KNIGHTLY_JUGGERNAUT = Builder
        .of(::KnightlyJuggernautEntity, MobCategory.CREATURE)
        .build(modLoc("knightly_juggernaut"))
    val MAZE_BOSS = Builder
        .of(::MazeBossEntity, MobCategory.MONSTER)
        .build(modLoc("maze_boss"))
    val MUSHROOM_ARCHER = Builder
        .of(::MushroomArcherEntity, MobCategory.MONSTER)
        .build(modLoc("mushroom_archer"))

    // projectile
    val SMALL_BULLET by ENTITY_TYPES.registerObject("small_bullet") {
        HEntityType(
            factory = ::SmallBulletEntity,
            classification = MobCategory.MISC,
            velocityUpdateSpecial = { true },
            size = EntityDimensions.scalable(0.2f, 0.2f)
        )
    }
    val MAGIC_BOLT by ENTITY_TYPES.registerObject("magic_bolt") {HEntityType(
        factory = ::MagicBoltEntity,
        classification = MobCategory.MISC,
        velocityUpdateSpecial = { true }
    )}

    val BLUE_EYES_SHADER = modLoc("shaders/post/blue_eyes.json")

    // preserves generic type and bundles build + setRegistryName into one call
    private fun <T : Entity> Builder<T>.build(name: ResourceLocation): EntityType<T> {
        val type = build(name.toString())
        type.registryName = name
        return type
    }

    fun registerEntityAttributes(event: EntityAttributeCreationEvent) {
        event.put(FRAYED_SOUL, FrayedSoulEntity.ATTRIBUTES.build())
        event.put(VOID_RUNNER, VoidRunnerEntity.ATTRIBUTES.build())
        event.put(CASTLETON_DEER, DeerEntity.DEFAULT_ATTRIBUTES.build())
        event.put(DEER, DeerEntity.DEFAULT_ATTRIBUTES.build())
        event.put(KNIGHTLY_JUGGERNAUT, KnightlyJuggernautEntity.ATTRIBUTES.build())
        event.put(MAZE_BOSS, MazeBossEntity.ATTRIBUTES.build())
        event.put(MUSHROOM_ARCHER, MushroomArcherEntity.ATTRIBUTES.build())
    }

    fun registerEntityRenderers(event: EntityRenderersEvent.RegisterRenderers) {
        event.registerEntityRenderer(MAZE_BOSS, ::MazeBossRenderer)
        event.registerEntityRenderer(MUSHROOM_ARCHER, ::MushroomArcherRenderer)
        event.registerEntityRenderer(DEER, ::DeerRenderer)
        event.registerEntityRenderer(FRAYED_SOUL, ::FrayedSoulRenderer)
        event.registerEntityRenderer(VOID_RUNNER, ::VoidRunnerRenderer)
        event.registerEntityRenderer(CASTLETON_DEER, ::DeerRenderer)
        event.registerEntityRenderer(KNIGHTLY_JUGGERNAUT, ::KnightlyJuggernautRenderer)
        event.registerEntityRenderer(SMALL_BULLET, ::ThrownItemRenderer)
        event.registerEntityRenderer(MAGIC_BOLT) { ctx -> ThrownItemRenderer(ctx, 1.0f, true) }
    }

    fun registerEntityShaders() {
        ClientRegistry.registerEntityShader(FrayedSoulEntity::class.java, BLUE_EYES_SHADER)
        ClientRegistry.registerEntityShader(VoidRunnerEntity::class.java, BLUE_EYES_SHADER)
        ClientRegistry.registerEntityShader(CastletonDeerEntity::class.java, BLUE_EYES_SHADER)
        ClientRegistry.registerEntityShader(KnightlyJuggernautEntity::class.java, BLUE_EYES_SHADER)
    }
}