package at.gkgo.canon.mixin.blocknbt;


import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.WorldChunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WorldChunk.class)
public class WorldChunkMixin extends ChunkMixin{
    @Inject(at = @At("RETURN"), method = "setBlockState")
    void canon$resetNbt(BlockPos pos, BlockState state, boolean moved, CallbackInfoReturnable<BlockState> cir){
        canon$resetNbtCore(pos,state);
    }
}
