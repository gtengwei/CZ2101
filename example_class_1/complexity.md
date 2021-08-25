# Time Complexity of Merge-Insertion Sort

Given `n` elements to sort, and threshold `s`,

## Merge portion

In normal merge sort, we have `log(n)` levels.

In the combined sort, we want to stop when an array has `<= s` elements. Hence, there are `log(s)` levels left if we stop at threshold s.

Therefore, we go through `log(n) - log(s)` levels for insertion sort. This is equivalent to `log(n/s)`.

We have `n-1` comparisons for each level. Hence, the time complexity for the insertion part of the algorithm is `O((n-1)log(n/s))` == `O(nlog(n/s))`. 

## Insertion portion

For the remaining levels, we have `s` elements in each array. There are `n/s` arrays left.

We know that the worst case time complexity for insertion sort is `O(n^2)`, where n is the number of elements.

Therefore, the time complexity of sorting the remaining arrays is `O((n/s) * s^2)` == `O(ns)`.

## Conclusion

Therefore, the time complexity of Merge-Insertion Sort is:
 `O(ns + nlog(n/s))`.

# Space Complexity of Merge-Insertion Sort

Only the merge portion of the algorithm takes up space.

The recursion stack takes up `O(log(n/s))` space. The auxiliary space to store the elements while merging the arrays is `O(n)`. The space to store the elements is much bigger than the recursion stack.

Therefore, in the worst case, the space complexity of the algorithm is simply:
`O(n)`.