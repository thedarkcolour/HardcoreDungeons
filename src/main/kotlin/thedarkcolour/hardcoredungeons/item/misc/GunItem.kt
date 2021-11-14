package thedarkcolour.hardcoredungeons.item.misc

import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.ShootableItem
import net.minecraft.item.UseAction
import net.minecraft.tags.ITag
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
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
    val ammoItem: () -> ITag<Item> = { HItemTags.AMMUNITION_SMALL },
    val chargeTime: Int = 1,
) : ShootableItem(properties) {
    /**
     * returns the action that specifies what animation to play when the items is being used
     */
    override fun getUseAnimation(stack: ItemStack): UseAction {
        return UseAction.BOW
    }

    /**
     * How long it takes to use or consume an item
     */
    override fun getUseDuration(stack: ItemStack) = chargeTime

    override fun use(worldIn: World, playerIn: PlayerEntity, handIn: Hand): ActionResult<ItemStack> {
        val stack = playerIn.getItemInHand(handIn)

        return if (!playerIn.getProjectile(stack).isEmpty) {
            playerIn.startUsingItem(handIn)
            ActionResult.consume(stack)
        } else {
            ActionResult.fail(stack)
        }
    }

    override fun finishUsingItem(stack: ItemStack, worldIn: World, entity: LivingEntity): ItemStack {
        val ammo = entity.getProjectile(stack)

        if (!worldIn.isClientSide) {
            val vec = entity.lookAngle

            // change to match fire type????
            val bullet = SmallBulletEntity(HEntities.SMALL_BULLET, worldIn)

            if (ammo.item.`is`(HItemTags.AMMUNITION_INCENDIARY)) {
                bullet.setSecondsOnFire(10)
            }

            bullet.shoot(entity, entity.z, entity.eyeY - 0.1, entity.z, vec.x, vec.y, vec.z)
        }

        if (!(entity is PlayerEntity && entity.isCreative)) {
            ammo.shrink(1)
        }

        if (!automatic) {
            entity.stopUsingItem()
        }

        return stack
    }

    override fun getAllSupportedProjectiles(): Predicate<ItemStack> {
        return Predicate { stack -> stack.item.`is`(getAmmoTag()) }
    }

    open fun getAmmoTag(): ITag<Item> {
        return HItemTags.AMMUNITION_GENERIC
    }

    override fun getDefaultProjectileRange(): Int {
        return 10 // doesn't really matter because we aren't going to shoot
    }
}