package at.gkgo.canon.api.material;

import net.minecraft.util.Identifier;

public abstract class Form<R> {
    public final Identifier id;
    public final RegistryResultKind<R> kind;

    protected Form(Identifier id, RegistryResultKind<R> kind) {
        this.id = id;
        this.kind = kind;
    }

    abstract R register(Material m);
}
