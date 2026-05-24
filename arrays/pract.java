import java.util.*;

class pract{
    public static void main(String[] args) {
        // int[] shahzad = new int[5];
        // shahzad[0] = 23;
        // shahzad[4] = 20;
        // shahzad[2] = 24;
        // shahzad[1] = 34;
        // shahzad[3] = 80;
        // for(int i = 0; i < 5; i++){
        //     System.out.println(shahzad[i]);
        // }
        //}
        System.out.println("Enter The Size of array: ");
        Scanner sc = new Scanner(System.in);
        int size = sc.nextInt();
        int numbers[] = new int[size];
        System.out.println("Enter The element to store in array: ");

        for(int i = 0; i < size; i++){
            numbers[i] = sc.nextInt();
            
        }
        
        System.out.println("Enter number to Find: ");
        int x = sc.nextInt();

        
        for(int i = 0; i<numbers.length; i++){
            if(numbers[i] == x){
                System.out.println("Number found at Index" + i);
            }

        }
    }
}