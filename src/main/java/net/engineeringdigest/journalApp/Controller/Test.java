package net.engineeringdigest.journalApp.Controller;

public class Test {
    public static void main (String[] args) {
        int[] array1 = {5,6,8,3,9,7};
        int [] array2 = {5,8,7};

        System.out.println("Case1 Result: " + validate(array1, array2));

        int [] array3 = {8,6};
        System.out.println("Case2 Result: " + validate(array1, array3));

    }
    public static boolean validate(int[] array1, int[] array2){
        int i = 0;
        int j = 0;
        while (i < array1.length && j < array2.length) {
            if (array1[i] == array2[j]) {
                j++;
            }
            i++;
        }
        return j == array2.length;
    }
}
