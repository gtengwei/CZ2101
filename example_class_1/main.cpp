#include "insertion_and_merge_sort.h"
#include "insertion_sort.h"
#include "merge_sort.h"
#include <iostream>

// TODO: Part a) and b)

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