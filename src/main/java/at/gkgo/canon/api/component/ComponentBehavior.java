package at.gkgo.canon.api.component;

import java.util.Optional;
import java.util.function.Function;

public interface ComponentBehavior<T>{
    <Q,C>Optional<Q> query(T value,Query<Q,C> query, C ctx);
    <C> T patch(T value, Patch<C> patch, C ctx);
    T copy(T x);
    public static <T> ComponentBehavior<T> empty(Function<T,T> copier){
        return new ComponentBehavior<T>() {
            @Override
            public T copy(T x) {
                return copier.apply(x);
            }

            @Override
            public <Q, C> Optional<Q> query(T value, Query<Q, C> query, C ctx) {
                return Optional.empty();
            }

            @Override
            public <C> T patch(T value, Patch<C> patch, C ctx) {
                return value;
            }
        };
    }
}
