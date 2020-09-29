package thedarkcolour.hardcoredungeons.container

import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.container.ContainerType
import net.minecraft.inventory.container.SimpleNamedContainerProvider
import net.minecraft.util.ActionResultType
import net.minecraft.util.text.ITextComponent
import net.minecraft.util.text.TranslationTextComponent
import thedarkcolour.hardcoredungeons.HardcoreDungeons

/**
 * [ContainerType] with extended functionality.
 *
 * Each instance of this class has a [displayName] property
 * that is used for the [SimpleNamedContainerProvider]
 *
 * This class provides its own [setRegistryKey] function
 * that sets the [displayName] as well as the registry name.
 *
 * @property displayName the display name of this container
 *
 * @author TheDarkColour
 * @see thedarkcolour.hardcoredungeons.registry.HContainers
 */
class HContainerType<T : HContainer>(factory: (Int, PlayerInventory) -> T) : ContainerType<T>(factory) {
    private lateinit var displayName: ITextComponent

    /**
     * Opens the container for the [playerIn]
     *
     * @param playerIn the player who is opening the container
     *
     * @return [ActionResultType.SUCCESS] for use in a return statement
     */
    fun openContainer(playerIn: PlayerEntity): ActionResultType {
        playerIn.openContainer(SimpleNamedContainerProvider({ id, playerInv, _ -> ExtractorContainer(id, playerInv) }, displayName))
        return ActionResultType.SUCCESS
    }

    fun setRegistryKey(key: String): HContainerType<T> {
        setRegistryName(HardcoreDungeons.ID + ":" + key)
        displayName = TranslationTextComponent(HardcoreDungeons.ID + ".container.$key")
        return this
    }
}