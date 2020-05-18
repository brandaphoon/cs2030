import java.util.function.Function;
import java.lang.Exception;
public class Voem<T> {
    T v;
    String msg;
    Boolean fail;
    private Voem(T v) {
        this.v = v;
        this.msg = "";
        this.fail = false;
    }
    private Voem(String s) {
        this.msg = s;
        this.v = null;
        this.fail = true;
    }
    static <T> Voem<T> ok(T v) {
        return new Voem<T>(v);
    }
    static <T> Voem<T> fail(String m) {
        return new Voem<T>(m);
    }
    <R> Voem<R> map(Function<? super T, ? extends R> func) {
        if (fail) {
            return Voem.fail(this.msg);
        } else {
            try {
                return Voem.ok(func.apply(v));
            } catch (Exception e) {
                return Voem.fail(e.getMessage());
            }
        }
    }

    <R> Voem<R> flatMap(Function<? super T, Voem<R>> func) {
        if (fail) {
            return Voem.fail(this.msg);
        } else {
            try {
                return func.apply(v);
            } catch (Exception e) {
                return Voem.fail(e.getMessage());
            }
        }
    }

    T orElse(T t) {
        if (fail) {
            return t;
        } else {
            return this.v;
        }
    }

    @Override
    public String toString() {
        if (fail) {
            return "Oops: " + this.msg;
        } else {
            return "Done: " + this.v;
        }
    }
}
