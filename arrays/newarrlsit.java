import java.util.*;

class newarrlsit{
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int size = sc.nextInt();
        int numbers[] = new int[size];
        

        ArrayList<Integer> list1 = new ArrayList<Integer>();

        for(int i = 0; i < size; i++){

            numbers[i]=sc.nextInt();
            list1.add(numbers[i]);
        }

        System.out.println("stored array" + list1);

    

    }

}