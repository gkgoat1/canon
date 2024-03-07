package at.gkgo.canon.impl.blocknbt;

import at.gkgo.canon.api.blocknbt.BNData;
import at.gkgo.canon.api.palette2.ChunkBlockPosPalette;
import com.mojang.serialization.Codec;
import io.wispforest.owo.serialization.Endec;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

import java.util.Map;

public class BNAttachment {
    public final ChunkBlockPosPalette<BNData> all;

    public BNAttachment(ChunkBlockPosPalette<BNData> all) {
        this.all = all;
    }
    public BNData get(BlockPos pos){
        var wrapped = new BlockPos(pos.getX() & 0xf,pos.getY(),pos.getZ() & 0xf);
        if(all.get(wrapped) != null){
            return all.get(wrapped);
        }
        var n = new BNData(Map.of());
        all.put(wrapped,n);
        return n;
    }
    public void put(BlockPos pos, BNData c){
        var p = new BlockPos(pos.getX() & 0xf,pos.getY(),pos.getZ() & 0xf);
//        if(c.){
//            all.remove(p);
//            return;
//        }
        all.put(p,c);
    }
    public void remove(BlockPos pos){
        var p = new BlockPos(pos.getX() & 0xf,pos.getY(),pos.getZ() & 0xf);
        all.remove(p);
    }

    public static final Endec<BNAttachment> ENDEC = ChunkBlockPosPalette.endec(BNData.ENDEC).xmap(BNAttachment::new,(a) -> a.all);
    public static final Codec<BNAttachment> CODEC = ENDEC.codec();
    public static final AttachmentType<BNAttachment> TYPE = AttachmentRegistry.<BNAttachment>builder().persistent(CODEC).initializer(() -> new BNAttachment(new ChunkBlockPosPalette<>())).buildAndRegister(Identifier.of("canon","block_nbt"));

}
