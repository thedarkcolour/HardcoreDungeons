package thedarkcolour.hardcoredungeons.mixin;

//@Mixin(LayerUtil.class)
public class MixinLayerUtil {
    //@Inject(method = "getDefaultLayer", locals = LocalCapture.PRINT, at = @At(shift = At.Shift.AFTER, value = "INVOKE_ASSIGN", target = "Lnet/minecraft/world/gen/layer/AddMushroomIslandLayer;apply(Lnet/minecraft/world/gen/IExtendedNoiseRandom;Lnet/minecraft/world/gen/area/IAreaFactory;)Lnet/minecraft/world/gen/area/IAreaFactory;"))
    //private static <T extends IArea> void patchLayers(boolean p_237216_0_, int p_237216_1_, int p_237216_2_, LongFunction<T> p_237216_3_, CallbackInfoReturnable<IAreaFactory<T>> cir, IAreaFactory iareafactory, IAreaFactory iareafactory1) {
        //p_202713_2_ = AddMushroomIslandLayer.INSTANCE.apply(p_202713_1_, p_202713_2_);
        //MushroomCliffsBiomeLayer.INSTANCE.apply(p_237216_3_.apply(5L), p_202713_2_);
    //}
/*
    @ModifyVariable(method = "getDefaultLayer", index = 4, at = @At(shift = At.Shift.AFTER, value = "INVOKE", target = "Lnet/minecraft/world/gen/layer/AddMushroomIslandLayer;apply(Lnet/minecraft/world/gen/IExtendedNoiseRandom;Lnet/minecraft/world/gen/area/IAreaFactory;)Lnet/minecraft/world/gen/area/IAreaFactory;"))
    private static <T extends IArea, C extends IExtendedNoiseRandom<T>> IAreaFactory<T> modifyTing(IAreaFactory<T> factory, boolean p_237216_0_, int p_237216_1_, int p_237216_2_, LongFunction<C> p_237216_3_) { //
        return MushroomCliffsBiomeLayer.INSTANCE.apply(p_237216_3_.apply(5L), factory);
    }*/
}
