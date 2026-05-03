import java.util.*;
class pract1{

    public static void printMyName(String name){
        System.out.println("Your Bullshit name is " + name);
        return;
    }
    public static void main(String[] args) {
        System.out.println("U gotta have write some Bullshit");
        Scanner sc = new Scanner(System.in);
        String name = sc.next();
        printMyName(name);
        sc.close();
    
    }

}
