package at.gkgo.canon.api.blocknbt;

import at.gkgo.canon.api.component.ComponentBehavior;
import io.wispforest.owo.serialization.Endec;

public class BlockComponentData <T>{
    public final Endec<T> endec;
    public final ComponentBehavior<T> behavior;

    public BlockComponentData(Endec<T> endec, ComponentBehavior<T> behavior) {
        this.endec = endec;
        this.behavior = behavior;
    }
}
