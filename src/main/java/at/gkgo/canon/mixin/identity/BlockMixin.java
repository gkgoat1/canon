package at.gkgo.canon.mixin.identity;

import at.gkgo.canon.Canon;

import at.gkgo.canon.api.blocknbt.BlockNBT;
import at.gkgo.canon.api.identity.Identity;
import at.gkgo.canon.impl.blocknbt.TransientStatics;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(Block.class)
public class BlockMixin{


    @ModifyReturnValue(at = @At("RETURN"), method = "getPickStack")
    private ItemStack canon$injectPicks(ItemStack stack, @Local WorldView w, @Local BlockPos pos){
        if(w instanceof World world){
//                var cm = BNComponent.get(world,pos);
//            if(!cm.getCompound(Canon.META).equals(new NbtCompound())) {
////                for (var stack : original) {
////                    if (stack.getItem() == state.getBlock().asItem()) {
//                        stack.getOrCreateNbt().put(Canon.META, cm.getCompound(Canon.META));
////                    }
////                }
//            }
            var cm = BlockNBT.getNbt(world,pos);
//            Identity.copy(cm,stack.getOrCreateNbt());
            cm.writeNbt(stack.getOrCreateNbt());
        }

        return stack;
    }
    @ModifyReturnValue(at = @At("RETURN"), method = "getDroppedStacks(Lnet/minecraft/block/BlockState;Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/entity/BlockEntity;)Ljava/util/List;")
    private static List<ItemStack> canon$tweakDroppedStacksForMeta(List<ItemStack> original, @Local BlockState state, @Local ServerWorld world, @Local BlockPos pos){
        var cm = TransientStatics.transient_interaction_compound;
        if(cm == null){
            cm = BlockNBT.getNbt(world,pos);
        }
//        if(!cm.getCompound(Canon.META).equals(new NbtCompound())) {
            for (var stack : original) {
                if (stack.getItem() == state.getBlock().asItem()) {
                    cm.writeNbt(stack.getOrCreateNbt());
                }
            }
//        }
        return original;
    }


}
