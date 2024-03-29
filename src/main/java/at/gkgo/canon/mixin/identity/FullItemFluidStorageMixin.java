package at.gkgo.canon.mixin.identity;

import at.gkgo.canon.api.identity.Identity;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.fluid.base.FullItemFluidStorage;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = FullItemFluidStorage.class, remap = false)
public class FullItemFluidStorageMixin {
    @Shadow @Final private ContainerItemContext context;
    @Inject(method = "extract(Lnet/fabricmc/fabric/api/transfer/v1/fluid/FluidVariant;JLnet/fabricmc/fabric/api/transfer/v1/transaction/TransactionContext;)J", at = @At("HEAD"), cancellable = true)
    private void canon$makeExtractCare(FluidVariant resource, long maxAmount, TransactionContext transaction, CallbackInfoReturnable<Long> cir){
        for(var k: Identity.KEYS) {
            if (!resource.copyOrCreateNbt().getCompound(k).equals(context.getItemVariant().copyOrCreateNbt().getCompound(k))) {
                cir.setReturnValue(0L);
                cir.cancel();
            }
        }
    }
    @ModifyArg(method = "extract(Lnet/fabricmc/fabric/api/transfer/v1/fluid/FluidVariant;JLnet/fabricmc/fabric/api/transfer/v1/transaction/TransactionContext;)J", at = @At(value = "INVOKE",target = "Lnet/fabricmc/fabric/api/transfer/v1/context/ContainerItemContext;exchange(Lnet/fabricmc/fabric/api/transfer/v1/item/ItemVariant;JLnet/fabricmc/fabric/api/transfer/v1/transaction/TransactionContext;)J"), index = 0)
    private ItemVariant canon$makeExtractBetter(ItemVariant newVariant){
        var n = newVariant.copyOrCreateNbt();
        for(var k: Identity.KEYS) {
            n.remove(k);
        }
//        if(!resource.getNbt().getCompound(Canon.META).equals(new NbtCompound())){
//            n.put(Canon.META,resource.getNbt().getCompound(Canon.META));
//        }
        return ItemVariant.of(newVariant.getItem(),n);
    }

    @ModifyReturnValue(at = @At("RETURN"),method = "getResource()Lnet/fabricmc/fabric/api/transfer/v1/fluid/FluidVariant;")
    private FluidVariant canon$makeItContainNbt(FluidVariant original){
        if(original.isBlank()){
            return original;
        }
var n = original.copyOrCreateNbt();
//        if(!context.getItemVariant().getNbt().getCompound(Canon.META).equals(new NbtCompound())){
//            n.put(Canon.META,context.getItemVariant().getNbt().getCompound(Canon.META));
//        }
        Identity.copy(context.getItemVariant().getNbt(),n);
        return FluidVariant.of(original.getFluid(),n);
    }
}
