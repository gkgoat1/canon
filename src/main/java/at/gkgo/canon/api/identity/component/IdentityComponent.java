package at.gkgo.canon.api.identity.component;

import at.gkgo.canon.api.identity.Identity;

import java.util.HashMap;
import java.util.Map;

public class IdentityComponent<T> {
    public static Map<String, IdentityComponent<?>> ALL = new HashMap<>();
    public final IdentityComponentData<T> data;
    public final String name;


    public IdentityComponent(IdentityComponentData<T> data, String name) {
        this.data = data;
        this.name = name;
    }
    public IdentityComponent<T> register(){
        ALL.put(name,this);
        Identity.KEYS.add(name);
        return this;
    }
}
