package thedarkcolour.hardcoredungeons.item.misc

import net.minecraft.tags.Tag
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
    properties: Properties,
    val bulletDamage: Float = 3.0f,
    val velocity: Float = 1.2f,
    val drop: Float = 0.0f,
    private val automatic: Boolean = false,
    val ammoItem: () -> Tag<Item> = { HItemTags.AMMUNITION_SMALL },
    val chargeTime: Int = 1,
) : ProjectileWeaponItem(properties) {
    /**
     * returns the action that specifies what animation to play when the items is being used
     */
    override fun getUseAnimation(stack: ItemStack): UseAnim {
        return UseAnim.BOW
    }

    /**
     * How long it takes to use or consume an item
     */
    override fun getUseDuration(stack: ItemStack) = chargeTime

    override fun use(worldIn: Level, playerIn: Player, handIn: InteractionHand): InteractionResultHolder<ItemStack> {
        val stack = playerIn.getItemInHand(handIn)

        return if (!playerIn.getProjectile(stack).isEmpty) {
            playerIn.startUsingItem(handIn)
            InteractionResultHolder.consume(stack)
        } else {
            InteractionResultHolder.fail(stack)
        }
    }

    override fun finishUsingItem(stack: ItemStack, level: Level, entity: LivingEntity): ItemStack {
        val ammo = entity.getProjectile(stack)

        if (!level.isClientSide) {
            val vec = entity.lookAngle

            // change to match fire type????
            val bullet = SmallBulletEntity(HEntities.SMALL_BULLET, level)

            if (HItemTags.AMMUNITION_INCENDIARY.contains(ammo.item)) {
                bullet.setSecondsOnFire(10)
            }

            bullet.shoot(entity, entity.z, entity.eyeY - 0.1, entity.z, vec.x, vec.y, vec.z)
        }

        if (!(entity is Player && entity.isCreative)) {
            ammo.shrink(1)
        }

        if (!automatic) {
            entity.stopUsingItem()
        }

        return stack
    }

    // Why is this a predicate instead of a boolean method?
    override fun getAllSupportedProjectiles(): Predicate<ItemStack> {
        return Predicate { stack -> getAmmoTag().contains(stack.item) }
    }

    open fun getAmmoTag(): Tag<Item> {
        return HItemTags.AMMUNITION_GENERIC
    }

    override fun getDefaultProjectileRange(): Int {
        return 10 // doesn't really matter because we aren't going to shoot
    }
}