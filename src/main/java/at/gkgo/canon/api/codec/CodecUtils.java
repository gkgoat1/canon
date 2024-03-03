package at.gkgo.canon.api.codec;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;

import static java.lang.Integer.parseInt;

public class CodecUtils {
    private CodecUtils(){}
    public static<T> Codec<T> json(Codec<T> wrapped){
        return Codec.STRING.flatXmap((s) -> wrapped.decode(JsonOps.INSTANCE,new Gson().fromJson(s, JsonElement.class)).map(Pair::getFirst),(a) -> wrapped.encodeStart(JsonOps.INSTANCE,a).map(Object::toString));
    }
    public static Codec<Integer> STRINT = Codec.STRING.xmap(Integer::parseInt, Object::toString);
}
