// This file contains an implementation for a
// merge sort.
#include "merge_sort.h"
#include <iostream>

int __merge(int* array, int begin, int mid, int end) {
    int comparisons{0};

    // Create a temporary array to store the sorted elements.
    int len{end - begin + 1};
    int* temp{new int[len]};
    int insert_index{0};

    // Loop through both sides of the array and insert accordingly.
    int left_index{begin};
    int right_index{mid+1};
    while (left_index <= mid && right_index <= end) {
        if (array[left_index] < array[right_index]) {
            temp[insert_index] = array[left_index];
            ++left_index;
        } else {
            temp[insert_index] = array[right_index];
            ++right_index;
        }
        ++insert_index;
        comparisons++;
    }

    // Check remaining elements for both sides.
    while (left_index <= mid) {
        temp[insert_index] = array[left_index];
        ++left_index;
        ++insert_index;
    }
    while (right_index <= end) {
        temp[insert_index] = array[right_index];
        ++right_index;
        ++insert_index;
    }

    // Store the temp elements into the array.
    for (int i{0}; i < len; ++i) {
        array[begin] = temp[i];
        ++begin;
    }

    // Make sure to delete the dynamic array.
    delete[] temp;
    return comparisons;
}

int merge_sort(int* array, int begin, int end) {
    // Terminating condition. If begin == end, then only
    // one element left, just return.
    if (begin >= end) {
        return 0;
    }

    int comparisons{0};
    // Calculate the middle of the array
    int mid{begin + (end - begin) / 2};

    // Recursively sort each half of the array.
    comparisons += merge_sort(array, begin, mid);
    comparisons += merge_sort(array, mid+1, end);

    // Then we gotta merge the arrays.
    comparisons += __merge(array, begin, mid, end);
    return comparisons;
}