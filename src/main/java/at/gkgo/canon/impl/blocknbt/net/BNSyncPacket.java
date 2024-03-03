package at.gkgo.canon.impl.blocknbt.net;

import at.gkgo.canon.impl.blocknbt.BNAttachment;
import io.wispforest.owo.network.OwoNetChannel;
import io.wispforest.owo.serialization.Endec;
import io.wispforest.owo.serialization.StructEndec;
import io.wispforest.owo.serialization.endec.StructEndecBuilder;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;

public record BNSyncPacket(int i, int j, BNAttachment attach) {
    public static StructEndec<BNSyncPacket> ENDEC = StructEndecBuilder.of(
            Endec.INT.fieldOf("i",(a) -> a.i),
            Endec.INT.fieldOf("j",(a) -> a.j),
            BNAttachment.ENDEC.fieldOf("newChunk",(a) -> a.attach),
            BNSyncPacket::new
    );
    public static OwoNetChannel CHAN = OwoNetChannel.create(Identifier.of("canon","block_nbt/sync"));
    static{
        CHAN.registerClientbound(BNSyncPacket.class, BNSyncPacket.ENDEC,(message,ctx) -> {
            ctx.runtime().world.getChunk(message.i,message.j).setAttached(BNAttachment.TYPE,message.attach);
        });
    }
    public static void init(){

    }
}
