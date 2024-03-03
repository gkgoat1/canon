package at.gkgo.canon.api.recipe;

import com.mojang.serialization.Codec;
import io.wispforest.owo.mixin.PacketByteBufMixin;
import io.wispforest.owo.serialization.Deserializer;
import io.wispforest.owo.serialization.Endec;
import io.wispforest.owo.serialization.format.bytebuf.ByteBufDeserializer;
import io.wispforest.owo.serialization.format.bytebuf.ByteBufSerializer;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;

@FunctionalInterface
public interface EndecRecipeSerializer<R extends Recipe<?>> extends RecipeSerializer<R> {
Endec<R> endec();

    @Override
    default Codec<R> codec(){
        return endec().codec();
    }

    @Override
    default R read(PacketByteBuf buf){
        return endec().decode(ByteBufDeserializer.of(buf));
    }

    @Override
    default void write(PacketByteBuf buf, R recipe){
        endec().encode(ByteBufSerializer.of(buf),recipe);
    }
}
