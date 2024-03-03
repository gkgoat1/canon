package at.gkgo.canon.mixin.blocknbt;

import at.gkgo.canon.impl.blocknbt.BNAttachment;
import at.gkgo.canon.impl.blocknbt.net.BNSyncPacket;
import net.minecraft.server.world.ChunkHolder;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.chunk.WorldChunk;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChunkHolder.class)
public class ChunkHolderMixin {
    @Shadow private boolean pendingBlockUpdates;

    @Shadow @Final private ChunkHolder.PlayersWatchingChunkProvider playersWatchingChunkProvider;

    @Shadow @Final
    ChunkPos pos;

    @Inject(at = @At("HEAD"), method = "flushUpdates")
    private void canon$flushNbt(WorldChunk chunk, CallbackInfo ci){
        if(!pendingBlockUpdates){
            return;
        }
        var a = chunk.getAttachedOrCreate(BNAttachment.TYPE);
        if(a == null){
            return;
        }
        var players = playersWatchingChunkProvider.getPlayersWatchingChunk(pos,false);
        for(var player: players){
            BNSyncPacket.CHAN.serverHandle(player).send(new BNSyncPacket(pos.x,pos.z,a));
        }
    }
}
