// This file contains an implementation for a
// insertion sort.
#include "insertion_sort.h"
#include <iostream>

int insertion_sort(int* array, int n) {
    int comparisons{0};
    // Sorts an array of length n using
    // insertion sort algorithm.
    for (int i{1}; i < n; ++i) {
        int to_sort{array[i]};
        int j{i};
        while (j > 0 && to_sort < array[j-1]) {
            // Check if current element is less than
            // previous element.
            // If yes, then swap.
            array[j] = array[j-1];
            --j;
            comparisons++;
        }
        // Set the element to sort at the correct location.
        array[j] = to_sort;
    }
    return comparisons;
}