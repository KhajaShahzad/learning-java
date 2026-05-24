import java.util.*;

public class Hashing {
    public static void main(String[] args) {
        
        HashSet<Integer> set = new HashSet<>();

        set.add(1);
        set.add(3);
        set.add(8);

        //contains
        if(set.contains(1));
        System.out.println("1 found");

        if(!set.contains(2));
        System.out.println("2 not found");

    }
    
}

import java.util.*;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();

        TreeSet<Integer> set = new TreeSet<>();

        for(int i = 0; i < n; i++) {
            int num = sc.nextInt();
            set.add(num);  // duplicates automatically removed
            Collections.sort(num);
        }

        System.out.println(set);
    }
}