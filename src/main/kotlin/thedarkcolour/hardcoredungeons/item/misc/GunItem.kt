package thedarkcolour.hardcoredungeons.item.misc

import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.ShootableItem
import net.minecraft.item.UseAction
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.world.World
import thedarkcolour.hardcoredungeons.entity.misc.bullet.SmallBulletEntity
import thedarkcolour.hardcoredungeons.registry.HEntities
import thedarkcolour.hardcoredungeons.registry.HItems
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
 * @property fireType TODO
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
     * Gets the maximum number of items that this stack should be able to hold. This
     * is a ItemStack (and thus NBT) sensitive version of Item.getItemStackLimit()
     *
     * @param stack The ItemStack
     * @return The maximum number this item can be stacked to
     */
    override fun getItemStackLimit(stack: ItemStack?): Int {
        return 1
    }

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
        if (!worldIn.isRemote) {
            val vec = entity.lookVec

            // todo change to match fire type
            val bullet = SmallBulletEntity(HEntities.SMALL_BULLET, worldIn)

            bullet.shoot(entity, entity.posX, entity.posYEye - 0.1, entity.posZ, vec.x, vec.y, vec.z)
        }

        if (!automatic) {
            entity.resetActiveHand()
        }

        val ammo = entity.findAmmo(stack)

        if (!(entity is PlayerEntity && entity.isCreative)) {
            ammo.shrink(1)
        }

        return stack
    }

    override fun getInventoryAmmoPredicate(): Predicate<ItemStack> {
        return Predicate(getAmmoItem()::isItemEqual)
    }

    open fun getAmmoItem(): ItemStack {
        return ItemStack(HItems.BULLET)
    }

    override fun func_230305_d_(): Int {
        return 10 // doesn't really matter because we aren't going to shoot
    }

    class GunProperties : Properties() {
        private var bulletDamage = 0.0f
        private var velocity = 0.5f
        private var fireType = FireType.SMALL_BULLET
        private var automatic = false
        private var ammoItem = HItems.BULLET

        fun bulletDamage(bulletDamage: Float): GunProperties {
            this.bulletDamage = bulletDamage
            return this
        }

        fun fireType(fireType: FireType): GunProperties {
            this.fireType = fireType
            return this
        }

        /**
         * Whether this gun can fire multiple shots consecutively
         */
        fun auto(): GunProperties {
            this.automatic = true
            return this
        }

        fun velocity(velocity: Float): GunProperties {
            this.velocity = velocity
            return this
        }
    }

    enum class FireType {
        SMALL_BULLET, MEDIUM_BULLET, LARGE_BULLET,
        BIRDSHOT_SHELL, BUCKSHOT_SHELL, SLUG_SHELL
    }
}