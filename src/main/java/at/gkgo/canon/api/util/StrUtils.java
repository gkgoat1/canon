package at.gkgo.canon.api.util;

import com.google.common.base.Strings;

import java.util.PrimitiveIterator;
import java.util.function.Predicate;

public class StrUtils {
    private StrUtils(){}
    public static String mangle(String s, Predicate<Integer> isGood){
        StringBuilder a = new StringBuilder();
        final int length = s.length();
        for (int offset = 0; offset < length; ) {
            final int codepoint = s.codePointAt(offset);

            if(isGood.test(codepoint) && codepoint != '_'){
                a.append(Character.toString(codepoint));
            }else{
                a.append(String.format("_%d_", codepoint));
            }
            // do something with the codepoint

            offset += Character.charCount(codepoint);
        }
        return a.toString();
    }
    public static String demangle(String x){
        var t = x.split("_");
        StringBuilder s = new StringBuilder();
        boolean flag = false;
        for(var u: t){
            flag = !flag;
            if(flag){
                s.append(u);
            }else{
                var i = Integer.parseInt(u);
                s.append(Character.toString(i));
            }
        }
        return s.toString();
    }
}
