package thedarkcolour.hardcoredungeons.capability;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import thedarkcolour.hardcoredungeons.HardcoreDungeons;

// for some reason capability injection doesn't work in Kotlin
public final class CapabilityHolder {
    public static Capability<HPlayer> CAP;

    @CapabilityInject(HPlayer.class)
    public static void inject(Capability<HPlayer> capability) {
        CAP = capability;
        HardcoreDungeons.INSTANCE.getLOGGER().debug("Capability injected");
    }
}
