package at.gkgo.canon.impl.bake;

//import at.gkgo.core.Goatcore;

import at.gkgo.canon.api.bake.Baker;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public class BakerRegistry {
    public static Map<Identifier, BakedEntry> BAKED = new HashMap<>();
    public static Baker DEFAULT_BAKER = (i, j, k) -> BAKED.put(i,new BakedEntry(j,k));

}
