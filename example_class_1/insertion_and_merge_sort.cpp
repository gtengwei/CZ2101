#include "insertion_and_merge_sort.h"
#include "merge_sort.h"
#include <iostream>

int keyComparison_insertion_and_merge_sort = 0;

void __insertion(int *array, int begin, int end) {
    // Sort the array from beginning index to end index.
    for (int i{begin+1}; i <= end; ++i) {
        int to_insert{array[i]};
        int j{i};
        while (j > begin && to_insert < array[j-1]) {
            // Swap the values
            array[j] = array[j-1];
            --j;
            keyComparison_insertion_and_merge_sort++;

        }
        // Set element in the correct location
        array[j] = to_insert;
    }
}

// The idea is that past a certain size threshold,
// we will switch to using insertion sort to sort the array.
void merge_and_insert(int *array, int begin, int end, int threshold) {
    // Termination case.
    if (begin >= end) {
        return;
    }

    // Then we check for the threshold. If the size of the remaining array
    // is less or equal to the threshold, then we use insertion sort to sort the array.
    int size{begin-end};
    if (size <= threshold) {
        // We do insertion sort if under or equal to threshold.
        __insertion(array, begin, end);

    } else {
        // We do the typical merge sort here.
        int mid{begin + (end - begin) / 2};

        // Recursively call to merge sort the 2 halfs of the array.
        merge_and_insert(array, begin, mid, threshold);
        merge_and_insert(array, mid+1, end, threshold);

        // Then merge the arrays
        __merge(array, begin, mid, end);
    }
}

void print_insertion_and_merge_sort(){

    std::cout <<  "The number of key comparisons = " + std::to_string(keyComparison_insertion_and_merge_sort);
}
