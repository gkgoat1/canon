package at.gkgo.canon.api.identity;

import net.minecraft.nbt.NbtCompound;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Identity {
    public static Set<String> KEYS = new HashSet<>();
    public static void copy(NbtCompound src, NbtCompound dest){
        for(var t: KEYS){
            if(src.contains(t)){
                dest.put(t, Objects.requireNonNull(src.get(t)).copy());
            }
        }
    }
}
