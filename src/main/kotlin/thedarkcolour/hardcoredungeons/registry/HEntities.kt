package thedarkcolour.hardcoredungeons.registry

import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.entity.SpriteRenderer
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityClassification
import net.minecraft.entity.EntitySize
import net.minecraft.entity.EntityType
import net.minecraft.entity.EntityType.Builder
import net.minecraft.util.ResourceLocation
import net.minecraftforge.event.entity.EntityAttributeCreationEvent
import net.minecraftforge.fml.client.registry.ClientRegistry
import net.minecraftforge.fml.client.registry.RenderingRegistry
import net.minecraftforge.registries.IForgeRegistry
import thedarkcolour.hardcoredungeons.client.renderer.entity.DeerRenderer
import thedarkcolour.hardcoredungeons.entity.HEntityType
import thedarkcolour.hardcoredungeons.entity.castleton.frayedsoul.FrayedSoulEntity
import thedarkcolour.hardcoredungeons.entity.castleton.frayedsoul.FrayedSoulRenderer
import thedarkcolour.hardcoredungeons.entity.castleton.infinity.BlackStarEntity
import thedarkcolour.hardcoredungeons.entity.castleton.infinity.InfinityEntity
import thedarkcolour.hardcoredungeons.entity.castleton.knightlyjuggernaut.KnightlyJuggernautEntity
import thedarkcolour.hardcoredungeons.entity.castleton.knightlyjuggernaut.KnightlyJuggernautRenderer
import thedarkcolour.hardcoredungeons.entity.castleton.voidrunner.VoidRunnerEntity
import thedarkcolour.hardcoredungeons.entity.castleton.voidrunner.VoidRunnerRenderer
import thedarkcolour.hardcoredungeons.entity.overworld.deer.CastletonDeerEntity
import thedarkcolour.hardcoredungeons.entity.overworld.deer.DeerEntity
import thedarkcolour.hardcoredungeons.entity.overworld.mazeboss.MazeBossEntity
import thedarkcolour.hardcoredungeons.entity.overworld.mazeboss.MazeBossRenderer
import thedarkcolour.hardcoredungeons.entity.overworld.mushroomarcher.MushroomArcherEntity
import thedarkcolour.hardcoredungeons.entity.overworld.mushroomarcher.MushroomArcherRenderer
import thedarkcolour.hardcoredungeons.entity.projectile.bullet.SmallBulletEntity
import thedarkcolour.hardcoredungeons.entity.projectile.magic.MagicBoltEntity
import thedarkcolour.hardcoredungeons.util.modLoc

@Suppress("MemberVisibilityCanBePrivate")
object HEntities {
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
    val DEER = Builder
        .create(::DeerEntity, EntityClassification.CREATURE)
        .build(modLoc("deer"))
    val KNIGHTLY_JUGGERNAUT = Builder
        .create(::KnightlyJuggernautEntity, EntityClassification.CREATURE)
        .build(modLoc("knightly_juggernaut"))
    val MAZE_BOSS = Builder
        .create(::MazeBossEntity, EntityClassification.MONSTER)
        .build(modLoc("maze_boss"))
    val MUSHROOM_ARCHER = Builder
        .create(::MushroomArcherEntity, EntityClassification.MONSTER)
        .build(modLoc("mushroom_archer"))

    //val RAINBOWLAND_SHEEP = Builder
    //    .create(::RainbowlandSheepEntity, EntityClassification.CREATURE)
    //    .build(modLoc("rainbowland_sheep"))

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
        entities.register(DEER)
        entities.register(MAZE_BOSS)
        entities.register(MUSHROOM_ARCHER)

        entities.register(FRAYED_SOUL)
        entities.register(VOID_RUNNER)
        entities.register(CASTLETON_DEER)
        entities.register(KNIGHTLY_JUGGERNAUT)

        //entities.register(RAINBOWLAND_SHEEP)

        entities.register(SMALL_BULLET)
        entities.register(MAGIC_BOLT)
    }

    fun registerEntityAttributes(event: EntityAttributeCreationEvent) {
        event.put(FRAYED_SOUL, FrayedSoulEntity.ATTRIBUTES.create())
        event.put(VOID_RUNNER, VoidRunnerEntity.ATTRIBUTES.create())
        event.put(CASTLETON_DEER, DeerEntity.DEFAULT_ATTRIBUTES.create())
        event.put(DEER, DeerEntity.DEFAULT_ATTRIBUTES.create())
        event.put(KNIGHTLY_JUGGERNAUT, KnightlyJuggernautEntity.ATTRIBUTES.create())
        //event.put(RAINBOWLAND_SHEEP, RainbowlandSheepEntity.ATTRIBUTES.create())
        event.put(INFINITY, InfinityEntity.ATTRIBUTES.create())
        event.put(MAZE_BOSS, MazeBossEntity.ATTRIBUTES.create())
    }

    fun registerEntityRenderers() {
        RenderingRegistry.registerEntityRenderingHandler(MAZE_BOSS, ::MazeBossRenderer)
        RenderingRegistry.registerEntityRenderingHandler(MUSHROOM_ARCHER, ::MushroomArcherRenderer)
        RenderingRegistry.registerEntityRenderingHandler(DEER, ::DeerRenderer)
        RenderingRegistry.registerEntityRenderingHandler(FRAYED_SOUL, ::FrayedSoulRenderer)
        RenderingRegistry.registerEntityRenderingHandler(VOID_RUNNER, ::VoidRunnerRenderer)
        //RenderingRegistry.registerEntityRenderingHandler(RAINBOWLAND_SHEEP, ::SheepRenderer)
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