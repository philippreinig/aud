package adhoc;

import java.util.*;

public class HeapSortActuallySelectionSort {
    private static Integer[] swapMax(Integer[] nums, int n){
        int max_index =  getMax(nums, n);
        int temp = nums[n-1];
        nums[n-1] = nums[max_index];
        nums[max_index] = temp;
        return nums;
    }

    private static int getMax(Integer[] nums, int n){
        int max_index = 0;
        for(int i = 0; i < n; i++){
            if (nums[i] > nums[max_index]) max_index = i;
        }
        return max_index;
    }
    private static Integer[] heapSort(Integer[] nums){
        Integer[] nums_sorted = nums;
        for(int i = nums.length; i > 0; i--){
            nums_sorted = heapSort(nums_sorted, i);
        }
        return nums_sorted;
    }

    private static Integer[] heapSort(Integer[] nums, int x){
        Integer[] new_nums = swapMax(nums, x);
        return new_nums;

    }
    public static void main(String[] args) {
        Integer[] nums = {8,7,5,6,5,3,4};
        System.out.println(Arrays.toString(heapSort(nums)));


    }
}
