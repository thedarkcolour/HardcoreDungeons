package thedarkcolour.hardcoredungeons.capability

import net.minecraft.nbt.ByteNBT
import net.minecraft.nbt.INBT
import net.minecraft.util.Direction
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.capabilities.CapabilityManager
import net.minecraftforge.common.capabilities.ICapabilityProvider
import net.minecraftforge.common.util.LazyOptional
import thedarkcolour.hardcoredungeons.capability.CapabilityHolder.CAP
import thedarkcolour.hardcoredungeons.util.modLoc

interface HPlayer {
    var portalCooldown: Byte

    // default implementation
    private class Impl : HPlayer {
        override var portalCooldown: Byte = 0
    }

    class Provider : ICapabilityProvider {
        private val instance = LazyOptional.of { CAP.defaultInstance!! }

        override fun <T : Any?> getCapability(cap: Capability<T>, side: Direction?): LazyOptional<T> {
            return CAP.orEmpty(cap, instance)
        }
    }

    companion object {
        val ID = modLoc("player")

        fun get(): Capability<HPlayer> {
            return CAP
        }

        fun registerCapability() {
            CapabilityManager.INSTANCE.register(
                HPlayer::class.java,
                object : Capability.IStorage<HPlayer> {
                    override fun writeNBT(capability: Capability<HPlayer>?, instance: HPlayer, side: Direction?): INBT? {
                        return ByteNBT.valueOf(instance.portalCooldown)
                    }

                    override fun readNBT(capability: Capability<HPlayer>?, instance: HPlayer, side: Direction?, nbt: INBT) {
                        // prevent weird nbt stuff
                        instance.portalCooldown = (nbt as ByteNBT).asByte.coerceAtLeast(0)
                    }
                },
                ::Impl
            )
        }
    }
}