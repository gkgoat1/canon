package at.gkgo.canon.api.identity.typed;

import at.gkgo.canon.api.identity.Identity;
import at.gkgo.canon.api.util.TypeUtils;
import io.wispforest.owo.serialization.Deserializer;
import io.wispforest.owo.serialization.Endec;
import io.wispforest.owo.serialization.Serializer;
import io.wispforest.owo.serialization.endec.BuiltInEndecs;
import io.wispforest.owo.serialization.format.nbt.NbtDeserializer;
import io.wispforest.owo.serialization.format.nbt.NbtEndec;
import io.wispforest.owo.serialization.format.nbt.NbtSerializer;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.util.Util;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Bake {
    private final Map<String, Object> all;

    public Bake(Map<String, Object> all) {
        this.all = all;
    }
    public static Bake ofNbt(NbtCompound n){
        return BAKE.decodeFully(NbtDeserializer::of,n);
    }
    public NbtCompound toNbt(){
        return (NbtCompound) BAKE.encodeFully(() -> NbtSerializer.of(),this);
    }
    public void writeNbt(NbtCompound c){
        var d = toNbt();
        for(var k: d.getKeys()){
            c.put(k,d.get(k));
        }
    }

    public <T> T get(TypedIdentity<T> key) {
        return (T) all.get(key.name);
    }

    public <T> void put(TypedIdentity<T> key, T object) {
        all.put(key.name, object);
    }

    public <T> void remove(TypedIdentity<T> key){
        all.remove(key.name);
    }
    public static TypedIdentityData<Bake> DATA = new TypedIdentityData<>(Bake.BAKE, new TypedIdentityHandler<Bake>() {
        @Override
        public <Q, C> Optional<Q> query(Bake value, Query<Q, C> query, C ctx) {
            for (var i : value.all.entrySet()) {
                if(TypedIdentity.ALL.containsKey(i.getKey())) {
                    var h = TypedIdentity.ALL.get(i.getKey());
                    var r = h.data.handler.query(TypeUtils.unsafeCoerce(i.getValue()), query, ctx);
                    if (r.isPresent()) {
                        return r;
                    }
                }
            }
            return Optional.empty();
        }

        @Override
        public <C> Bake patch(Bake value, Patch<C> patch, C ctx) {
            return new Bake(Util.make(new HashMap<>(), (m) -> {
                for (var i : value.all.entrySet()) {
                    if(TypedIdentity.ALL.containsKey(i.getKey())) {
                        var h = TypedIdentity.ALL.get(i.getKey());
                        var r = h.data.handler.patch(TypeUtils.unsafeCoerce(i.getValue()), patch, ctx);
                        m.put(i.getKey(), r);
                    }else{
                        m.put(i.getKey(),i.getValue());
                    }
                }
            }));
        }
    });
    public static Endec<Bake> BAKE = new Endec<Bake>() {
        @Override
        public void encode(Serializer<?> serializer, Bake value) {
            try (var s = serializer.struct()) {
                for (var a : Identity.KEYS) {
                    if(TypedIdentity.ALL.containsKey(a)) {
                        var g = value.all.get(a);
//                        if (g == null) continue;
                        s.field(a, TypedIdentity.ALL.get(a).data.endec.optionalOf(), TypeUtils.unsafeCoerce(Optional.ofNullable(g)));
                    }else{
                        var g = value.all.get(a);
//                        if (g == null) continue;
                        s.field(a, NbtEndec.ELEMENT.optionalOf(), TypeUtils.unsafeCoerce(Optional.ofNullable(g)));
                    }
                }
            }
        }

        @Override
        public Bake decode(Deserializer<?> deserializer) {
            var m = new HashMap<String, Object>();
            var s = deserializer.struct();
            for (var a : Identity.KEYS) {
                if(TypedIdentity.ALL.containsKey(a)) {
                    var p = s.field(a, TypedIdentity.ALL.get(a).data.endec.optionalOf(), Optional.empty());
                    if (p.isEmpty()) continue;
                    m.put(a, p.get());
                }else{
                    var p = s.field(a,NbtEndec.ELEMENT.optionalOf());
                    if (p.isEmpty()) continue;
                    m.put(a, p.get());
                }
            }
            return new Bake(m);
        }
    };
}
