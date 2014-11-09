package Scriptozavr.Mafia.Helpers;

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

}
