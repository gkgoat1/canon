package at.gkgo.canon.api.pack;

import net.minecraft.util.collection.Int2ObjectBiMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Packer {
    private final Int2ObjectBiMap<String> all = Int2ObjectBiMap.create(2);
    public int get(String x){
        if(all.contains(x)){
            return all.getRawId(x);
        }else{
            return all.add(x);
        }
    }
    public String getKey(String x){
        return Integer.toString(get(x),36);
    }
}
