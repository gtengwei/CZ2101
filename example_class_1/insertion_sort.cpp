// This file contains an implementation for a 
// insertion sort.
#include <iostream>

void insertion_sort(int* array, int n) {
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
        }
        // Set the element to sort at the correct location.
        array[j] = to_sort;
    }
    
}

int main() {
    int arr[]{2, 4, 3, 5, 1};
    insertion_sort(arr, 5);
    for (auto i: arr) {
        std::cout << i << ' ';
    }
    std::cout << '\n';
}