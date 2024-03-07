package at.gkgo.canon.api.identity.component;

import at.gkgo.canon.api.component.ComponentBehavior;
import io.wispforest.owo.serialization.Endec;

public class IdentityComponentData<T>{
    public final Endec<T> endec;
    public final ComponentBehavior<T> handler;

    public IdentityComponentData(Endec<T> endec, ComponentBehavior<T> handler) {
        this.endec = endec;
        this.handler = handler;
    }
}
