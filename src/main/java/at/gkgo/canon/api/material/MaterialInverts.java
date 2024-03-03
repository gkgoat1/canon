package at.gkgo.canon.api.material;

import net.minecraft.util.Pair;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class MaterialInverts {
    static final Map<Object, Pair<Material,Form<?>>> INVERT = new HashMap<>();
    public static @Nullable Pair<Material, Form<?>> invert(Object x){
        return INVERT.get(x);
    }
}
