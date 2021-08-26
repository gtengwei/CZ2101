#include "hybrid_sort.h"
#include "merge_sort.h"
#include <iostream>

int __insertion(int *array, int begin, int end) {
    int comparisons{0};
    // Sort the array from beginning index to end index.
    for (int i{begin+1}; i <= end; ++i) {
        int to_insert{array[i]};
        int j{i};
        comparisons++; // NOTE: At least one comparison everytime.
        while (j > begin && to_insert < array[j-1]) {
            // Swap the values
            array[j] = array[j-1];
            --j;
            comparisons++;
        }
        // Set element in the correct location
        array[j] = to_insert;
    }
    return comparisons;
}

// The idea is that past a certain size threshold,
// we will switch to using insertion sort to sort the array.
int hybrid_sort(int *array, int begin, int end, int threshold) {
    // Termination case.
    if (begin >= end) {
        return 0;
    }

    int comparisons{0};
    // Then we check for the threshold. If the size of the remaining array
    // is less or equal to the threshold, then we use insertion sort to sort the array.
    int size{begin-end};
    if (size <= threshold) {
        // We do insertion sort if under or equal to threshold.
        comparisons += __insertion(array, begin, end);

    } else {
        // We do the typical merge sort here.
        int mid{begin + (end - begin) / 2};

        // Recursively call to merge sort the 2 halfs of the array.
        comparisons += hybrid_sort(array, begin, mid, threshold);
        comparisons += hybrid_sort(array, mid+1, end, threshold);

        // Then merge the arrays
        comparisons += __merge(array, begin, mid, end);
    }
    return comparisons;
}