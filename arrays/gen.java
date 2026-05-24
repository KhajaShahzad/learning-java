import java.util.*;

class Box<k> {
    k value;
}

public class gen {
    public static void main(String[] args) {
        Box<Integer> b = new Box<>();
        b.value=10;

        Box<String> s = new Box<>();
        s.value="shahzad";

        System.out.println(b.value + s.value);



    }
    
}
