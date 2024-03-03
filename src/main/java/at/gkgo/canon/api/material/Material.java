package at.gkgo.canon.api.material;

import net.minecraft.util.Identifier;

public class Material {
    public final Identifier id;

    public Material(Identifier id) {
        this.id = id;
    }
    public <T>void postRegister(Form<T> form,T result){

    }
}
