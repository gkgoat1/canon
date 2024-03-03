package at.gkgo.canon.api.identity.typed;

import at.gkgo.canon.api.identity.Identity;
import at.gkgo.canon.api.util.TypeUtils;
import com.mojang.serialization.Codec;
import io.wispforest.owo.serialization.Deserializer;
import io.wispforest.owo.serialization.Endec;
import io.wispforest.owo.serialization.Serializer;

import java.util.HashMap;
import java.util.Map;

public class TypedIdentity<T> {
    public static Map<String,TypedIdentity<?>> ALL = new HashMap<>();
    public final TypedIdentityData<T> data;
    public final String name;


    public TypedIdentity(TypedIdentityData<T> data, String name) {
        this.data = data;
        this.name = name;
    }
    public TypedIdentity<T> register(){
        ALL.put(name,this);
        Identity.KEYS.add(name);
        return this;
    }
}
