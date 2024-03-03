package at.gkgo.canon.api.trigger;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public class Trigger {
    public final Identifier id;
    private boolean done = false;

    private Trigger(Identifier id) {
        this.id = id;
    }
    public boolean ensure(){
        if(done){
            return true;
        }
        done = true;
        for(var e: FabricLoader.getInstance().getEntrypoints(id.toString(), EventBusEntrypoint.class)){
            e.attachEvents(this);
        }
        return false;
    }
    private static final Map<Identifier,Trigger> ALL = new HashMap<>();
    public static Trigger of(Identifier id){
        while(true) {
            if (ALL.containsKey(id)) {
                return ALL.get(id);
            }
            ALL.put(id,new Trigger(id));
        }
    }
}
