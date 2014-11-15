package Scriptozavr.Mafia.Helpers;

import java.util.ArrayList;

public class ArrayHelper {

    public static int getSum(int[] arr) {
        int sum = 0;
        for(int i: arr){
            sum += i;
        }
        return sum;
    }

    public static int countElements(int elem, int[] arr) {
        int count = 0;
        for(int i: arr){
            if (i == elem) count++;
        }
        return count;
    }

    public static int getMaxIndex(int[] arr) {
        int index = 0;
        int maxVotes = arr[0];
        for(int i = 0; i < arr.length; i++){
            if (arr[i] > maxVotes) {
                maxVotes = arr[i];
                index = i;
            }
        }
        return index;
    }
    public static String arrayToString(String message, ArrayList<Integer> arr) {
        StringBuilder resString = new StringBuilder();
        resString.append(message);
        for (int i: arr){
            resString.append(i + 1).append(", ");
        }
        resString.delete(resString.length() - 2, resString.length());
        return resString.toString();
    }
}
