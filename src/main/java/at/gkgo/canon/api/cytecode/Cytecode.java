package at.gkgo.canon.api.cytecode;

public interface Cytecode <R>{
    R execute(CytecodeContext ctx);
    CytecodeType<R,?> type();
}
