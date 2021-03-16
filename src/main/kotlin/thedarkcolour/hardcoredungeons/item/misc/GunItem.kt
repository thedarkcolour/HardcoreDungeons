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
import net.minecraft.world.World
import thedarkcolour.hardcoredungeons.entity.projectile.bullet.SmallBulletEntity
import thedarkcolour.hardcoredungeons.registry.HEntities
import thedarkcolour.hardcoredungeons.registry.HItems
import thedarkcolour.hardcoredungeons.tags.HItemTags
import java.util.function.Predicate

/**
 * Gun item for stuff like rifles and pistols.
 *
 * @property bulletDamage The base damage amount for bullets
 *
 * @property velocity The speed multiplier for bullets
 *
 * @property drop The amount of drop (ex. 0.002f, 0.0f)
 *
 * @property fireType todo remember this idea wtf is fire type
 *
 * @property automatic Whether this weapon is automatic or semi-automatic
 *
 * @property ammoItem The item to use for bullets
 *
 * @property chargeTime The amount of time it takes to charge up a shot
 */
open class GunItem(
    properties: Properties,
    val bulletDamage: Float = 3.0f,
    val velocity: Float = 1.2f,
    val drop: Float = 0.0f,
    val fireType: FireType = FireType.SMALL_BULLET,
    private val automatic: Boolean = false,
    val ammoItem: () -> Item = HItems::BULLET,
    val chargeTime: Int = 1,
) : ShootableItem(properties) {
    /**
     * returns the action that specifies what animation to play when the items is being used
     */
    override fun getUseAction(stack: ItemStack): UseAction {
        return UseAction.BOW
    }

    /**
     * How long it takes to use or consume an item
     */
    override fun getUseDuration(stack: ItemStack) = chargeTime

    override fun onItemRightClick(worldIn: World, playerIn: PlayerEntity, handIn: Hand): ActionResult<ItemStack> {
        val stack = playerIn.getHeldItem(handIn)

        return if (!playerIn.findAmmo(stack).isEmpty) {
            playerIn.activeHand = handIn
            ActionResult.resultConsume(stack)
        } else {
            ActionResult.resultFail(stack)
        }
    }

    override fun onItemUseFinish(stack: ItemStack, worldIn: World, entity: LivingEntity): ItemStack {
        val ammo = entity.findAmmo(stack)

        if (!worldIn.isRemote) {
            val vec = entity.lookVec

            // change to match fire type????
            val bullet = SmallBulletEntity(HEntities.SMALL_BULLET, worldIn)

            if (ammo.item.isIn(HItemTags.AMMUNITION_INCENDIARY)) {
                bullet.setFire(10)
            }

            bullet.shoot(entity, entity.posX, entity.posYEye - 0.1, entity.posZ, vec.x, vec.y, vec.z)
        }

        if (!(entity is PlayerEntity && entity.isCreative)) {
            ammo.shrink(1)
        }

        if (!automatic) {
            entity.resetActiveHand()
        }

        return stack
    }

    override fun getInventoryAmmoPredicate(): Predicate<ItemStack> {
        return Predicate { stack -> stack.item.isIn(getAmmoTag()) }
    }

    open fun getAmmoTag(): ITag<Item> {
        return HItemTags.AMMUNITION_GENERIC
    }

    override fun func_230305_d_(): Int {
        return 10 // doesn't really matter because we aren't going to shoot
    }

    enum class FireType {
        SMALL_BULLET, MEDIUM_BULLET, LARGE_BULLET,
        BIRDSHOT_SHELL, BUCKSHOT_SHELL, SLUG_SHELL
    }
}