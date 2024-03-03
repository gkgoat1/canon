package at.gkgo.canon.api.material;

public interface MaterialRegisterEvent {
    <T>void materialRegistered(Material material, Form<T> form,T result);
}
