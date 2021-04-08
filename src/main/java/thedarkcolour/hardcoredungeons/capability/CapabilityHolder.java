package thedarkcolour.hardcoredungeons.capability;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

// for some reason capability injection doesn't work in Kotlin
public final class CapabilityHolder {
    @CapabilityInject(HPlayer.class)
    public static Capability<HPlayer> CAP;
}
