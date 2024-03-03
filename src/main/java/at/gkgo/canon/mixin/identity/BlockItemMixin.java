package at.gkgo.canon.mixin.identity;

import at.gkgo.canon.Canon;
import at.gkgo.canon.api.blocknbt.BNData;
import at.gkgo.canon.api.blocknbt.BlockNBT;
import at.gkgo.canon.api.identity.Identity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockItem.class)
public class BlockItemMixin{
    @Inject(at = @At("HEAD"), method = "writeNbtToBlockEntity")
    private static void canon$writeMetaToBlockNBT(World world, PlayerEntity player, BlockPos pos, ItemStack stack, CallbackInfoReturnable<Boolean> cir){
        BlockNBT.putNbt(world,pos, BNData.fromNbt(stack.getOrCreateNbt()));
    }


}
