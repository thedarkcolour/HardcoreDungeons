package thedarkcolour.hardcoredungeons.capability

import net.minecraft.core.Direction
import net.minecraft.nbt.CompoundTag
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.capabilities.ICapabilityProvider
import net.minecraftforge.common.util.INBTSerializable
import net.minecraftforge.common.util.LazyOptional

class HPlayerDataProvider : ICapabilityProvider, INBTSerializable<CompoundTag> {
    private val holder = LazyOptional.of { HPlayerData() }

    override fun <T> getCapability(cap: Capability<T>, side: Direction?): LazyOptional<T> {
        if (cap == HPlayerData.PLAYER_DATA) {
            return holder.cast()
        }
        return LazyOptional.empty()
    }

    override fun serializeNBT(): CompoundTag {
        return CompoundTag().also { tag ->
            holder.ifPresent { data ->
                tag.putByte("PortalCooldown", data.portalCooldown)
            }
        }
    }

    override fun deserializeNBT(nbt: CompoundTag) {
        holder.ifPresent { data ->
            if (nbt.contains("PortalCooldown")) {
                data.portalCooldown = nbt.getByte("PortalCooldown")
            }
        }
    }
}