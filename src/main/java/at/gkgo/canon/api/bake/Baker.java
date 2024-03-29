package at.gkgo.canon.api.bake;

import at.gkgo.canon.impl.bake.BakerRegistry;
import com.google.gson.JsonObject;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;

import java.nio.charset.StandardCharsets;

@FunctionalInterface
public interface Baker {
    default void bake(Identifier i, JsonObject b, ResourceType ty){
        bake(i,b.toString().getBytes(StandardCharsets.UTF_8),ty);
    }
    void bake(Identifier i, byte[] b, ResourceType ty);
    static Baker canon(){
        return BakerRegistry.DEFAULT_BAKER;
    }
}
