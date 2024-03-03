package at.gkgo.canon.api.material;

import at.gkgo.canon.api.trigger.Trigger;
import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class MaterialRegistries {
    private static final Map<Identifier,Material> MATERIALS = new HashMap<>();
    private static final Map<Identifier,Form<?>> FORMS = new HashMap<>();
    public static  @Nullable Form<?> getForm(Identifier id){
        return FORMS.get(id);
    }
    public static  @Nullable Material getMaterial(Identifier id){
        return MATERIALS.get(id);
    }
    public static Codec<Material> MATERIAL_CODEC = Identifier.CODEC.xmap(MaterialRegistries::getMaterial,(a) -> a.id);
    public static Codec<Form<?>> FORM_CODEC =  Identifier.CODEC.xmap(MaterialRegistries::getForm,(a) -> a.id);
    public static Trigger TRIGGER = Trigger.of(Identifier.of("canon","material"));
    public static Event<MaterialRegisterEvent> EVENT = EventFactory.createArrayBacked(MaterialRegisterEvent.class,(l) -> new MaterialRegisterEvent() {
        @Override
        public <T> void materialRegistered(Material material, Form<T> form, T result) {
            for(var m: l){
                m.materialRegistered(material, form, result);
            }
        }
    });
    private static<T> void register(Form<T> form, Material material){
        TRIGGER.ensure();
        var t = form.register(material);
        for(var o: form.kind.getAll(t)){
            MaterialInverts.INVERT.put(o, new Pair<>(material,form));
        }
        material.postRegister(form,t);
        EVENT.invoker().materialRegistered(material, form,t);
    }
    public static<M extends Material> M registerMaterial(M material){
        for(var f: FORMS.values()){
            register(f,material);
        }
        MATERIALS.put(material.id,material);
        return material;
    }
    public static<F extends Form<?>> F registerForm(F form){
        for(var m: MATERIALS.values()){
            Form<?> f = form;
            register(f,m);
        }
        FORMS.put(form.id,form);
        return form;
    }
}
