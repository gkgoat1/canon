package at.gkgo.canon.api.blocknbt;

import at.gkgo.canon.impl.blocknbt.BNAttachment;
import net.minecraft.block.WoodType;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

import java.util.HashSet;
import java.util.Set;

public class BlockNBT {
    public static BNData getNbt(Chunk chunk, BlockPos pos){
//        var e = chunk.getBlockEntity(pos);
//        if(e != null)return e.createNbt();
        return chunk.getAttachedOrCreate(BNAttachment.TYPE).get(pos);
    }
    public static BlockEntity reset(Chunk k, BlockEntity e){
        var s = e.getType().instantiate(e.getPos(),e.getCachedState());
        k.setBlockEntity(s);
        return s;
    }
    public static BNData getNbt(World world, BlockPos pos){
        return getNbt(world.getChunk(pos),pos);
    }
    public static void putNbt(Chunk c, BlockPos pos, BNData x){
////        var c = world.getChunk(pos);
//        var e = c.getBlockEntity(pos);
//        if(e == null) {
            c.getAttachedOrCreate(BNAttachment.TYPE).put(pos, x);
            c.setNeedsSaving(true);
//        }else{
//            e = reset(c,e);
//            c.getAttachedOrCreate(BNAttachment.TYPE).remove(pos);
//            e.readNbt(x);
//        }
    }
    public static void removeNbt(Chunk c, BlockPos pos){
        c.getAttachedOrCreate(BNAttachment.TYPE).remove(pos);
//        var e = c.getBlockEntity(pos);
//        if(e == null){
//            return;
//        }
//        e = reset(c,e);
    }
    public static void putNbt(World world, BlockPos pos, BNData x){
        putNbt(world.getChunk(pos),pos,x);
    }
    public static void removeNbt(World world, BlockPos pos){
        removeNbt(world.getChunk(pos),pos);
    }
}
