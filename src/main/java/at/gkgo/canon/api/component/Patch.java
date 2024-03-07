package at.gkgo.canon.api.component;

import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public class Patch<C>{
    public final Identifier id;

    private Patch(Identifier id) {
        this.id = id;
    }
    private static Map<Identifier, Patch<?>> ALL = new HashMap<>();
    public static <C> Patch<C> get(Identifier id){
        return (Patch<C>) ALL.computeIfAbsent(id, Patch::new);
    }
}
