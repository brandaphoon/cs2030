import java.util.List;
import java.util.ArrayList;
public class A {
    private final List<String> list;    
    A(int num) {
        List<String> s = new ArrayList<>();
        s.add("[A:" + num + "]");
        this.list = s;
    }
    A(List<String> list) {
        this.list = list;
    }
    public List<String> getList() {
        return this.list;
    }
    public A next(int n) {
        List<String> finalL = new ArrayList<>();
        for (String str: this.list) {
            finalL.add(str);
        }   
        A a = new A(n);
        for(String str: a.getList()) {
            finalL.add(str);
        }
        return new A(finalL);
    }
    @Override
    public String toString() {
        String s = "";
        for (String str: this.list) {
            s += str;
        }
        return s;
    }
}            
