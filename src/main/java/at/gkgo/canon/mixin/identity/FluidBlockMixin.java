package at.gkgo.canon.mixin.identity;

import at.gkgo.canon.Canon;
import at.gkgo.canon.api.blocknbt.BlockNBT;
import at.gkgo.canon.api.identity.Identity;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.block.FluidBlock;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(FluidBlock.class)
public class FluidBlockMixin{
    @Shadow @Final protected FlowableFluid fluid;

    @ModifyReturnValue(at = @At("RETURN"), method = "tryDrainFluid")
    private ItemStack canon$tryMetaFluid(ItemStack original, @Local WorldAccess w, @Local BlockPos pos){
        if(w instanceof World world) {
//            if (!BNComponent.get(world, pos).getCompound(Canon.META).equals(new NbtCompound())) {
////            for (var stack : original) {
////                if (stack.getItem() == state.getBlock().asItem()) {
//                original.getOrCreateNbt().put(Canon.META, BNComponent.get(world, pos).getCompound(Canon.META));
////                }
////            }
//            }
            BlockNBT.getNbt(world,pos).writeNbt(original.getOrCreateNbt());
        }
        return original;
    }
}
