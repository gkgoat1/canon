package at.gkgo.canon.api.identity.typed;

import io.wispforest.owo.serialization.Endec;

import java.util.function.Supplier;

public class TypedIdentityData <T>{
    public final Endec<T> endec;
    public final TypedIdentityHandler<T> handler;

    public TypedIdentityData(Endec<T> endec, TypedIdentityHandler<T> handler) {
        this.endec = endec;
        this.handler = handler;
    }
}
