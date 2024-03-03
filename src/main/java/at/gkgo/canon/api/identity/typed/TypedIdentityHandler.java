package at.gkgo.canon.api.identity.typed;

import java.util.Optional;

public interface TypedIdentityHandler <T>{
    <Q,C>Optional<Q> query(T value,Query<Q,C> query, C ctx);
    <C> T patch(T value, Patch<C> patch, C ctx);
}
