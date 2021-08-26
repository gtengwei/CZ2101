#include "hybrid_sort.h"
#include "insertion_sort.h"
#include "merge_sort.h"

#include <iostream>
#include <random>
#include <chrono>    // For timing code
#include <fstream>   // For writing results to file

using namespace std::chrono;

// Generate CPU times for each sort, for different input sizes and different input types (ascending, descending, random)
void part_a() {
    // Create a random generator with seed 19937 (feel free to change this).
    static std::mt19937 mersenne{static_cast<std::mt19937::result_type>(19937)};

    static constexpr int ITERATIONS = 50;
    static constexpr int MAX_SIZE = 500;
    // Study how to determine an optimal value of S for best performance of this
    //hybrid algorithm on different input cases and input sizes.
    
    // Save results in result.csv.
    std::ofstream file{"results.csv"};
    if (!file) {
        std::cerr << "Error opening file!";
        return;
    }

    // Columns for the CSV file.
    file << "size,sort_type,input_type,micro_time,comparisons" << std::endl;

    // To do so, we test ascending, descending, and random inputs for both 
    // insertion sort and merge sort.
    // For each size, we test.
    for (int size{0}; size < MAX_SIZE; ++size) {
        // Create dynamic array.
        int* arr{new int[size]};

        microseconds totals[6]{};
        double comps[6]{};
        for (int iter{0}; iter < ITERATIONS; ++iter) {
            // Test ascending.
            for (int i{0}; i < size; ++i) {
                arr[i] = i;
            }
            auto start = high_resolution_clock::now();
            comps[0] += insertion_sort(arr, size);
            auto end = high_resolution_clock::now();
            totals[0] += duration_cast<microseconds>(end-start);

            for (int i{0}; i < size; ++i) {
                arr[i] = i;
            }
            start = high_resolution_clock::now();
            comps[1] += merge_sort(arr, 0, size-1);
            end = high_resolution_clock::now();
            totals[1] += duration_cast<microseconds>(end-start);

            // Test descending
            for (int i{size-1}, index{0}; i >= 0; --i, ++index) {
                arr[index] = i;
            }
            start = high_resolution_clock::now();
            comps[2] += insertion_sort(arr, size);
            end = high_resolution_clock::now();
            totals[2] += duration_cast<microseconds>(end-start);

            for (int i{size-1}, index{0}; i >= 0; --i, ++index) {
                arr[index] = i;
            }
            start = high_resolution_clock::now();
            comps[3] += merge_sort(arr, 0, size-1);
            end = high_resolution_clock::now();
            totals[3] += duration_cast<microseconds>(end-start);

            // Test random
            std::uniform_int_distribution<> dist{0, 1000};
            for (int i{0}; i < size; ++i) {
                arr[i] = dist(mersenne);
            }
            start = high_resolution_clock::now();
            comps[4] += insertion_sort(arr, size);
            end = high_resolution_clock::now();
            totals[4] += duration_cast<microseconds>(end-start);

            for (int i{0}; i < size; ++i) {
                arr[i] = dist(mersenne);
            }
            start = high_resolution_clock::now();
            comps[5] += merge_sort(arr, 0, size-1);
            end = high_resolution_clock::now();
            totals[5] += duration_cast<microseconds>(end-start);
        }
        file << size << ',' << "insertion" << ',' << "ascending"  << ',' 
            << (static_cast<double>(totals[0].count()) / ITERATIONS) << ',' << (comps[0] / ITERATIONS) << std::endl;
        file << size << ',' << "merge"     << ',' << "ascending"  << ',' 
            << (static_cast<double>(totals[1].count()) / ITERATIONS) << ',' << (comps[1] / ITERATIONS) << std::endl;
        file << size << ',' << "insertion" << ',' << "descending" << ',' 
            << (static_cast<double>(totals[2].count()) / ITERATIONS) << ',' << (comps[2] / ITERATIONS) << std::endl;
        file << size << ',' << "merge"     << ',' << "descending" << ',' 
            << (static_cast<double>(totals[3].count()) / ITERATIONS) << ',' << (comps[3] / ITERATIONS) << std::endl;
        file << size << ',' << "insertion" << ',' << "random"     << ',' 
            << (static_cast<double>(totals[4].count()) / ITERATIONS) << ',' << (comps[4] / ITERATIONS) << std::endl;
        file << size << ',' << "merge"     << ',' << "random"     << ',' 
            << (static_cast<double>(totals[5].count()) / ITERATIONS) << ',' << (comps[5] / ITERATIONS) << std::endl;
        delete[] arr;
    }
    file.close();
}

int main() {
    part_a();
    return 0;
}
