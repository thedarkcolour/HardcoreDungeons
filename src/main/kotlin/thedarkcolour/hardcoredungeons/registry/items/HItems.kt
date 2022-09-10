package thedarkcolour.hardcoredungeons.registry.items

import net.minecraft.client.renderer.item.ItemProperties
import net.minecraft.world.item.Item
import net.minecraft.world.item.alchemy.PotionBrewing
import net.minecraft.world.item.crafting.Ingredient
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn
import net.minecraftforge.client.event.RegisterColorHandlersEvent
import net.minecraftforge.registries.ForgeRegistries
import thedarkcolour.hardcoredungeons.client.color.PotionColor
import thedarkcolour.hardcoredungeons.client.color.RainbowColor
import thedarkcolour.hardcoredungeons.item.MalachitePickaxeItem
import thedarkcolour.hardcoredungeons.registry.HRegistry
import thedarkcolour.hardcoredungeons.registry.block.HBlocks
import thedarkcolour.hardcoredungeons.util.modLoc

/**
 * Handles item registry and contains references to every `Item` and `BlockItem` in HCD.
 *
 * Categorized by dimension and `BlockItem` instances always go first.
 *
 * @author thedarkcolour, genericrandom64
 */
@Suppress("MemberVisibilityCanBePrivate", "HasPlatformType", "DuplicatedCode")
object HItems : HRegistry<Item>(ForgeRegistries.Keys.ITEMS) {
    init {
        onceRegistered {
            PotionBrewing.ALLOWED_CONTAINERS.add(Ingredient.of(POTION_SYRINGE_ITEM))
        }
    }

    // Needed to ensure CastletonItems.kt and others have their items registered
    override fun init() {
        super.init()

        Class.forName("thedarkcolour.hardcoredungeons.registry.items.AubrumItemsKt")
        Class.forName("thedarkcolour.hardcoredungeons.registry.items.CandylandItemsKt")
        Class.forName("thedarkcolour.hardcoredungeons.registry.items.CastletonItemsKt")
        Class.forName("thedarkcolour.hardcoredungeons.registry.items.MiscItemsKt")
        Class.forName("thedarkcolour.hardcoredungeons.registry.items.RainbowlandItemsKt")
    }

    /**
     * Fired during the ColorHandlerEvent
     *
     * Registers IItemColors for certain items
     *
     * @see RainbowColor
     */
    @OnlyIn(Dist.CLIENT)
    fun setItemColors(colors: RegisterColorHandlersEvent.Item) {
        colors.register(RainbowColor, HBlocks.RAINBOW_SOIL.grass)
        colors.register(RainbowColor, HBlocks.RAINBOW_GLASS)
        colors.register(RainbowColor, HBlocks.RAINBOW_GLASS_PANE)
        colors.register(PotionColor, POTION_SYRINGE_ITEM)
    }

    // Item Model Overrides
    fun registerItemProperties() {
        ItemProperties.register(MALACHITE_PICKAXE_ITEM, modLoc("charged")) { stack, _, _, _ ->
            MalachitePickaxeItem.getBoost(stack).toFloat()
        }
    }


}