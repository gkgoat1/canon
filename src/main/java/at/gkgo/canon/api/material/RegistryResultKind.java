package at.gkgo.canon.api.material;

import java.util.Set;

public interface RegistryResultKind <R>{
    Set<Object> getAll(R result);
}
