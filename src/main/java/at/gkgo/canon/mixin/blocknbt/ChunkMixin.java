package at.gkgo.canon.mixin.blocknbt;

import at.gkgo.canon.Canon;
import at.gkgo.canon.api.blocknbt.BlockNBT;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(Chunk.class)
public class ChunkMixin {
    @Unique
    public void canon$resetNbtCore(BlockPos pos, BlockState bs){
        BlockNBT.removeNbt((Chunk) (Object)this,pos);
    }
}
