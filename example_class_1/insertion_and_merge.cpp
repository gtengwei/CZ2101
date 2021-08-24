#include "merge_sort.cpp"
#include <iostream>

void insertion(int *array, int begin, int end) {
    // Sort the array from beginning index to end index.
    for (int i{begin+1}; i <= end; ++i) {
        int to_insert{array[i]};
        int j{i};
        while (j > begin && to_insert < array[j-1]) {
            // Swap the values
            array[j] = array[j-1];
            --j;
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
        insertion(array, begin, end);
    } else {
        // We do the typical merge sort here.
        int mid{begin + (end - begin) / 2};

        // Recursively call to merge sort the 2 halfs of the array.
        merge_and_insert(array, begin, mid, threshold);
        merge_and_insert(array, mid+1, end, threshold);

        // Then merge the arrays
        merge(array, begin, mid, end);
    }
}

int main() {
    int array[]{4, 7, 5, 6, 8, 4, 2, 0, 5, 2, 5, 7, -2, -5, 5, 3, 12, 34, 67, 2, 2, 1, 7};
    int size{sizeof(array)/sizeof(int)};
    merge_and_insert(array, 0, size, 4);

    for (int i{0}; i < size; ++i) {
        std::cout << array[i] << ' ';
    }
    std::cout << '\n';
    return 0;
}