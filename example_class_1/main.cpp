#include "insertion_and_merge_sort.h"
#include "insertion_sort.h"
#include "merge_sort.h"
#include "global.h"
#include <iostream>
#include <windows.h>
#include <string>
#include <iomanip>
// TODO: Part a) and b)



int main() {

    int n,i,random,threshold;
    LARGE_INTEGER counterStart, counterEnd, frequency;
    QueryPerformanceFrequency(&frequency);
    double time;


    // take input from user for number of elements
    std::cout << "please enter the number of elements : ";
    std::cin >> n;
    // declare array size of n
    int array[n];

    std::cout << "please enter your threshold value: ";
    std::cin >> threshold;

    for (i=0;i<n;i++){
        random = rand();
        array[i] = random;
    }

    //int array[]{4, 7, 5, 6, 8, 4, 2, 0, 5, 2, 5, 7, -2, -5, 5, 3, 12, 34, 67, 2, 2, 1, 7};

    int size{sizeof(array)/sizeof(int)};



    //merge_and_insert hybrid performance

    QueryPerformanceCounter(&counterStart);
    merge_and_insert(array, 0, size, threshold);
    QueryPerformanceCounter(&counterEnd);


    for (int i{0}; i < size; ++i) {
        std::cout << array[i] << ' ';
    }

    std::cout << '\n';
    //print the key comparisons
    std::cout <<  "The number of key comparisons = " + std::to_string(comparison);
    std::cout << '\n';



    time = (double)((double)(counterEnd.QuadPart - counterStart.QuadPart)/(double)frequency.QuadPart);

    //change from seconds to milliseconds
    time *= 1000;



    std::ostringstream ss;
    ss.precision(8);
    ss << std::fixed << time;
    std::cout << "The time it took for merge_and_insert is " + ss.str() + " milliseconds";

    //reset the counter
    comparison = 0;

    std::cout << '\n';
    std::cout << '\n';

    //original merge_sort implementation

    QueryPerformanceCounter(&counterStart);
    merge_sort(array, 0, size);
    QueryPerformanceCounter(&counterEnd);

    for (int i{0}; i < size; ++i) {
        std::cout << array[i] << ' ';
    }

    std::cout << '\n';
    //print the key comparisons
    std::cout <<  "The number of key comparisons = " + std::to_string(comparison);
    std::cout << '\n';

    time = (double)((double)(counterEnd.QuadPart - counterStart.QuadPart)/(double)frequency.QuadPart);

    //change from seconds to milliseconds
    time *= 1000;

    std::ostringstream sd;
    sd.precision(8);
    sd << std::fixed << time;

    std::cout << "The time it took for merge_sort is " + sd.str() + " milliseconds";

    return 0;
}
