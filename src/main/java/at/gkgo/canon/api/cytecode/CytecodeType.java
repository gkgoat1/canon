package at.gkgo.canon.api.cytecode;

import io.wispforest.owo.serialization.Endec;
import io.wispforest.owo.serialization.endec.BuiltInEndecs;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

public class CytecodeType <R,C extends Cytecode<R>>{
    public final Endec<C> endec;

    public CytecodeType(Endec<C> endec) {
        this.endec = endec;
    }
    public static Registry<CytecodeType<?,?>> TYPES = FabricRegistryBuilder.<CytecodeType<?,?>>createSimple(RegistryKey.ofRegistry(Identifier.of("canon","cytecode"))).buildAndRegister();
public static Endec<Cytecode<?>> ENDEC = Endec.dispatched(t -> t.endec, Cytecode::type,BuiltInEndecs.ofRegistry(TYPES));
}
