#include "insertion_and_merge_sort.h"
#include "insertion_sort.h"
#include "merge_sort.h"

#include <iostream>
#include <random>
#include <chrono>
#include <fstream>

using namespace std::chrono;

// TODO: Remove global counter and let each sort count its own number of comparisons.
// TODO: Add comparisons column in the CSV file.

// Generate CPU times for each sort, for different input sizes and different input types (ascending, descending, random)
void part_a() {
    static std::mt19937 mersenne{static_cast<std::mt19937::result_type>(19937)};
    static constexpr int ITERATIONS = 50;
    static constexpr int MAX_SIZE = 500;
    // Study how to determine an optimal value of S for best performance of this
    //hybrid algorithm on different input cases and input sizes.
    
    // Save results in new file.
    std::ofstream file{"results.csv"};
    if (!file) {
        std::cerr << "Error opening file!";
        return;
    }
    file << "size,sort_type,input_type,micro_time" << std::endl;

    // To do so, we test ascending, descending, and random inputs for both 
    // insertion sort and merge sort.
    // For each size, we test.
    for (int size{0}; size < MAX_SIZE; ++size) {
        // Create dynamic array.
        int* arr{new int[size]};

        microseconds totals[6]{};
        for (int iter{0}; iter < ITERATIONS; ++iter) {
            // Test ascending.
            for (int i{0}; i < size; ++i) {
                arr[i] = i;
            }
            auto start = high_resolution_clock::now();
            insertion_sort(arr, size);
            auto end = high_resolution_clock::now();
            totals[0] += duration_cast<microseconds>(end-start);

            for (int i{0}; i < size; ++i) {
                arr[i] = i;
            }
            start = high_resolution_clock::now();
            merge_sort(arr, 0, size-1);
            end = high_resolution_clock::now();
            totals[1] += duration_cast<microseconds>(end-start);

            // Test descending
            for (int i{size-1}, index{0}; i >= 0; --i, ++index) {
                arr[index] = i;
            }
            start = high_resolution_clock::now();
            insertion_sort(arr, size);
            end = high_resolution_clock::now();
            totals[2] += duration_cast<microseconds>(end-start);

            for (int i{size-1}, index{0}; i >= 0; --i, ++index) {
                arr[index] = i;
            }
            start = high_resolution_clock::now();
            merge_sort(arr, 0, size-1);
            end = high_resolution_clock::now();
            totals[3] += duration_cast<microseconds>(end-start);

            // Test random
            std::uniform_int_distribution<> dist{0, 1000};
            for (int i{0}; i < size; ++i) {
                arr[i] = dist(mersenne);
            }
            start = high_resolution_clock::now();
            insertion_sort(arr, size);
            end = high_resolution_clock::now();
            totals[4] += duration_cast<microseconds>(end-start);

            for (int i{0}; i < size; ++i) {
                arr[i] = dist(mersenne);
            }
            start = high_resolution_clock::now();
            merge_sort(arr, 0, size-1);
            end = high_resolution_clock::now();
            totals[5] += duration_cast<microseconds>(end-start);
        }
        file << size << ',' << "insertion" << ',' << "ascending"  << ',' << (static_cast<double>(totals[0].count()) / ITERATIONS) << std::endl;
        file << size << ',' << "merge"     << ',' << "ascending"  << ',' << (static_cast<double>(totals[1].count()) / ITERATIONS) << std::endl;
        file << size << ',' << "insertion" << ',' << "descending" << ',' << (static_cast<double>(totals[2].count()) / ITERATIONS) << std::endl;
        file << size << ',' << "merge"     << ',' << "descending" << ',' << (static_cast<double>(totals[3].count()) / ITERATIONS) << std::endl;
        file << size << ',' << "insertion" << ',' << "random"     << ',' << (static_cast<double>(totals[4].count()) / ITERATIONS) << std::endl;
        file << size << ',' << "merge"     << ',' << "random"     << ',' << (static_cast<double>(totals[5].count()) / ITERATIONS) << std::endl;
        delete[] arr;
    }
    file.close();
}

int main() {
    part_a();
    return 0;
}
