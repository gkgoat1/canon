package at.gkgo.canon.api.codec;

import io.wispforest.owo.serialization.Deserializer;
import io.wispforest.owo.serialization.Endec;
import io.wispforest.owo.serialization.Serializer;
import net.minecraft.util.Pair;

import java.util.function.Function;

public class EndecUtils {
    public static <T,U> Endec<Pair<T,U>> dependentPair(Endec<T> initial, Function<? super T,Endec<U>> next){
        return new Endec<Pair<T, U>>() {
            @Override
            public void encode(Serializer<?> serializer, Pair<T, U> value) {
                initial.encode(serializer,value.getLeft());
                next.apply(value.getLeft()).encode(serializer,value.getRight());
            }

            @Override
            public Pair<T, U> decode(Deserializer<?> deserializer) {
                var d = initial.decode(deserializer);
                var e = next.apply(d).decode(deserializer);
                return new Pair<>(d,e);
            }
        };
    }
    public static Endec<Integer> STRINT = Endec.STRING.xmap(Integer::parseInt, Object::toString);
}
