package thedarkcolour.hardcoredungeons.item

import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.tags.TagKey
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.ProjectileWeaponItem
import net.minecraft.world.item.UseAnim
import net.minecraft.world.level.Level
import thedarkcolour.hardcoredungeons.entity.projectile.bullet.SmallBulletEntity
import thedarkcolour.hardcoredungeons.registry.HEntities
import thedarkcolour.hardcoredungeons.tags.HItemTags
import java.util.function.Predicate

/**
 * Gun item for stuff like rifles and pistols.
 *
 * @property bulletDamage The base damage amount for bullets
 * @property velocity The speed multiplier for bullets
 * @property drop The amount of drop (ex. 0.002f, 0.0f)
 * @property ammoItem The item to use for bullets
 * @property chargeTime The amount of time it takes to charge up a shot
 */
open class GunItem(
    properties: Item.Properties,
    val bulletDamage: Float = 3.0f,
    val velocity: Float = 1.2f,
    val drop: Float = 0.0f,
    private val automatic: Boolean = false,
    val ammoItem: () -> TagKey<Item> = { HItemTags.AMMUNITION_SMALL },
    val chargeTime: Int = 1,
) : ProjectileWeaponItem(properties) {
    override fun getUseAnimation(stack: ItemStack) = UseAnim.BOW
    override fun getUseDuration(stack: ItemStack) = chargeTime

    override fun use(level: Level, playerIn: Player, handIn: InteractionHand): InteractionResultHolder<ItemStack> {
        val stack = playerIn.getItemInHand(handIn)
        val ammo = playerIn.getProjectile(stack)

        return if (!ammo.isEmpty) {
            playerIn.startUsingItem(handIn)
            if (!level.isClientSide) {
                val vec = playerIn.lookAngle
                val pos = playerIn.eyePosition.add(vec.normalize().scale(0.2))

                level.playSound(null, pos.x, pos.y, pos.z, SoundEvents.IRON_GOLEM_REPAIR, SoundSource.PLAYERS, 1.0f, 2.0f)

                // change to match fire type????
                val bullet = SmallBulletEntity(HEntities.SMALL_BULLET, level)
                var ammoType = SmallBulletEntity.BULLET

                if (ammo.`is`(HItemTags.AMMUNITION_INCENDIARY)) {
                    ammoType = SmallBulletEntity.INCENDIARY_BULLET
                }

                bullet.shoot(playerIn, ammoType, playerIn.x, playerIn.eyeY - 0.1, playerIn.z, vec.x * 5, vec.y * 5, vec.z * 5)
            }
            playerIn.cooldowns.addCooldown(this, chargeTime)

            if (!playerIn.isCreative) {
                ammo.shrink(1)
            }
            InteractionResultHolder.consume(stack)
        } else {
            InteractionResultHolder.fail(stack)
        }
    }

    override fun finishUsingItem(stack: ItemStack, level: Level, entity: LivingEntity): ItemStack {
        val ammo = entity.getProjectile(stack)

        // todo allow entities to use this

        if (!automatic) {
            entity.stopUsingItem()
        }

        return stack
    }

    override fun getAllSupportedProjectiles(): Predicate<ItemStack> {
        return Predicate { stack ->
            stack.`is`(getAmmoTag())
        }
    }

    open fun getAmmoTag(): TagKey<Item> {
        return HItemTags.AMMUNITION_GENERIC
    }

    override fun getDefaultProjectileRange(): Int {
        return 10 // doesn't really matter because we aren't going to shoot
    }

    fun zoomModifier(player: Player): Float {
        if (player.isCrouching) {
            return 0.95f
        }
        return 1.0f
    }
}