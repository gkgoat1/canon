package at.gkgo.canon.api.identity.typed;

import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public class Query <T,C>{
    public final Identifier id;

    private Query(Identifier id) {
        this.id = id;
    }
    private static Map<Identifier,Query<?,?>> ALL = new HashMap<>();
    public static <T,C> Query<T,C> get(Identifier id){
        return (Query<T, C>) ALL.computeIfAbsent(id,Query::new);
    }
}
