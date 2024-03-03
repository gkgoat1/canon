package at.gkgo.canon.api.util;

public class TypeUtils {
    private TypeUtils(){}
    public static <T,U> U unsafeCoerce(T x){
        return (U)x;
    }
}
