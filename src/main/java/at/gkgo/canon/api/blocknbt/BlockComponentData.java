package at.gkgo.canon.api.blocknbt;

import io.wispforest.owo.serialization.Endec;

public class BlockComponentData <T>{
    public final Endec<T> endec;

    public BlockComponentData(Endec<T> endec) {
        this.endec = endec;
    }
}
