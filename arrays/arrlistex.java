import java.util.*;
public class arrlistex {
    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<Integer>();

        list.add(20);
        list.add(10);
        list.add(60);
        list.add(90);
        list.add(40);
        list.add(50);


        System.out.println(list);

        //get element

        int element = list.get(0);
        System.out.println(element);
        

        // add e1 in btween

        list.add(1,2);
        System.out.println("Added new array list: " + list);

        //size of array in number

        int size = list.size();
        System.out.println("Size of array: " + size);

        //loops

        // for(int i = 0; i < list.size(); i++){
        // //     System.out.println(list.get(i));

        // }

        Collections.sort(list);
        System.out.println("Ascending Order: " + list);

        Collections.sort(list, Collections.reverseOrder());
        System.out.println("Descending Order: " + list);


    }
    
}
