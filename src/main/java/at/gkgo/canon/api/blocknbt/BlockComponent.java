package at.gkgo.canon.api.blocknbt;

import at.gkgo.canon.api.component.ComponentBehavior;
import at.gkgo.canon.api.identity.Identity;
import at.gkgo.canon.api.identity.Bake;
import io.wispforest.owo.serialization.format.nbt.NbtEndec;
import net.minecraft.nbt.NbtCompound;

import java.util.HashMap;
import java.util.Map;

public class BlockComponent<T> {
    public static Map<String, BlockComponent<?>> ALL = new HashMap<>();
    public final BlockComponentData<T> data;
    public final String name;


    public BlockComponent(BlockComponentData<T> data, String name) {
        this.data = data;
        this.name = name;
    }
    public BlockComponent<T> register(){
        ALL.put(name,this);
        Identity.KEYS.add(name);
        return this;
    }
    public static void init(){

    }
    public static BlockComponent<Bake> IDENTITY = new BlockComponent<Bake>(new BlockComponentData<Bake>(Bake.BAKE,Bake.DATA.handler),"canon:identity").register();
    public static BlockComponent<NbtCompound> CUSTOM_DATA = new BlockComponent<>(new BlockComponentData<>(NbtEndec.COMPOUND, ComponentBehavior.empty((a) -> a.copy())),"minecraft:custom_data").register();
}
