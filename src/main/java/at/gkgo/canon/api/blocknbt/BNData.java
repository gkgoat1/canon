package at.gkgo.canon.api.blocknbt;

import at.gkgo.canon.api.identity.typed.Bake;
import at.gkgo.canon.api.util.TypeUtils;
import io.wispforest.owo.serialization.Deserializer;
import io.wispforest.owo.serialization.Endec;
import io.wispforest.owo.serialization.Serializer;
import io.wispforest.owo.serialization.endec.StructEndecBuilder;
import io.wispforest.owo.serialization.format.nbt.NbtDeserializer;
import io.wispforest.owo.serialization.format.nbt.NbtEndec;
import io.wispforest.owo.serialization.format.nbt.NbtSerializer;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class BNData {
//    public final Bake normal;
    private final Map<String,Object> components;

    public BNData(Map<String,Object> components) {
//        this.normal = normal;
        this.components = new HashMap<>(components);
    }
    public <T> @Nullable T get(BlockComponent<T> component){
        return TypeUtils.unsafeCoerce(components.get(component.name));
    }
    public <T> void put(BlockComponent<T> component, T value){
        components.put(component.name, value);
    }
    public <T> void remove(BlockComponent<T> component){
        components.remove(component.name);
    }
    public static Endec<BNData> ENDEC = new Endec<BNData>() {
        @Override
        public void encode(Serializer<?> serializer, BNData value) {
            var s = serializer.struct();
            for(var i: BlockComponent.ALL.entrySet()){
                s.field(i.getKey(),i.getValue().data.endec.optionalOf(), TypeUtils.unsafeCoerce(Optional.ofNullable(value.components.get(i.getKey()))));
            }
        }

        @Override
        public BNData decode(Deserializer<?> deserializer) {
            var m = new HashMap<String,Object>();
            var s = deserializer.struct();
            for(var a: BlockComponent.ALL.entrySet()){
                var o = s.field(a.getKey(),a.getValue().data.endec.optionalOf(),Optional.empty());
                if(o.isEmpty())continue;
                m.put(a.getKey(),o.get());
            }
            return new BNData(m);
        }
    };
    public NbtCompound toNbt(){
        var x = ENDEC.encodeFully(NbtSerializer::of,this);
        if(x instanceof NbtCompound c){
            return c;
        }
        return new NbtCompound();
    }
    public static BNData fromNbt(NbtCompound x){
        return ENDEC.decodeFully(NbtDeserializer::of,x);
    }
    public void writeNbt(NbtCompound c){
        var d = toNbt();
        for(var k: d.getKeys()){
            c.put(k,d.get(k));
        }
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof BNData a){
            return toNbt().equals(a.toNbt());
        }
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return toNbt().hashCode();
    }
}
