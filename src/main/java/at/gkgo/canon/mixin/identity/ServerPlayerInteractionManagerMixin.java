package at.gkgo.canon.mixin.identity;


import at.gkgo.canon.api.blocknbt.BlockNBT;
import at.gkgo.canon.impl.blocknbt.TransientStatics;
import net.minecraft.server.network.ServerPlayerInteractionManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayerInteractionManager.class)
public class ServerPlayerInteractionManagerMixin {
    @Shadow protected ServerWorld world;

    @Inject(at = @At("HEAD"), method = "tryBreakBlock")
    private void canon$saveTransient(BlockPos pos, CallbackInfoReturnable<Boolean> cir){
        TransientStatics.transient_interaction_compound = BlockNBT.getNbt(world,pos);
    }
    @Inject(at = @At("RETURN"), method = "tryBreakBlock")
    private void canon$dropTransient(BlockPos pos, CallbackInfoReturnable<Boolean> cir){
        TransientStatics.transient_interaction_compound = null;
    }
}
